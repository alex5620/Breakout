package GameEngine.GameObjects;

import GameEngine.Game;

public class ObjectFactory {
    private static volatile ObjectFactory factory;
    private ObjectFactory() { }
    public static ObjectFactory getInstance() {
        if (factory == null) {
            synchronized (ObjectFactory.class) {
                if (factory == null) {
                    factory = new ObjectFactory();
                }
            }

        }
        return factory;
    }
    public static synchronized void Reset() {
        factory = null;
    }
    public GameObject getPlayer(Game game)
    {
        return new Player(game);
    }
    public GameObject getBall(Game game)
    {
        return new Ball(game);
    }
    public GameObject getBrick(Game game,int posX, int posY, int imageIndex, int hitsRemained)
    {
        return new Brick(game, posX, posY, imageIndex, hitsRemained);
    }
    public GameObject getMagicObject(Game game, int posX, int posY, MagicObject.Type type)
    {
        return new MagicObject(game, posX, posY, type);
    }
}
