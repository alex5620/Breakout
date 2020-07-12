package GameEngine.GameObjects;

import GameEngine.States.PlayState.PlayingState;

public class ObjectsFactory {
    private static volatile ObjectsFactory factory;
   private ObjectsFactory() { }
    public static ObjectsFactory getInstance() {
        if (factory == null) {
            synchronized (ObjectsFactory.class) {
                if (factory == null) {
                    factory = new ObjectsFactory();
                }
            }
        }
        return factory;
    }
    public GameObject getObject(PlayingState playingState, String type)
    {
        switch (type)
        {
            case "player":
                return new Paddle(playingState);
            case "ball":
                return new Ball(playingState);
            case "brick":
                return new Brick(playingState);
            case "magic":
                return new MagicObject(playingState);
            case "laser":
                return new Laser(playingState);
            default:
                return null;
        }
    }
}
