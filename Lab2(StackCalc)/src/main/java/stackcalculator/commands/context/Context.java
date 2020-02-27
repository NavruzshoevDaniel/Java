package stackcalculator.commands.context;

import java.util.HashMap;
import java.util.Stack;

public class Context {
    private Stack<Double> stack=new Stack<>();
    private HashMap<String,Double> defineTable=new HashMap<>();


    public void putInDefineTable(String key,Double value){
        defineTable.put(key,value);
    }

    public Double getFromDefineTable(String key){
       return defineTable.get(key);
    }

    public void push(Double value){
        stack.push(value);
    }

    public Double pop(){
        return stack.pop();
    }

    public Double peek(){
        return stack.peek();
    }

    public int getStackSize(){
        return stack.size();
    }
}
