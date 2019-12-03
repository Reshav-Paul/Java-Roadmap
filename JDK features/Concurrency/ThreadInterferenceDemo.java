import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//--------------------------IMPORTANT NOTICE---------------------------//
//The following code is for demo purpose only
//It showcases how multithreading can cause thread interference
//Java code should not be written in the style it has been in the
//methods increment() and decrement()
//Here these methods only demonstrate how these operations are done internally

class Counter{
    private int count = 0;
    //Unsyncronized Counter which can lead to thread interference
    //and inconsistency in data
    int getCount(){
        return count;
    }
    void increment(){
        //count++;
        //the following code performs increment the way it is performed internally
        //and purposefully introduces a delay
        //in the hope that it might get interrupted
        int temp = count;
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        temp++;
        count = temp;
    }
    void decrement(){
        //count--;
        //the following code performs decrement the way it is performed internally
        int temp = count;
        temp--;
        count = temp;
    }
}


public class ThreadInterferenceDemo {
    public static void main(String[] args){
        
        Counter counter = new Counter();
        Thread incrementerThread = new Thread(){
            public void run(){
                counter.increment();
            }
        };
        Thread decrementerThread = new Thread(){
            public void run(){
                counter.decrement();
            }
        };
        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        for(int i = 0; i < 10; i++){
            threadPool.execute(incrementerThread);
            threadPool.execute(decrementerThread);
        }

        threadPool.shutdown();
        while(!threadPool.isTerminated()){}

        //Output should be zero, but in some cases
        //it comes out as non zero due to thread interference
        System.out.println(counter.getCount());
    }    
}