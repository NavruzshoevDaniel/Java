package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.*;

class SqrtTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Sqrt sqrt=new Sqrt();
        context.push(16.0d);
        sqrt.execute(context,new String[0]);
        assertEquals(context.peek(),4.0,0.00000001);
        sqrt.execute(context,new String[0]);
        assertEquals(context.peek(),2.0,0.00000001);

    }
}