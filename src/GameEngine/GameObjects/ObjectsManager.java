package GameEngine.GameObjects;

import GameEngine.Game;

import java.util.ArrayList;

public class ObjectsManager {
    Game game;
    private ArrayList<GameObject> objects;
    public ObjectsManager(Game game)
    {
        this.game = game;
        objects = new ArrayList<GameObject>();
    }
    public void addObject(GameObject object)
    {
        objects.add(object);
    }
    public void update(float dt)
    {
        for (int i = 0; i < objects.size(); ++i) {
            objects.get(i).update(game, dt);
            if (objects.get(i).isDead()) {
                if((!game.getLost()) && (objects.get(i) instanceof Brick)) {
                    game.incremetScore();
                    generateMagicObjects(objects.get(i).getPosX()+objects.get(i).getWidth()/2, objects.get(i).getPosY()+objects.get(i).getHeight());
                }
                objects.remove(i);
                i--;
            }
        }
    }
    public void render()
    {
        for (int i = 0; i < objects.size(); ++i) {
            if (!game.getLevelPassed())
                objects.get(i).render(game, game.getRenderer());
            else {
                if (objects.get(i) instanceof Ball || objects.get(i) instanceof Player)
                    objects.get(i).render(game, game.getRenderer());//de indata ce castigam se randeaza noua mapa
            }                                                 //de aceea nu trebuiesc randate caramizile
        }
    }
    public void setPlayerBallToInitial()
    {
        for (Object obj : objects) {
            if (obj instanceof Ball) {
                ((Ball) obj).setToInitialPosition();
            }
            if (obj instanceof Player) {
                ((Player) obj).setToInitialPosition();
            }
        }
    }
    public void setPlayerNormalMovements() {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                ((Player) obj).setReversedMovement(false);
            }
        }
    }
    public void setReversedMovement()
    {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                ((Player) obj).reverseMovement();
            }
        }
    }
    public void playerSize(int changeSize)
    {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                ((Player) obj).changeSize(changeSize);
            }
        }
    }
    public void changePlayerSpeed(int speed)
    {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                ((Player) obj).changeSpeed(speed);
            }
        }
    }
    public void changeBallSpeed() {
        for (Object obj : objects) {
            if (obj instanceof Ball) {
                ((Ball) obj).changeVelY();
            }
        }
    }
    public void destroyAllBricks()
    {
        for (GameObject obj : objects) {
            if (obj instanceof Brick) {
                obj.setDead(true);
            }
        }
    }
    public void destroyAllObjects()
    {
        for (GameObject obj : objects) {
            obj.setDead(true);
        }
    }
    public ArrayList<GameObject> getObjects()
    {
        return objects;
    }
    public void destroyAlmostAllBricks()
    {
        for (GameObject obj : objects) {
            if (obj.getTag().equals("brick") && Brick.getBricksNumber()!=1) {
                obj.setDead(true);
            }
        }
    }
    public void generateMagicObjects(int x, int y)
    {
        int val=(int)(Math.random()*100);
        if(val<30) {
            int val2 = (int) (Math.random() * 5);
            switch (val2) {
                case 0:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.bigger));
                    break;
                case 1:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.smaller));
                    break;
                case 2:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.reverse));
                    break;
                case 3:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.slower));
                    break;
                case 4:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.faster));
                    break;
            }//de sters obiectele magice atunci cand treci un nivel
        }
    }
    public void destroyAllBonuses()
    {
        for(GameObject obj: objects)
        {
            if(obj.getTag().equals("magic"))
            {
                obj.setDead(true);
            }
        }
    }
}
