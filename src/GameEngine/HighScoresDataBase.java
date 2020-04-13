package GameEngine;

import java.sql.*;

public class HighScoresDataBase {
    private static Connection con;
    private static boolean hasData=false;
    public ResultSet displayUsers() {
        try {
            if (con == null) {
                getConnection();
            }
            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT * FROM user ORDER BY score DESC");
            return res;
        }
        catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    private void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:highscores.db");
        initialise();
    }

    private void initialise() throws SQLException {
        if(!hasData)
        {
            hasData=true;
            Statement state=con.createStatement();
            ResultSet res=state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
            if(!res.next())
            {
                Statement state2=con.createStatement();
                state2.execute("CREATE TABLE user(name varchar(30),"+"score integer,"+ "primary key(name));");
                PreparedStatement prep;
                for(int i=10;i>0;--i) {
                    prep = con.prepareStatement("INSERT INTO user(name, score) values(?,?);");
                    prep.setString(1, "Robot"+(10-i+1));
                    prep.setInt(2, i*50);
                    prep.execute();
                }
            }
        }
    }
    public void addUser(String firstName, int score) throws SQLException, ClassNotFoundException {
        if(con==null)
        {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?);");
        prep.setString(1, firstName);
        prep.setInt(2, score);
        prep.execute();
    }
    public void updateUser(String name, int newScore) throws SQLException, ClassNotFoundException {
        if(con==null)
        {
            getConnection();
        }
        String qry = "SELECT score From user where name=?";
        PreparedStatement st = (PreparedStatement) con.prepareStatement(qry);
        st.setString(1, name);
        ResultSet rs =  st.executeQuery();
        int actualScore = rs.getInt("score");
        if (newScore > actualScore) {
            PreparedStatement prep = con.prepareStatement("UPDATE user SET score= ? WHERE name=?;");
            prep.setInt(1, newScore);
            prep.setString(2, name);
            prep.execute();
        }
    }
    public int playersNumber() throws SQLException, ClassNotFoundException {
        if(con==null)
        {
            getConnection();
        }
        Statement stmt = con.createStatement();
        String query = "select count(*) from user";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt(1);
        return count;
    }
    public void deleteAllUser() {
        try {
            if(con==null)
            {
                getConnection();
            }
            PreparedStatement prep = con.prepareStatement("DELETE from user;");
            prep.execute();

            for(int i=10;i>0;--i) {
                prep = con.prepareStatement("INSERT INTO user(name, score) values(?,?);");
                prep.setString(1, "Robot"+(10-i+1));
                prep.setInt(2, i*50);
                prep.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e) {
        }
    }
    public boolean checkIfUserExists(String Name) throws SQLException, ClassNotFoundException {
        if(con==null) {
            getConnection();
        }
        boolean exists = false;
        PreparedStatement st = con.prepareStatement("select * from user");
        ResultSet r=st.executeQuery();
        String usernameCounter;
        while(r.next())
        {
            usernameCounter =  r.getString("name");
            if(usernameCounter.equals(Name))
                exists = true;
        }
        return exists;
    }
    public int minScore() {
        try {
            if (con == null) {
                getConnection();
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT min(score) FROM user");
            int minValue = 0;
            if (rs.next()) {
                minValue = rs.getInt(1);
            }
            return minValue;
        }
        catch (SQLException | ClassNotFoundException e) {
            return 0;
        }
    }
    public String minName() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT name FROM user where score=(SELECT min(score) FROM user)");
        String nume="";
        if (rs.next()) {
            nume = rs.getString(1);
        }
        return nume;
    }
    public void deleteLowest(int score) throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement st = con.prepareStatement("DELETE FROM user WHERE score = ? and name =?");
        st.setInt(1, score);
        st.setString(2, minName());//mi-ar fi sters toate instantele ce au scorul minim
        st.executeUpdate();
    }
    public void updateTable(String nume, int score){
        try {
            if (checkIfUserExists(nume)) {
                updateUser(nume, score);
            } else {
                if (playersNumber() < 10) {
                    addUser(nume, score);
                } else {
                    if (minScore() < score) {
                        deleteLowest(minScore());
                        addUser(nume, score);
                    }
                }
            }
        }
        catch (SQLException | ClassNotFoundException e) {
        }
    }
}