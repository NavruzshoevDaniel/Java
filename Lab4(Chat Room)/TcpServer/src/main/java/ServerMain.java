

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerMain {
    private static Logger logger = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) throws IOException {
        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(ServerMain.class.getResourceAsStream("resources/logging.properties"));

        try {
            Thread server = new Thread(new TcpServer(2048));

            server.start();
            server.join();
        } catch (InterruptedException ex) {
            logger.log(Level.WARNING, "Server has just interrupted");
        }




    }
}
