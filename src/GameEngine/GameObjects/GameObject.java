package GameEngine.GameObjects;

import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;

public abstract class GameObject {
    protected PlayingState playingState;
    protected String tag;
    protected int posX;
    protected int posY;
    protected boolean dead;
    protected final int limit;
    public GameObject()
    {
        dead=false;
        limit=768;
    }
    public abstract void update();
    public abstract void render(Renderer renderer);
    public void setDead(boolean dead) { this.dead = dead; }
    public boolean isDead() { return dead; }
    public String getTag() {
        return tag;
    }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
}
