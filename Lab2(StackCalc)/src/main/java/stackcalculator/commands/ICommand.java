package stackcalculator.commands;

import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;


public interface ICommand {

    static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    void execute(Context context, String[] args) throws StackCalculatorExceptions;
}