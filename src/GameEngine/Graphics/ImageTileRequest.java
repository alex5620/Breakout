package GameEngine.Graphics;

public class ImageTileRequest extends ImageRequest {
    private int tileW;
    private int tileH;
    public ImageTileRequest(Image image, int offX, int offY, int tileW, int tileH) {
        super(image, offX, offY);
        this.tileW=tileW;
        this.tileH=tileH;
    }
    public int getTileW()
    {
        return tileW;
    }
    public int getTileH() {
        return tileH;
    }
    @Override
    public Image getImage() {
        return super.getImage();
    }
}
