package GameEngine;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Sound{
    private static boolean soundEnabled=true;
    private Clip clip;
    private FloatControl gainControl;
    public Sound(String path)
    {
        clip=null;
        try {
            InputStream audioSource= Sound.class.getResourceAsStream(path);
            InputStream bufferedIn=new BufferedInputStream(audioSource);
            AudioInputStream ais= AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat=ais.getFormat();
            AudioFormat decodeFormat= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip=AudioSystem.getClip();
            clip.open(dais);
            gainControl=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }
    public static void reverseSoundSettings()
    {
        soundEnabled=!soundEnabled;
    }
    public static boolean isSoundEnabled()
    {
        return soundEnabled;
    }
    public void play()
    {
        if(soundEnabled==false)
        {
            return;
        }
        if(clip.isRunning()==false)
        {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }
    public void loop()
    {
        if(soundEnabled==false)
        {
            return;
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        play();
    }
    public Sound setVolume(float value)
    {
        gainControl.setValue(value);
        return this;
    }
}