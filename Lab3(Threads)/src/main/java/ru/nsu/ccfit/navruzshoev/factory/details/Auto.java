package ru.nsu.ccfit.navruzshoev.factory.details;

import java.util.concurrent.atomic.AtomicInteger;

public class Auto implements Detail {
    private static volatile AtomicInteger sizeID=new AtomicInteger(0);
    private final int ID;
    private BodyDetail bodyDetail;
    private AccessoryDetail accessoryDetail;
    private EngineDetail engineDetail;

    public Auto(BodyDetail bodyDetail, AccessoryDetail accessoryDetail, EngineDetail engineDetail) {
        ID = sizeID.incrementAndGet();
        this.bodyDetail = bodyDetail;
        this.accessoryDetail = accessoryDetail;
        this.engineDetail = engineDetail;
    }

    @Override
    public int getID() {
        return ID;
    }

    public BodyDetail getBodyDetail() {
        return bodyDetail;
    }

    public AccessoryDetail getAccessoryDetail() {
        return accessoryDetail;
    }

    public EngineDetail getEngineDetail() {
        return engineDetail;
    }
}
