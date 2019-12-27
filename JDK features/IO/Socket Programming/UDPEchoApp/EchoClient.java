import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoClient {

    private DatagramSocket clientSocket;
    private byte buffer[];
    public boolean isRunning;

    EchoClient(int port) {
        try {
            clientSocket = new DatagramSocket(port, InetAddress.getByName("localhost"));
        } catch (Exception e) {
            System.out.println("Client Initiation Error");
        }
        isRunning = true;
    }

    public void startClient() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final int BUFFER_SIZE = 32;
        if (clientSocket == null)
            isRunning = false;

        while (isRunning) {
            try {
                String clientMsg = reader.readLine();
                buffer = clientMsg.getBytes();
                if (clientMsg.toLowerCase().equals("stop"))
                    isRunning = false;

                InetAddress address = InetAddress.getByName("localhost");
                DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, address, 9800);
                clientSocket.send(sendPacket);

                buffer = new byte[BUFFER_SIZE];
                DatagramPacket recievePacket = new DatagramPacket(buffer, buffer.length);

                clientSocket.receive(recievePacket);
                int msgLength = 0;
                for(int i = 0; i < buffer.length && buffer[i] != 0; i++)
                    msgLength++;
                String serverMsg = new String(buffer);
                serverMsg = serverMsg.substring(0, msgLength);
                System.out.println(serverMsg);
            } catch (IOException e) {
                System.out.println("An IO Exception Occured");
            } finally {
                if (!isRunning)
                    clientSocket.close();
            }
        }
    }

    public static void main(String[] args) {
        new EchoClient(9000).startClient();
    }
}