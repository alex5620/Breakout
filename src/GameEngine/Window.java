package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics graphics;
    public Window(GameEngine gc)
    {
        image=new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);//buffer= store in the RAM
        canvas=new Canvas();
        Dimension s=new Dimension((int)(gc.getWidth()*gc.getScale()),(int) (gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame=new JFrame(gc.getTitle());//setare titlu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();//frameul va avea dimensiunea egala cu cea a canvas
        frame.setLocationRelativeTo(null);//fereastra va aparea in mijlocul ecranului
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.createBufferStrategy(2);
        bs=canvas.getBufferStrategy();
        graphics=bs.getDrawGraphics();
    }

    public void update()
    {
        graphics.drawImage(image,0,0, canvas.getWidth(), canvas.getHeight(), null);
        bs.show();
    }
    public Canvas getCanvas()
    {
        return canvas;
    }
    public BufferedImage getImage()
    {
        return image;
    }
    public void closeWindow()
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
