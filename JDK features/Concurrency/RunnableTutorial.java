class RunnableDemoMessagePrinter implements Runnable{
    String message;
    int val;
    RunnableDemoMessagePrinter(String message){
        this.message = message;
        val = 0;
    }

    public void print(){
        System.out.println(message + val);
    }

    @Override
    public void run(){
        for(int i = 1; i <= 5; i++){
            val = (int)(Math.random() * 10);
            print();
            try{Thread.sleep(500);} catch(Exception e){}
        }
    }
}


public class RunnableTutorial{
    public static void main(String[] args) throws InterruptedException {
        
        //Create Runnable references to the class that implements Runnable
        Runnable rObj1 = new RunnableDemoMessagePrinter("Series 1 - ");
        Runnable rObj2 = new RunnableDemoMessagePrinter("Series 2 - ");

        //Create Threads
        Thread t1 = new Thread(rObj1);
        Thread t2 = new Thread(rObj2, "SeriesTwoThread"); //Set name during creation

        t1.setName("SeriesOneThread"); //Set name after creation

        //Start first thread
        t1.start();
        try{Thread.sleep(10);} catch(Exception e){} // Make main Thread wait
        //Start second thread
        t2.start();

        //print name and priority after checking whether the threads are alive
        if(t1.isAlive())
            System.out.println(t1.getName() + " is alive with priority = " + t1.getPriority());
        if(t2.isAlive())
            System.out.println(t2.getName() + " is alive with priority = " + t2.getPriority());

        //Make the main thread wait until both threads are finished            
        t1.join();
        t2.join();
        System.out.println("Printing Complete");

        //Display that the threads are dead after joining the main thread
        if(!t1.isAlive())
            System.out.println(t1.getName() + " is dead");
        if(!t2.isAlive())
            System.out.println(t2.getName() + " is dead");
    }
}