import stackcalculator.StackCalculator;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;



public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        LogManager logManager=LogManager.getLogManager();

        logManager.readConfiguration(Main.class.getResourceAsStream("logging.properties"));


        StackCalculator stackCalculator;
        if (args.length > 2) {
            logger.log(Level.SEVERE, "Too many arguments");
        } else if (args.length == 0) {
            logger.log(Level.FINE, "Program selected System.in stream");
            stackCalculator = new StackCalculator(System.in);
            stackCalculator.calculate();

        } else {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(args[0]);
                logger.log(Level.FINE, "Program selected File stream");
                stackCalculator = new StackCalculator(fileInputStream);
                stackCalculator.calculate();
            } catch (Exception e) {
                logger.log(Level.WARNING, "",e);
            } finally {
                try {
                    if (fileInputStream != null)
                        fileInputStream.close();
                } catch (IOException ex) {
                    System.err.format("IOException: %s%n", ex);
                }
            }
        }
        logger.log(Level.FINE, "Successful completion of the calculator");
    }
}
