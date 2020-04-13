package GameEngine.GameInput;

import GameEngine.GameEngine;
import java.awt.event.*;
import GameEngine.Game;

public class KeyboardInput implements KeyListener {
    private GameEngine gameEngine;
    private final int NUM_KEYS=256;
    private boolean [] keys=new boolean[NUM_KEYS];
    private boolean [] keysLast=new boolean[NUM_KEYS];//starea key-urilor la ultimul frame

    public KeyboardInput(GameEngine engine)
    {
        this.gameEngine=engine;
        engine.getWindow().getCanvas().addKeyListener(this);
    }

    public void update()
    {
        for(int i=0;i<NUM_KEYS;++i)
        {
            keysLast[i]=keys[i];
        }
    }

    public boolean isKey(int keyCode)
    {
        return keys[keyCode];
    }
    public boolean isKeyUp(int keyCode)//key a fost apasata in frame-ul trecut, dar a fost eliberat in acest frame
    {
        return !keys[keyCode] && keysLast[keyCode];
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {//functia este apelata de fiecare data cand apasam o tasta
        keys[e.getKeyCode()]=true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()]=false;
    }

}
