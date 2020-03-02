package stackcalculator;

import com.sun.xml.internal.bind.v2.ClassFactory;
import stackcalculator.commands.ICommand;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factory {
    private Properties property;

    private Factory() {
        InputStream fis = null;
        try {
            fis = getClass().getClassLoader().getResourceAsStream("resources/config.properties");
            logger.log(Level.FINE, "InputStream was created");
            property = new Properties();
            property.load(fis);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Config file wasn't opened");
            e.printStackTrace();
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

    };

    private volatile static Factory instance = null;
    private static Logger logger = Logger.getLogger(Factory.class.getName());

    public static Factory getInstance() {
        if (instance == null) {
            synchronized (ClassFactory.class) {
                if (instance == null){
                    instance = new Factory();
                }
            }
        }
        return instance;
    }


    public ICommand createCommand(String commandName) {
        ICommand iCommand = null;

        try {
            String clazzCommandName = property.getProperty(commandName);
            Class command = Class.forName(clazzCommandName);
            iCommand = (ICommand) command.newInstance();
            logger.log(Level.FINE, "New Command({0}) was created", iCommand.getClass().getName());
        } catch (Exception e) {
            logger.log(Level.WARNING, "New Command wasn't created:", e);
        }


        return iCommand;
    }
}
