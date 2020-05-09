package GameEngine.States;

import GameEngine.GameEngine;
import GameEngine.Renderer;

public class AboutMenuState extends State {
    public AboutMenuState(GameEngine gameEngine) {
        super(gameEngine);
    }
    @Override
    public void update() {
        if (gameEngine.getMouseInput().isClick1Up() && checkIfBack()) {
            gameEngine.setState(GameEngine.STATE.MainMenuState);
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
