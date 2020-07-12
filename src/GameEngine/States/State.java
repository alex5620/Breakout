package GameEngine.States;
import GameEngine.GameEngine;
import GameEngine.Graphics.Renderer;


public abstract class State{
    protected GameEngine gameEngine;
    public State(GameEngine gameEngine)
    {
        this.gameEngine=gameEngine;
    }
    public abstract void update();
    public abstract void render(Renderer renderer);
}
