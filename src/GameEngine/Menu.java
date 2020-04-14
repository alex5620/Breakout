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
    public enum MSTATE{MAIN_MENU, SETTINGS_MENU, HIGHSCORE_MENU};
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
                renderer.drawImage(gameEngine.getImagesLoader().getPlayImage(), 265, 230);
            }
            if (checkIfHighScores()) {
                renderer.drawImage(gameEngine.getImagesLoader().getHighScoresImage(), 210, 330);
            }
            if (checkIfSettings()) {
                renderer.drawImage(gameEngine.getImagesLoader().getSettingsImage(), 240, 430);
            }
            if (checkIfQuit()) {
                renderer.drawImage(gameEngine.getImagesLoader().getQuitImage(), 230, 520);
            }
        }
        if(mstate ==MSTATE.SETTINGS_MENU)
        {
            int x=input.getX();
            int y=input.getY();
            renderer.drawImage(gameEngine.getImagesLoader().getSettingsMenuImage(), 0, 0);
            renderer.drawText("Set Name:", 195, 208, 0xffff0606);
            renderer.drawText(name, 190, 235, 0xffff0606);
            if((input.getX()>635) && (input.getX()<760) && (input.getY()>565) && (input.getY()<615))
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
            renderer.drawText("High scores", 252, 175, 0xffff0606);
            renderer.drawText(" Pos      Name                             Score", 150, 230, 0xffff0606);
            try {
                ResultSet rs = highscores.displayUsers();
                int i = 1;
                while (rs.next()) {
                    renderer.drawText(i + ") ", 170, 240 + i * 30, 0xffff0606);
                    renderer.drawText(rs.getString("name"), 245, 240 + i * 30, 0xffff0606);
                    renderer.drawText(rs.getInt("score") + "", 530, 240 + i * 30, 0xffff0606);
                    i++;
                }
            } catch (SQLException e) {
                renderer.drawText("No records.", 170, 270, 0xffff0606);
            }
            if((input.getX()>635) && (input.getX()<760) && (input.getY()>565) && (input.getY()<615))
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBack_ResetImage(), 635, 565);
            }
            if((input.getX()>635) && (input.getX()<760) && (input.getY()>485) && (input.getY()<535))
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBack_ResetImage(), 635, 485);
            }
        }
    }
    public void update()
    {
        int x=input.getX();
        int y=input.getY();
        if(pressCondition()) {
            if ((mstate == MSTATE.SETTINGS_MENU) && (x > 200) && (x < 550) && (y > 280) && (y < 590)) {
                char car = 'A';
                int sx = x - 185;
                int sy = y - 283;
                sx = sx / 96;
                sy = sy / 45;
                car += sx + 4 * sy;
                if (car <= 'Z') {
                    if (name.length() < 11)
                        name += car;
                } else {
                    if (name.length() > 0) {
                        name = name.substring(0, name.length() - 1);
                    }
                }
            }
            if (mstate == MSTATE.MAIN_MENU) {
                if ((x > 265) && (x < 440) && (y > 235) && (y < 300)) {
                    gameEngine.setState(GameEngine.STATE.GAME);
                    gameEngine.getGame().init();
                }
                if ((x > 230) && (x < 490) && (y > 530) && (y < 580)) {
                    gameEngine.getWindow().closeWindow();
                }
                if ((x > 240) && (x < 470) && (y > 440) && (y < 490)) {
                    mstate = MSTATE.SETTINGS_MENU;
                }
                if ((x > 210) && (x < 520) && (y > 340) && (y < 390)) {
                    mstate = MSTATE.HIGHSCORE_MENU;
                }
            }
            if (mstate == MSTATE.SETTINGS_MENU)
                if ((x > 635) && (x < 760) && (y > 565) && (y < 615)) {
                    mstate = MSTATE.MAIN_MENU;
                }
            if (mstate == MSTATE.HIGHSCORE_MENU) {
                if ((x > 635) && (x < 760) && (y > 565) && (y < 615)) {
                    mstate = MSTATE.MAIN_MENU;
                }
                if ((x > 635) && (x < 760) && (y > 485) && (y < 535)) {
                    highscores.deleteAllUser();
                }
            }
            setPressed(false);
        }
    }
    public boolean checkIfPlay()
    {
        if((input.getX()>265) && (input.getX()<440) && (input.getY()>235) && (input.getY()<300))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfHighScores()
    {
        if((input.getX()>210) && (input.getX()<520) && (input.getY()>340) && (input.getY()<390))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfSettings()
    {
        if ((input.getX()>240) && (input.getX()<470) && (input.getY()>440) && (input.getY()<490))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfQuit()
    {
        if((input.getX()>230) && (input.getX()<490) && (input.getY()>530) && (input.getY()<580))
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
