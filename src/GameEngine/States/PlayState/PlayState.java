package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.Graphics.Renderer;
import GameEngine.States.State;

public class PlayState extends State {
    private State currentPState;
    public enum PState {PlayingState, WonState, LostState};
    public PlayState(GameEngine gameEngine) {
        super(gameEngine);
        currentPState=new PlayingState(gameEngine, this);
    }
    @Override
    public void update() {
        currentPState.update();
    }
    @Override
    public void render(Renderer renderer) {
        currentPState.render(gameEngine.getRenderer());
    }
    public void setCurrentPState(PState state)
    {
        switch (state)
        {
            case PlayingState:
                currentPState=new PlayingState(gameEngine, this);
                break;
            case WonState:
                currentPState=new WonState(gameEngine);
                break;
            case LostState:
                currentPState=new LostState(gameEngine, this);
                break;
        }
    }
}
