package MultiThreadedServerApp;

import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {
    public static void main(String[] args) throws IOException {
        MultiThreadedServer server = new MultiThreadedServer(9800);
        server.start();
        //Use the EchoClient java application in the EchoApp package
        //to supply clients to this server
        InputStreamReader reader = new InputStreamReader(System.in);
        reader.read();
        server.closeServer();
    }    
}