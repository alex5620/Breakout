package GameEngine.GameObjects;

import GameEngine.Renderer;
import GameEngine.States.PlayState.PlayingState;

public abstract class GameObject {
    protected PlayingState playingState;
    protected String tag;//ne ajuta sa identificam obiectele in tabloul cu obiecte
    protected int posX, posY;
    protected int width, height;
    protected boolean dead=false;
    protected final int limit=768;//limita inferioara a ferestrei jocului

    public abstract void update();
    public abstract void render(Renderer r);
    public String getTag() {
        return tag;
    }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isDead() { return dead; }
    public void setDead(boolean dead) { this.dead = dead; }
}
