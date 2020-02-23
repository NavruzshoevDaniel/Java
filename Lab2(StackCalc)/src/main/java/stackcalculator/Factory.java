package stackcalculator;

import stackcalculator.commands.ICommand;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factory {

    private Factory() { };
    private static Factory instance = null;
    private static Logger logger = Logger.getLogger(Factory.class.getName());

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }


    public ICommand createCommand(String commandName) {
        FileInputStream fis = null;
        ICommand iCommand=null;

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            logger.log(Level.FINE, "FileInputStream was created");
            Properties property = new Properties();
            property.load(fis);
            String clazzCommandName = property.getProperty(commandName);
            Class command = Class.forName(clazzCommandName);
            iCommand= (ICommand) command.newInstance();
            logger.log(Level.FINE, "New Command({0}) was created",iCommand.getClass().getName());

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Config file wasn't opened");
            e.printStackTrace();

        } catch (Exception e){
            logger.log(Level.WARNING, "New Command wasn't created:",e);
        }  finally {
            try {
                if (fis != null){
                    fis.close();
                    logger.log(Level.FINE, "FileInputStream was closed successfully ");
                }

            } catch (IOException ex) {
                logger.log(Level.WARNING, "The program couldn't close FileInputStream");
            }
        }


        return iCommand;
    }
}
