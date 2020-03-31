package mvc.controller;

import mvc.model.Model;
import mvc.view.View;

public class Controller implements IController {
    private Model model;
    private View view;


    public Controller(Model model) {
        this.model=model;
        view= new View(this.model,this);
        view.createView();
        model.on();
    }
}
