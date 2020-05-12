package ru.nsu.ccfit.navruzshoev.factory.controller;


import ru.nsu.ccfit.navruzshoev.factory.details.AccessoryDetail;
import ru.nsu.ccfit.navruzshoev.factory.details.Auto;
import ru.nsu.ccfit.navruzshoev.factory.details.BodyDetail;
import ru.nsu.ccfit.navruzshoev.factory.details.EngineDetail;
import ru.nsu.ccfit.navruzshoev.factory.storages.Storage;
import ru.nsu.ccfit.navruzshoev.factory.storages.StorageObservable;
import ru.nsu.ccfit.navruzshoev.threadpool.Worker;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements IController {
    private Logger logger = Logger.getLogger(Controller.class.getName());
    private ThreadPoolExecutor workers;
    private StorageObservable<Auto> storageObservable;
    private Storage<AccessoryDetail> accessoryDetailStorage;
    private Storage<EngineDetail> engineDetailStorage;
    private Storage<BodyDetail> bodyDetailStorage;
    private double minProportion=0.5;

    public Controller(ThreadPoolExecutor workers, StorageObservable<Auto> storageObservable,
                      Storage<AccessoryDetail> accessoryDetailStorage, Storage<EngineDetail> engineDetailStorage,
                      Storage<BodyDetail> bodyDetailStorage) {
        this.workers = workers;
        this.storageObservable = storageObservable;
        this.accessoryDetailStorage = accessoryDetailStorage;
        this.engineDetailStorage = engineDetailStorage;
        this.bodyDetailStorage = bodyDetailStorage;
    }


    @Override
    public void update() {
        int minCountAuto= (int) ((double)storageObservable.getCapacity()*minProportion);
        while (minCountAuto >= workers.getActiveCount() + storageObservable.getSize()) {
            workers.execute(new Worker(storageObservable, accessoryDetailStorage, bodyDetailStorage, engineDetailStorage));
        }
    }
}
