package HelloWorldApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloWorldServer {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9800);
            System.out.println("\nWaiting for client");
            Socket socket = serverSocket.accept();
            System.out.println("Connection established");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client says: " + reader.readLine());

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello World");

            reader.close();
            writer.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}