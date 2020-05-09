package GameEngine;

import GameEngine.GameInput.KeyboardInput;
import GameEngine.GameInput.MouseInput;
import GameEngine.GameObjects.*;
import GameEngine.Loaders.ImagesLoader;
import GameEngine.Loaders.SoundsLoader;

public class Game {
    private GameEngine gameEngine;
    private Renderer renderer;
    private ObjectsManager objectsManager;
    private Collider collider;
    private int score;
    private boolean lost, levelPassed;
    private int level;
    private boolean instructionsPresented;
    public enum STATE{playing, lost, won, pause};
    private STATE gstate;
    public Game(GameEngine engine)
    {
        gstate=STATE.playing;
        this.gameEngine=engine;
        renderer=engine.getRenderer();
        objectsManager=new ObjectsManager(this);
        lost = false;
        levelPassed = false;
        instructionsPresented=false;
    }
    public void init() {
        ObjectFactory factory=ObjectFactory.getInstance();
        level=1;
        objectsManager.addObject(factory.getPlayer(this));
        objectsManager.addObject(factory.getBall(this));
        MapGenerator.generateMap(this,level);
        collider = new Collider(this);
        score=0;
    }
    public void update() {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if (gstate != STATE.pause) {
            objectsManager.update();
            checkIfLevelPassed();
            checkIfLost();
            collider.update();
            if (gstate == STATE.lost && gameEngine.getMouseInput().isClick1Up()) {
                if (checkIfPlayAgain()) {
                    gstate = STATE.playing;
                    restartGame();
                }
                if (checkIfGoToMenuAfterLosing()) {
                    gameEngine.setState(GameEngine.STATE.MENU);
                    gstate = STATE.playing;
                    lost = false;
                }
            }
            if (gstate == STATE.won) {
                objectsManager.destroyAllObjects();
                lost = false;
                levelPassed = false;
                if (checkIfGoBackAfterWinning() && gameEngine.getMouseInput().isClick1Up()) {
                    gstate = STATE.playing;
                    gameEngine.setState(GameEngine.STATE.MENU);
                }
            }
        }
        else
        {
            objectsManager.update();
            if(checkIfExit())
                if(gameEngine.getMouseInput().isClick1Up())
                {
                    gameEngine.getMenu().updateHighscores(score);
                    objectsManager.destroyAllObjects();
                    objectsManager.update();
                    gstate=STATE.playing;
                    gameEngine.setState(GameEngine.STATE.MENU);
                }
        }
    }
    public void render() {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if(gstate==STATE.pause)
        {
            renderer.drawImage(gameEngine.getImagesLoader().getBackgroundImage(), 0, 0);
            renderer.drawImage(gameEngine.getImagesLoader().getPauseImage(), 120, 70);
            renderer.drawImage(gameEngine.getImagesLoader().getExitImage(), 619, 580);
            if(checkIfExit())
                renderer.drawImage(gameEngine.getImagesLoader().getExit2Image(), 614, 575);
            objectsManager.render();
            printLevel_Score();
            renderPlayerLives();
        }
        if (gstate == STATE.playing) {
            renderer.drawImage(gameEngine.getImagesLoader().getBackgroundImage(), 0, 0);
            objectsManager.render();
            if(level==1 && ((Ball)objectsManager.getBall()).isMoving()==false && !instructionsPresented)
            {
                renderer.drawImage(gameEngine.getImagesLoader().getHowToPlayImage(), 150, 250);
            }
            if (!lost) {
                if (!levelPassed) {
                    printLevel_Score();
                    renderPlayerLives();
                } else {
                    if(gstate!=STATE.won)
                        printLevelPassedMessage();
                }
            }
        }
        if(gstate==STATE.lost)
        {
            printLoseMessage(renderer);
            renderer.drawImage(gameEngine.getImagesLoader().getLostMenuImage(), 0, 0);
            if(checkIfPlayAgain())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getPlayAgainImage(), 272, 215);
            }
            if(checkIfGoToMenuAfterLosing())
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBackToMenuImage(), 240, 293);
            }
        }
        if(gstate==STATE.won)
        {
            renderer.drawImage(gameEngine.getImagesLoader().getWonImage(), 0, 0);
            renderer.drawText("Your score: "+score , 265, 210, 0xffff0606);
            if(score>gameEngine.getMenu().getMinimumScore())
                renderer.drawText("Congrats, you are in top 10!" , 190, 280, 0xffff0606);
            if(checkIfGoBackAfterWinning())
            {
            renderer.drawImage(gameEngine.getImagesLoader().getBackToMenu2Image(), 248, 386);
            }
        }
    }
    public void checkIfLevelPassed()//0 bricks ramase-> level up
    {
        if (Brick.getBricksNumber() == 0 && lost == false) {
                if(level==5)
                {
                    gameEngine.getMenu().updateHighscores(score);
                    gstate=STATE.won;
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
    public void printLoseMessage(Renderer renderer)
    {
        renderer.drawText("    GAME OVER", 275, 60, 0xff0000ff);
        renderer.drawText("     SCORE: " + score, 280, 100, 0xff0000ff);
    }
    public void printLevelPassedMessage()
    {
        renderer.drawText("Level" + level, 320, 200, 0xff0000ff);
        renderer.drawText("Press enter to continue ", 210, 250, 0xff0000ff);
    }
    public void printLevel_Score()
    {
        renderer.drawText("Level" + level, 30, 6, 0xffff0000);
        renderer.drawText("SCORE: " + score, 600, 7, 0xffff0000);
    }
    public void incremetScore() {
        score += 5*level;
    }
    public void setLost(boolean lost) {
        this.lost = lost;
        gstate=STATE.lost;
        objectsManager.destroyAllObjects();
        gameEngine.getMenu().updateHighscores(score);
    }
    public void setLeveLPassed(boolean levelPassed)
    {
        this.levelPassed=levelPassed;
    }
    public void restartGame()
    {
        if(lost==true)
        {
            init();
            lost=false;
        }
    }
    private void renderPlayerLives()
    {
        int lives=objectsManager.getPlayerLives();
        for(int i=0;i<lives;++i) {
            renderer.drawImage(gameEngine.getImagesLoader().getHeartImage(), 135+i*20, 14);
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
    public Renderer getRenderer()
    {
        return renderer;
    }
    public ImagesLoader getImagesLoader() { return gameEngine.getImagesLoader(); }
    public boolean getLost() { return lost; }
    public void checkIfLost()
    {
        if(gstate!=STATE.won && objectsManager.getPlayerLives()==0)
        {
            setLost(true);
        }
    }
    public void increaseScore(int score)
    {
        this.score+=score;
    }
    public void setPause()
    {
        if(gstate==STATE.playing && levelPassed!=true)
            gstate=STATE.pause;
        else {
            if (gstate == STATE.pause)
                   gstate = STATE.playing;
        }
    }
    public STATE getState()
    {
        return gstate;
    }
    public int getLevel()
    {
        return level;
    }
    public void setInstructionsPresented() { instructionsPresented=true; }
    public KeyboardInput getKeyboardInput() { return gameEngine.getKeyboardInput(); }
    public MouseInput getMouseInput() { return gameEngine.getMouseInput(); }
    public boolean isInstructionsPresented() {
        return instructionsPresented;
    }
    public boolean checkIfPlayAgain()
    {
        int x=getMouseInput().getX();
        int y=getMouseInput().getY();
        if((x > 272) && (x < 470) && (y > 217) && (y < 270))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfGoToMenuAfterLosing()
    {
        int x=getMouseInput().getX();
        int y=getMouseInput().getY();
        if((x > 230) && (x < 520) && (y > 295) && (y < 348))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfGoBackAfterWinning()
    {
        int x=getMouseInput().getX();
        int y=getMouseInput().getY();
        if((x > 250) && (x < 505) && (y > 386) && (y < 441))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfExit()
    {
        int x=getMouseInput().getX();
        int y=getMouseInput().getY();
        if((x>619 && y>580))
        {
            return true;
        }
        return false;
    }
}
