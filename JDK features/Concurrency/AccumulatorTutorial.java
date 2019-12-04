import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

//Accumulators: Thread Safe Operations
//Thread Safety: Concurrent access and updation of shared resource
//               without side effects

public class AccumulatorTutorial{
    public static void main(String[] args) throws InterruptedException{
        
        //While LongAdder and DoubleAdder provide addition
        //and subtraction capabilities,
        //Accumulators can be used to perform any valid operation

        //accumulators are the shared resources
        LongAccumulator longAccumulator = new LongAccumulator((a, b) -> a + b + 10, 0);
        DoubleAccumulator doubleAccumulator = new DoubleAccumulator((a, b) -> a + b + 20, 0);

        //the accumulate() operation is thread safe
        Runnable accumulateLong = () -> longAccumulator.accumulate(1);
        Runnable accumulateDouble = () -> doubleAccumulator.accumulate(1);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        //100 threads try to update two shared values from a pool of 10 threads
        IntStream.range(0, 100).forEach(e ->{
            threadPool.submit(accumulateLong);
            threadPool.submit(accumulateDouble);
        });

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Long Accumulated Value: " + longAccumulator.intValue());
        System.out.println("Double Accumulated Value: " + doubleAccumulator.intValue());
    }
}