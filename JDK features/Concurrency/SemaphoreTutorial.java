import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//Semaphore is used to provide controlled access to a limited resource

class LimitedResource implements Runnable {

    Semaphore semaphore;

    LimitedResource(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            //acquire semaphore for accessing the resource
            semaphore.acquire();
            System.out.println("(+) " + Thread.currentThread().getName() + " is using the service");

            //do some work
            Thread.sleep((int)(Math.random()) * 500 + 500);
            System.out.println("(-) " + Thread.currentThread().getName() + " is exiting the service");
        } catch (InterruptedException e) { //handle exception
        } finally {
            //release the semaphore after the work is done
            semaphore.release();
        }
    }
}

public class SemaphoreTutorial {
    public static void main(String[] args) throws InterruptedException{

        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        //Create a Semaphore. The paramter denotes the number of threads that
        //can simultaneously access the shared resource
        Semaphore semaphore = new Semaphore(3);

        for(int i = 0; i < 10; i++){
            threadPool.submit(new LimitedResource(semaphore));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
    }
}