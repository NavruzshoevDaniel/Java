package mvc.controller;

import mvc.model.Model;
import mvc.view.View;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(model,this);
        this.view.createView();
    }
}
