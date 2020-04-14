package mvc.model.game.components;


import java.awt.*;

public class Sprite{
    protected Rectangle area;
    protected int x;
    protected int y;
    protected boolean visible;

    public Sprite(int x, int y,int width,int hiegth) {
        this.x = x;
        this.y = y;
        area= new Rectangle(x,y,width,hiegth);
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
