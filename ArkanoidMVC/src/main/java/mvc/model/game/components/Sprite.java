package mvc.model.game.components;


import java.awt.*;

public class Sprite{
    protected Rectangle area;
    protected int x;
    protected int y;
    protected int width;
    protected int hiegth;
    protected boolean visible;

    public Sprite(int x, int y,int width,int hiegth) {
        this.x = x;
        this.y = y;
        this.width=width;
        this.hiegth=hiegth;
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

    public Rectangle getArea() {
        return new Rectangle(x,y,width,hiegth);
    }

    public int getWidth() {
        return width;
    }

    public int getHiegth() {
        return hiegth;
    }
}
