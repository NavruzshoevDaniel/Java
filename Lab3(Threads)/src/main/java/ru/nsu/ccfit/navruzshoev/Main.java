package ru.nsu.ccfit.navruzshoev;

import ru.nsu.ccfit.navruzshoev.factory.Factory;
import ru.nsu.ccfit.navruzshoev.factory.FactoryInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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


        InputStream inputStream;
        inputStream = Main.class.getResourceAsStream("/settings.properties");
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FactoryInfo info = null;
        try {
            info = new FactoryInfo(properties);
            logger.log(Level.INFO, "The factory info has just created successfully");
        } catch (Exception e) {
            logger.log(Level.WARNING, "The factory info couldn't create");
        }

        Factory factory = new Factory(info);


        if (factory != null) {
            factory.startWorking();
        }

        Scanner in = new Scanner(System.in);
        while (true) {
            if (in.nextLine().equals("exit")) {
                break;
            }

        }

        if (factory != null) {
            factory.finishWorking();
        }
        logger.log(Level.INFO, "Factory has just finished working");
    }
}
