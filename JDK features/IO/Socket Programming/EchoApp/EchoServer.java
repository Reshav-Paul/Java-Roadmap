package EchoApp;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class EchoServer{
    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(9800);
        Socket socket;
        try{
            socket = server.accept();
            System.out.println("\nConnection Established");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String message = reader.readLine();
            while(message.compareTo("STOP") != 0){
                System.out.println("Client says: " + message);
                writer.println("Server says: " + message);
                message = reader.readLine();
            }

            reader.close();
            writer.close();
            socket.close();
            server.close();
        } catch(IOException e){
            System.out.println(e);
        }
    }
}