package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;

import java.awt.event.KeyEvent;

public class Ball extends GameObject {
    Game game;
    private Image objectImage;
    private int velX = 0;
    private int velY = -5;
    private boolean move=false;
    public Ball(Game game) {
        this.game = game;
        this.tag = "ball";
        setToInitialPosition();
        this.objectImage = game.getImagesLoader().getBallImage();
        this.width=objectImage.getWidth();
        this.height=objectImage.getHeight();
    }

    @Override
    public void update(Game game, float dt) {
        if(game.getInput().isKey(KeyEvent.VK_SPACE))
        {
            if(game.getLevelPassed()==false) {//bug aparut, nu functioneaza fara
                move = true;
            }
        }
        /*if(game.getInput().isKey(KeyEvent.VK_A))
        {
            posX-=velX;
        }
        if(game.getInput().isKey(KeyEvent.VK_D))
        {
            posX+=velX;
        }
        if(game.getInput().isKey(KeyEvent.VK_W))
        {
            posY-=velY;
        }
        if(game.getInput().isKey(KeyEvent.VK_S))
        {
            posY+=velY;
        }*/
        if(move)
        {
            checkForBoundaries();
        }
        else
        {
            GameObject obj;   //ne permite la inceputul jocului sa miscam mingea concomitent cu playerul
            for(int i = 0; i< game.getObjectsManager().getObjects().size(); ++i)
            {
                obj= game.getObjectsManager().getObjects().get(i);
                if(obj.getTag()=="player") {
                    posX=((Player)obj).getPosX()+obj.getWidth()/2-width/2;
                }
            }
        }
    }

    public void reverseVelX() {
        velX = -velX;
        posX+=velX;
    }

    public void reverseVelY()
    {
        velY=-velY;
        posY+=velY;
    }

    private void checkForBoundaries() {
        posX += velX;
        posY += velY;
        if (posX < 0) {
            game.getCollider().resetHits();
            reverseVelX();
        }
        if (posY < 0) {
            game.getCollider().resetHits();
            reverseVelY();
        }
        if (posX > (limit - 30)) {
            game.getCollider().resetHits();
            reverseVelX();
        }
        if  (posY>limit)
        {
            game.setLost(true);
            dead=true;
        }

    }
    public void incrementVelX()
    {
        ++velX;
    }
    public void decrementVelX()
    {
        --velX;
    }
    @Override
    public void render(Game gc, Renderer r) {
        r.drawImage(objectImage, posX, posY);
    }
    public void setToInitialPosition()
    {
        this.posX = 340;
        this.posY = 519;
        this.velX=0;
        this.velY=-5;//-5;
        move=false;
    }
    public void changeVelY()
    {
        velY+=velY/Math.abs(velY);
    }
}