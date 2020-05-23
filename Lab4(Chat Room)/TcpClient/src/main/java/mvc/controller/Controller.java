package mvc.controller;

import messages.MessageType;
import mvc.model.TcpClient;
import mvc.view.View;

public class Controller {
    private TcpClient model;
    private View view;

    public Controller(TcpClient model) {
        this.model = model;
        this.view = new View(this.model, this);
        this.view.createView();
    }

    public void sendMessage(String name) {
        MessageType type=model.parseText(name);
        model.sendMessage(type,name);
    }
}
