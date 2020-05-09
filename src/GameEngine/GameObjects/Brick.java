package GameEngine.GameObjects;

import GameEngine.Image;
import GameEngine.Renderer;
import GameEngine.States.PlayState.PlayingState;

public class Brick extends GameObject {
    private static int bricksNumber=0;//contorizeaza numarul de caramizi
    private int hitsRemained;//unele caraminzi sunt ditruse din prima lovitura, altele nu
    private boolean specialBrick;
    private Image objectImage[];
    public Brick(PlayingState playingState) {
        ++bricksNumber;
        this.playingState=playingState;
        this.tag = "brick";
    }
    public static int getBricksNumber()
    {
        return bricksNumber;
    }
    @Override
    public void update() {
        if(hitsRemained==0)
        {
            setDead(true);
        }
    }
    @Override
    public void render(Renderer r) {
        if(specialBrick)
            r.drawImage(objectImage[hitsRemained%2],posX, posY);//randam imaginea corespunzatoare
        else                                      //in functie de numarul de lovituri ramase
            r.drawImage(objectImage[0], posX, posY);
    }
    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
        --bricksNumber;
    }
    public GameObject setDetails(int posX, int posY, int imageIndex, int hitsRemained)
    {
        this.posX = posX;
        this.posY = posY;
        this.hitsRemained = hitsRemained;
        objectImage=new Image[this.hitsRemained];
        objectImage[0] = playingState.getImagesLoader().getImage("brick"+imageIndex);
        if(this.hitsRemained==2)
        {
            objectImage[1] = playingState.getImagesLoader().getImage("brick"+(imageIndex+5));
            specialBrick=true;
        }
        this.width=objectImage[0].getWidth();
        this.height=objectImage[0].getHeight();
        return this;
    }
    public void decrementHitsRemained()
    {
        --hitsRemained;
    }
    public static void resetBrickNumber()
    {
        bricksNumber=0;
    }
}