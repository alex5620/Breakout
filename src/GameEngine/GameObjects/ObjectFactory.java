package GameEngine.GameObjects;

import GameEngine.States.PlayState.PlayingState;

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
    public GameObject getObject(PlayingState playingState, String type)
    {
        switch (type)
        {
            case "player":
                return new Player(playingState);
            case "ball":
                return new Ball(playingState);
            case "brick":
                return new Brick(playingState);
            case "magic":
                return new MagicObject(playingState);
            default:
                return null;
        }
    }
}
