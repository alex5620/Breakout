package GameEngine.Graphics;

import GameEngine.GameEngine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Renderer {
    private ArrayList<ImageRequest> imageRequest;
    private int pixelWidth;
    private int pixelHeight;
    private int[] pixels;
    private boolean processing;
    private Font font;
    public Renderer(GameEngine gameEngine)
    {
        imageRequest=new ArrayList<ImageRequest>();
        font= Font.STANDARD;
        processing=false;
        pixelWidth=gameEngine.getWindow().getWidth();
        pixelHeight=gameEngine.getWindow().getHeight();
        pixels=((DataBufferInt)gameEngine.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }
    public void process()
    {
        processing=true;
        for(int i=0;i<imageRequest.size();++i) {
            ImageRequest image = imageRequest.get(i);
            if (image instanceof ImageTileRequest) {
                drawImageTile((ImageTile) image.getImage(), image.getOffX(), image.getOffY(), ((ImageTileRequest) image).getTileW(), ((ImageTileRequest) image).getTileH());
            }
            else
            {
                drawImage(image.getImage(), image.getOffX(), image.getOffY());
            }
        }
        imageRequest.clear();
        processing=false;
    }
    public void setPixel(int x, int y, int value)
    {
        int alpha=((value>>24)& 0xff);
        if(x<0 || x>=pixelWidth || y<0 || y>=pixelHeight || alpha==0)
        {
            return;
        }
        if(alpha==255)
        {
            pixels[x+y*pixelWidth]=value;
        }
        else
        {
            int pixelColor=pixels[x+y*pixelWidth];
            int newRed=((pixelColor>>16) & 0xff) - (int)(((pixelColor>>16) & 0xff - ((value>>16) &0xff ))*(alpha/255f));
            int newGreen=((pixelColor>>8) & 0xff) - (int)(((pixelColor>>8) & 0xff - ((value>>8) &0xff ))*(alpha/255f));
            int newBlue=(pixelColor & 0xff) - (int)(((pixelColor & 0xff) - (value &0xff))*(alpha/255f));
            pixels[x+y*pixelWidth]=(255 << 24 | newRed << 16 | newGreen << 8 | newBlue );
        }
    }
    public void drawText(String text, int offX, int offY, int color)
    {
        Image fontImage=font.getFontImage();
        int unicodeValue, offset=0;
        text=text.toUpperCase();
        for(int i=0;i<text.length();++i)
        {
            unicodeValue=text.codePointAt(i)-32;
            for(int y=0;y<fontImage.getHeight();++y)
            {
                for(int x=0;x<font.getWidths()[unicodeValue];++x)
                {
                    if(fontImage.getPixels()[(x+font.getOffsets()[unicodeValue])+y*fontImage.getWidth()]==0xff000000)
                    {
                        setPixel(x + offX+offset, y + offY, color);
                        setPixel(x + offX+offset+1, y + offY+1, color);
                    }
                }
            }
            offset+=font.getWidths()[unicodeValue];
        }
    }
    public void drawImage(Image image, int offX, int offY)
    {
        if(processing==false)
        {
            imageRequest.add(new ImageRequest(image, offX, offY));
            return;
        }
        for(int y=0;y<image.getHeight();++y)
        {
            for(int x=0;x<image.getWidth();++x)
            {
                setPixel(x + offX, y+ offY, image.getPixels()[x+y*image.getWidth()]);
            }
        }
    }
    public void drawMarks(GameEngine gameEngine)
    {
        for(int i=0;i<gameEngine.getWindow().getWidth();i+=24) {
            for (int x = 0; x <= 12; ++x) {
                setPixel(i + x + 6, 45, 0xff00ff00);
                setPixel(i + x + 6, 46, 0xff00ff00);
            }
        }
    }
    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY)
    {
        if(processing==false)
        {
            imageRequest.add(new ImageTileRequest(image, offX, offY, tileX, tileY));
            return;
        }
        for(int y=0;y<image.getTileHeight();++y)
        {
            for(int x=0;x<image.getTileWidth();++x)
            {
                setPixel(x + offX, y+ offY, image.getPixels()[(x+tileX*image.getTileWidth())+(y+tileY*image.getTileHeight())*image.getWidth()]);
            }
        }
    }
    public void drawRect(int offX, int offY, int width, int height, int color)
    {
        for(int y=0;y<=height;++y)
        {
            setPixel(offX, y+offY, color);
            setPixel(offX+width, y+offY, color);
        }
        for(int x=0;x<=width;++x)
        {
            setPixel(x+offX, offY, color);
            setPixel(offX+x, offY+height, color);
        }
    }
}
