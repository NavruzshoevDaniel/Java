package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.*;

class SubTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Sub sub=new Sub();
        context.getStack().push(5.0d);
        context.getStack().push(4.0d);
        context.getStack().push(1.0d);
        sub.execute(context,new String[0]);
        assertEquals(context.getStack().peek(),-3.0,0.00000001);
        sub.execute(context,new String[0]);
        assertEquals(context.getStack().peek(),-8.0,0.00000001);
        context.getStack().push(10.0d);
        sub.execute(context,new String[0]);
        assertEquals(context.getStack().peek(),18.0,0.00000001);
    }
}