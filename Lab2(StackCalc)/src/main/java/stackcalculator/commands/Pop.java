package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;
import stackcalculator.exceptions.WrongNumberOfArguments;

public class Pop implements ICommand {
    @Override
    public void execute(Context context, String[] args) throws StackCalculatorExceptions {
        if (args == null || args.length != 0) throw new WrongNumberOfArguments("WrongArguments exceptions for Pop");
        context.getStack().pop();
    }
}
