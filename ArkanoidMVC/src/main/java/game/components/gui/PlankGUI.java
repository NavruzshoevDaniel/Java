package game.components.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlankGUI extends JPanel{
    private static final Logger logger = Logger.getLogger(Board.class.getName());
    private Image image;
    private int x;
    private int y;
    private int width;
    private int height;

    public PlankGUI(int x, int y) {
        this.x = x;
        this.y = y;
        init();
    }

    private void init() {
        try {
            image= ImageIO.read(PlankGUI.class.getResourceAsStream("/plank.png"));
            width=image.getWidth(null);
            height=image.getHeight(null);
        } catch (IOException e) {
            logger.log(Level.WARNING,"Plank file doesn't exits",e);
        }
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
