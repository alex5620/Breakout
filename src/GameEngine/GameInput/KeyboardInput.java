package GameEngine.GameInput;

import GameEngine.GameEngine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyboardInput implements KeyListener {
    private final int NUM_KEYS=256;
    private boolean [] keys;
    private boolean [] keysLast;
    public KeyboardInput(GameEngine engine)
    {
        keys=new boolean[NUM_KEYS];
        keysLast=new boolean[NUM_KEYS];
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
    public boolean isKeyPressedOnce(int keyCode)
    {
        return !keys[keyCode] && keysLast[keyCode];
    }
    @Override
    public void keyPressed(KeyEvent e) {//functia este apelata de fiecare data cand apasam o tasta
        keys[e.getKeyCode()]=true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()]=false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
