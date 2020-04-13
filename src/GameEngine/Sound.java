package GameEngine;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Sound{
    private Clip clip=null;
    private FloatControl gainControl;

    public Sound(String path)
    {
        try {
            InputStream audioSrc= Sound.class.getResourceAsStream(path);
            InputStream bufferedIn=new BufferedInputStream(audioSrc);
            AudioInputStream ais= AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat=ais.getFormat();
            AudioFormat decodeFormat= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels()*2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip=AudioSystem.getClip();
            clip.open(dais);
            gainControl=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e ) {
            e.printStackTrace();
        }
    }
    public void play()
    {
        if(clip==null)
        {
            return;
        }
        stop();
        clip.setFramePosition(0);
        while(!clip.isRunning())
        {
            clip.start();
        }
    }
    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }
}