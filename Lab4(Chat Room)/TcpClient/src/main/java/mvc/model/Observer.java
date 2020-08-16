package mvc.model;

import messages.Message;

public interface Observer {
    void updateView(OutputType type, String text);
}
