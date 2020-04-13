package GameEngine.GameInput;
import GameEngine.GameEngine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {
    private GameEngine gameEngine;
    private int x;
    private int y;
    public MouseInput(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
        gameEngine.getWindow().getCanvas().addMouseListener(this);
        gameEngine.getWindow().getCanvas().addMouseMotionListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        gameEngine.getMenu().setPressed(true);
        }
    @Override
    public void mouseReleased(MouseEvent e) {
        gameEngine.getMenu().setPressed(false);
    }
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    public int getX() { return x; }
    public int getY() { return y; }
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {
        x=e.getX();
        y=e.getY();
    }
}
