package MultiThreadedServerApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoServiceProvider extends Thread{
    Socket clientSocket;

    EchoServiceProvider(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        String message = "";
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            while(!message.equals("STOP")){
                message = reader.readLine();
                writer.println("Server says: " + message);
            }
            reader.close();
            writer.close();
            clientSocket.close();
        } catch(IOException e){
            System.out.println(e);
        }
    }
}