package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Push implements ICommand {
    private final static Logger logger = Logger.getLogger(Push.class.getName());

    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 1)
            throw new WrongNumberOfArguments("WrongArguments exceptions for PUSH");


        if(ICommand.isNumeric(args[0])){
            context.push(Double.valueOf(args[0]));
        } else {
            Double defineValue=context.getFromDefineTable(args[0]);
            if(defineValue==null)
                throw new WrongNumberOfArguments("WrongArguments exceptions for PUSH");
            else {
                context.push(defineValue);
            }

        }
        logger.log(Level.FINE,"Successfully did operation {0}", this.getClass().getName());
    }
}
