package GameEngine.Loaders;

import GameEngine.Graphics.Image;
import GameEngine.Graphics.ImageTile;

import java.util.HashMap;

public class ImagesLoader {
    private HashMap<String ,Image> images;
    public ImagesLoader()
    {
        images=new HashMap<>();
        images.put("ball", new Image("/Resources/Images/ball2.png"));
        images.put("paddle1",new ImageTile("/Resources/Images/paddle_size1.png", 70, 18));
        images.put("paddle2",new ImageTile("/Resources/Images/paddle_size2.png", 96, 18));
        images.put("paddle3",new ImageTile("/Resources/Images/paddle_size3.png", 128, 18));
        images.put("paddle4",new ImageTile("/Resources/Images/paddle_size4.png", 160, 18));
        images.put("paddle5",new ImageTile("/Resources/Images/paddle_size5.png", 192, 18));
        images.put("background", new Image("/Resources/Images/background.png"));
        images.put("menu", new Image("/Resources/Images/menu2.png"));
        images.put("play", new Image("/Resources/Images/play.png"));
        images.put("highscores", new Image("/Resources/Images/highscores.png"));
        images.put("settings", new Image("/Resources/Images/settings.png"));
        images.put("quit",new Image("/Resources/Images/quit.png"));
        images.put("settingsmenu",new Image("/Resources/Images/settingsmenu.png"));
        images.put("back_reset",new Image("/Resources/Images/back.png"));
        images.put("simplemenu",new Image("/Resources/Images/simplemenu.png"));
        images.put("highscoresmenu",new Image("/Resources/Images/highscoresmenu.png"));
        images.put("losttext",new Image("/Resources/Images/gotext.png"));
        images.put("playagain",new Image("/Resources/Images/playagain.png"));
        images.put("backtomenu",new Image("/Resources/Images/backtomenu.png"));
        images.put("wontext", new Image("/Resources/Images/wontext.png"));
        images.put("youwon", new Image("/Resources/Images/won.png"));
        images.put("letter",new Image("/Resources/Images/letter.png"));
        images.put("heart",new Image("/Resources/Images/heart.png"));
        images.put("delete",new Image("/Resources/Images/delete.png"));
        images.put("pause",new Image("/Resources/Images/pause.png"));
        images.put("exit",new Image("/Resources/Images/exit.png"));
        images.put("exit2",new Image("/Resources/Images/exit2.png"));
        images.put("howtoplay",new Image("/Resources/Images/howToPlay.png"));
        images.put("about",new Image("/Resources/Images/about.png"));
        images.put("laser",new Image("/Resources/Images/laser.png"));
        images.put("checked", new Image("/Resources/Images/checked.png"));
        images.put("soundenabled", new Image("/Resources/Images/soundenabled.png"));
        images.put("sounddisabled", new Image("/Resources/Images/sounddisabled.png"));
        images.put("bricks", new ImageTile("/Resources/Images/bricks.png", 88, 44));
        images.put("sbricks", new ImageTile("/Resources/Images/smallbricks.png", 76, 38));
        images.put("bonuses", new ImageTile("/Resources/Images/bonuses.png", 40, 20));
    }
    public Image getImage(String image)
    {
        return images.get(image);
    }
}
