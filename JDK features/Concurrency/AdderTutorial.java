import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

//Adders: Thread Safe Operations
//Thread Safety: Concurrent access and updation of shared resource
//               without side effects

public class AdderTutorial{
    public static void main(String[] args) throws InterruptedException{ 
        //Use LongAdder and DoubleAdder when there are a lot of threads
        //in contention to update the same count
        //AtomicLong and AtomicDouble have negative performance impact
        //in high contention environments

        //adders are the shared resources
        LongAdder longCounter = new LongAdder();
        DoubleAdder doubleCounter = new DoubleAdder();

        //the increment() operation is thread safe
        Runnable incrementer = () -> longCounter.increment();
        Runnable doubleAdder = () -> doubleCounter.add(1.0);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        //100 threads try to update two shared values from a pool of 10 threads
        IntStream.range(0, 100).forEach(e -> {
            threadPool.submit(incrementer);
            threadPool.submit(doubleAdder);
        });

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Long Sum: " + longCounter.sum());
        System.out.println("Double Sum: " + doubleCounter.sum());

    }
}