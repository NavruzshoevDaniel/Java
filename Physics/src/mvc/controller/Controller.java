package mvc.controller;

import mvc.view.View;
import mvc.model.Model;

public class Controller {
    private View view;
    private Model model;


    public Controller(Model model) {
        this.model = model;
        view = new View(model, this);
        view.createView();
    }

    public void update(int value, String text) {

        try {
            double b=Double.parseDouble(text);
            model.update(value,b);
        }
        catch (Exception ex){

        }

    }
}
