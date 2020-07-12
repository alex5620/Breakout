package GameEngine.States;

import GameEngine.GameEngine;
import GameEngine.Graphics.Renderer;
import GameEngine.Sound;

public class MainMenuState extends State{
    public MainMenuState(GameEngine gameEngine) {
        super(gameEngine);
        gameEngine.getSoundsLoader().getSound("menu").loop();
    }
    @Override
    public void update() {
        if(gameEngine.getMouseInput().isClickOnePressedOnce()) {
            int x = gameEngine.getMouseInput().getX();
            int y = gameEngine.getMouseInput().getY();
            if (checkIfPlay(x, y)) {
                gameEngine.setState(GameEngine.STATE.PlayState);
                gameEngine.getSoundsLoader().getSound("buttonPressed").play();
            }
            if (checkIfHighScores(x, y)) {
                gameEngine.setState(GameEngine.STATE.HighscoreMenuState);
                gameEngine.getSoundsLoader().getSound("buttonPressed").play();
            }
            if (checkIfSettings(x, y)) {
                gameEngine.setState(GameEngine.STATE.SettingsState);
                gameEngine.getSoundsLoader().getSound("buttonPressed").play();
            }
            if (checkIfAbout(x, y)) {
                gameEngine.setState(GameEngine.STATE.AboutState);
                gameEngine.getSoundsLoader().getSound("buttonPressed").play();
            }
            if(checkIfSoundEnabled(x, y))
            {
                Sound.reverseSoundSettings();
                if(Sound.isSoundEnabled())
                {
                    gameEngine.getSoundsLoader().getSound("menu").loop();
                }
                else
                {
                    gameEngine.getSoundsLoader().getSound("menu").stop();
                }
            }
            if (checkIfQuit(x, y)) {
                gameEngine.getSoundsLoader().getSound("buttonPressed").play();
                gameEngine.getWindow().closeWindow();
            }
        }
    }
    @Override
    public void render(Renderer renderer) {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        renderer.drawImage(gameEngine.getImagesLoader().getImage("menu"), 0, 0);
        if(Sound.isSoundEnabled()) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("soundenabled"), 685, 555);
        }
        else
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("sounddisabled"), 685, 555);
        }
        if (checkIfPlay(x, y)) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("play"), 266, 235);
        }
        if (checkIfHighScores(x, y)) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("highscores"), 195, 312);
        }
        if (checkIfSettings(x, y)) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("settings"), 240, 382);
        }
        if(checkIfAbout(x, y))
        {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("play"), 265, 460);
        }
        if (checkIfQuit(x, y)) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("quit"), 225, 538);
        }
    }
    public boolean checkIfPlay(int x, int y)
    {
        if((x>268) && (x<435) && (y>238) && (y<298))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfHighScores(int x, int y)
    {
        if((x>200) && (x<508) && (y>315) && (y<370))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfSettings(int x, int y)
    {
        if ((x>244) && (x<473) && (y>390) && (y<440))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfAbout(int x, int y)
    {
        if((x>268) && (x<435) && (y>465) && (y<525))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfSoundEnabled(int x, int y)
    {
        if((x>693) && (x<765) && (y>570) && (y<632))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfQuit(int x, int y)
    {
        if((x>230) && (x<490) && (y>545) && (y<600))
        {
            return true;
        }
        return false;
    }
}
