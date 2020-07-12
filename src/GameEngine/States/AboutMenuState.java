package GameEngine.States;

import GameEngine.GameEngine;
import GameEngine.Graphics.Renderer;

public class AboutMenuState extends State {
    public AboutMenuState(GameEngine gameEngine) {
        super(gameEngine);
    }
    @Override
    public void update() {
        if (gameEngine.getMouseInput().isClickOnePressedOnce() && checkIfBack()) {
            gameEngine.setState(GameEngine.STATE.MainMenuState);
            gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
        }
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImage(gameEngine.getImagesLoader().getImage("about"), 0, 0);
        if(checkIfBack())
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("back_reset"), 635, 565);
        }
    }
    public boolean checkIfBack()
    {
        int x= gameEngine.getMouseInput().getX();
        int y= gameEngine.getMouseInput().getY();
        if((x>645) && (x<770) && (y>575) && (y<628))
        {
            return true;
        }
        return false;
    }
}
