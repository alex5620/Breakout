package GameEngine.States.PlayState;

import GameEngine.GameEngine;
import GameEngine.GameInput.KeyboardInput;
import GameEngine.GameObjects.*;
import GameEngine.Loaders.ImagesLoader;
import GameEngine.Loaders.SoundsLoader;
import GameEngine.Graphics.Renderer;
import GameEngine.Collider;
import GameEngine.States.State;

import java.awt.event.KeyEvent;

public class PlayingState extends State {
    private PlayState playState;
    private ObjectsManager objectsManager;
    private Collider collider;
    private int score;
    private boolean paused;
    private boolean levelPassed;
    private int level;
    private boolean instructionsPresented;
    private int difficulty;
    public PlayingState(GameEngine gameEngine, PlayState playState) {
        super(gameEngine);
        gameEngine.getSoundsLoader().getSound("menu").stop();
        gameEngine.getSoundsLoader().getSound("background").loop();
        this.playState=playState;
        objectsManager=new ObjectsManager(this);
        levelPassed = false;
        paused =false;
        instructionsPresented=gameEngine.getSettings().getInstructionsPresented();
        if(gameEngine.getSettings().getIsEasy()==true)
        {
            difficulty=1;
        }
        else
        {
            difficulty=2;
        }
        initialize();
    }
    public void initialize() {
        ObjectsFactory factory= ObjectsFactory.getInstance();
        level=1;
        objectsManager.addObject(factory.getObject(this, "player"));
        objectsManager.addObject(factory.getObject(this, "ball"));
        MapGenerator.generateMap(this,level, difficulty);
        collider = new Collider(this);
        score=0;
    }
    @Override
    public void update() {
        if(getKeyboardInput().isKeyPressedOnce(KeyEvent.VK_P))
        {
            setPause();
        }
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if (paused == true) {
            if (gameEngine.getMouseInput().isClickOnePressedOnce() && checkIfExit(x, y)) {
                gameEngine.getSoundsLoader().getSound("buttonPressed2").play();
                Brick.resetBricksNumber();
                gameEngine.updateHighscores(score);
                gameEngine.getSettings().setScore(score);
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
        }
        else {
            if (getKeyboardInput().isKey(KeyEvent.VK_U)) {
                if(levelPassed==false && instructionsPresented)
                    destroyAlmostAllBricks();
            }
            if (getKeyboardInput().isKey(KeyEvent.VK_ENTER) && levelPassed==true) {
                setLeveLPassed(false);
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
        renderer.drawImage(gameEngine.getImagesLoader().getImage("background"), 0, 0);
        if(paused)
        {
            objectsManager.render(gameEngine.getRenderer());
            renderer.drawImage(gameEngine.getImagesLoader().getImage("pause"), 120, 70);
            renderer.drawImage(gameEngine.getImagesLoader().getImage("exit"), 619, 580);
            renderer.drawMarks(gameEngine);
            if(checkIfExit(x, y))
                renderer.drawImage(gameEngine.getImagesLoader().getImage("exit2"), 614, 575);
            printLevel_Score(renderer);
            renderPlayerLives(renderer);
        }
        else {
            objectsManager.render(gameEngine.getRenderer());
            if(levelPassed) {
                printLevelPassedMessage(renderer);
            }else {
                renderer.drawMarks(gameEngine);
                if (level == 1 && (objectsManager.getBall()).isMoving() == false && !instructionsPresented) {
                    renderer.drawImage(gameEngine.getImagesLoader().getImage("howtoplay"), 150, 250);
                }
                printLevel_Score(renderer);
                renderPlayerLives(renderer);
            }
        }
    }
    private void renderPlayerLives(Renderer renderer)
    {
        int lives=objectsManager.getPaddleLives();
        for(int i=0;i<lives;++i) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("heart"), 135+i*20, 14);
        }
    }
    public void checkIfLevelPassed()
    {
        if (Brick.getBricksNumber() == 0) {
            if(level==5)
            {
                score+=objectsManager.getPaddleLives()*300;
                gameEngine.updateHighscores(score);
                gameEngine.getSettings().setScore(score);
                playState.setCurrentPState(PlayState.PState.WonState);
            }
            else {
                if(level<5) {
                    objectsManager.destroyBonusesAndLaser();
                    ++level;
                    levelPassed = true;
                    LoadNewLevel();
                }
            }
        }
    }
    public void LoadNewLevel()
    {
        objectsManager.setPaddleBallToInitial();
        objectsManager.setPaddleNormalMovement();
        MapGenerator.generateMap(this,level, difficulty);
    }
    public void printLevelPassedMessage(Renderer renderer)
    {
        renderer.drawText("Level" + level, 320, 200, 0xff0000ff);
        renderer.drawText("Press enter to continue ", 210, 250, 0xff0000ff);
    }
    public void printLevel_Score(Renderer renderer)
    {
        renderer.drawText("Level" + level, 30, 6, 0xff00ff00);
        renderer.drawText("SCORE: " + score, 600, 7, 0xff00ff00);
    }
    public void incrementScore(int score)
    {
        this.score+=score;
    }
    public void increaseScore(int value) {
        score += 5*level*value;
    }
    public void setLost() {
        gameEngine.updateHighscores(score);
        gameEngine.getSettings().setScore(score);
        playState.setCurrentPState(PlayState.PState.LostState);
    }
    public void checkIfLost()
    {
        if(objectsManager.getPaddleLives()==0)
        {
            setLost();
        }
    }
    public void setLeveLPassed(boolean levelPassed)
    {
        this.levelPassed=levelPassed;
    }
    public void destroyAlmostAllBricks() {
        objectsManager.destroyAlmostAllBricks();
    }
    public boolean getLevelPassed() { return levelPassed; }
    public ObjectsManager getObjectsManager() { return objectsManager; }
    public ImagesLoader getImagesLoader() { return gameEngine.getImagesLoader(); }
    public SoundsLoader getSoundsLoader(){ return gameEngine.getSoundsLoader(); }
    public void setPause()
    {
        if(levelPassed==false) {
            paused =!paused;
            if(paused)
            {
                gameEngine.getSoundsLoader().getSound("background").stop();
            }
            else
            {
                gameEngine.getSoundsLoader().getSound("background").loop();
            }
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
        return paused;
    }
    public int getDifficulty(){
        return difficulty;
    }
}
