package mvc.model.observers;

public interface Observer {
    void updateField();
    void updateWall(int index);
    void updateBall();
    void updatePlank();
    void updateStartMenu();
}
