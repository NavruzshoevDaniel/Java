package mvc.model;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TcpClient implements Runnable, Observable {
    private Logger logger = Logger.getLogger(TcpClient.class.getName());
    private ArrayList<Observer> observers = new ArrayList<>();
    private String ip;
    private final int port;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String name;

    public TcpClient(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                objectOutputStream.flush();
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, "Socket error", e);
        }
    }

    public void login(String name) {
        this.name = name;
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException();
        }
        if (observers.contains(observer)) {
            throw new IllegalArgumentException("Repeated observer:" + observer);
        }
        observers.add(observer);
    }

    @Override
    public void updateObservers() {
        for (Observer observer : observers) {
            observer.updateView();
        }
    }

    public void sendMessage(MessageType messageType,String text) {
        try {
            objectOutputStream.writeObject(text);
        } catch (IOException e) {
            logger.log(Level.WARNING, Thread.currentThread().getName() + " can't send text");
        }
    }

}
