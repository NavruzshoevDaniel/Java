package ru.nsu.ccfit.navruzshoev.threadpool;

import ru.nsu.ccfit.navruzshoev.factory.storages.Storage;
import ru.nsu.ccfit.navruzshoev.factory.details.Auto;
import ru.nsu.ccfit.navruzshoev.factory.storages.StorageObservable;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dealer implements Runnable {
    private Logger logger = Logger.getLogger(Dealer.class.getName());
    private static volatile boolean logging=true;
    private Storage<Auto> storageAuto;
    private int delay;

    public Dealer(StorageObservable<Auto> storageAuto, int delay) {
        this.storageAuto = storageAuto;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                logger.log(Level.INFO,Thread.currentThread().getName()+" ask car");

                Auto auto=storageAuto.get();

                if(logging){
                    logger.log(Level.INFO,new Date()+" : "+Thread.currentThread().getName()+" get: Auto #"+
                            auto.getID()+" (Body:"+auto.getBodyDetail().getID()+", Motor:"+auto.getEngineDetail().getID()+
                            ", Accessory:"+auto.getAccessoryDetail().getID()+")");
                }

                Thread.sleep(delay);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING,Thread.currentThread().getName()+" has just interrupted");
                break;
            }
        }
    }
}
