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
    public int getPlayerLives() {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                return ((Player) obj).getLives();
            }
        }
        return 0;//linia asta n ar trebuie sa fie
    }
    public void changePlayerLives(int life) {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                ((Player) obj).changeLivesNo(life);
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
    public int getPlayerX() {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                return ((Player) obj).getPosX();
            }
        }
        return 0;
    }
    public int getPlayerY() {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                return ((Player) obj).getPosY();
            }
        }
        return 0;
    }
    public int getPlayerWidth() {
        for (Object obj : objects) {
            if (obj instanceof Player) {
                return ((Player) obj).getWidth();
            }
        }
        return 0;
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
        if(game.getState()== Game.STATE.pause)
            return;
        int val=(int)(Math.random()*100);
        if(val<20) {
            int val2 = (int) (Math.random() * 9);
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
                case 5:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.life));
                    break;
                case 6:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.bonus100));
                    break;
                case 7:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.bonus250));
                    break;
                case 8:
                    addObject(new MagicObject(game, x, y, MagicObject.Type.bonus500));
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
