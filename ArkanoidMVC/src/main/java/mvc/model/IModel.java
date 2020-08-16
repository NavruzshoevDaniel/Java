package mvc.model;

import mvc.model.observers.Observer;

public interface IModel {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyGameObserver(Model.Operation operation, Object arg);

    void init();

}
