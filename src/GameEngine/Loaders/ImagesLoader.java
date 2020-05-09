package GameEngine.Loaders;

import GameEngine.Image;
import java.util.HashMap;

public class ImagesLoader {
    private HashMap<String ,Image> images;
    public ImagesLoader()
    {
        images=new HashMap<>();
        for(int i=0;i<10;++i)
        {
            images.put("brick"+i,new Image("/Resources/brick"+i+".png"));
        }
        images.put("ball", new Image("/Resources/ball.png"));
        for(int i=0;i<5;++i)
        {
            images.put("player"+(i+1),new Image("/Resources/paddle_size"+(i+1)+".png"));
        }
        images.put("background", new Image("/Resources/back2.png"));
        images.put("menu", new Image("/Resources/menu2.png"));
        images.put("play", new Image("/Resources/play.png"));
        images.put("highscores", new Image("/Resources/highscores.png"));
        images.put("settings", new Image("/Resources/settings.png"));
        images.put("quit",new Image("/Resources/quit.png"));
        images.put("settingsmenu",new Image("/Resources/settingsmenu.png"));
        images.put("back_reset",new Image("/Resources/back.png"));
        images.put("simplemenu",new Image("/Resources/simplemenu.png"));
        images.put("highscoresmenu",new Image("/Resources/highscoresmenu.png"));
        images.put("lostmenu",new Image("/Resources/gameover.png"));
        images.put("playagain",new Image("/Resources/playagain.png"));
        images.put("backtomenu",new Image("/Resources/backtomenu.png"));
        images.put("won", new Image("/Resources/youwon.png"));
        images.put("backtomenu2",new Image("/Resources/backtomenu2.png"));
        images.put("reverse", new Image("/Resources/reverse.png"));
        images.put("bigger", new Image("/Resources/bigger.png"));
        images.put("smaller", new Image("/Resources/smaller.png"));
        images.put("slower",new Image("/Resources/slower.png"));
        images.put("faster",new Image("/Resources/faster.png"));
        images.put("heart",new Image("/Resources/heart.png"));
        images.put("life",new Image("/Resources/life.png"));
        images.put("bonus100", new Image("/Resources/100.png"));
        images.put("bonus250",new Image("/Resources/250.png"));
        images.put("bonus500" ,new Image("/Resources/500.png"));
        images.put("letter",new Image("/Resources/letter.png"));
        images.put("delete",new Image("/Resources/delete.png"));
        images.put("pause",new Image("/Resources/pause.png"));
        images.put("exit",new Image("/Resources/exit.png"));
        images.put("exit2",new Image("/Resources/exit2.png"));
        images.put("howtoplay",new Image("/Resources/howToPlay.png"));
        images.put("about",new Image("/Resources/about.png"));
    }
    public Image getImage(String image)
    {
        return images.get(image);
    }
}
