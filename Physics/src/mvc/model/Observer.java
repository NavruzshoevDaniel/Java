package mvc.model;

import org.jfree.data.xy.XYSeriesCollection;

public interface Observer {
    void updateView(XYSeriesCollection collection);
}
