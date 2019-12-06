import java.util.concurrent.Phaser;

/*
* Phaser is a versatile class that provides the functionalities of both
* CountDownLatch and CyclicBarrier
* The following program demonstrates its use as a CountDownLatch
*/

class PhaserDemo implements Runnable{

    Phaser phaser;
    PhaserDemo(Phaser phaser){
        this.phaser = phaser;
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
        System.out.println(currentThread + " : is waiting to proceed...");

        //arrive and await until all other threads arrive
        phaser.arriveAndAwaitAdvance();

        //Execution proceeds after all threads have successfully arrived
        System.out.println(currentThread + " : has successfully completed execution");
    }
}

public class PhaserTutorial {
    public static void main(String[] args) throws InterruptedException{
        Phaser phaser = new Phaser(2);
        Thread thread1 = new Thread(new PhaserDemo(phaser));
        Thread thread2 = new Thread(new PhaserDemo(phaser));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }    
}