package ru.nsu.ccfit.navruzshoev.factory.details;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessoryDetail implements Detail {
    private static volatile AtomicInteger sizeID=new AtomicInteger(0);
    private final int ID;

    public AccessoryDetail() {
        ID = sizeID.incrementAndGet();
    }

    @Override
    public int getID() {
        return ID;
    }
}
