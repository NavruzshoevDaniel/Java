package stackcalculator.commands;

import org.junit.jupiter.api.Test;
import stackcalculator.commands.context.Context;
import stackcalculator.exceptions.StackCalculatorExceptions;

import static org.junit.jupiter.api.Assertions.*;

class PushTest {

    @Test
    void execute() throws StackCalculatorExceptions {
        Context context= new Context();
        Push push=new Push();
        String[] args=new String[1];
        args[0]="5";

        push.execute(context,args);
        assertEquals(context.peek(),5.0,0.00000001);

        context.putInDefineTable("a",4.0);
        args[0]="a";
        push.execute(context,args);
        assertEquals(context.peek(),4.0,0.00000001);

    }
}