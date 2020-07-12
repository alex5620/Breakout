package GameEngine.GameObjects;

import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;

import java.util.ArrayList;

public class ObjectsManager {
    private PlayingState playingState;
    private ArrayList<GameObject> objects;
    public ObjectsManager(PlayingState playing)
    {
        this.playingState = playing;
        objects = new ArrayList<>();
    }
    public void addObject(GameObject object)
    {
        objects.add(object);
    }
    public void update()
    {
        if(playingState.IsPaused()==false) {
            for (int i = 0; i < objects.size(); ++i) {
                objects.get(i).update();
                if (objects.get(i).isDead()) {
                    if ((objects.get(i).getTag().equals("brick"))) {
                        if(((Brick)objects.get(i)).isSpecialBrick())
                            playingState.increaseScore(2);
                        else
                            playingState.increaseScore(1);
                        int x=objects.get(i).getPosX() + ((Brick) objects.get(i)).getWidth() / 2;
                        int y=objects.get(i).getPosY() + ((Brick) objects.get(i)).getHeight();
                        generateMagicObjects(x, y);
                    }
                    objects.remove(i);
                    i--;
                }
            }
        }
    }
    public void render(Renderer renderer)
    {
        int size=objects.size();
        for (int i = 0; i < size; ++i) {
            if (playingState.getLevelPassed()==false)
                objects.get(i).render(renderer);
            else {
                if (objects.get(i) instanceof Ball || objects.get(i) instanceof Paddle)
                    objects.get(i).render(renderer);
            }
        }
    }
    public void generateMagicObjects(int x, int y)
    {
        ObjectsFactory factory= ObjectsFactory.getInstance();
        int val=(int)(Math.random()*100);
        if(val<12) {
            boolean repeat;
            do {
                repeat=false;
                val = (int) (Math.random() * 10);
                if(checkIfLaserExists() && val==9 || getPaddle().getSize()==1 && val ==1
                || getPaddle().getSize()==5 && val ==0)
                {
                    repeat=true;
                }
            }while(repeat==true);
            switch (val) {
                case 0:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 1,1));
                    break;
                case 1:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 0, 1));
                    break;
                case 2:
                    addObject(((MagicObject)(factory.getObject(playingState,"magic"))).setDetails(x, y, 3,1));
                    break;
                case 3:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 3,0));
                    break;
                case 4:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 4,0));
                    break;
                case 5:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 4,1));
                    break;
                case 6:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 0,0));
                    break;
                case 7:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 1, 0));
                    break;
                case 8:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 2,0));
                    break;
                case 9:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, 2,1));
            }
        }
    }
    public boolean checkIfLaserExists()
    {
        for(int i=0;i<objects.size();++i)
        {
            if(objects.get(i).getTag().equals("laser"))
            {
                return true;
            }
        }
        return false;
    }
    public void addLaser()
    {
        if(checkIfLaserExists())
        {
            return;
        }
        objects.add(ObjectsFactory.getInstance().getObject(playingState, "laser"));
    }
    public void destroyAlmostAllBricks()
    {
        for (GameObject obj : objects) {
            if (obj.getTag().equals("brick") && Brick.getBricksNumber()>1) {
                obj.setDead(true);
            }
        }
    }
    public void destroyBonusesAndLaser()
    {
        for(GameObject obj: objects)
        {
            if(obj.getTag().equals("magic") || obj.getTag().equals("laser"))
            {
                obj.setDead(true);
            }
        }
    }
    public void setPaddleBallToInitial()
    {
        getBall().setToInitialPosition();
        getPaddle().setToInitialPosition();
    }
    public void setPaddleNormalMovement() {
        getPaddle().setReversedMovement(false);
    }
    public int getPaddleLives() {
        return getPaddle().getLives();
    }
    public void changePaddleLives(int life) {
        getPaddle().changeLivesNo(life);
    }
    public void setPaddleReversedMovement()
    {
        getPaddle().reverseMovement();
    }
    public void changePaddleSize(int changeSize)
    {
        getPaddle().changeSize(changeSize);
    }
    public void changePaddleSpeed(int speed)
    {
        getPaddle().changeSpeed(speed);
    }
    public void incrementBallYVel() {
        getBall().incrementVelY();
    }
    public ArrayList<GameObject> getObjects()
    {
        return objects;
    }
    public Paddle getPaddle()
    {
        for(GameObject obj:objects)
        {
            if(obj.getTag().equals("paddle"))
            {
                return (Paddle)obj;
            }
        }
        return null;
    }
    public Ball getBall()
    {
        for(GameObject obj:objects) {
            if (obj.getTag().equals("ball")) {
                return (Ball)obj;
            }
        }
        return null;
    }
    public Laser getLaser()
    {
        for(GameObject obj:objects) {
            if (obj.getTag().equals("laser")) {
                return (Laser)obj;
            }
        }
        return null;
    }
    public int getPaddleX() {
        return getPaddle().getPosX();
    }
    public int getPaddleY() {
        return getPaddle().getPosY();
    }
    public int getPaddleWidth() {
        return getPaddle().getWidth();
    }
    public boolean isPaddleMovementReversed()
    {
        return getPaddle().isMovementReversed();
    }
}
