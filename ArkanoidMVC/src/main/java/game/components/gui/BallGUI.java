package game.components.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BallGUI extends JPanel {
    private static final Logger logger = Logger.getLogger(Background.class.getName());
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;

    public BallGUI(int x, int y) {
        this.x = x;
        this.y = y;
        initImage();
    }

    private void initImage() {

        try {
            image = ImageIO.read(BallGUI.class.getResourceAsStream("/ball.png"));
            width = image.getWidth(null);
            height = image.getHeight(null);
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Ball GUI image doesn't exits", e);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        logger.log(Level.INFO,getWidth()+" "+getHeight()+":"+getImageWidth()+" "+getImageHeight());
        g.drawImage(image, x, y, null);
    }


    public int getImageWidth() {
        return width;
    }

    public int getImageHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
