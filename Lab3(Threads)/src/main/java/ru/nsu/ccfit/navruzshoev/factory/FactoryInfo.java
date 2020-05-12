package ru.nsu.ccfit.navruzshoev.factory;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FactoryInfo {
    private static Logger logger = Logger.getLogger(FactoryInfo.class.getName());
    private int storageBodySize;
    private int storageMotorSize;
    private int storageAccessorySize;
    private int storageAutoSize;
    private int accessorySuppliers;
    private int workers;
    private int dealers;
    private boolean logSale;
    private int bodyDelay;
    private int accessoryDelay;
    private int engineDelay;
    private int dealersDelay;


    public FactoryInfo(Properties settingsProperty) throws Exception {
        init(settingsProperty);
    }


    private void init(Properties properties) throws Exception {

        try {
            storageAccessorySize = Integer.parseInt(properties.getProperty("StorageAccessorySize"));
            storageBodySize = Integer.parseInt(properties.getProperty("StorageBodySize"));
            storageMotorSize = Integer.parseInt(properties.getProperty("StorageMotorSize"));
            storageAutoSize = Integer.parseInt(properties.getProperty("StorageAutoSize"));
            accessorySuppliers = Integer.parseInt(properties.getProperty("AccessorySuppliers"));
            workers = Integer.parseInt(properties.getProperty("Workers"));
            dealers = Integer.parseInt(properties.getProperty("Dealers"));
            bodyDelay = Integer.parseInt(properties.getProperty("BodyDelay"));
            accessoryDelay = Integer.parseInt(properties.getProperty("AccessoryDelay"));
            engineDelay = Integer.parseInt(properties.getProperty("EngineDelay"));
            dealersDelay = Integer.parseInt(properties.getProperty("DealersDelay"));
            logSale = Boolean.parseBoolean(properties.getProperty("LogSale"));

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "No such key", e);
            throw e;
        } catch (NullPointerException e) {
            logger.log(Level.WARNING, "No such property file", e);
            throw e;
        }
    }


    public int getStorageBodySize() {
        return storageBodySize;
    }

    public int getStorageMotorSize() {
        return storageMotorSize;
    }

    public int getStorageAccessorySize() {
        return storageAccessorySize;
    }

    public int getStorageAutoSize() {
        return storageAutoSize;
    }

    public int getAccessorySuppliers() {
        return accessorySuppliers;
    }

    public int getCountWorkers() {
        return workers;
    }

    public int getDealers() {
        return dealers;
    }

    public boolean isLogSale() {
        return logSale;
    }

    public int getBodyDelay() {
        return bodyDelay;
    }

    public int getAccessoryDelay() {
        return accessoryDelay;
    }

    public int getEngineDelay() {
        return engineDelay;
    }

    public int getDealersDelay() {
        return dealersDelay;
    }
}
