package mvc.controller;

import mvc.model.TcpClient;
import mvc.view.View;

public class Controller {
    private TcpClient model;
    private View view;

    public Controller(TcpClient model) {
        this.model = model;
        this.view = new View(this.model,this);
        this.view.createView();
    }

    void sendMessage(String name){
        model.login(name);
    }
}
