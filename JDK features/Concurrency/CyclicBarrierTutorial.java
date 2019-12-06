import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/*
* CyclicBarrier is similar to CountDownLatch except that it can be used
* again and again in a continuous cycle.
* In the following program, 3 threads generate random numbers and then
* wait a random amount of time. They then wait for the other threads
* to complete their random waiting periods and after they all have completed
* their waiting periods the CyclicBarrier is broken and they add the
* generated numbers into a LongAdder
*/

class RandomSumGenerator implements Runnable {
    CyclicBarrier barrier;
    LongAdder adder;

    RandomSumGenerator(LongAdder adder, CyclicBarrier barrier) {
        this.adder = adder;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        long number = (long) (Math.random() * 100);
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println(threadName + " started working");

            // doing some work
            Thread.sleep(number);

            // work completed
            System.out.println(threadName + " is waiting to proceed...");

            // waiting for other threads to complete their work
            barrier.await();
            
            System.out.println(threadName + " Adding: " + number);
            adder.add(number);
        } catch (InterruptedException e) {
        } catch (BrokenBarrierException b) {
        }
    }
}

public class CyclicBarrierTutorial {
    public static void main(String args[]) throws InterruptedException {

        LongAdder adder = new LongAdder();
        CyclicBarrier barrier = new CyclicBarrier(3); // can be reused over and over in a cycle

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            // clearing the sum and the list of threads at the beginning of each iteration
            adder.reset();
            threads.clear();

            // create threads
            Stream.generate(() -> threads.add(new Thread(new RandomSumGenerator(adder, barrier)))).limit(3)
                    .collect(Collectors.toList());

            // start the threads
            threads.forEach(Thread::start);

            // wait for them to finish
            for (Thread t : threads) {
                t.join();
            }
            System.out.println("Final Sum: " + adder.sum());
        }
    }
}