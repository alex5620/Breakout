package GameEngine.GameInput;
import GameEngine.GameEngine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {
    private final int NUM_BUTTONS=5;
    private boolean [] buttons;
    private boolean [] buttonsLast;
    private int x;
    private int y;
    public MouseInput(GameEngine gameEngine)
    {
        buttons=new boolean[NUM_BUTTONS];
        buttonsLast=new boolean[NUM_BUTTONS];
        gameEngine.getWindow().getCanvas().addMouseListener(this);
        gameEngine.getWindow().getCanvas().addMouseMotionListener(this);
    }
    public void update()
    {
        for(int i=0;i<NUM_BUTTONS;++i)
        {
            buttonsLast[i]=buttons[i];
        }
    }
    public boolean isClickOnePressedOnce()
    {
        return !buttons[1] && buttonsLast[1];
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()]=true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()]=false;
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        x=e.getX();
        y=e.getY();
    }
    public int getX() { return x; }
    public int getY() { return y; }
}
