package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;

public class MagicObject extends GameObject {
    Game game;
    private Image objectImage;
    enum Type{reverse, smaller, bigger};
    private Type type;
    public MagicObject(Game game, int posX, int posY, Type type)
    {
        this.game=game;
        this.tag = "magic";
        this.posX = posX;
        this.posY = posY;
        this.type=type;
        setDetails();
        this.width=objectImage.getWidth();
        this.height=objectImage.getHeight();
    }
    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }

    @Override
    public void update(Game gc, float dt) {
        posY+=2;
    }
    @Override
    public void render(Game gc, Renderer r) {
            r.drawImage(objectImage,(int) posX,(int) posY);
    }
    public void setDetails() {
        switch (type) {
            case reverse: objectImage = game.getImagesLoader().getReverseImage();
                break;
            case bigger: objectImage = game.getImagesLoader().getBiggerImage();
                break;
            case smaller: objectImage=game.getImagesLoader().getSmallerImage();
        }
    }
    public void execute()
    {
        switch (type) {
            case reverse:
                game.getObjectsManager().setReversedMovement();
                break;
            case bigger:
                game.getObjectsManager().playerSize(1);
                break;
            case smaller:
                game.getObjectsManager().playerSize(-1);
        }
    }
    public Type getType() {
        return type;
    }
}