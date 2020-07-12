package GameEngine.Graphics;


public class ImageTile extends Image {
    private int tileWidth;
    private int tileHeight;
    public ImageTile(String path, int tileWidth, int tileHeight) {
        super(path);
        this.tileWidth=tileWidth;
        this.tileHeight=tileHeight;
    }
    public int getTileWidth() {
        return tileWidth;
    }
    public int getTileHeight() {
        return tileHeight;
    }
}
