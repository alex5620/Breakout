package GameEngine.GameObjects;

import GameEngine.Image;
import GameEngine.Renderer;
import GameEngine.States.PlayState.PlayingState;

public class MagicObject extends GameObject {
    private Image objectImage;
    enum Type{reverse, smaller, bigger, slower, faster, life, bonus100, bonus250, bonus500};
    private Type type;
    public MagicObject(PlayingState playingState)
    {
        this.playingState=playingState;
        this.tag = "magic";
    }
    @Override
    public void update() {
        if(playingState.IsPaused()==true)//obiectele nu mai sunt updatate in pauza
            return;
        posY+=2;
    }
    @Override
    public void render(Renderer r) {
        r.drawImage(objectImage,(int) posX,(int) posY);
    }
    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
    }
    public GameObject setDetails(int posX, int posY, Type type)
    {
        this.posX=posX;
        this.posY=posY;
        this.type=type;
        setObjectType();
        this.width=objectImage.getWidth();
        this.height=objectImage.getHeight();
        return this;
    }

    public void setObjectType() {//se alege imaginea corespunzatoare in functie de tipul obiectului
        switch (type) {
            case reverse: objectImage = playingState.getImagesLoader().getImage("reverse");
                break;
            case bigger: objectImage = playingState.getImagesLoader().getImage("bigger");
                break;
            case smaller: objectImage=playingState.getImagesLoader().getImage("smaller");
                break;
            case slower: objectImage=playingState.getImagesLoader().getImage("slower");
                break;
            case faster: objectImage=playingState.getImagesLoader().getImage("faster");
                break;
            case life: objectImage=playingState.getImagesLoader().getImage("life");
                break;
            case bonus100: objectImage=playingState.getImagesLoader().getImage("bonus100");
                break;
            case bonus250: objectImage=playingState.getImagesLoader().getImage("bonus250");
                break;
            case bonus500: objectImage=playingState.getImagesLoader().getImage("bonus500");
        }
    }
    public void executeCommand()//se executa comanda corespunzatoare in functie de tipul obiectului
    {
        switch (type) {
            case reverse:
                playingState.getObjectsManager().setPlayerReversedMovement();
                break;
            case bigger:
                playingState.getObjectsManager().changePlayerSize(1);
                break;
            case smaller:
                playingState.getObjectsManager().changePlayerSize(-1);
                break;
            case slower:
                playingState.getObjectsManager().changePlayerSpeed(-1);
                break;
            case faster:
                playingState.getObjectsManager().changePlayerSpeed(1);
                playingState.getObjectsManager().incrementBallSpeed();
                break;
            case life:
                playingState.getObjectsManager().changePlayerLives(1);
                break;
            case bonus100:
                playingState.increaseScore(100);
                break;
            case bonus250:
                playingState.increaseScore(250);
                break;
            case bonus500:
                playingState.increaseScore(500);
                break;
        }
    }
}