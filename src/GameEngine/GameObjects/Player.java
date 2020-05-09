package GameEngine.GameObjects;

import GameEngine.Game;
import GameEngine.Image;
import GameEngine.Renderer;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private Image objectImages[];
    private int velX=5;
    private int size;
    private boolean reversedMovement;
    private int lives;
    public Player(Game game){
        this.game = game;
        this.tag="player";
        size=3;
        setToInitialPosition();
        objectImages=new Image[5];
        this.objectImages= game.getImagesLoader().getPlayerImages();
        this.width=objectImages[size-1].getWidth();
        this.height=objectImages[size-1].getHeight();
        reversedMovement=false;
        lives=1;
    }
    @Override
    public void update() {
        if(game.getKeyboardInput().isKeyUp(KeyEvent.VK_P))//pauza joc
        {
            game.setPause();
        }
        if(game.getState()== Game.STATE.pause) {//daca e pauza, nu se mai face update
            return;
        }
        if (game.getKeyboardInput().isKey(KeyEvent.VK_LEFT)) {//update pozitie jucator
            if (!reversedMovement)
                posX -= velX;
            else
                posX += velX;
            checkForBoundaries();
        }
        if (game.getKeyboardInput().isKey(KeyEvent.VK_U)) {   //se distrug toate caramizile, in afara de una
            if(game.getLevelPassed()==false && game.isInstructionsPresented())
                game.destroyAlmostAllBricks();              //aceasta functionalitate e lasata doar pentru verificarea
        }                                               //corectitudinii functionalitatii jocului
        if (game.getKeyboardInput().isKey(KeyEvent.VK_ENTER)) { //intre levele trebuie apasata tasta enter
            game.setLeveLPassed(false);                 //pentru a putea trece la levelul urmator
        }
        if (game.getKeyboardInput().isKey(KeyEvent.VK_RIGHT)) { //update pozitie jucator
            if (!reversedMovement)
                posX += velX;
            else
                posX -= velX;
            checkForBoundaries();
        }
    }
    @Override
    public void render(Renderer r) {
        r.drawImage(objectImages[size-1], posX,posY); //se randeaza imaginea corespunzatoare in functie de
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
            width = objectImages[size - 1].getWidth();//in functie de noua dimensiune sunt updatate
            height = objectImages[size - 1].getHeight();//inaltimea si latimea
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