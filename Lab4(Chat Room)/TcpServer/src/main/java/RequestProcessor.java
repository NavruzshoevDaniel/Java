
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestProcessor implements Runnable {
    private Logger logger = Logger.getLogger(RequestProcessor.class.getName());

    private final TcpServer tcpServer;
    private final Socket socket;
    private String name;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public RequestProcessor(Socket socket, TcpServer tcpServer) {
        this.socket = socket;
        this.tcpServer = tcpServer;
    }

    public void close() throws IOException {
        socket.close();
    }

    public void sendMessage(Message message) throws IOException {
        if (objectOutputStream != null) {
            objectOutputStream.writeObject(message);
        }
    }

    @Override
    public void run() {

        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            objectInputStream = new ObjectInputStream(input);
            objectOutputStream = new ObjectOutputStream(output);

            while (true) {
                logger.log(Level.INFO, Thread.currentThread().getName() + " is waiting message...");
                Message message = (Message) objectInputStream.readObject();
                logger.log(Level.INFO, Thread.currentThread().getName() + " received: type=" + message.getType() +
                        " text=" + message.getText());

                objectOutputStream.flush();
            }

        } catch (IOException e) {
            logger.log(Level.WARNING, "Socket Stream error", e);

        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Read object error", e);
        } finally {
            tcpServer.removeClient(this);
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Object Streams close error", ex);
            }
            logger.log(Level.INFO, Thread.currentThread() + " has just ended work");
        }
    }

    void login(String name) {
        this.name = name;
        tcpServer.addClient(this);
    }

    public String getName() {
        return name;
    }


}
