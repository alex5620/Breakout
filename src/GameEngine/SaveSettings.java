package GameEngine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SaveSettings {
    private Properties prop;
    private FileOutputStream output;
    private FileInputStream in;
    public SaveSettings()
    {
        prop=null;
    }
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
            name=prop.getProperty("name");
            if(name==null)
                name="Player";
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
        String stringScore;
        int score;
        checkProperties();
        try {
            in=new FileInputStream("settings.properties");
            prop.load(in);
            in.close();
            stringScore=prop.getProperty("score");
            if(stringScore==null)
                score=0;
            else
                score=Integer.parseInt(stringScore);
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
        String stringPresented;
        boolean presented;
        checkProperties();
        try {
            in=new FileInputStream("settings.properties");
            prop.load(in);
            in.close();
            stringPresented=prop.getProperty("instructionsPresented");
            if(stringPresented==null)
                presented=false;
            else
                presented=Boolean.parseBoolean(stringPresented);
        }
        catch (Exception e) {
            presented=false;
        }
        return presented;
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
        } catch (IOException e) {}
    }
}
