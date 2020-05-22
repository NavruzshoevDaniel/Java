

import mvc.controller.Controller;
import mvc.model.Model;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ClientMain {
    private static Logger logger = Logger.getLogger(ClientMain.class.getName());

    public static void main(String[] args) throws IOException {
        LogManager logManager= LogManager.getLogManager();

        logManager.readConfiguration(ClientMain.class.getResourceAsStream("/logging.properties"));

        //Model model =new Model();
        //Controller controller= new Controller(model);


    }
}
