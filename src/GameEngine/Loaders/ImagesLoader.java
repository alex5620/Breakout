package GameEngine.Loaders;

import GameEngine.Image;

public class ImagesLoader {
    private Image ballImage;
    private Image []playerImage;
    private Image brickImage[];
    private Image backgroundImage;
    private Image menuImage;
    private Image playImage;
    private Image highScoresImage;
    private Image settingsImage;
    private Image quitImage;
    private Image settingsMenuImage;
    private Image back_resetImage;
    private Image simpleMenuImage;
    private Image highscoresMenuImage;
    private Image lostMenuImage;
    private Image playAgainImage;
    private Image backToMenuImage;
    private Image wonImage;
    private Image backToMenu2Image;
    private Image reverseImage;
    private Image biggerImage;
    private Image smallerImage;
    private final int brickImagesNumber=10;
    public ImagesLoader()
    {
        brickImage=new Image[brickImagesNumber];
        brickImage[0]=new Image("/Resources/gbrick.png");
        brickImage[1]=new Image("/Resources/bbrick.png");
        brickImage[2]=new Image("/Resources/rbrick.png");
        brickImage[3]=new Image("/Resources/vbrick.png");
        brickImage[4]=new Image("/Resources/ybrick.png");
        brickImage[5]=new Image("/Resources/gbrick2.png");
        brickImage[6]=new Image("/Resources/bbrick2.png");
        brickImage[7]=new Image("/Resources/rbrick2.png");
        brickImage[8]=new Image("/Resources/vbrick2.png");
        brickImage[9]=new Image("/Resources/ybrick2.png");
        ballImage=new Image("/Resources/ball2.png");
        playerImage=new Image[5];
        for(int i=0;i<5;++i)
        {
            playerImage[i]=new Image("/Resources/paddle_size"+(i+1)+".png");
        }
        backgroundImage =new Image("/Resources/back2.png");
        menuImage = new Image("/Resources/menu.png");
        playImage = new Image("/Resources/play.png");
        highScoresImage=new Image("/Resources/highscores.png");
        settingsImage= new Image("/Resources/settings.png");
        quitImage= new Image("/Resources/quit.png");
        settingsMenuImage= new Image("/Resources/settingsmenu.png");
        back_resetImage=new Image("/Resources/back.png");
        simpleMenuImage=new Image("/Resources/simplemenu.png");
        highscoresMenuImage=new Image("/Resources/highscoresmenu.png");
        lostMenuImage=new Image("/Resources/gameover.png");
        playAgainImage=new Image("/Resources/playagain.png");
        backToMenuImage=new Image("/Resources/backtomenu.png");
        wonImage=new Image("/Resources/youwon.png");
        backToMenu2Image=new Image("/Resources/backtomenu2.png");
        reverseImage=new Image("/Resources/reverse.png");
        biggerImage=new Image("/Resources/bigger.png");
        smallerImage=new Image("/Resources/smaller.png");
    }
    public Image getBallImage() {
        return ballImage;
    }

    public Image[] getPlayerImages() {
        return playerImage;
    }

    public Image getBrickImage(int index) {
        return brickImage[index];
    }
    public Image getBackgroundImage()
    {
        return backgroundImage;
    }
    public Image getMenuImage()
    {
        return  menuImage;
    }
    public Image getPlayImage() { return playImage; }
    public Image getHighScoresImage() { return highScoresImage; }
    public Image getSettingsImage() { return settingsImage; }
    public Image getQuitImage() { return quitImage; }
    public Image getSettingsMenuImage() { return settingsMenuImage; }
    public Image getBack_ResetImage() { return back_resetImage; }
    public Image getSimpleMenuImage() { return simpleMenuImage; }
    public Image getHighscoresMenuImage() { return highscoresMenuImage; }
    public Image getLostMenuImage() {return lostMenuImage;}
    public Image getPlayAgainImage() { return playAgainImage;}
    public Image getBackToMenuImage() {return backToMenuImage;}
    public Image getWonImage() { return wonImage; }
    public Image getBackToMenu2Image() {return backToMenu2Image;}
    public Image getReverseImage(){ return reverseImage; }
    public Image getBiggerImage() { return biggerImage; }
    public Image getSmallerImage() { return smallerImage; }
}
