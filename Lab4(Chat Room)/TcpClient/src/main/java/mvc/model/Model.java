package mvc.model;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Model implements Observable {
    private static Logger logger = Logger.getLogger(Model.class.getName());
    private ArrayList<Observer> observers = new ArrayList<>();
    private TcpClient tcpClient;
    private String userName;

    public Model(TcpClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public void startClient(){
        while(true){

        }
    }


    @Override
    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (observers.contains(observer)){
            throw new IllegalArgumentException("Repeated observer:" + observer);
        }
        observers.add(observer);
    }

    @Override
    public void updateObservers() {
        for (Observer observer:observers){
            observer.updateView();
        }
    }

}
