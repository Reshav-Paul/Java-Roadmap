package EchoApp;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        Socket client;
        try {
            client = new Socket("localhost", 9800);

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

            String message = "";
            while (!message.equals("STOP")) {
                message = userInput.readLine();
                writer.println(message);
                
                if(!message.equals("STOP"))
                    System.out.println(reader.readLine());
            }

            userInput.close();
            writer.close();
            client.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}