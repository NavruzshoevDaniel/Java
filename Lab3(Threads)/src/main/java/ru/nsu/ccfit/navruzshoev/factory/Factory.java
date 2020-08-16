package ru.nsu.ccfit.navruzshoev.factory;

import ru.nsu.ccfit.navruzshoev.factory.controller.Controller;
import ru.nsu.ccfit.navruzshoev.factory.details.*;
import ru.nsu.ccfit.navruzshoev.factory.storages.Storage;
import ru.nsu.ccfit.navruzshoev.factory.storages.StorageObservable;
import ru.nsu.ccfit.navruzshoev.threadpool.Dealer;
import ru.nsu.ccfit.navruzshoev.threadpool.Producer;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factory {
    private static Logger logger = Logger.getLogger(Factory.class.getName());
    private Thread[] threadsDealers;
    private ThreadPoolExecutor poolWorkers;
    private Thread[] threadsSuppliers;
    private Thread threadEngine;
    private Thread threadBody;
    private StorageObservable<Auto> storageObservable;
    private Storage<BodyDetail> bodyDetailStorage;
    private Storage<AccessoryDetail> accessoryDetailStorage;
    private Storage<EngineDetail> engineDetailStorage;
    private FactoryInfo info;


    public Factory(FactoryInfo info) {
        this.info = info;
        init();
    }

    private void init() {
        storageObservable = new StorageObservable<>(info.getStorageAutoSize(), Auto.class);
        bodyDetailStorage = new Storage<>(info.getStorageBodySize(), BodyDetail.class);
        accessoryDetailStorage = new Storage<>(info.getStorageAccessorySize(), AccessoryDetail.class);
        engineDetailStorage = new Storage<>(info.getStorageMotorSize(), EngineDetail.class);

        threadsDealers = new Thread[info.getDealers()];
        threadsSuppliers = new Thread[info.getAccessorySuppliers()];

        for (int i = 0; i < info.getDealers(); i++) {
            threadsDealers[i] = new Thread(new Dealer(storageObservable, info.getDealersDelay()));
        }
        for (int i = 0; i < info.getAccessorySuppliers(); i++) {
            threadsSuppliers[i] = new Thread(new Producer<AccessoryDetail>(accessoryDetailStorage, AccessoryDetail.class,
                    info.getAccessoryDelay()));
        }

        poolWorkers = (ThreadPoolExecutor) Executors.newFixedThreadPool(info.getCountWorkers());

        threadEngine = new Thread(new Producer<EngineDetail>(engineDetailStorage, EngineDetail.class,
                info.getEngineDelay()));
        threadBody = new Thread(new Producer<BodyDetail>(bodyDetailStorage, BodyDetail.class, info.getBodyDelay()));
    }

    public void startWorking() {

        Controller controller = new Controller(poolWorkers, storageObservable, accessoryDetailStorage, engineDetailStorage,
                bodyDetailStorage);

        storageObservable.registerObserver(controller);


        threadEngine.start();
        threadBody.start();

        for (int i = 0; i < info.getAccessorySuppliers(); i++) {
            threadsSuppliers[i].start();
        }

        for (int i = 0; i < info.getDealers(); i++) {
            threadsDealers[i].start();
        }


    }

    public void finishWorking() {
        for (int i = 0; i < info.getDealers(); i++) {
            threadsDealers[i].interrupt();
        }
        for (int i = 0; i < info.getAccessorySuppliers(); i++) {
            threadsSuppliers[i].interrupt();
        }

        threadBody.interrupt();

        threadEngine.interrupt();



        poolWorkers.shutdownNow();

        try {
            joinThreads();
        } catch (InterruptedException e) {
            logger.log(Level.WARNING,"Threads interrupted while they were closing");
        }

        logger.log(Level.INFO, "All threads have just interrupted");
    }

    private void joinThreads() throws InterruptedException {
        for (int i = 0; i < info.getDealers(); i++) {
            threadsDealers[i].join();
        }
        for (int i = 0; i < info.getAccessorySuppliers(); i++) {
            threadsSuppliers[i].join();
        }

        threadBody.join();

        threadEngine.join();

        poolWorkers.awaitTermination(30, TimeUnit.SECONDS);

    }
}
