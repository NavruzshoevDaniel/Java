import stackcalculator.StackCalculator;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        StackCalculator stackCalculator;

        if(args.length==0){
            stackCalculator= new StackCalculator(System.in);
            stackCalculator.calculate();
        }
        else{
            FileInputStream fileInputStream=null;
            try {
                fileInputStream=new FileInputStream(args[0]);
                stackCalculator= new StackCalculator(fileInputStream);
                stackCalculator.calculate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileInputStream != null)
                        fileInputStream.close();
                } catch (IOException ex) {
                    System.err.format("IOException: %s%n", ex);
                }
            }
        }

    }
}
