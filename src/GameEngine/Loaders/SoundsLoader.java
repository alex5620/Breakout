package GameEngine.Loaders;
import GameEngine.Sound;

import java.util.HashMap;

public class SoundsLoader {
    private HashMap<String , Sound> sounds;
    public SoundsLoader() {
        sounds=new HashMap<>();
        sounds.put("brickDestroyed",new Sound("/Resources/Audio/hitsound.wav").setVolume(-10.0f));
        sounds.put("laserDestroyed",new Sound("/Resources/Audio/laserDestroyed.wav").setVolume(-10.0f));
        sounds.put("lifeLost",new Sound("/Resources/Audio/lifelost.wav").setVolume(4.0f));
        sounds.put("gameWon",new Sound("/Resources/Audio/won.wav"));
        sounds.put("gameLost",new Sound("/Resources/Audio/lost.wav").setVolume(-10.0f));
        sounds.put("bonusCollected",new Sound("/Resources/Audio/bonusCollected.wav"));
        sounds.put("buttonPressed",new Sound("/Resources/Audio/buttonpressed.wav"));
        sounds.put("buttonPressed2",new Sound("/Resources/Audio/buttonpressed2.wav"));
        sounds.put("shortClick",new Sound("/Resources/Audio/shortclick.wav"));
        sounds.put("checked",new Sound("/Resources/Audio/checked.wav").setVolume(-10.0f));
        sounds.put("ballBouncing",new Sound("/Resources/Audio/ballbouncing.wav"));
        sounds.put("background",new Sound("/Resources/Audio/background.wav"));
        sounds.put("menu",new Sound("/Resources/Audio/menu.wav").setVolume(-10.0f));
    }
    public Sound getSound(String sound)
    {
        return sounds.get(sound);
    }
}
