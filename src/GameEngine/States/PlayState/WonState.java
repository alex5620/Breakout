package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.Renderer;
import GameEngine.States.State;

public class WonState extends State {
    private int score;
    public WonState(GameEngine gameEngine) {
        super(gameEngine);
        score=gameEngine.getSettings().getScore();
    }
    @Override
    public void update() {
        if (checkIfGoBackAfterWinning() && gameEngine.getMouseInput().isClick1Up()) {
            gameEngine.setState(GameEngine.STATE.MainMenuState);
        }
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImage(gameEngine.getImagesLoader().getImage("background"), 0 ,0 );
        renderer.drawImage(gameEngine.getImagesLoader().getImage("won"), 0, 0);
        renderer.drawText("Your score: "+score , 265, 210, 0xffff0606);
        if(score>gameEngine.getHighscore().minScore())
            renderer.drawText("Congrats, you are in top 10!" , 190, 280, 0xffff0606);
        else
            renderer.drawText("You can do better!" , 210, 280, 0xffff0606);
        if(checkIfGoBackAfterWinning())
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("backtomenu2"), 248, 386);
        }
    }
    public boolean checkIfGoBackAfterWinning()
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
