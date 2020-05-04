package ru.nsu.ccfit.navruzshoev;

import java.io.IOException;
import java.util.logging.LogManager;

public class Main {
    public static void main(String[] args) {
        LogManager logManager=LogManager.getLogManager();
        try {
            logManager.readConfiguration(Main.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
