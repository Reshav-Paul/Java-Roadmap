import java.util.concurrent.CountDownLatch;
/*
* CountDownLatch is used to ensure that all threads reach a certain point
* in execution before they can move on to the next step.
*
* Example Use case scenario:
* All threads should have successfully fetched user data
* from the network before the required sort operation can begin.
*
* In the following program, 2 threads generate random numbers and then
* wait that corressponding amount of time. They then wait for the other thread
* to complete its random waiting period and after they all have completed
* their waiting periods the threads can proceed towards completion
*/
class CountDownDemo implements Runnable{

    CountDownLatch waitLatch;
    CountDownDemo(CountDownLatch latch){
        waitLatch = latch;
    }
    @Override
    public void run(){
        String currentThread = Thread.currentThread().getName();
        System.out.println(currentThread + " : is working...");
        try{
            //doing some work
            Thread.sleep((long) (Math.random() * 2000));
        } catch(InterruptedException e){};

        //work done
        //countdown() to signal that this thread is ready to proceed
        waitLatch.countDown();
        System.out.println(currentThread + " : is waiting to proceed...");

        try{
            //wait for the count to reach zero when all threads are ready
            waitLatch.await();
        }catch(InterruptedException e){
            System.out.println(currentThread + " was Interrupted");
        }
        //Execution proceeds after all threads have successfully arrived
        System.out.println(currentThread + " : has successfully completed execution");
    }
}

public class CountDownLatchTutorial{
    public static void main(String args[]) throws InterruptedException{

        CountDownLatch latch = new CountDownLatch(2);
        Thread thread1 = new Thread(new CountDownDemo(latch));
        Thread thread2 = new Thread(new CountDownDemo(latch));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}