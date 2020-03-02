package stackcalculator.exceptions;

public class StackAmountElements extends StackCalculatorExceptions {
    public StackAmountElements(String string){
        super(string);
    }

    public StackAmountElements() {
        super();
    }

    public StackAmountElements(String message, Throwable cause) {
        super(message, cause);
    }

    public StackAmountElements(Throwable cause) {
        super(cause);
    }
}
