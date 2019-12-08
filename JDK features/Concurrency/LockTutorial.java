import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Lock is an interface. Its implementations like the ReentrantLock provides
//the capability to execute programs in a thread-safe manner. They provide
//synchronization among threads in critical sections of code.

class LockedCounter implements Runnable {
    private Lock lock;
    // Locks can be used to create conditions which tell a thread whether
    // it should wait before executing a critical section. The particular
    // condition is provided by the program code
    Condition entryCondition;
    private long count;
    private int delta;
    private int nThreadsDispatched;

    LockedCounter(int delta) {
        lock = new ReentrantLock(true);
        entryCondition = lock.newCondition();
        count = 0;
        this.delta = delta;
        nThreadsDispatched = 0;
    }

    @Override
    public void run() {
        // acquire lock
        lock.lock();

        try {
            // number of threads dispatched by main thread
            nThreadsDispatched++;

            // if main thread has dispatched all threads they can proceed
            // to the critical section
            if (nThreadsDispatched == 5) {
                entryCondition.signalAll();
                System.out.println("Main Thread has disptached all threads");
                // increment so that no other thread invokes signalAll()
                nThreadsDispatched++;
            }

            // if the main thread has not dispatched all threads current thread
            // has to wait until timeout happens or main thread dispatches all threads.
            // NOTE: Use while loop to prevent spurious wake-ups
            while (nThreadsDispatched < 5)
                entryCondition.await(5, TimeUnit.SECONDS);

            // critical section begins
            count += delta;
            // critical section ends

            System.out.println(Thread.currentThread().getName() + " incremented count to: " + count);
        } catch (InterruptedException e) {
            entryCondition.signalAll();
        } finally {
            // release lock
            lock.unlock();
        }
    }

    long getCount() {
        return count;
    }
}

public class LockTutorial {
    public static void main(String args[]) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        LockedCounter counter = new LockedCounter(1);

        for (int i = 0; i < 10; i++) {
            threadPool.submit(counter);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("Final Count: " + counter.getCount());
    }
}