package ru.nsu.ccfit.navruzshoev.factory;

import ru.nsu.ccfit.navruzshoev.factory.controller.Controller;
import ru.nsu.ccfit.navruzshoev.factory.details.*;
import ru.nsu.ccfit.navruzshoev.factory.storages.Storage;
import ru.nsu.ccfit.navruzshoev.factory.storages.StorageObservable;
import ru.nsu.ccfit.navruzshoev.threadpool.Dealer;
import ru.nsu.ccfit.navruzshoev.threadpool.Producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factory {
    private static Logger logger = Logger.getLogger(Factory.class.getName());
    private ThreadPoolExecutor poolDealers;
    private ThreadPoolExecutor poolWorkers;
    private ThreadPoolExecutor poolSuppliers;
    private ExecutorService threadEngine;
    private ExecutorService threadBody;
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


    public Factory() throws IOException {
        init();
    }

    private void init() throws IOException {
        InputStream inputStream;
        inputStream = getClass().getResourceAsStream("/settings.properties");
        Properties properties = new Properties();

        try {
            properties.load(inputStream);
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
        } catch (IOException e) {
            logger.log(Level.WARNING, "IOException", e);
            throw e;
        }
        poolDealers = (ThreadPoolExecutor) Executors.newFixedThreadPool(dealers);
        poolSuppliers = (ThreadPoolExecutor) Executors.newFixedThreadPool(accessorySuppliers);
        poolWorkers = (ThreadPoolExecutor) Executors.newFixedThreadPool(workers);
        threadEngine = Executors.newSingleThreadExecutor();
        threadBody = Executors.newSingleThreadExecutor();
    }

    public void startWorking() {

        StorageObservable<Auto> storageObservable = new StorageObservable<>(storageAutoSize, Auto.class);
        Storage<BodyDetail> bodyDetailStorage = new Storage<>(storageBodySize, BodyDetail.class);
        Storage<AccessoryDetail> accessoryDetailStorage = new Storage<>(storageAccessorySize, AccessoryDetail.class);
        Storage<EngineDetail> engineDetailStorage = new Storage<>(storageMotorSize, EngineDetail.class);

        Controller controller = new Controller(poolWorkers, storageObservable, accessoryDetailStorage, engineDetailStorage,
                bodyDetailStorage);
        storageObservable.registerObserver(controller);


        threadEngine.execute(new Producer<EngineDetail>(engineDetailStorage, EngineDetail.class, engineDelay));
        threadBody.execute(new Producer<BodyDetail>(bodyDetailStorage, BodyDetail.class, bodyDelay));

        for (int i = 0; i < accessorySuppliers; i++) {
            poolSuppliers.execute(new Producer<AccessoryDetail>(accessoryDetailStorage, AccessoryDetail.class,
                    accessoryDelay));
        }

        for (int i = 0; i < dealers; i++) {
            poolDealers.execute(new Dealer(storageObservable, dealersDelay));
        }


    }

    public void finishWorking() {
        poolDealers.shutdownNow();
        poolSuppliers.shutdownNow();
        poolWorkers.shutdownNow();
        threadBody.shutdownNow();
        threadEngine.shutdownNow();
        logger.log(Level.INFO, "All threads have just interrupted");
    }
}
