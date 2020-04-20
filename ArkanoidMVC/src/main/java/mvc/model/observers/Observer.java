package mvc.model.observers;

public interface Observer {
    void updateField();
    void updateWall(int index);
    void updateBall();
    void updatePlank();
    void initApplication();
    void updateScore();
    void win();
    void lose();
    void reset();
}
