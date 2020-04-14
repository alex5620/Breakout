package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;

import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private Game game;
    private Image objectImage[];
    private int speed=5;
    private int size;
    boolean reversedMovement;
    int lives;
    public Player(Game game){
        this.game = game;
        this.tag="player";
        size=3;
        setToInitialPosition();
        objectImage=new Image[5];
        this.objectImage= game.getImagesLoader().getPlayerImages();
        this.width=objectImage[size-1].getWidth();
        this.height=objectImage[size-1].getHeight();
        reversedMovement=false;
        lives=1;
    }
    @Override
    public void update(Game game, float dt) {
        if(game.getInput().isKeyUp(KeyEvent.VK_P))//Pauza joc
        {
            game.setPause();
        }
        if(game.getState()== Game.STATE.pause) {
            return;
        }
            if (game.getInput().isKey(KeyEvent.VK_LEFT)) {
                if (reversedMovement)
                    posX += speed;
                else
                    posX -= speed;
                if (posX <= 0) {
                    posX = 0;
                }
                if (posX >= (limit - width)) {
                    posX = limit - width;
                }
            }
            if (game.getInput().isKeyUp(KeyEvent.VK_U)) {
                System.out.println("Game reinitialized");
                game.reinitializeGame();
            }
            if (game.getInput().isKey(KeyEvent.VK_ENTER)) {
                game.setLeveLPassed(false);
            }
            if (game.getInput().isKey(KeyEvent.VK_RIGHT)) {
                if (reversedMovement)
                    posX -= speed;
                else
                    posX += speed;
                if (posX >= (limit - width)) {
                    posX = limit - width;
                }
                if (posX <= 0) {
                    posX = 0;
                }
            }
    }
    @Override
    public void render(Game game, Renderer r) {
        r.drawImage(objectImage[size-1], posX,posY);
    }
    public void setToInitialPosition()
    {
        this.posX =320;
        this.posY=544;
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
        if(changeSize==-1) {
            if (size > 1) {
                size += changeSize;
                width = objectImage[size - 1].getWidth();
                height = objectImage[size - 1].getHeight();
            }
        }
        else
        {
            if(changeSize==1)
            {
                if(size<5)
                {
                    size+=changeSize;
                    width=objectImage[size-1].getWidth();
                    height=objectImage[size-1].getHeight();
                }
            }
        }
    }
    public void changeSpeed(int speed)
    {
        if(speed>0 && this.speed<8) {
            this.speed += speed;
        }
        else
        {
            if(this.speed>=4)
            {
                this.speed+=speed;
            }
        }
    }
    public int getLives()
    {
        return lives;
    }
    public void changeLivesNo(int life)
    {
        lives+=life;
    }
}