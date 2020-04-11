package game.components;

import java.awt.event.KeyEvent;

public class Plank extends Sprite {
    private int dx;
    private int speed;

    public Plank(int x, int y) {
        super(x, y);
    }

    public void move(){
        x+=dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }
}
