package stackcalculator;

import stackcalculator.commands.ICommand;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Factory {

    private Factory() { };
    private static Factory instance = null;

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
            Properties property = new Properties();
            property.load(fis);
            String clazzCommandName = property.getProperty(commandName);
            Class command = Class.forName(clazzCommandName);
            iCommand= (ICommand) command.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
        }

        if (iCommand!=null){

        }

        return iCommand;
    }
}
