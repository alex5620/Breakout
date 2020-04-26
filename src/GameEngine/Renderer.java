package GameEngine;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Renderer {
    private ArrayList<ImageRequest> imageRequest=new ArrayList<ImageRequest>();
    private int pixelWidth, pixelHeight;
    private int[] pixels;
    private boolean processing=false;
    private Font font=Font.STANDARD;

    public Renderer(GameEngine gc)
    {
        pixelWidth=gc.getWidth();
        pixelHeight=gc.getHeight();
        pixels=((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();//ne permite sa modificam pixelii din image(BufferedImage)
    }
    public void clear()
    {
        for(int i=0;i< pixels.length;++i)
        {
            pixels[i]=0;//face ecranul negru
        }
    }

    public void process()
    {
        processing=true;
        for(int i=0;i<imageRequest.size();++i)
        {
            ImageRequest ir=imageRequest.get(i);
            drawImage(ir.image, ir.offX, ir.offY);
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
        int unicode, offset=0;
        text=text.toUpperCase();
        for(int i=0;i<text.length();++i)
        {
            unicode=text.codePointAt(i)-32;
            for(int y=0;y<fontImage.getHeight();++y)
            {
                //int val=(int)(Math.random()*1000);
                for(int x=0;x<font.getWidths()[unicode];++x)
                {

                    if(fontImage.getPixels()[(x+font.getOffsets()[unicode])+y*fontImage.getWidth()]==0xff000000)
                    {
                        //if(val>=10)
                            setPixel(x + offX+offset, y + offY, color);
                        //else
                            setPixel(x + offX+offset+1, y + offY+1, color);
                    }
                }
            }
            offset+=font.getWidths()[unicode];
        }
    }
    public void drawImage(Image image, int offX, int offY)
    {
        if(!processing)
        {
            imageRequest.add(new ImageRequest(image, offX, offY));
            return;
        }
        //Don't render
        if(offX < -image.getWidth()) {
            return;
        }
        if(offY < -image.getHeight()) {
            return;
        }
        if(offX >= pixelWidth) {
            return;
        }
        if(offY >= pixelHeight) {
            return;
        }

        int newX=0, newY=0  ;//mananca degeaba resurse declararea variabilelor daca cel putin o conditie de mai sus
        int newW=image.getWidth();//nu este respectata
        int newH=image.getHeight();
        //Clipping Code
        if(offX<0) {
            newX-=offX;
        }
        if(offY<0) {
            newY-=offY;
        }
        if(newW+offX>=pixelWidth)
        {
            newW-=newW+offX-pixelWidth;
        }
        if(newH+offY>=pixelHeight) {
            newH -= newH + offY - pixelHeight;
        }
        for(int y=newY;y<newH;++y)
        {
            for(int x=newX;x<newW;++x)
            {
                setPixel(x + offX, y+ offY, image.getPixels()[x+y*image.getWidth()]);
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
    public void drawFillRect(int offX, int offY, int width, int height, int color)
    {
        if(offX < -width)  return;
        if(offY < -height)  return;
        if(offX >= pixelWidth) return;
        if(offY >= pixelHeight) return;

        int newX=0, newY=0  ;
        int newW=width;
        int newH=height;

        //Clipping Code
        if(offX<0) newX-=offX;
        if(offY<0) newY-=offY;
        if(newW+offX>=pixelWidth) newW-=newW+offX-pixelWidth;
        if(newH+offY>=pixelHeight) newH-=newH+offY-pixelHeight;

        for(int y=newY;y<=newH;++y) {
            for (int x = newX; x <= newW; ++x) {
                setPixel(offX + x, offY + y, color);
            }
        }
    }

}
