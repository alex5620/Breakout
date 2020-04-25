package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;

public class Brick extends GameObject {
    private static int bricksNumber=0;//contorizeaza numarul de caramizi
    private int hitsRemained;//unele caraminzi sunt ditruse din prima lovitura, altele nu
    private boolean specialBrick;
    private Image objectImage[];
    public Brick(Game game, int posX, int posY, int imageIndex, int hitsRemained) {
        ++bricksNumber;
        this.tag = "brick";
        this.posX = posX;
        this.posY = posY;
        objectImage=new Image[hitsRemained];
        this.objectImage[0] = game.getImagesLoader().getBrickImage(imageIndex);
        if(hitsRemained==2)
        {
            this.objectImage[1] = game.getImagesLoader().getBrickImage(imageIndex+5);
            specialBrick=true;
        }
        this.width=objectImage[0].getWidth();
        this.height=objectImage[0].getHeight();
        this.hitsRemained = hitsRemained;
    }
    /*public Brick(int posX, int posY, Image image[], int hitsRemained) {
        ++bricksNumber;
        this.tag = "brick";
        this.posX = posX;
        this.posY = posY;
        this.width=image[0].getWidth();
        this.height=image[0].getHeight();
        objectImage=new Image[2];
        this.objectImage[0] = image[0];
        this.objectImage[1] = image[1];
        this.hitsRemained = hitsRemained;
        if(hitsRemained==2)
        {
            specialBrick=true;
        }
    }*/
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
    public void decrementHitsRemained()
    {
        --hitsRemained;
    }
}