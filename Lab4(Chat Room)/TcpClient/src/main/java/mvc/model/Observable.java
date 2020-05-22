package mvc.model;

public interface Observable {
    void registerObserver(Observer observer);
    void updateObservers();
}
