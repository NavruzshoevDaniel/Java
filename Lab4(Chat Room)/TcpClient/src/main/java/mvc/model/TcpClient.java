package mvc.model;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpClient implements Runnable {
    private Logger logger = Logger.getLogger(TcpClient.class.getName());
    private String ip;
    private final int port;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

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
}
