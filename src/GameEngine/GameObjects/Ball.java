package GameEngine.GameObjects;

import GameEngine.Graphics.Image;
import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;

import java.awt.event.KeyEvent;


public class Ball extends GameObject {
    private Image objectImage;
    private int diameter;
    private int velX;
    private int velY;
    private boolean move;
    public Ball(PlayingState playingState) {
        this.playingState = playingState;
        this.tag = "ball";
        this.objectImage = playingState.getImagesLoader().getImage("ball");
        this.diameter =objectImage.getWidth();
        move=false;
        setToInitialPosition();
    }
    @Override
    public void update() {
        if(playingState.getKeyboardInput().isKey(KeyEvent.VK_SPACE))
        {
            if(playingState.getLevelPassed()==false && move==false) {
                playingState.setInstructionsPresented();
                initializeVelX();
                move = true;
            }
        }
        if(move)
        {
            updatePosition();
            checkForBounds();
        }
        else
        {
           moveAlongThePaddle();
        }
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImage(objectImage, posX, posY);
    }
    private void updatePosition()
    {
        posX += velX;
        posY += velY;
    }
    private void checkForBounds() {
        if (posX < 0) {
            playingState.getSoundsLoader().getSound("ballBouncing").play();
            reverseVelX();
        }
        if (posY < 45) {
            playingState.getSoundsLoader().getSound("ballBouncing").play();
            reverseVelY();
        }
        if (posX > (limit - diameter)) {
            playingState.getSoundsLoader().getSound("ballBouncing").play();
            reverseVelX();
        }
        if  (posY>limit)
        {
            playingState.getObjectsManager().changePaddleLives(-1);
            playingState.getObjectsManager().setPaddleNormalMovement();
            setToInitialPosition();
        }
    }
    public void moveAlongThePaddle() {
        int paddleX = playingState.getObjectsManager().getPaddleX();
        int paddleWidth = playingState.getObjectsManager().getPaddleWidth();
        if (posX<paddleX+paddleWidth-diameter && posX>paddleX) {
            posX += velX;
        }
        else
        {
            if(posX<=paddleX)
            {
                posX=paddleX;
                if(playingState.getKeyboardInput().isKey(KeyEvent.VK_RIGHT) ||
                        (playingState.getKeyboardInput().isKey(KeyEvent.VK_LEFT) &&
                        playingState.getObjectsManager().isPaddleMovementReversed()))
                {
                    return;
                }
                reverseVelX();
            }
            else
            {
                posX=paddleX+paddleWidth-diameter;
                if(playingState.getKeyboardInput().isKey(KeyEvent.VK_LEFT) ||
                        (playingState.getKeyboardInput().isKey(KeyEvent.VK_RIGHT) &&
                                playingState.getObjectsManager().isPaddleMovementReversed()))
                {
                    return;
                }
                reverseVelX();
            }
        }
    }
    public void initializeVelX()
    {
        int paddleWidth=playingState.getObjectsManager().getPaddleWidth();
        int paddleX=playingState.getObjectsManager().getPaddleX();
        int distance=(posX+diameter/2)-(paddleX+paddleWidth/2);
        distance/=(paddleWidth/11);
        velX=distance;
    }
    public void setToInitialPosition()
    {
        this.posX=playingState.getObjectsManager().getPaddleX()+
                playingState.getObjectsManager().getPaddleWidth()/2;
        this.posY=playingState.getObjectsManager().getPaddleY()- diameter;
        setInitialVelocities();
    }
    private void setInitialVelocities()
    {
        this.velX=4;
        this.velY=-5-(playingState.getLevel()+1)/2-(playingState.getDifficulty()-1)*2;
        move=false;
    }
    public void reverseVelX() {
        velX=-velX;
        posX+=velX;
    }
    public void reverseVelY()
    {
        velY=-velY;
        posY+=velY;
    }
    public void incrementVelY()
    {
        velY+=Math.abs(velY)/velY;
    }
    public void changeVelX(int val) {
        if (val < 0) {
            decreaseVelX(val);
        } else {
            increaseVelX(val);
        }
    }
    public void increaseVelX(int val)
    {
        while(velX<=5 && val!=0) {
            ++velX;
            --val;
        }
    }
    public void decreaseVelX(int val)
    {
        while(velX>=-5 && val!=0)
        {
            --velX;
            ++val;
        }
    }
    public int getVelX()
    {
        return velX;
    }
    public int getVelY()
    {
        return velY;
    }
    public boolean isMoving() {
        return move;
    }
    public int getDiameter()
    {
        return diameter;
    }
}