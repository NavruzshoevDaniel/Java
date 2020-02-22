package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;


public class Define implements ICommand {
    private boolean checkArgs(String[] args) {
        if (args == null || args.length != 2)
            return false;
        if ((!ICommand.isNumeric(args[0])) && ICommand.isNumeric(args[1]))
            return true;
        else return false;

    }

    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (checkArgs(args))
            throw new WrongNumberOfArguments("WrongArguments exceptions for Define");
        context.getDefineTable().put(args[0], Double.valueOf(args[1]));
    }

}
