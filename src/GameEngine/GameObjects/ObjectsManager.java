package GameEngine.GameObjects;

import GameEngine.Renderer;
import GameEngine.States.PlayState.PlayingState;

import java.util.ArrayList;

public class ObjectsManager {
    private PlayingState playingState;
    private ArrayList<GameObject> objects;
    private Player player;//player-ul si mingea sunt obiecte mai utilizate, de aceea avem o referinta catre ele
    private Ball ball;    //mai utilizate, in sensul ca functiile acestora sunt apelate mai des
    public ObjectsManager(PlayingState playing)//de aceea, pentru a nu fi nevoiti sa facem mereu un loop care sa caute
    {                               //player-ul si mingea, am preferat sa pastrez aceste referinte
        this.playingState = playing;
        objects = new ArrayList<>();
    }
    public void addObject(GameObject object)
    {
        objects.add(object);
        if(object.getTag().equals("player"))
        {
            player=(Player)object;
        }
        if(object.getTag().equals("ball"))
        {
            ball=(Ball)object;
        }
    }
    public void update()
    {
        for (int i = 0; i < objects.size(); ++i) {
            objects.get(i).update();
            if (objects.get(i).isDead()) {
                if((objects.get(i) instanceof Brick)) {
                    playingState.incremetScore();
                    generateMagicObjects(objects.get(i).getPosX()+objects.get(i).getWidth()/2, objects.get(i).getPosY()+objects.get(i).getHeight());
                }                   //daca obiectul distrus este o caramida, atunci se incrementeaza scorul
                objects.remove(i);  //incrementarea se facem doar daca jocul nu este pierdut
                i--;                //in sensul ca, atunci cand player-ul pierde, toate obiectele din joc sunt distruse
            }                       //astfel, trebuie sa se tina seama ca scorul sa fie incrementat corespunzator
        }
    }
    public void render(Renderer renderer)
    {
        int size=objects.size();
        for (int i = 0; i < size; ++i) {
            if (!playingState.getLevelPassed())
                objects.get(i).render(renderer);
            else {
                if (objects.get(i) instanceof Ball || objects.get(i) instanceof Player)
                    objects.get(i).render(renderer);
            }                               //de indata ce player-ul a trecut un level, noua mapa este generata,
        }                                   //dar caramizile nu sunt randate pana cand player-ul
    }                                       //nu apasa tasta enter de trecere la level-ul urmator
    public void generateMagicObjects(int x, int y)
    {
        ObjectFactory factory=ObjectFactory.getInstance();
        if(playingState.IsPaused()== true)
            return;
        int val=(int)(Math.random()*100);
        if(val<12) {
            int val2 = (int) (Math.random() * 9);
            switch (val2) {
                case 0:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.bigger));
                    break;
                case 1:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.smaller));
                    break;
                case 2:
                    addObject(((MagicObject)(factory.getObject(playingState,"magic"))).setDetails(x, y, MagicObject.Type.reverse));
                    break;
                case 3:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.slower));
                    break;
                case 4:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.faster));
                    break;
                case 5:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.life));
                    break;
                case 6:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.bonus100));
                    break;
                case 7:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.bonus250));
                    break;
                case 8:
                    addObject(((MagicObject)(factory.getObject(playingState, "magic"))).setDetails(x, y, MagicObject.Type.bonus500));
                    break;
            }
        }
    }
    public void destroyAlmostAllBricks()
    {
        for (GameObject obj : objects) {
            if (obj.getTag().equals("brick") && Brick.getBricksNumber()>1) {
                obj.setDead(true);
            }
        }
    }
    public void destroyAllBonuses()//cand se trece la un nou nivel toate bonusurile se randau pana in acel moment
    {                              //sunt distruse
        for(GameObject obj: objects)
        {
            if(obj.getTag().equals("magic"))
            {
                obj.setDead(true);
            }
        }
    }
    public void setPlayerBallToInitial()
    {
        ball.setToInitialPosition();
        player.setToInitialPosition();
    }
    public void setPlayerNormalMovement() {//atunci cand se trece la urmatorul nivel, indiferent de modul in care
        player.setReversedMovement(false);//player-ul se misca inainte(normal, inversat), acesta se va misca normal
    }
    public int getPlayerLives() {
        return player.getLives();
    }
    public void changePlayerLives(int life) {
        player.changeLivesNo(life);
    }
    public void setPlayerReversedMovement()
    {
        player.reverseMovement();
    }
    public void changePlayerSize(int changeSize)
    {
        player.changeSize(changeSize);
    }
    public void changePlayerSpeed(int speed)
    {
        player.changeSpeed(speed);
    }
    public void incrementBallSpeed() {
        ball.incrementVelY();
    }
    public int getPlayerX() {
        return player.getPosX();
    }
    public int getPlayerY() {
        return player.getPosY();
    }
    public int getPlayerWidth() {
        return player.getWidth();
    }
    public ArrayList<GameObject> getObjects()
    {
        return objects;
    }
    public GameObject getPlayer()
    {
        return player;
    }
    public GameObject getBall()
    {
        return ball;
    }
}
