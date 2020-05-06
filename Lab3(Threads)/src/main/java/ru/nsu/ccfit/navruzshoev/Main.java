package ru.nsu.ccfit.navruzshoev;

import ru.nsu.ccfit.navruzshoev.factory.Factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();
        try {
            logManager.readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Factory factory=null;

        try {
            factory = new Factory();
            logger.log(Level.INFO, "The factory has just created successfully");
        } catch (IOException e) {
            logger.log(Level.WARNING, "The factory couldn't create");
        }

        if (factory!=null){
            factory.startWorking();
        }
        Scanner in = new Scanner(System.in);
        while (true) {

            if(in.nextLine().equals("exit")){
                break;
            }

        }

        if (factory != null) {
            factory.finishWorking();
        }
        logger.log(Level.INFO,"Factory has just finished working");
    }
}
