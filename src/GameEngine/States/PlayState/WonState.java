package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.Graphics.Renderer;
import GameEngine.States.State;

public class WonState extends State {
    private int score;
    private boolean isScoreInTopTen;
    public WonState(GameEngine gameEngine) {
        super(gameEngine);
        score=gameEngine.getSettings().getScore();
        gameEngine.getSoundsLoader().getSound("background").stop();
        gameEngine.getSoundsLoader().getSound("gameWon").loop();
        if(score>gameEngine.getHighscore().minScore(true))
            isScoreInTopTen=true;
        else
            isScoreInTopTen=false;
    }
    @Override
    public void update() {
        if (checkIfGoBack() && gameEngine.getMouseInput().isClickOnePressedOnce()) {
            gameEngine.getSoundsLoader().getSound("gameWon").stop();
            gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
            gameEngine.setState(GameEngine.STATE.MainMenuState);
        }
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImage(gameEngine.getImagesLoader().getImage("background"), 0, 0);
        renderer.drawImage(gameEngine.getImagesLoader().getImage("wontext"), 255, 387);
        renderer.drawImage(gameEngine.getImagesLoader().getImage("youwon"), 245, 20);
        renderer.drawText("Your score: "+score , 265, 210, 0xff0000ff);
        if(isScoreInTopTen){
            renderer.drawText("Congrats, you are in top 10!", 190, 280, 0xff0000ff);
        }
        else {
            renderer.drawText("You can do better!", 210, 280, 0xff0000ff);
        }
        if(checkIfGoBack())
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("backtomenu"), 240, 388);
        }
    }
    public boolean checkIfGoBack()
    {

        int x=gameEngine.getMouseInput().getX();
        int y=gameEngine.getMouseInput().getY();
        if((x > 250) && (x < 505) && (y > 386) && (y < 441))
        {
            return true;
        }
        return false;
    }
}
