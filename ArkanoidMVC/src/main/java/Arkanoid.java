

import mvc.controller.Controller;
import mvc.model.Model;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Arkanoid extends JFrame {

    private static final Logger logger = Logger.getLogger(Arkanoid.class.getName());

    public static void main(String[] args) {

        LogManager logManager = LogManager.getLogManager();

        try {
            logManager.readConfiguration(Arkanoid.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Model model = new Model();
        Controller controller = new Controller(model);
    }
}
