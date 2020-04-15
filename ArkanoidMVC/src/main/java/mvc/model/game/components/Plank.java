package mvc.model.game.components;


public class Plank extends Sprite {
    private int dx;
    private int speed=3;
    private boolean inMove=false;

    public Plank(int x, int y,int width,int hiegth) {
        super(x, y,width,hiegth);
    }

    public void move(){
        x+=dx;
    }

    public void setDx(int dx) {
        this.dx = dx*speed;
    }

    public boolean isInMove() {
        return inMove;
    }

    public void setInMove(boolean inMove) {
        this.inMove = inMove;
    }

    public int getDx() {
        return dx;
    }
}
