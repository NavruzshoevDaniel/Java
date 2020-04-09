package game.components;

public class Ball extends Sprite {

    private int BALL_SPEED = 2;
    private int directionX = 1;
    private int directionY = 1;

    public Ball(int x, int y) {
        super(x, y);
    }

    public void move(){
        x += directionX * BALL_SPEED;
        y += directionY * BALL_SPEED;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }
}
