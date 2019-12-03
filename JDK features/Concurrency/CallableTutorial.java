import java.util.concurrent.Callable;

//The type argument to Callable should be the type of value to return
interface CallableTutorialMessagePrinter extends Callable<String>{
    public String call() throws Exception;
}


public class CallableTutorial {
    public static void main(String[] args) throws Exception{
        
        //can override the call() method to return different Strings
        CallableTutorialMessagePrinter evenPrinter = () -> "Even : " + (int)(Math.random() * 5) * 2;
        //CallableTutorialMessagePrinter oddPrinter = () -> "Odd : " +  ((int)(Math.random() * 5) * 2 + 1);
        CallableTutorialMessagePrinter oddPrinter = new CallableTutorialMessagePrinter(){
            @Override
            public String call() throws Exception{
                Thread.sleep(500);
                return "Odd : " +  ((int)(Math.random() * 5) * 2 + 1);
            }
        };

        for(int i = 0; i < 5; i++){
            //call() returns a String
            System.out.println(evenPrinter.call());
            Thread.sleep(200);
            System.out.println(oddPrinter.call());
        }

        //Implementing Runnable or extending thread has a drawback that the 
        //start() and run() functions cannot return any value
    }
}