package game.components;

import javax.swing.*;
import java.awt.*;

public class Sprite {
    protected Image image;
    protected int x;
    protected int y;
    protected int width;
    protected int higth;
    protected boolean visible;


    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void getImageDimensions() {
        width = image.getWidth(null);
        higth = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(String imageName) {
        ImageIcon imageIcon=new ImageIcon(imageName);
        this.image=imageIcon.getImage();
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
