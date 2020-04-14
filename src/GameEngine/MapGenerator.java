package GameEngine;

import GameEngine.GameObjects.Brick;

public class MapGenerator {
    private static final int row=4, column=8;
    public static void generateMap(Game game, int level)
    {
        switch(level) {
            case 1:
                level1(game);
                break;
            case 2:
                level2(game);
                break;
            case 3:
                level3(game);
                break;
            case 4:
                level4(game);
                break;
            case 5:
                level5(game);
                break;
            default:
                level1(game);
                break;
        }
    }
    public static void level1(Game game)
    {
        Image []brickImage=new Image[2];
        int height= game.getImagesLoader().getBrickImage(0).getHeight();
        int width= game.getImagesLoader().getBrickImage(0).getWidth();
        for(int i=0;i<row;++i)
        {
            for(int j=0;j<column;++j)
            {
                if(i==0 || (i==row-1) || j==0 || (j==column-1)) {
                    brickImage[0] = game.getImagesLoader().getBrickImage(2);
                    brickImage[1]=game.getImagesLoader().getBrickImage(7);
                }
                else {
                    brickImage[0] = game.getImagesLoader().getBrickImage(1);
                    brickImage[1]=game.getImagesLoader().getBrickImage(6);
                }
                game.getObjectsManager().getObjects().add(new Brick((j*width+14+j*5), i*height+60+i*5, brickImage, 1));
            }
        }
    }
    public static void level2(Game game)
    {
        Image []brickImage=new Image[2];
        int height= game.getImagesLoader().getBrickImage(0).getHeight();
        int width= game.getImagesLoader().getBrickImage(0).getWidth();
        int val;
        for(int i=0;i<row;++i)
        {
            for(int j=0;j<column;++j)
            {
                if(i==row-1) {
                    brickImage[0] = game.getImagesLoader().getBrickImage(1);
                    brickImage[1] = game.getImagesLoader().getBrickImage(6);
                    val = 2;
                }
                else
                {
                    if(i==0 || j==0 || (j==column-1))
                    {
                        brickImage[0] = game.getImagesLoader().getBrickImage(3);
                    }
                    else
                    {
                        brickImage[0]=game.getImagesLoader().getBrickImage(2);
                    }
                    val=1;
                }
                game.getObjectsManager().getObjects().add(new Brick((j*width+14+j*5),  i*height+60+i*5, brickImage, val));
            }
        }
    }
    public static void level3(Game game)
    {
        Image []brickImage=new Image[2];
        int height= game.getImagesLoader().getBrickImage(0).getHeight();
        int width= game.getImagesLoader().getBrickImage(0).getWidth();
        int val;
        for(int i=0;i<row;++i)
        {
            for(int j=0;j<column;++j)
            {
                val=1;
                if(j==0 || j==column-1) {
                    brickImage[0] = game.getImagesLoader().getBrickImage(2);
                    brickImage[1] = game.getImagesLoader().getBrickImage(7);
                    val=2;
                }
                else
                {
                    if(j==1 || j==column-2)
                    {
                        brickImage[0] = game.getImagesLoader().getBrickImage(1);
                        brickImage[1] = game.getImagesLoader().getBrickImage(6);
                    }
                    else
                    {
                        if(j==2 || j==column-3) {
                            brickImage[0] = game.getImagesLoader().getBrickImage(0);
                            brickImage[1]=game.getImagesLoader().getBrickImage(5);
                            val=2;
                        }
                        else {
                            brickImage[0] = game.getImagesLoader().getBrickImage(3);
                            brickImage[1]=game.getImagesLoader().getBrickImage(8);
                        }
                    }
                }
                game.getObjectsManager().getObjects().add(new Brick((j*width+14+j*5), i*height+60+i*5, brickImage, val));
            }
        }
    }
    public static void level4(Game game)
    {
        Image []brickImage=new Image[2];
        int height= game.getImagesLoader().getBrickImage(0).getHeight();
        int width= game.getImagesLoader().getBrickImage(0).getWidth();
        int val;
        for(int i=0;i<row;++i)
        {
            for(int j=0;j<column;++j)
            {
                val=1;
                if(j==0 || (j==column/2-1) || ((i==0 && j<column/2) || ((i==row-1) && j<column/2))) {
                    brickImage[0] = game.getImagesLoader().getBrickImage(1);
                    brickImage[1] = game.getImagesLoader().getBrickImage(6);
                    val=2;
                }
                else
                {
                    if((j==column-1) || (j==column/2) || ((i==0 && j>=column/2) || ((i==row-1) && j>=column/2)))
                    {
                        brickImage[0] = game.getImagesLoader().getBrickImage(2);
                        brickImage[1] = game.getImagesLoader().getBrickImage(7);
                        val=2;
                    }
                    else
                    {
                        if(j<column/2) {
                            brickImage[0] = game.getImagesLoader().getBrickImage(0);
                        }
                        else {
                            brickImage[0] = game.getImagesLoader().getBrickImage(3);
                        }
                    }
                }
                game.getObjectsManager().getObjects().add(new Brick((j*width+14+j*5), i*height+60+i*5, brickImage, val));
            }
        }
    }
    public static void level5(Game game)
    {
        Image []brickImage=new Image[2];
        int height= game.getImagesLoader().getBrickImage(0).getHeight();
        int width= game.getImagesLoader().getBrickImage(0).getWidth();
        int val;
        for(int i=0;i<row;++i)
        {
            for(int j=0;j<column;++j)
            {
                val=2;
                if(j<3) {
                    brickImage[0] = game.getImagesLoader().getBrickImage(2);
                    brickImage[1] = game.getImagesLoader().getBrickImage(7);
                }
                else
                {
                    if(j<5)
                    {
                        brickImage[0] = game.getImagesLoader().getBrickImage(4);
                        brickImage[1] = game.getImagesLoader().getBrickImage(9);
                    }
                    else
                    {
                        brickImage[0] = game.getImagesLoader().getBrickImage(1);
                        brickImage[1] = game.getImagesLoader().getBrickImage(6);
                    }
                }
                game.getObjectsManager().getObjects().add(new Brick((j*width+14+j*5), i*height+60+i*5, brickImage, val));
            }
        }
    }
}
