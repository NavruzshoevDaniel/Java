package mvc.model.game.components;

public class Brick extends Sprite {
    private boolean destroyed;

    public Brick(int x, int y,int width,int hiegth) {
        super(x, y,width,hiegth);
        destroyed=false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
