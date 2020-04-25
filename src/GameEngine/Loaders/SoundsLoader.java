package GameEngine.Loaders;
import GameEngine.Sound;

public class SoundsLoader {
    private Sound brickHitSound;
    public SoundsLoader() {
        brickHitSound = new Sound("/Resources/hitsound.wav");
    }
    public void playBrickHitSound()
    {
        brickHitSound.play();
    }
}
