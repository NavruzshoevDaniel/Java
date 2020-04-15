package mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface IController {
    void startGame();
    void continueGame();
    void stopGame();
    void endGame();
    void keyPressed(KeyEvent keyEvent);
    void keyReleased(KeyEvent keyEvent);
    void chooseDirection(MouseEvent keyEvent);
}
