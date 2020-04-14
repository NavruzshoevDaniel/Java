package mvc.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiComponent extends JPanel {
    private static final Logger logger = Logger.getLogger(GuiComponent.class.getName());
    protected Image image;
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GuiComponent(String imageName,int x, int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width=width;
        this.height=height;
        loadImage(imageName);
    }

    public GuiComponent(String imageName,int width,int height) {
        this.width=width;
        this.height=height;
        loadImage(imageName);
    }

    private void loadImage(String imageName) {

        try {
            Image imageNatural = ImageIO.read(GuiComponent.class.getResourceAsStream(imageName));
            ImageIcon imageIcon= new ImageIcon(imageNatural.getScaledInstance(width,height,Image.SCALE_DEFAULT));
            this.image=imageIcon.getImage();
        } catch (IOException e) {
            logger.log(Level.WARNING,imageName+" image doesn't exits",e);
        }


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

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
