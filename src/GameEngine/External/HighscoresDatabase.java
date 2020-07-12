package GameEngine.External;

import java.sql.*;
import java.util.LinkedHashMap;

public class HighscoresDatabase {
    private Connection con;
    private void getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:highscores.db");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void initialize() {
        try {
            getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
            if (!res.next()) {
                Statement stmt2 = con.createStatement();
                stmt2.execute("CREATE TABLE users(name varchar(20)," + "score integer," + "primary key(name));");
                PreparedStatement prep;
                for (int i = 10; i > 0; --i) {
                    prep = con.prepareStatement("INSERT INTO users(name, score) values(?,?);");
                    prep.setString(1, "Robot" + (10 - i + 1));
                    prep.setInt(2, i * 600);
                    prep.execute();
                }
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeTheDatabase();
        }
    }
    public LinkedHashMap<String, Integer> getUsers() {
        try {
            LinkedHashMap<String, Integer> hashMap=new LinkedHashMap<>();
            getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM users ORDER BY score DESC");
            while(res.next())
            {
                hashMap.put(res.getString("name"), res.getInt("score"));
            }
            stmt.close();
            return hashMap;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            closeTheDatabase();
        }
    }
    public void addUser(String name, int score) {
        try {
            PreparedStatement prep = con.prepareStatement("INSERT INTO users values(?,?);");
            prep.setString(1, name);
            prep.setInt(2, score);
            prep.execute();
            prep.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateUser(String name, int newScore){
        try {
          PreparedStatement stmt =con.prepareStatement("SELECT score From users where name=?");
          stmt.setString(1, name);
          ResultSet rs = stmt.executeQuery();
          int actualScore = rs.getInt("score");
          if (newScore > actualScore) {
              PreparedStatement prep = con.prepareStatement("UPDATE users SET score= ? WHERE name=?;");
              prep.setInt(1, newScore);
              prep.setString(2, name);
              prep.execute();
          }
          stmt.close();
      }
      catch(Exception e) {
          e.printStackTrace();
      }
    }
    public int recordsNumber() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select count(*) from users");
            rs.next();
            int count = rs.getInt(1);
            stmt.close();
            return count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    public void deleteAllUsers() {
        try {
            getConnection();
            PreparedStatement prep = con.prepareStatement("DELETE from users;");
            prep.execute();
            for(int i=10;i>0;--i) {
                prep = con.prepareStatement("INSERT INTO users(name, score) values(?,?);");
                prep.setString(1, "Robot"+(10-i+1));
                prep.setInt(2, i*600);
                prep.execute();
            }
            prep.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeTheDatabase();
        }
    }
    public boolean checkIfUserExists(String searchedName){
       try {
           boolean exists = false;
           PreparedStatement stmt = con.prepareStatement("select * from users");
           ResultSet r = stmt.executeQuery();
           String name;
           while (r.next()) {
               name = r.getString("name");
               if (name.equals(searchedName)) {
                   exists = true;
                   break;
               }
           }
           stmt.close();
           return exists;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return false;
       }
    }
    public int minScore(boolean cond) {
        try {
            if(cond) {
                getConnection();
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT min(score) FROM users");
            int minValue = 0;
            if (rs.next()) {
                minValue = rs.getInt(1);
            }
            st.close();
            return minValue;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            if(cond)
                closeTheDatabase();
        }
    }
    public String minName() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM users where score=(SELECT min(score) FROM users)");
            String nume = "";
            if (rs.next()) {
                nume = rs.getString(1);
            }
            st.close();
            return nume;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Player";
        }
    }
    public void deleteLowest(int score) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM users WHERE score = ? and name =?");
            st.setInt(1, score);
            st.setString(2, minName());
            st.executeUpdate();
            st.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateTable(String nume, int score){
        try {
            getConnection();
            if (checkIfUserExists(nume)) {
                updateUser(nume, score);
            } else {
                if (recordsNumber() < 10) {
                    addUser(nume, score);
                } else {
                    if (minScore(false) < score) {
                        deleteLowest(minScore(false));
                        addUser(nume, score);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeTheDatabase();
        }
    }
    private void closeTheDatabase()
    {
        try {
            if(con.isClosed()==false) {
                con.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}