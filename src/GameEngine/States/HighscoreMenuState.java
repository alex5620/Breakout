package GameEngine.States;

import GameEngine.GameEngine;
import GameEngine.Graphics.Renderer;

import java.util.*;

public class HighscoreMenuState extends State {
    private LinkedHashMap<String, Integer> records;
    public HighscoreMenuState(GameEngine gameEngine)
    {
        super(gameEngine);
        records = gameEngine.getHighscore().getUsers();
    }
    @Override
    public void update() {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if (gameEngine.getMouseInput().isClickOnePressedOnce()) {
            if (checkIfBack(x, y)) {
                gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
            if (checkIfReset(x, y)) {
                gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
               gameEngine.getHighscore().deleteAllUsers();
               records = gameEngine.getHighscore().getUsers();
            }
        }
    }
    @Override
    public void render(Renderer renderer) {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        renderer.drawImage(gameEngine.getImagesLoader().getImage("highscoresmenu"), 0, 0);
        renderer.drawText("High scores", 300, 175, 0xffff0606);
        renderer.drawText("Pos     Name                                    Score", 135, 230, 0xffff0606);
        if(records!=null) {
            Object[] set = records.keySet().toArray();
            int length = set.length;
            for (int i = 0; i < length; ++i) {
                renderer.drawText((i + 1) + ") ", 145, 240 + (i + 1) * 30, 0xffff0606);
                renderer.drawText(set[i] + "", 205, 240 + (i + 1) * 30, 0xffff0606);
                renderer.drawText(records.get(set[i]) + "", 545, 240 + (i + 1) * 30, 0xffff0606);
            }
        }
        else
        {
            renderer.drawText("No records.", 170, 270, 0xffff0606);
        }
        if(checkIfBack(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("back_reset"), 635, 565);
        }
        if(checkIfReset(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("back_reset"), 635, 485);
        }
    }
    public boolean checkIfBack(int x, int y)
    {
        if((x>645) && (x<770) && (y>575) && (y<628))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfReset(int x, int y)
    {
        if((x>643) && (x<770) && (y>493) && (y<545))
        {
            return true;
        }
        return false;
    }
}
