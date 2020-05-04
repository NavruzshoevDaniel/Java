package ru.nsu.ccfit.navruzshoev.threadpool;

import ru.nsu.ccfit.navruzshoev.factory.storages.Storage;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer<T> implements Runnable {
    private Logger logger = Logger.getLogger(Producer.class.getName());
    private Storage<T> storage;
    private Class<T> detailCreator;
    private int delay;

    public Producer(Storage<T> storage, Class<T> detailCreator, int delay) {
        this.storage = storage;
        this.detailCreator = detailCreator;
        this.delay = delay;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                storage.add(detailCreator.getDeclaredConstructor().newInstance());
                Thread.sleep(delay);
            } catch (InstantiationException | IllegalAccessException |
                    InvocationTargetException | NoSuchMethodException e) {
                logger.log(Level.WARNING, "", e);

            } catch (InterruptedException e) {
                logger.log(Level.WARNING, Thread.currentThread().getName() + " has just interrupted");
                break;
            }
        }
    }
}
