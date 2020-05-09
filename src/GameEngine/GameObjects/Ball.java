package GameEngine.GameObjects;

import GameEngine.Image;
import GameEngine.Renderer;
import GameEngine.States.PlayState.PlayingState;

import java.awt.event.KeyEvent;

public class Ball extends GameObject {
    private Image objectImage;
    private int velX;
    private int velY;
    private boolean move=false;
    public Ball(PlayingState playingState) {
        this.playingState = playingState;
        this.tag = "ball";
        this.objectImage = playingState.getImagesLoader().getImage("ball");
        this.width=objectImage.getWidth();
        this.height=objectImage.getHeight();
        setToInitialPosition();
    }
    @Override
    public void update() {
        if(playingState.IsPaused()== true)//cand e pauza, obiectele nu mai sunt updatate
        {
            return;
        }
        if(playingState.getKeyboardInput().isKey(KeyEvent.VK_SPACE))
        {
            if(playingState.getLevelPassed()==false) {//daca nu facem aceasta verificare, atunci cand trecem un level
                playingState.setInstructionsPresented();
                move = true;               //si suntem in acel moment in care trebuie sa apasam enter pentru
            }                              //a trece la level-ul urmator, daca noi apasam space in loc de enter
        }                                  //atunci mingea ar incepe sa se miste, desi nu am confirmat
        if(move)                           //ca dorim sa incepem urmatorul level(nu am apasat enter)
                                           //mai exact, intre levele mingea ramane blocata,
        {                                  //aceasta avand void sa se miste doar dupa ce apasam tasta enter
            updatePosition();
            checkForBoundaries();
        }
        else
        {
            int playerX=playingState.getObjectsManager().getPlayerX();
            int playerWidth=playingState.getObjectsManager().getPlayerWidth();
            posX=playerX+playerWidth/2-width/2;//intre levele, dar si la inceperea unui nou level
        }                          //inainte de a apasa tasta space pentru a da voie mingii sa se miste normal
                                   //aceasta se misca pe axa OX odata concomitent cu player-ul
    }
    @Override
    public void render(Renderer r) {
        r.drawImage(objectImage, posX, posY);
    }
    private void updatePosition()
    {
        posX += velX;
        posY += velY;
    }
    private void checkForBoundaries() {
        if (posX < 0) {//verificare margine stanga
            reverseVelX();
        }
        if (posY < 30) {//verificare margine sus
            reverseVelY();
        }
        if (posX > (limit - width)) {//verificare margine dreapta
            reverseVelX();
        }
        if  (posY>limit)//verificare margine jos
        {
            playingState.changePlayerLives(-1);
            playingState.getObjectsManager().setPlayerNormalMovement();
            setToInitialPosition();
        }
    }
    public void setToInitialPosition()//mingea este pozitionata deasupra player-ului
    {
        this.posX=playingState.getObjectsManager().getPlayerX()+playingState.getObjectsManager().getPlayerWidth()/2;
        this.posY=playingState.getObjectsManager().getPlayerY()-height;
        setInitialVelocities();//se stabile vitezele pe axele OX, OY
    }
    private void setInitialVelocities()
    {
        this.velX=0;
        this.velY=-5-(playingState.getLevel()+1)/2;
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
    public void incrementVelY()//incrementam viteza mingii pe axa OY
    {
        velY+=Math.abs(velY)/velY;
    }
    public void increaseVelX(int val)
    {
        while(velX<=4 && val!=0) { //pe axa OX mingea poate avea viteza in intervalul [-5,5]
            ++velX;
            --val;
        }
    }
    public void decreaseVelX(int val)
    {
        while(velX>=-4 && val!=0)
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
}