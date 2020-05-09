package GameEngine.States;

import GameEngine.GameEngine;
import GameEngine.Renderer;

public class SettingsMenuState extends State{
    private String name;
    public SettingsMenuState(GameEngine gameEngine) {
        super(gameEngine);
        name=gameEngine.getSettings().getName();
    }
    @Override
    public void update() {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        if (gameEngine.getMouseInput().isClick1Up()) {
            if (checkIfChooseName(x, y)) {
                char car = 'A';
                int sx = x - 185;
                int sy = y - 283;
                sx = sx / 96;
                sy = sy / 45;
                car += sx + 4 * sy;
                if (car <= 'Z') {
                    if (name.length() < 14)
                        name += car;
                } else {
                    if (name.length() > 0) {
                        name = name.substring(0, name.length() - 1);
                    }
                }
            }
            if (checkIfBack(x, y)) {
                gameEngine.getSettings().setName(name);
                gameEngine.setState(GameEngine.STATE.MainMenuState);
            }
        }
    }
    @Override
    public void render(Renderer renderer) {
        int x = gameEngine.getMouseInput().getX();
        int y = gameEngine.getMouseInput().getY();
        renderer.drawImage(gameEngine.getImagesLoader().getImage("settingsmenu"), 0, 0);
        renderer.drawText("Set Name:", 195, 208, 0xffff0606);
        renderer.drawText(name, 190, 235, 0xffff0606);
        if (checkIfBack(x, y)) {
            renderer.drawImage(gameEngine.getImagesLoader().getImage("back_reset"), 635, 565);
        }
        if (checkIfChooseName(x, y)) {
            if (x < 375 || y < 550) {
                int sx = x - 185;
                int sy = y - 283;
                sx = sx / 96;
                sy = sy / 45;
                renderer.drawImage(gameEngine.getImagesLoader().getImage("letter"), 208 + sx * 92, 280 + sy * 44);
            } else {
                renderer.drawImage(gameEngine.getImagesLoader().getImage("delete"), 390, 542);
            }
        }
    }
    public boolean checkIfChooseName(int x, int y)
    {
        if((x > 200) && (x < 550) && (y > 280) && (y < 590))
        {
            return true;
        }
        return false;
    }
    public boolean checkIfBack(int x, int y)
    {
        if((x>645) && (x<770) && (y>575) && (y<628))
        {
            return true;
        }
        return false;
    }
}
