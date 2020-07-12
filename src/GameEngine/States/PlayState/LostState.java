package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.GameObjects.Brick;
import GameEngine.Graphics.Renderer;
import GameEngine.States.State;

public class LostState extends State {
    private PlayState playState;
    private int score;
    public LostState(GameEngine gameEngine, PlayState playState) {
        super(gameEngine);
        this.playState=playState;
        Brick.resetBricksNumber();
        score=gameEngine.getSettings().getScore();
        gameEngine.getSoundsLoader().getSound("background").stop();
        gameEngine.getSoundsLoader().getSound("gameLost").play();
    }
    @Override
    public void update() {
        if(gameEngine.getMouseInput().isClickOnePressedOnce()) {
            int x = gameEngine.getMouseInput().getX();
            int y = gameEngine.getMouseInput().getY();
            if (checkIfPlayAgain(x, y)) {
                gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
                playState.setCurrentPState(PlayState.PState.PlayingState);
            }
            if (checkIfGoBack(x, y)) {
                gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
        }
    }
    @Override
    public void render(Renderer renderer) {
        int x=gameEngine.getMouseInput().getX();
        int y=gameEngine.getMouseInput().getY();
        renderer.drawImage(gameEngine.getImagesLoader().getImage("background"), 0, 0);
        printLoseMessage(renderer);
        if(checkIfPlayAgain(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("playagain"), 272, 215);
        }
        if(checkIfGoBack(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("backtomenu"), 240, 293);
        }
    }
    public void printLoseMessage(Renderer renderer)
    {
        renderer.drawImage(gameEngine.getImagesLoader().getImage("losttext"), 250, 165);
        renderer.drawText("    GAME OVER", 275, 60, 0xff0000ff);
        renderer.drawText("     SCORE: " + score, 280, 100, 0xff0000ff);
    }
    public boolean checkIfGoBack(int x, int y)
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
