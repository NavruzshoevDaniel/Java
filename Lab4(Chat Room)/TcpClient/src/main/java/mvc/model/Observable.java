package mvc.model;

import messages.Message;

public interface Observable {
    void registerObserver(Observer observer);
    void updateObservers(OutputType type, String text);
}
