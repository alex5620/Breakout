package GameEngine;

import GameEngine.GameInput.KeyboardInput;
import GameEngine.GameObjects.Ball;
import GameEngine.GameObjects.Brick;
import GameEngine.GameObjects.ObjectsManager;
import GameEngine.GameObjects.Player;
import GameEngine.Loaders.ImagesLoader;
import GameEngine.Loaders.SoundsLoader;

public class Game {
    private GameEngine gameEngine;
    private Renderer renderer;
    private ObjectsManager objectsManager;
    private KeyboardInput input;
    private Collider collider;
    private int score;
    private boolean lost, levelPassed;
    private int level;
    public enum STATE{playing, lost, won};
    private STATE gstate;
    public Game(GameEngine engine)
    {
        gstate=STATE.playing;
        this.gameEngine=engine;
        renderer=engine.getRenderer();
        input = new KeyboardInput(gameEngine);
        objectsManager=new ObjectsManager(this);
        lost = false;
        levelPassed = false;
    }
    public void init() {
        level=1;
        objectsManager.addObject(new Player(this));
        objectsManager.addObject(new Ball(this));
        generateMap();
        collider = new Collider(this);
        score=0;
    }
    public void update(float dt) {
            objectsManager.update(dt);
            checkIfLevelPassed();
            input.update();
            collider.update();
            if(gstate==STATE.lost && gameEngine.getMenu().pressCondition())
            {
                int x=gameEngine.getMenu().getInput().getX();
                int y=gameEngine.getMenu().getInput().getY();
                if((x>272) && (x<470) && (y>217) && (y<270))
                {
                    gstate=STATE.playing;
                    restartGame();
                }
                if((x>230) && (x<520) && (y>295) && (y<348))
                {
                    gameEngine.setState(GameEngine.STATE.MENU);
                    gstate=STATE.playing;
                    lost=false;
                }
                gameEngine.getMenu().setPressed(false);
            }
            if(gstate==STATE.won)
            {
                int x=gameEngine.getMenu().getInput().getX();
                int y=gameEngine.getMenu().getInput().getY();
                objectsManager.destroyAllObjects();
                lost=false;
                levelPassed=false;
                if((x>250) && (x<505) && (y>386) && (y<441) && gameEngine.getMenu().pressCondition())
                {
                    System.out.println("Cevaaaaaaaaa");
                    gstate=STATE.playing;
                    gameEngine.setState(GameEngine.STATE.MENU);
                }
                gameEngine.getMenu().setPressed(false);
            }
    }
    public void render()
    {
        if(gstate==STATE.playing) {
            renderer.drawImage(gameEngine.getImagesLoader().getBackgroundImage(), 0, 0);
            objectsManager.render();
            if (!lost) {
                if (!levelPassed) {
                    printLevel_Score();
                } else {
                    if(gstate!=STATE.won)
                        printLevelPassedMessage();
                }
            }
        }
        if(gstate==STATE.lost)
        {
            int x=gameEngine.getMenu().getInput().getX();
            int y=gameEngine.getMenu().getInput().getY();
            printLoseMessage(renderer);
            renderer.drawImage(gameEngine.getImagesLoader().getLostMenuImage(), 0, 0);
            if((x>272) && (x<470) && (y>217) && (y<270))
            {
                renderer.drawImage(gameEngine.getImagesLoader().getPlayAgainImage(), 272, 215);
            }
            if((x>230) && (x<520) && (y>295) && (y<348))
            {
                renderer.drawImage(gameEngine.getImagesLoader().getBackToMenuImage(), 240, 293);
            }
        }
        if(gstate==STATE.won)
        {
            int x=gameEngine.getMenu().getInput().getX();
            int y=gameEngine.getMenu().getInput().getY();
            renderer.drawImage(gameEngine.getImagesLoader().getWonImage(), 0, 0);
            renderer.drawText("Your score: "+score , 225, 210, 0xffff0606);
            if(score>gameEngine.getMenu().getMinimumScore())
                renderer.drawText("Congrats, you are in top 10!" , 100, 280, 0xffff0606);
            renderer.drawText("Your score: "+score , 225, 210, 0xffff0606);
            if((x>250) && (x<505) && (y>386) && (y<441))
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
                    ++level;
                    gstate=STATE.won;
                    return;
                }
                else {
                    if(level<5) {
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
        objectsManager.setPlayerNormalMovements();
        generateMap();
    }
    public void printLoseMessage(Renderer renderer)
    {
        renderer.drawText("GAME OVER", 275, 60, 0xff0000ff);
        renderer.drawText(" SCORE: " + score, 280, 100, 0xff0000ff);
    }
    public void printLevelPassedMessage()
    {
        renderer.drawText("Level" + level, 300, 200, 0xff000000);
        renderer.drawText("Press enter to continue ", 150, 250, 0xff000000);
    }
    public void printLevel_Score()
    {
        renderer.drawText("Level" + level, 50, 5, 0xffff0000);
        renderer.drawText("SCORE:" + score, 600, 5, 0xffff0000);
    }
    public KeyboardInput getInput() {
        return input;
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
    public void generateMap() { MapGenerator.generateMap(this,level);}
    public void reinitializeGame() {
        objectsManager.destroyAlmostAllBricks();
    }
    public boolean getLevelPassed() { return levelPassed; }
    public ObjectsManager getObjectsManager() { return objectsManager; }
    public Renderer getRenderer()
    {
        return renderer;
    }
    public ImagesLoader getImagesLoader() { return gameEngine.getImagesLoader(); }
    public SoundsLoader getSoundsLoader() { return gameEngine.getSoundsLoader(); }
    public boolean getLost() { return lost; }
    public int getScore() { return score; }
    public Collider getCollider() { return collider; }
}
