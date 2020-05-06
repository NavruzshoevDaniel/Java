package ru.nsu.ccfit.navruzshoev.factory.storages;

import ru.nsu.ccfit.navruzshoev.factory.details.Detail;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage<T extends Detail> {
    private Logger logger = Logger.getLogger(Storage.class.getName());
    private int capacity;
    private LinkedList<T> details;
    private String name;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.details = new LinkedList<>();
        this.name="";
    }

    public Storage(int capacity, Class<T> detail) {
        this.capacity = capacity;
        this.details = new LinkedList<>();
        this.name = detail.getSimpleName()+ " Storage";
    }

    public synchronized void add(T detail) throws InterruptedException {

        while (true) {

            if (details.size() < capacity) {
                details.add(detail);
                logger.log(Level.INFO, "Added the detail in " + name+"("+Thread.currentThread().getName()+")");
                logger.log(Level.INFO, "Size of " + name + ":" + details.size()
                        +"("+Thread.currentThread().getName()+")");
                notify();
                return;
            }
            logger.log(Level.INFO, name + " is full! Waiting for the release of space"
                    +"("+Thread.currentThread().getName()+")");
            logger.log(Level.INFO, "Size of " + name + ":" + details.size());
            this.wait();
        }


    }

    public synchronized T get() throws InterruptedException {

        while (true) {

            if (details.size() > 0) {
                logger.log(Level.INFO, "Got the  detail from " + name+"("+Thread.currentThread().getName()+")");
                T detail=details.pop();
                logger.log(Level.INFO, "Size of " + name + ":" + details.size()
                        +"("+Thread.currentThread().getName()+")");
                notify();
                return detail;
            }

            logger.log(Level.INFO, name + " can't take anything! Waiting for delivery of details"
                    +"("+Thread.currentThread().getName()+")");
            this.wait();
        }

    }
}
