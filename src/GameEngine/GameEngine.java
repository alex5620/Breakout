package GameEngine;

import GameEngine.GameInput.KeyboardInput;
import GameEngine.GameInput.MouseInput;
import GameEngine.Loaders.ImagesLoader;
import GameEngine.Loaders.SoundsLoader;
import GameEngine.States.*;
import GameEngine.States.PlayState.PlayState;
import GameEngine.States.PlayState.PlayingState;

import static java.lang.Thread.sleep;

public class GameEngine implements Runnable {
    private Thread thread;
    private Window window;
    private Renderer renderer;
    private KeyboardInput keyboardInput;
    private HighScoresDataBase highscores;
    private MouseInput mouseInput;
    private ImagesLoader ImagesLoader;
    private SoundsLoader SoundsLoader;
    private State currentState;
    private SaveSettings settings;
    private boolean running;
    private final double UPDATE_CAP = 1.0 / 60.0;
    private final int width = 768, height = 640;
    private int scale = 1;
    private final String title = "Breakout";
    public enum STATE{MainMenuState, PlayState, HighscoreMenuState, SettingsState, AboutState};
    public GameEngine() {
        currentState=new MainMenuState(this);
        highscores=new HighScoresDataBase();
        running = false;
        thread = new Thread(this);
        window = new Window(this);
        renderer = new Renderer(this);
        ImagesLoader= new ImagesLoader();
        SoundsLoader=new SoundsLoader();
        keyboardInput = new KeyboardInput(this);
        mouseInput=new MouseInput(this);
        settings=new SaveSettings();
        settings.setInstructionsPresented(false);
    }

    private void update() {
        currentState.update();
        keyboardInput.update();
        mouseInput.update();
    }
    private void render() {
        renderer.clear();
        renderer.process();
        currentState.render(renderer);
        window.update();
    }
    public void run() {
        running = true;
        boolean Render;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;//system time
        double passedTime = 0;
        double unprocessedTime = 0;

        int frames = 0;
        int fps = 0;
        double frameTime = 0;

        while (running) {
            Render = false;
            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;//timp nou-timp vechi
            lastTime = firstTime;

            unprocessedTime += passedTime;//se aduna diferenta de mai sus
            frameTime += passedTime;
            while (unprocessedTime >= UPDATE_CAP)//daca depaseste a 60-a parte din 1 atunci facem update
            {
                unprocessedTime -= UPDATE_CAP;
                Render = true;
                update();
                if (frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("Fps: " + fps);
                }
            }
            if (Render) {
                render();
                ++frames;
            } else {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
    public synchronized void start() {
        thread.run();
    }
    public synchronized void stop() {
        running = false;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public int getScale() {
        return scale;
    }
    public String getTitle() {
        return title;
    }
    public Window getWindow() {
        return window;
    }
    public Renderer getRenderer() {
        return renderer;
    }
    public boolean isRunning() {
        return running;
    }
    public ImagesLoader getImagesLoader() { return ImagesLoader; }
    public SoundsLoader getSoundsLoader(){ return SoundsLoader; }
    public KeyboardInput getKeyboardInput() { return keyboardInput; }
    public MouseInput getMouseInput() { return mouseInput; }
    public void updateHighscores(int score)
    {
        String name=settings.getName();
        if(name.length()>0)
            highscores.updateTable(name, score);
        else
            highscores.updateTable("Player", score);
    }
    public HighScoresDataBase getHighscore()
    {
        return highscores;
    }

    public SaveSettings getSettings() {
        return settings;
    }
}