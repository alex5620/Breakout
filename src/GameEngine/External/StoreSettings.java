package GameEngine.External;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class StoreSettings {
    private Properties prop;
    private FileOutputStream output;
    private FileInputStream in;
    public void setName(String name)
    {
        checkProperties();
        prop.setProperty("name", name);
        storeSettings();
    }
    public String getName()
    {
        String name;
        checkProperties();
        try {
            in=new FileInputStream("settings.properties");
            prop.load(in);
            in.close();
            name=prop.getProperty("name", "Player");
        }
        catch (Exception e) {
            name="Player";
        }
        return name;
    }
    public void setScore(int score)
    {
        checkProperties();
        prop.setProperty("score", Integer.toString(score));
        storeSettings();
    }
    public int getScore()
    {
        int score;
        checkProperties();
        try {
            in=new FileInputStream("settings.properties");
            prop.load(in);
            in.close();
            score=Integer.parseInt(prop.getProperty("score", "0"));
        }
        catch (Exception e) {
            score=0;
        }
        return score;
    }
    public void setInstructionsPresented(boolean value)
    {
        checkProperties();
        prop.setProperty("instructionsPresented", Boolean.toString(value));
        storeSettings();
    }
    public boolean getInstructionsPresented()
    {
        boolean presented;
        checkProperties();
        try {
            in=new FileInputStream("settings.properties");
            prop.load(in);
            in.close();
            presented=Boolean.parseBoolean(prop.getProperty("instructionsPresented", "false"));
        }
        catch (Exception e) {
            presented=false;
        }
        return presented;
    }
    public void setIsEasy(boolean value)
    {
        checkProperties();
        prop.setProperty("isEasy", Boolean.toString(value));
        storeSettings();
    }
    public boolean getIsEasy()
    {
        boolean isEasy;
        checkProperties();
        try {
            in=new FileInputStream("settings.properties");
            prop.load(in);
            in.close();
            isEasy=Boolean.parseBoolean(prop.getProperty("isEasy", "true"));
        }
        catch (Exception e) {
            isEasy = true;
        }
        return isEasy;
    }
    private void checkProperties()
    {
        if(prop==null)
        {
            prop=new Properties();
        }
    }
    private void storeSettings()
    {
        try {
            output=new FileOutputStream("settings.properties");
            prop.store(output, null);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
