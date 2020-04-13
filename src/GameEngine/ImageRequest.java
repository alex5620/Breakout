package GameEngine;

public class ImageRequest {
    public Image image;
    public int offX, offY;
    public ImageRequest(Image image, int offX, int offY)
    {
        this.image=image;
        this.offX=offX;
        this.offY=offY;
    }
}