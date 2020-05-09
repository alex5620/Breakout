package GameEngine.GameObjects;

import GameEngine.Image;
import GameEngine.Renderer;
import GameEngine.States.PlayState.PlayingState;

import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private Image objectImage;
    private int velX=5;
    private int size;
    private boolean reversedMovement;
    private int lives;
    public Player(PlayingState playingState){
        this.playingState = playingState;
        this.tag="player";
        size=3;
        setToInitialPosition();
        objectImage=playingState.getImagesLoader().getImage("player"+size);
        this.width=objectImage.getWidth();
        this.height=objectImage.getHeight();
        reversedMovement=false;
        lives=1;
    }
    @Override
    public void update() {
        if(playingState.IsPaused()== true) {//daca e pauza, nu se mai face update
            return;
        }
        if (playingState.getKeyboardInput().isKey(KeyEvent.VK_LEFT)) {//update pozitie jucator
            if (!reversedMovement)
                posX -= velX;
            else
                posX += velX;
            checkForBoundaries();
        }
        if (playingState.getKeyboardInput().isKey(KeyEvent.VK_RIGHT)) { //update pozitie jucator
            if (!reversedMovement)
                posX += velX;
            else
                posX -= velX;
            checkForBoundaries();
        }
    }
    @Override
    public void render(Renderer r) {
        r.drawImage(objectImage, posX,posY); //se randeaza imaginea corespunzatoare in functie de
    }                                                 //dimensiunea player-ului
    private void checkForBoundaries()            //se verifica limita din stanga si din dreapta a ferestrei
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
        this.posY=550;
    }
    public void setReversedMovement(boolean cond)
    {
        reversedMovement=cond;
    }
    public void reverseMovement()//inversam modul in care se misca player-ul pe axa OX
    {
        reversedMovement=!reversedMovement;
    }
    public void changeSize(int changeSize)
    {
        if((changeSize==-1 && size>1)||(changeSize==1 && size<5)) {
            size += changeSize;               //player-ul poate avea dimensiunea cuprinsa in intervalul [1,5]
            objectImage=playingState.getImagesLoader().getImage("player"+size);
            width = objectImage.getWidth();//in functie de noua dimensiune sunt updatate
            height = objectImage.getHeight();//inaltimea si latimea
        }
    }
    public void changeSpeed(int speed)
    {
        if((speed>0 && this.velX <8)||(speed<0 && this.velX>3))//player-ul poate avea viteza cuprinsa intre [3,8]
            this.velX += speed;
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