package GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private final String title;
    private final int width;
    private final int height;

    public Window(String title, int width, int height)
    {
        this.title=title;
        this.width=width;
        this.height=height;
    }
    public void BuildGameWindow()
    {
        image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        canvas=new Canvas();
        Dimension dim=new Dimension(width,height);
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);
        frame=new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        canvas.createBufferStrategy(3);
        bufferStrategy =canvas.getBufferStrategy();
        graphics= bufferStrategy.getDrawGraphics();
    }

    public void update()
    {
        graphics.drawImage(image,0,0, canvas.getWidth(), canvas.getHeight(), null);
        bufferStrategy.show();
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
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
