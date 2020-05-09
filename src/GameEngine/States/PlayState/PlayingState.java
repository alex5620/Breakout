package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.GameInput.KeyboardInput;
import GameEngine.GameObjects.*;
import GameEngine.Loaders.ImagesLoader;
import GameEngine.Renderer;
import GameEngine.Collider;
import GameEngine.States.State;

import java.awt.event.KeyEvent;

public class PlayingState extends State {
    private PlayState playState;
    private ObjectsManager objectsManager;
    private Collider collider;
    private int score;
    private boolean isPaused, levelPassed;
    private int level;
    private boolean instructionsPresented;
    public PlayingState(GameEngine gameEngine, PlayState playState) {
        super(gameEngine);
        this.playState=playState;
        objectsManager=new ObjectsManager(this);
        levelPassed = false;
        isPaused=false;
        instructionsPresented=gameEngine.getSettings().getInstructionsPresented();
        init();
    }
    public void init() {
        ObjectFactory factory=ObjectFactory.getInstance();
        level=1;
        objectsManager.addObject(factory.getObject(this, "player"));
        objectsManager.addObject(factory.getObject(this, "ball"));
        MapGenerator.generateMap(this,level);
        collider = new Collider(this);
        score=0;
    }
    @Override
    public void update() {
        if(getKeyboardInput().isKeyUp(KeyEvent.VK_P))//pauza joc
        {
            setPause();
        }
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if (isPaused == true) {
            if (gameEngine.getMouseInput().isClick1Up() && checkIfExit(x, y)) {
                gameEngine.updateHighscores(score);
                gameEngine.getSettings().setScore(score);
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
        } else {
            if (getKeyboardInput().isKey(KeyEvent.VK_U)) {   //se distrug toate caramizile, in afara de una
                if(levelPassed==false && instructionsPresented)
                    destroyAlmostAllBricks();              //aceasta functionalitate e lasata doar pentru verificare
            }
            if (getKeyboardInput().isKey(KeyEvent.VK_ENTER)) { //intre levele trebuie apasata tasta enter
                setLeveLPassed(false);                 //pentru a putea trece la levelul urmator
            }
            objectsManager.update();
            checkIfLevelPassed();
            checkIfLost();
            collider.update();
        }
    }

    @Override
    public void render(Renderer renderer) {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if(isPaused)
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("background"), 0, 0);
            renderer.drawImage(gameEngine.getImagesLoader().getImage("pause"), 120, 70);
            renderer.drawImage(gameEngine.getImagesLoader().getImage("exit"), 619, 580);
            objectsManager.render(gameEngine.getRenderer());
            if(checkIfExit(x, y))
                renderer.drawImage(gameEngine.getImagesLoader().getImage("exit2"), 614, 575);
            printLevel_Score(renderer);
            renderPlayerLives(renderer);
        }
        else {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("background"), 0, 0);
            objectsManager.render(gameEngine.getRenderer());
            if(levelPassed) {
                printLevelPassedMessage(renderer);
            }else {
                if (level == 1 && ((Ball) objectsManager.getBall()).isMoving() == false && !instructionsPresented) {
                    renderer.drawImage(gameEngine.getImagesLoader().getImage("howtoplay"), 150, 250);
                }
                printLevel_Score(renderer);
                renderPlayerLives(renderer);
            }
        }
    }
    public void checkIfLevelPassed()//0 bricks ramase-> level up
    {
        if (Brick.getBricksNumber() == 0) {
            if(level==5)
            {
                gameEngine.updateHighscores(score);
                gameEngine.getSettings().setScore(score);
                playState.setCurrentPState(PlayState.PState.WonState);
            }
            else {
                if(level<5) {
                    objectsManager.destroyAllBonuses();
                    ++level;
                    levelPassed = true;
                    LoadNewLevel();
                }
            }
        }
    }
    public void LoadNewLevel()
    {
        objectsManager.setPlayerBallToInitial();
        objectsManager.setPlayerNormalMovement();
        MapGenerator.generateMap(this,level);
    }
    public void printLevelPassedMessage(Renderer renderer)
    {
        renderer.drawText("Level" + level, 320, 200, 0xff0000ff);
        renderer.drawText("Press enter to continue ", 210, 250, 0xff0000ff);
    }
    public void printLevel_Score(Renderer renderer)
    {
        renderer.drawText("Level" + level, 30, 6, 0xffff0000);
        renderer.drawText("SCORE: " + score, 600, 7, 0xffff0000);
    }
    public void incremetScore() {
        score += 5*level;
    }
    public void setLost() {
        gameEngine.updateHighscores(score);
        gameEngine.getSettings().setScore(score);
        playState.setCurrentPState(PlayState.PState.LostState);
    }
    public void setLeveLPassed(boolean levelPassed)
    {
        this.levelPassed=levelPassed;
    }
    private void renderPlayerLives(Renderer renderer)
    {
        int lives=objectsManager.getPlayerLives();
        for(int i=0;i<lives;++i) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("heart"), 135+i*20, 14);
        }
    }
    public void changePlayerLives(int life)
    {
        objectsManager.changePlayerLives(life);
    }
    public void destroyAlmostAllBricks() {
        objectsManager.destroyAlmostAllBricks();
    }
    public boolean getLevelPassed() { return levelPassed; }
    public ObjectsManager getObjectsManager() { return objectsManager; }
    public ImagesLoader getImagesLoader() { return gameEngine.getImagesLoader(); }
    public void checkIfLost()
    {
        if(objectsManager.getPlayerLives()==0)
        {
            setLost();
        }
    }
    public void increaseScore(int score)
    {
        this.score+=score;
    }
    public void setPause()
    {
        if(levelPassed==false) {
            isPaused =!isPaused;
        }
    }
    public int getLevel()
    {
        return level;
    }
    public void setInstructionsPresented() {
        instructionsPresented=true;
        gameEngine.getSettings().setInstructionsPresented(true);
    }
    public KeyboardInput getKeyboardInput() { return gameEngine.getKeyboardInput(); }
    public boolean checkIfExit(int x, int y)
    {
        if((x>619 && y>580))
        {
            return true;
        }
        return false;
    }
    public boolean IsPaused()
    {
        return isPaused;
    }
}
