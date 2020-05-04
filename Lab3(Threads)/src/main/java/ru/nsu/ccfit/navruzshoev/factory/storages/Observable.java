package ru.nsu.ccfit.navruzshoev.factory.storages;

import ru.nsu.ccfit.navruzshoev.factory.controller.IController;

public interface Observable {
     void notifyControllers();
     void registerObserver(IController controller);
}
