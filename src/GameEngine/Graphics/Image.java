package GameEngine.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {
    private int width;
    private int height;
    private int[] pixels;
    public Image(String path)
    {
        BufferedImage image=null;
        try {
            image= ImageIO.read(Image.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width=image.getWidth();
        height=image.getHeight();
        pixels=image.getRGB(0, 0, width, height, null, 0, width);
        image.flush();
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int[] getPixels() {
        return pixels;
    }
}

