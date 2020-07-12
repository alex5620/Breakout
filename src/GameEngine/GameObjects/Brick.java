package GameEngine.GameObjects;

import GameEngine.Graphics.ImageTile;
import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;


public class Brick extends GameObject {
    private static int bricksNumber=0;
    private int hitsRemained;
    private boolean specialBrick;
    private int height;
    private int width;
    private int imageIndex;
    private ImageTile objectImage;
    public Brick(PlayingState playingState) {
        ++bricksNumber;
        this.playingState=playingState;
        this.tag = "brick";
    }
    public static int getBricksNumber()
    {
        return bricksNumber;
    }
    public static void resetBricksNumber()
    {
        bricksNumber=0;
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
        if (specialBrick) {
            if (hitsRemained == 2)
                renderer.drawImageTile(objectImage, posX, posY, imageIndex, 0);
            else
                renderer.drawImageTile(objectImage, posX, posY, imageIndex, 1);
        } else {
            renderer.drawImageTile(objectImage, posX, posY, imageIndex, 0);
        }
    }
    @Override
    public void setDead(boolean dead) {
        playingState.getSoundsLoader().getSound("brickDestroyed").play();
        super.setDead(dead);
        --bricksNumber;
    }
    public GameObject setDetails(int posX, int posY, int imageIndex, int hitsRemained, int difficulty)
    {
        this.posX = posX;
        this.posY = posY;
        this.hitsRemained = hitsRemained;
        if(difficulty==1) {
            objectImage = (ImageTile) playingState.getImagesLoader().getImage("bricks");
        }
        else
        {
            objectImage = (ImageTile) playingState.getImagesLoader().getImage("sbricks");
        }
        this.imageIndex=imageIndex;
        this.width=objectImage.getTileWidth();
        this.height=objectImage.getTileHeight();
        if(this.hitsRemained==2)
        {
            specialBrick=true;
        }
        return this;
    }
    public void decrementHitsRemained()
    {
        if(hitsRemained==2) {
            playingState.getSoundsLoader().getSound("ballBouncing").play();
        }
        --hitsRemained;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public boolean isSpecialBrick()
    {
        return specialBrick;
    }
}