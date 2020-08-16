package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.*;

class DivisionTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Division div=new Division();
        context.push(2.0d);
        context.push(4.0d);
        context.push(20.0d);
        div.execute(context,new String[0]);
        assertEquals(context.peek(),5.0,0.00000001);
        div.execute(context,new String[0]);
        assertEquals(context.peek(),2.5,0.00000001);

    }
}