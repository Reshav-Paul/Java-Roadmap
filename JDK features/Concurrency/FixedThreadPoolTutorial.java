import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

interface TPoolTutorialMessagePrinter extends Runnable{
    @Override
    public void run();
}

interface TPoolTutorialMessagePrinter2 extends Callable<String>{
    @Override
    String call();
}

public class FixedThreadPoolTutorial {
    public static void main(String[] args) throws Exception{
        
        //create a new fixed thread pool of 4 threads
        ExecutorService service = Executors.newFixedThreadPool(4);
        //Other kinds of thread pools are also available.

        //SingleThreadExecutor is a pool with a single thread that executes all tasks,
        //and is used to complete tasks in a sequential manner

        //CachedThreadPool adds a new thread to the pool if one is not available when a
        //new task arrives. Threads that remain inactive for 60 seconds are deleted.
        //Existing threads in the pool are reused if they are free.

        //ScheduledThreadPool can execute commands periodically or after certain delays
        
        //Custom thread pools may be created using the ThreadPoolExecutor() and providing
        //custom parameters
        
        for(int i = 0; i < 5; i++){
            //Thread pools can accept objects of a class that implement Runnable
            TPoolTutorialMessagePrinter evenPrinter = () -> System.out.println(getRandomEven());
            TPoolTutorialMessagePrinter oddPrinter = () -> System.out.println(getRandomOdd());

            //Use execute() function to execute Runnable objects
            service.execute(evenPrinter);
            Thread.sleep(500);
            service.execute(oddPrinter);
        }

        //Initializes shutdown of thread pool service but would not shut down
        //immediately if tasks are pending
        service.shutdown();
        while(!service.isTerminated()){}    //Check to see if all tasks have finished
        System.out.println("Runnable tasks completed");

        //create a new thread pool with 4 threads
        service = Executors.newFixedThreadPool(4);
        for(int i = 0; i < 5; i++){
            //Thread pools can accept objects of a class that implement Callable
            TPoolTutorialMessagePrinter2 evenPrinter2 = () -> getRandomEven();
            TPoolTutorialMessagePrinter2 oddPrinter2 = () -> getRandomOdd();

            //Use submit() function to execute Callable objects
            //submit() returns a future of a generic type
            Future<String> evenMsg = service.submit(evenPrinter2);
            Thread.sleep(500);
            Future<String> oddMsg = service.submit(oddPrinter2);
            
            //get() is a blocking function and blocks the main thread until
            //the Future returned by submit() finishes and returns the expected value
            System.out.println(evenMsg.get());
            System.out.println(oddMsg.get());
        }

        //Initializes shutdown of thread pool service but would not shut down
        //immediately if tasks are pending
        service.shutdown();
        while(!service.isTerminated()){}    //Check to see if all tasks have finished

        System.out.println("Callable tasks completed");
        System.out.println("All tasks completed");
    }

    public static String getRandomEven(){
        return Thread.currentThread().getName() + " Even : " + (int)(Math.random() * 5) * 2;
    }

    public static String getRandomOdd(){
        return Thread.currentThread().getName() +  " Odd : " + ((int)(Math.random() * 5) * 2 + 1);
    }
}