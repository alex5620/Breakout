package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.Renderer;
import GameEngine.States.State;

public class LostState extends State {
    private PlayState playState;
    private int score;
    public LostState(GameEngine gameEngine, PlayState playState) {
        super(gameEngine);
        this.playState=playState;
        score=gameEngine.getSettings().getScore();
    }

    @Override
    public void update() {
        if(gameEngine.getMouseInput().isClick1Up()) {
            int x = gameEngine.getMouseInput().getX();
            int y = gameEngine.getMouseInput().getY();
            if (checkIfPlayAgain(x, y)) {
                playState.setCurrentPState(PlayState.PState.PlayingState);
            }
            if (checkIfGoToMenuAfterLosing(x, y)) {
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
        }
    }

    @Override
    public void render(Renderer renderer) {
        int x=gameEngine.getMouseInput().getX();
        int y=gameEngine.getMouseInput().getY();
        printLoseMessage(renderer);
        renderer.drawImage(gameEngine.getImagesLoader().getImage("lostmenu"), 0, 0);
        if(checkIfPlayAgain(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("playagain"), 272, 215);
        }
        if(checkIfGoToMenuAfterLosing(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("backtomenu"), 240, 293);
        }
    }
    public void printLoseMessage(Renderer renderer)
    {
        renderer.drawText("    GAME OVER", 275, 60, 0xff0000ff);
        renderer.drawText("     SCORE: " + score, 280, 100, 0xff0000ff);
    }
    public boolean checkIfGoToMenuAfterLosing(int x, int y)
    {
        if((x > 230) && (x < 520) && (y > 295) && (y < 348))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfPlayAgain(int x, int y)
    {
        if((x > 272) && (x < 470) && (y > 217) && (y < 270))
        {
            return true;
        }
        return false;
    }
}
