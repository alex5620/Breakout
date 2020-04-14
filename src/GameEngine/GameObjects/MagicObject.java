package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;

public class MagicObject extends GameObject {
    Game game;
    private Image objectImage;
    enum Type{reverse, smaller, bigger, slower, faster, life, bonus100, bonus250, bonus500};
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
        if(game.getState()== Game.STATE.pause)
            return;
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
                break;
            case slower: objectImage=game.getImagesLoader().getSlowImage();
                break;
            case faster: objectImage=game.getImagesLoader().getFastImage();
                break;
            case life: objectImage=game.getImagesLoader().getLifeImage();
                break;
            case bonus100: objectImage=game.getImagesLoader().getBonus_100_Image();
                break;
            case bonus250: objectImage=game.getImagesLoader().getBonus_250_Image();
                break;
            case bonus500: objectImage=game.getImagesLoader().getBonus_500_Image();
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
                break;
            case slower:
                game.getObjectsManager().changePlayerSpeed(-1);
                break;
            case faster:
                game.getObjectsManager().changePlayerSpeed(1);
                game.getObjectsManager().changeBallSpeed();
                break;
            case life:
                game.getObjectsManager().changePlayerLives(1);
                break;
            case bonus100:
                game.increaseScore(100);
                break;
            case bonus250:
                game.increaseScore(250);
                break;
            case bonus500:
                game.increaseScore(500);
                break;
        }
    }
    public Type getType() {
        return type;
    }
}