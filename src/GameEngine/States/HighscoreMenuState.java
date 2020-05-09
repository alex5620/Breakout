package GameEngine.States;

import GameEngine.GameEngine;
import GameEngine.Renderer;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HighscoreMenuState extends State {
    public HighscoreMenuState(GameEngine gameEngine) {
        super(gameEngine);
    }

    @Override
    public void update() {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if (gameEngine.getMouseInput().isClick1Up()) {
            if (checkIfBack(x, y)) {
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
            if (checkIfReset(x, y)) {
               gameEngine.getHighscore().deleteAllUser();
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
        try {
            ResultSet rs = gameEngine.getHighscore().displayUsers();
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
