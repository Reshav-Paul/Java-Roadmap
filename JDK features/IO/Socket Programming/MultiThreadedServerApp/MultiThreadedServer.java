package MultiThreadedServerApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer extends Thread{
    private ServerSocket server = null;
    private boolean connClosed;
    private int portNumber = 9800;

    MultiThreadedServer(int portNumber){
        this.portNumber = portNumber;
        connClosed = false;
    }

    @Override
    public void run(){
        startServer();
        System.out.println("Server Started...");
        while(!connClosed){
            Socket clientSocket = null;
            try{
                clientSocket = server.accept();
                System.out.println("Client Arrived.");
                new EchoServiceProvider(clientSocket).start();
            } catch(IOException e){
                if(connClosed)
                    System.out.println("Server Closed.");
                else
                    throw new RuntimeException("Error in connecting to client", e);
            }
        }
    }

    private void startServer(){
        try{
            server = new ServerSocket(portNumber);
        } catch(IOException e){
            throw new RuntimeException("could not connect to port" + portNumber, e);
        }
    }

    synchronized public void closeServer(){
        try{
            connClosed = true;
            server.close();
        } catch(IOException e){
            System.out.println(e);
        }
    }
}