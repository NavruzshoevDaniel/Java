package mvc.model;

import mvc.model.observers.Observer;

public interface IModel {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();

    void on();

}
