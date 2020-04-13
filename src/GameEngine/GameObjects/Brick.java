package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;

public class Brick extends GameObject {
    private static int bricksNumber=0;
    private int hitsRemained;
    private boolean specialBrick;
    private Image objectImage[];
    public Brick(int posX, int posY, Image image[], int hitsRemained) {
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
    }

    public static int getBricksNumber()
    {
        return bricksNumber;
    }
    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
        --bricksNumber;
    }

    @Override
    public void update(Game gc, float dt) {
        //if(hitsRemained==0)
        //{
        //    dead=true;
        //}
    }
    @Override
    public void render(Game gc, Renderer r) {
        if(specialBrick)
            r.drawImage(objectImage[hitsRemained%2],(int) posX,(int) posY);
        else
            r.drawImage(objectImage[0],(int) posX,(int) posY);
    }
    public void decrementHitsRemained()
    {
        if(specialBrick && hitsRemained==2)
        {
            --hitsRemained;
        }
        else
        {
            setDead(true);
        }
    }
}