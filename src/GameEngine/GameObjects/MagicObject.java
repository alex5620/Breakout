package GameEngine.GameObjects;

import GameEngine.Graphics.ImageTile;
import GameEngine.Graphics.Renderer;
import GameEngine.States.PlayState.PlayingState;

public class MagicObject extends GameObject {
    private ImageTile objectImage;
    private int width;
    private int height;
    private int tileX;
    private int tileY;
    public MagicObject(PlayingState playingState)
    {
        this.playingState=playingState;
        this.tag = "magic";
    }
    @Override
    public void update()
    {
        moveY();
    }
    @Override
    public void render(Renderer renderer) {
        renderer.drawImageTile(objectImage, posX, posY, tileX, tileY);
    }
    public GameObject setDetails(int posX, int posY, int tileX, int tileY)
    {
        this.posX=posX;
        this.posY=posY;
        this.tileX=tileX;
        this.tileY=tileY;
        objectImage = (ImageTile)playingState.getImagesLoader().getImage("bonuses");
        this.width=objectImage.getTileWidth();
        this.height=objectImage.getTileHeight();
        return this;
    }
    public void executeCommand()
    {
        switch (""+tileX+tileY) {
            case "31":
                playingState.getObjectsManager().setPaddleReversedMovement();
                break;
            case "11":
                playingState.getObjectsManager().changePaddleSize(1);
                break;
            case "01":
                playingState.getObjectsManager().changePaddleSize(-1);
                break;
            case "30":
                playingState.getObjectsManager().changePaddleSpeed(-1);
                break;
            case "40":
                playingState.getObjectsManager().changePaddleSpeed(1);
                playingState.getObjectsManager().incrementBallYVel();
                break;
            case "41":
                playingState.getObjectsManager().changePaddleLives(1);
                break;
            case "00":
                playingState.incrementScore(100);
                break;
            case "10":
                playingState.incrementScore(250);
                break;
            case "20":
                playingState.incrementScore(500);
                break;
            case "21":
                playingState.getObjectsManager().addLaser();
                break;
        }
    }
    public void moveY()
    {
        posY+=2;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}