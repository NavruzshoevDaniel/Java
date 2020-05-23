

import mvc.controller.Controller;
import mvc.model.TcpClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ClientMain {
    private static Logger logger = Logger.getLogger(ClientMain.class.getName());

    public static void main(String[] args) throws IOException {
        LogManager logManager= LogManager.getLogManager();

        logManager.readConfiguration(ClientMain.class.getResourceAsStream("/logging.properties"));

        TcpClient tcpClient =new TcpClient("127.0.0.1",0);
        Controller controller= new Controller(tcpClient);
        Thread thread= new Thread(tcpClient);
        thread.start();


        try {
            thread.join();
        } catch (InterruptedException e) {
           logger.log(Level.WARNING,"Client has just interrupted",e);
        }



    }
}
