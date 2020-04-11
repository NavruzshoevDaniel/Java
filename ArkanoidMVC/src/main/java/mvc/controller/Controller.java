package mvc.controller;

import mvc.model.Model;
import mvc.view.View;

import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class Controller implements IController {
    private static final Logger logger =Logger.getLogger(Controller.class.getName());
    private Model model;
    private View view;


    public Controller(Model model) {
        this.model=model;
        view= new View(this.model,this);
        view.createView();
        model.init();
    }

    @Override
    public void startGame() {
        model.setStateGame(Model.State.inGame);
        view.enableGame();
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

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
           model.getPlank().setDx(-1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            model.getPlank().setDx(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            model.getPlank().setDx(0);
        }
        if (key == KeyEvent.VK_RIGHT) {
            model.getPlank().setDx(0);
        }
    }
}
