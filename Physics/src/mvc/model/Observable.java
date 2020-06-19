package mvc.model;

import org.jfree.data.xy.XYSeriesCollection;

public interface Observable {
    void registerObserver(Observer observer);
    void updateObservers(XYSeriesCollection collection);
}
