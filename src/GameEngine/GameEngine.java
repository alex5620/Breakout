package GameEngine;

import GameEngine.External.HighscoresDatabase;
import GameEngine.External.StoreSettings;
import GameEngine.GameInput.KeyboardInput;
import GameEngine.GameInput.MouseInput;
import GameEngine.Graphics.Renderer;
import GameEngine.Loaders.ImagesLoader;
import GameEngine.Loaders.SoundsLoader;
import GameEngine.States.*;
import GameEngine.States.PlayState.PlayState;

public class GameEngine implements Runnable {
    private static volatile GameEngine gameEngine;
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private KeyboardInput keyboardInput;
    private HighscoresDatabase highscores;
    private MouseInput mouseInput;
    private ImagesLoader ImagesLoader;
    private SoundsLoader SoundsLoader;
    private State currentState;
    private StoreSettings settings;
    private boolean running;
    public enum STATE{MainMenuState, PlayState, HighscoreMenuState, SettingsState, AboutState};      /*!< Starile in care poate tranzita obiectul de tip GameEngine*/
    private GameEngine(String title, int width, int height) {
        window = new Window(title, width, height);
        window.BuildGameWindow();
        highscores=new HighscoresDatabase();
        highscores.initialize();
        ImagesLoader= new ImagesLoader();
        SoundsLoader=new SoundsLoader();
        currentState=new MainMenuState(this);
        running = false;
        renderer = new Renderer(this);
        keyboardInput = new KeyboardInput(this);
        mouseInput=new MouseInput(this);
        settings=new StoreSettings();
        settings.setInstructionsPresented(false);
    }
    public static GameEngine getInstance(String title, int width, int height) {
        if (gameEngine == null) {
            synchronized (GameEngine.class) {
                if (gameEngine == null) {
                    gameEngine = new GameEngine(title, width, height);
                }
            }
        }
        return gameEngine;
    }
    private void update() {
        currentState.update();
        keyboardInput.update();
        mouseInput.update();
    }
    private void render() {
        renderer.process();
        currentState.render(renderer);
        window.update();
    }
    public void run() {
        long oldTime = System.nanoTime();
        long curentTime;
        final int framesPerSecond   = 60;
        final double timeFrame      = 1000000000 / framesPerSecond;
        while (running == true)
        {
            curentTime = System.nanoTime();
            if((curentTime - oldTime) > timeFrame)
            {
                update();
                render();
                oldTime = curentTime;
            }
        }
    }
    public void setState(STATE state) {
        switch (state)
        {
            case MainMenuState:
                currentState=new MainMenuState(this);
                break;
            case PlayState:
                currentState=new PlayState(this);
                break;
            case HighscoreMenuState:
                currentState=new HighscoreMenuState(this);
                break;
            case SettingsState:
                currentState=new SettingsMenuState(this);
                break;
            case AboutState:
                currentState=new AboutMenuState(this);
                break;
        }
    }
    public void updateHighscores(int score)
    {
        String name=settings.getName();
        if(name.length()>0)
            highscores.updateTable(name, score);
        else
            highscores.updateTable("Player", score);
    }
    public synchronized void start() {
        if(running == false)
        {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
        else
        {
            return;
        }
    }
    public synchronized void stop() {
        running = false;
        if(running == true)
        {
            running = false;
            try
            {
                thread.join();
            }
            catch(InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            return;
        }
    }
    public Window getWindow() {
        return window;
    }
    public Renderer getRenderer() {
        return renderer;
    }
    public ImagesLoader getImagesLoader() { return ImagesLoader; }
    public SoundsLoader getSoundsLoader(){ return SoundsLoader; }
    public KeyboardInput getKeyboardInput() { return keyboardInput; }
    public MouseInput getMouseInput() { return mouseInput; }
    public HighscoresDatabase getHighscore()
    {
        return highscores;
    }
    public StoreSettings getSettings() {
        return settings;
    }
}