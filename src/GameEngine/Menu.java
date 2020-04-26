package GameEngine;

import GameEngine.GameInput.MouseInput;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Menu {
    private GameEngine gameEngine;
    private HighScoresDataBase highscores;
    private MouseInput input;
    private String name;
    boolean pressed=false;
    boolean pressedLast=true;
    public enum MSTATE{MAIN_MENU, SETTINGS_MENU, HIGHSCORE_MENU, ABOUT_MENU};
    private MSTATE mstate;
    Menu(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
        mstate=MSTATE.MAIN_MENU;
        highscores=new HighScoresDataBase();
        input=new MouseInput(gameEngine);
        name="ALEX";
    }
    public void render()
    {
        Renderer renderer= gameEngine.getRenderer();
        if(mstate == MSTATE.MAIN_MENU) {
            renderer.drawImage(gameEngine.getImagesLoader().getMenuImage(), 0, 0);
            if (checkIfPlay()) {
                renderer.drawImage(gameEngine.getImagesLoader().getPlayImage(), 266, 235);
            }
            if (checkIfHighScores()) {
                renderer.drawImage(gameEngine.getImagesLoader().getHighScoresImage(), 195, 312);
            }
            if (checkIfSettings()) {
                renderer.drawImage(gameEngine.getImagesLoader().getSettingsImage(), 240, 382);
            }
            if(checkIfAbout())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getPlayImage(), 265, 460);
            }
            if (checkIfQuit()) {
                renderer.drawImage(gameEngine.getImagesLoader().getQuitImage(), 225, 538);
            }
        }
        if(mstate ==MSTATE.SETTINGS_MENU)
        {
            int x=input.getX();
            int y=input.getY();
            renderer.drawImage(gameEngine.getImagesLoader().getSettingsMenuImage(), 0, 0);
            renderer.drawText("Set Name:", 195, 208, 0xffff0606);
            renderer.drawText(name, 190, 235, 0xffff0606);
            if(checkIfBack())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBack_ResetImage(), 635, 565);
            }
            if ((x > 200) && (x < 550) && (y > 280) && (y < 590)) {
                if (x < 375 || y < 550) {
                    int sx = x - 185;
                    int sy = y - 283;
                    sx = sx / 96;
                    sy = sy / 45;
                    renderer.drawImage(gameEngine.getImagesLoader().getLetterImage(), 208 + sx * 92, 280 + sy * 44);
                }
                else
                {
                    renderer.drawImage(gameEngine.getImagesLoader().getDeleteImage(), 390, 542);
                }
            }
        }
        if(mstate ==MSTATE.HIGHSCORE_MENU)
        {
            renderer.drawImage(gameEngine.getImagesLoader().getHighscoresMenuImage(), 0, 0);
            renderer.drawText("High scores", 300, 175, 0xffff0606);
            renderer.drawText("Pos     Name                                    Score", 135, 230, 0xffff0606);
            try {
                ResultSet rs = highscores.displayUsers();
                int i = 1;
                while (rs.next()) {
                    renderer.drawText(i + ") ", 145, 240 + i * 30, 0xffff0606);
                    renderer.drawText(rs.getString("name"), 205, 240 + i * 30, 0xffff0606);
                    renderer.drawText(rs.getInt("score") + "", 545, 240 + i * 30, 0xffff0606);
                    i++;
                }
            } catch (SQLException e) {
                renderer.drawText("No records.", 170, 270, 0xffff0606);
            }
            if(checkIfBack())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBack_ResetImage(), 635, 565);
            }
            if(checkIfReset())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBack_ResetImage(), 635, 485);
            }
        }
        if(mstate == MSTATE.ABOUT_MENU) {
            renderer.drawImage(gameEngine.getImagesLoader().getAboutImage(), 0, 0);
            if(checkIfBack())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBack_ResetImage(), 635, 565);
            }
        }
    }
    public void update()
    {
        int x=input.getX();
        int y=input.getY();
        if(pressCondition()) {
            if((mstate == MSTATE.SETTINGS_MENU) && (x > 200) && (x < 550) && (y > 280) && (y < 590)) {
                char car = 'A';
                int sx = x - 185;
                int sy = y - 283;
                sx = sx / 96;
                sy = sy / 45;
                car += sx + 4 * sy;
                if (car <= 'Z') {
                    if (name.length() < 14)
                        name += car;
                } else {
                    if (name.length() > 0) {
                        name = name.substring(0, name.length() - 1);
                    }
                }
            }
            if (mstate == MSTATE.MAIN_MENU) {
                if (checkIfPlay()) {
                    gameEngine.setState(GameEngine.STATE.GAME);
                    gameEngine.getGame().init();
                }
                if (checkIfHighScores()) {
                    mstate = MSTATE.HIGHSCORE_MENU;
                }
                if (checkIfSettings()) {
                    mstate = MSTATE.SETTINGS_MENU;
                }
                if (checkIfAbout()) {
                    mstate = MSTATE.ABOUT_MENU;
                }
                if (checkIfQuit()) {
                    gameEngine.getWindow().closeWindow();
                }
            }
            if (mstate == MSTATE.SETTINGS_MENU)
                if (checkIfBack()) {
                    mstate = MSTATE.MAIN_MENU;
                }
            if (mstate == MSTATE.HIGHSCORE_MENU) {
                if (checkIfBack()) {
                    mstate = MSTATE.MAIN_MENU;
                }
                if (checkIfReset()) {
                    highscores.deleteAllUser();
                }
            }
            if (mstate == MSTATE.ABOUT_MENU) {
                if (checkIfBack()) {
                    mstate = MSTATE.MAIN_MENU;
                }
            }
            setPressed(false);
        }
    }
    public boolean checkIfPlay()
    {
        if((input.getX()>268) && (input.getX()<435) && (input.getY()>238) && (input.getY()<298))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfHighScores()
    {
        if((input.getX()>200) && (input.getX()<508) && (input.getY()>315) && (input.getY()<370))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfSettings()
    {
        if ((input.getX()>244) && (input.getX()<473) && (input.getY()>390) && (input.getY()<440))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfAbout()
    {
        if((input.getX()>268) && (input.getX()<435) && (input.getY()>465) && (input.getY()<525))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfQuit()
    {
        if((input.getX()>230) && (input.getX()<490) && (input.getY()>545) && (input.getY()<600))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfBack()
    {
        if((input.getX()>645) && (input.getX()<770) && (input.getY()>575) && (input.getY()<628))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfReset()
    {
        if((input.getX()>643) && (input.getX()<770) && (input.getY()>493) && (input.getY()<545))
        {
            return true;
        }
        return false;
    }
    public String getName()
    {
        return name;
    }

    public void setPressed(boolean pressed) {
        this.pressedLast=this.pressed;
        this.pressed = pressed;
        System.out.println("Last: "+pressedLast+", this: "+this.pressed);
    }
    public void updateHighscores(int score)
    {
        if(name.length()>0)
            highscores.updateTable(name, score);
        else
            highscores.updateTable("Noname", score);
    }
    public MouseInput getInput() { return input; }
    public boolean pressCondition()
    {
        return pressed && !pressedLast;
    }
    public int getMinimumScore() { return highscores.minScore(); }
}
