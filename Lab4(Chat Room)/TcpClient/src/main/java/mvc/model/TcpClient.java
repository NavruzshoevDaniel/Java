package mvc.model;


import messages.Message;
import messages.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TcpClient implements Runnable, Observable {
    private Logger logger = Logger.getLogger(TcpClient.class.getName());
    private ArrayList<Observer> observers = new ArrayList<>();
    private String ip;
    private final int port;
    private Socket socket;
    private Properties property;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean isLogin = false;
    private boolean exit = false;
    private boolean connect = false;

    public TcpClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initCommands();
    }

    private void initCommands() {
        InputStream fis = null;
        try {
            fis = getClass().getClassLoader().getResourceAsStream("resources/commands.properties");
            if (fis == null)
                throw new IOException("Config file wasn't opened");
            logger.log(Level.FINE, "InputStream was created");
            property = new Properties();
            property.load(fis);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Config file wasn't opened");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    logger.log(Level.FINE, "InputStream was closed successfully ");
                }

            } catch (IOException ex) {
                logger.log(Level.WARNING, "The program couldn't close InputStream");
            }
        }
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
        } catch (IOException e) {
            logger.log(Level.WARNING, "Server isn't working");
            updateObservers(OutputType.ERROR, "Server isn't working now.\nTry again later");
        }
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (!exit) {
                Message newMessage = (Message) objectInputStream.readObject();
                if (newMessage.getType() == MessageType.SUCCESS_LOGIN) {
                    isLogin = true;
                }
                updateObservers(whichOutType(newMessage), newMessage.getText());
                if (newMessage.getType() == MessageType.EXIT) {
                    isLogin = false;
                    exit = true;
                }
            }

            objectInputStream.close();
            objectOutputStream.close();
            logger.log(Level.INFO, "You have just finished chat");

        } catch (IOException e) {
            logger.log(Level.WARNING, "Socket error", e);
            updateObservers(OutputType.ERROR, "Sorry, but something has just happened on the server.\n" +
                    "Try again later");
            connect = false;

        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Object Input Stream error", e);
        } finally {

            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }

                if (objectInputStream != null) {
                    objectInputStream.close();
                }

                if (socket != null) {
                    close();
                }

            } catch (IOException e) {
                logger.log(Level.WARNING, "", e);
            }
        }


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
    public void updateObservers(OutputType type, String text) {
        for (Observer observer : observers) {
            observer.updateView(type, text);
        }
    }

    public void sendMessage(Message message) {
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            logger.log(Level.WARNING, Thread.currentThread().getName() + " can't send text");
        }
    }

    public void sendMessage(MessageType type, String text) {
        sendMessage(new Message(type, text));
    }

    private OutputType whichOutType(Message message) {
        MessageType type = message.getType();
        switch (type) {
            case ERROR_NAME:
                return OutputType.ERROR;

            case TEXT_RESPONSE:
                return OutputType.SHARED;

            default:
                return OutputType.SYSTEM;
        }
    }


    public MessageType parseText(String string) {
        if (!isLogin) {
            return MessageType.LOGIN;
        }

        try {
            MessageType type = MessageType.valueOf(property.getProperty(string));
            return type;
        } catch (Exception e) {
            return MessageType.TEXT_REQUEST;
        }


    }


}
