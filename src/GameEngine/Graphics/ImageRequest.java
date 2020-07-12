package GameEngine.Graphics;

public class ImageRequest {
    private Image image;
    private int offX;
    private int offY;
    public ImageRequest(Image image, int offX, int offY)
    {
        this.image=image;
        this.offX=offX;
        this.offY=offY;
    }
    public int getOffX() {
        return offX;
    }
    public int getOffY() {
        return offY;
    }
    public Image getImage() {
        return image;
    }
}