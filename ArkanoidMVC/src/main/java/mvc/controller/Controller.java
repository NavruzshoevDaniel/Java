package mvc.controller;

import mvc.model.Model;
import mvc.view.View;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements IController {
    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    private Model model;
    private View view;


    public Controller(Model model) {
        this.model = model;
        view = new View(this.model, this);
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
            model.getPlank().setInMove(true);
        }
        if (key == KeyEvent.VK_RIGHT) {
            model.getPlank().setDx(1);
            model.getPlank().setInMove(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();

        if (key == KeyEvent.VK_LEFT && model.getPlank().getDx() < 0) {
            model.getPlank().setDx(0);
            model.getPlank().setInMove(false);
        }

        if (key == KeyEvent.VK_RIGHT && model.getPlank().getDx() > 0) {
            model.getPlank().setDx(0);
            model.getPlank().setInMove(false);
        }
    }

    @Override
    public void chooseDirection(MouseEvent keyEvent) {
        logger.log(Level.INFO, "Direction coords:x=" + keyEvent.getX() + " y=" + keyEvent.getY());

        int xCoord = keyEvent.getX() - model.getBall().getX();
        int yCoord = keyEvent.getY() - model.getBall().getY();

        double norm = Math.ceil(Math.sqrt(xCoord * xCoord + yCoord * yCoord));

        model.getBall().setDirectionX((int) (xCoord < 0 ? Math.floor(xCoord / norm) : Math.ceil(xCoord / norm)));
        model.getBall().setDirectionY((int) (yCoord < 0 ? Math.floor(yCoord / norm) : Math.ceil(yCoord / norm)));

        model.setBallStart(true);
    }
}
