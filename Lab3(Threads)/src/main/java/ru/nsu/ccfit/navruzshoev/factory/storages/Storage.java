package ru.nsu.ccfit.navruzshoev.factory.storages;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage<T> {
    private Logger logger = Logger.getLogger(Storage.class.getName());
    private int capacity;
    private LinkedList<T> details;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.details = new LinkedList<>();
    }

    public synchronized void add(T detail) throws InterruptedException {

        while (true) {

            if (details.size() <= capacity) {
                details.add(detail);
                logger.log(Level.INFO, "Added the detail in " + getClass().getName());
                notify();
                return;
            }
            logger.log(Level.INFO,getClass().getName()+" overflow! Waiting for the release of space");
            this.wait();
        }


    }

    public synchronized T get() throws InterruptedException {

        while (true){

            if (details.size() > 0) {
                logger.log(Level.INFO, "Got the  detail from " + getClass().getName());
                notify();
                return details.pop();
            }

            logger.log(Level.INFO,getClass().getName()+" can't take anything! Waiting for delivery of details");
            this.wait();
        }

    }
}
