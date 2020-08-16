package ru.nsu.ccfit.navruzshoev.threadpool;

import ru.nsu.ccfit.navruzshoev.factory.storages.Storage;
import ru.nsu.ccfit.navruzshoev.factory.details.AccessoryDetail;
import ru.nsu.ccfit.navruzshoev.factory.details.Auto;
import ru.nsu.ccfit.navruzshoev.factory.details.BodyDetail;
import ru.nsu.ccfit.navruzshoev.factory.details.EngineDetail;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker implements Runnable {
    private Logger logger = Logger.getLogger(Worker.class.getName());
    private Storage<Auto> autoStorage;
    private Storage<AccessoryDetail> accessoryDetailStorage;
    private Storage<BodyDetail> bodyDetailStorage;
    private Storage<EngineDetail> engineDetailStorage;

    public Worker(Storage<Auto> autoStorage, Storage<AccessoryDetail> accessoryDetailStorage,
                  Storage<BodyDetail> bodyDetailStorage, Storage<EngineDetail> engineDetailStorage) {
        this.autoStorage = autoStorage;
        this.accessoryDetailStorage = accessoryDetailStorage;
        this.bodyDetailStorage = bodyDetailStorage;
        this.engineDetailStorage = engineDetailStorage;
    }


    @Override
    public void run() {

        try {
            logger.log(Level.INFO, Thread.currentThread().getName() + " has just started creating the car");
            Auto auto = new Auto(bodyDetailStorage.get(), accessoryDetailStorage.get(), engineDetailStorage.get());
            autoStorage.add(auto);
            logger.log(Level.INFO, Thread.currentThread().getName() + " has just finished creating the car");
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, Thread.currentThread().getName() + " has just interrupted");
        }

    }
}
