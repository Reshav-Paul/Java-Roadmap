class ThreadDemoMessagePrinter extends Thread{
    String message;
    int val;
    ThreadDemoMessagePrinter(String message){
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


public class ThreadTutorial {

    public static void main(String[] args) {
        
        ThreadDemoMessagePrinter evenPrinter = new ThreadDemoMessagePrinter("Series 1 - ");
        ThreadDemoMessagePrinter oddPrinter = new ThreadDemoMessagePrinter("Series 2 - ");

        evenPrinter.start();
        try{Thread.sleep(10);} catch(Exception e){}
        oddPrinter.start();
    }
}