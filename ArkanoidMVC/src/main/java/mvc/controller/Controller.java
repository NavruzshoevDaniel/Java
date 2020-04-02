package mvc.controller;

import mvc.model.Model;
import mvc.view.View;

import java.util.logging.Logger;

public class Controller implements IController {
    private static final Logger logger =Logger.getLogger(Controller.class.getName());
    private Model model;
    private View view;


    public Controller(Model model) {
        this.model=model;
        view= new View(this.model,this);
        view.createView();
        model.on();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void continueGame() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public void endGame() {

    }
}
