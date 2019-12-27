import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoServer {

    public boolean isRunning;
    private byte[] buffer;
    private DatagramSocket serverSocket;

    EchoServer(int port){
        try{
            serverSocket = new DatagramSocket(port);
        } catch(Exception e){
            System.out.println("Could not start Server");
        }
        isRunning = true;
    }

    public void startServer(){
        System.out.println("Server Started");
        while(isRunning){
            buffer = new byte[256];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            //int msgLength = 0;
                        
            try{
                serverSocket.receive(receivePacket);
                // for(int i = 0; i < buffer.length && buffer[i] != 0; i++)
                //     msgLength++;
            }catch(Exception e){
                e.printStackTrace();
            }

            InetAddress address = receivePacket.getAddress();
            int port = receivePacket.getPort();
            buffer = receivePacket.getData();
            String message = new String(buffer);
            for(byte b: buffer)
                System.out.print(b + ' ');
            //message = message.substring(0, msgLength);

            System.out.println("\nReceived: " + message);
            if(message.toLowerCase().equals("stop"))
                isRunning = false;

            try{ 
                byte reply[] = ("Server Says: " + message).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(reply, reply.length, address, port);
                serverSocket.send(sendPacket);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("Server Stopped.");
    }

    public static void main(String[] args) {
        new EchoServer(9800).startServer();
    }    
}