import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SynchronousCounter {

    private int count = 0;
    public int getCount(){
        return count;
    }
    synchronized public void increment(){
        //method synchronization
        count++;
    }
    public void decrement(){
        //statement synchronization
        synchronized(this){
            count--;
        }
    }
}


public class SynchronousTutorial {
    public static void main(String[] args) {
        
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        SynchronousCounter counter = new SynchronousCounter();
        Thread synchedIncrementer = new Thread(){
            public void run(){
                counter.increment();
            }
        };
        Thread synchedDecrementer = new Thread(){
            public void run(){
                counter.decrement();
            }
        };

        for(int i = 0; i < 10; i++){
            threadPool.execute(synchedIncrementer);
            threadPool.execute(synchedDecrementer);
        }

        threadPool.shutdown();
        while(!threadPool.isTerminated()){}

        //Output will be zero always because of synchronized access
        System.out.println(counter.getCount());
    }    
}