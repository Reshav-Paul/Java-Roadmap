package HelloWorldApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HelloWorldClient{
    public static void main(String args[]) throws IOException{
        Socket clientSocket;
        try{
            clientSocket = new Socket("localhost", 9800);
            
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println("Hello Socket Programming");

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("\nServer says: " + reader.readLine());
            
            writer.close();
            reader.close();
            clientSocket.close();
        } catch(IOException e){
            System.out.println(e);
        }
    }
}