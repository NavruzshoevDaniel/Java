package ru.nsu.ccfit.navruzshoev.factory.details;

import java.util.concurrent.atomic.AtomicInteger;

public class BodyDetail implements Detail {
    private static volatile AtomicInteger sizeID=new AtomicInteger(0);
    private final int ID;

    public BodyDetail() {
        ID = sizeID.incrementAndGet();
    }

    @Override
    public int getID() {
        return ID;
    }
}
