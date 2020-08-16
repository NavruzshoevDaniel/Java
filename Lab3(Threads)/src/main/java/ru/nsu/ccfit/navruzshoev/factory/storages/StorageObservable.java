package ru.nsu.ccfit.navruzshoev.factory.storages;

import ru.nsu.ccfit.navruzshoev.factory.controller.IController;
import ru.nsu.ccfit.navruzshoev.factory.details.Detail;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageObservable<T extends Detail> extends Storage<T> implements Observable {
    private Logger logger = Logger.getLogger(StorageObservable.class.getName());
    private ArrayList<IController> controllers;

    public StorageObservable(int capacity) {
        super(capacity);
        controllers = new ArrayList<>();
    }

    public StorageObservable(int capacity, Class<T> tClass) {
        super(capacity, tClass);
        controllers = new ArrayList<>();
    }

    @Override
    public synchronized T get() throws InterruptedException {
        notifyControllers();
        return super.get();
    }

    @Override
    public void notifyControllers() {
        for (IController controller : controllers) {
            controller.update();
        }
    }

    @Override
    public void registerObserver(IController controller) {
        if (controller == null) {
            throw new NullPointerException();
        }
        if (controllers.contains(controller)) {
            throw new IllegalArgumentException("Repeated controller:" + controller);
        }
        controllers.add(controller);
        logger.log(Level.INFO, "New controller subscribed: " + controller);
    }


}
