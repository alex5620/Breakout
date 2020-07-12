package GameEngine.GameObjects;

import GameEngine.Graphics.ImageTile;
import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private ImageTile objectImage;
    private int velX;
    private int width;
    private int height;
    private int size;
    private boolean reversedMovement;
    private int lives;
    private int direction;
    public Paddle(PlayingState playingState){
        this.playingState = playingState;
        this.tag="paddle";
        velX=5;
        size=3;
        setToInitialPosition();
        objectImage=(ImageTile)playingState.getImagesLoader().getImage("paddle"+size);
        this.width=objectImage.getTileWidth();
        this.height=objectImage.getTileHeight();
        reversedMovement=false;
        lives=1;
        direction=0;
    }
    @Override
    public void update() {
        direction=0;
        checkIfLeft();
        checkIfRight();
        checkForBounds();
        checkDirectionProperly();
    }
    public void checkDirectionProperly()
    {
        if(direction!=0 && reversedMovement)
        {
            direction=(direction==1? 2 : 1);
        }
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImageTile(objectImage, posX, posY, 0, direction);
    }
    public void checkIfLeft()
    {
        if (playingState.getKeyboardInput().isKey(KeyEvent.VK_LEFT)) {
            if (reversedMovement==false)
                posX -= velX;
            else
                posX += velX;
            direction=1;
        }
    }
    public void checkIfRight()
    {
        if (playingState.getKeyboardInput().isKey(KeyEvent.VK_RIGHT)) {
            if (!reversedMovement)
                posX += velX;
            else
                posX -= velX;
            direction=2;
        }
    }
    private void checkForBounds()
    {
        if (posX >= (limit - width)) {
            posX = limit - width;
        }
        if (posX <= 0) {
            posX = 0;
        }
    }
    public void setToInitialPosition()
    {
        this.posX=300;
        this.posY=550+(playingState.getDifficulty()-1)*50;
    }
    public void setReversedMovement(boolean cond)
    {
        reversedMovement=cond;
    }
    public void reverseMovement()
    {
        reversedMovement=!reversedMovement;
    }
    public void changeSize(int changeSize)
    {
        if((changeSize==-1 && size>1)||(changeSize==1 && size<5)) {
            size += changeSize;
            objectImage=(ImageTile) playingState.getImagesLoader().getImage("paddle"+size);
            width = objectImage.getWidth();
            height = objectImage.getHeight();
        }
    }
    public void changeSpeed(int speed)
    {
        if((speed>0 && this.velX <8)||(speed<0 && this.velX>3))
            this.velX += speed;
    }
    public void changeLivesNo(int life)
    {
        if(life<0 && lives>1)
        {
            playingState.getSoundsLoader().getSound("lifeLost").play();
        }
        lives+=life;
    }
    public int getLives()
    {
        return lives;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public int getSize()
    {
        return size;
    }
    public boolean isMovementReversed()
    {
        return reversedMovement;
    }
}