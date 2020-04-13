package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Renderer;
import GameEngine.Image;

public abstract class GameObject {
    protected String tag;//ne ajuta sa identificam obiectele in tabloul cu obiecte
    protected int posX, posY;
    protected int width, height;
    protected boolean dead=false;
    protected final int limit=768;

    public abstract void update(Game game, float dt);//dt=durata intre frame-uri
    public abstract void render(Game game, Renderer r);
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
