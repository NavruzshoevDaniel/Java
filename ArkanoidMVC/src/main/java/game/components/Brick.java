package game.components;

public class Brick extends Sprite {
    private boolean destroyed;

    public Brick(int x, int y) {
        super(x, y);
        destroyed=false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
