package mvc.controller;

import mvc.view.View;
import mvc.model.Model;

import java.util.logging.Logger;

public class Controller {
    private final Logger logger = Logger.getLogger(Controller.class.getName());
    private View view;
    private Model model;


    public Controller(Model model) {
        this.model = model;
        view = new View(model, this);
        view.createView();
    }

    public void update(String param,double value){
        model.putInDefineTable(param,value);
        model.update();
    }
}
