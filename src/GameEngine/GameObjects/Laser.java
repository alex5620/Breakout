package GameEngine.GameObjects;

import GameEngine.Graphics.Image;
import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;

public class Laser extends GameObject {
    private Image objectImage;
    private int width;
    private int height;
    private int hitsRemained;
    public Laser(PlayingState playingState)
    {
        objectImage=playingState.getImagesLoader().getImage("laser");
        width=objectImage.getWidth();
        height=objectImage.getHeight();
        dead=false;
        hitsRemained=1;
        posX=0;
        posY=569+(playingState.getDifficulty()-1)*50;
        tag="laser";
    }
    @Override
    public void update() {
        if(hitsRemained==0)
        {
            setDead(true);
        }
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImage(objectImage, posX, posY);
    }
    public void setDead(boolean dead)
    {
        super.setDead(dead);
    }
    public void decrementHits()
    {
        --hitsRemained;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
}
