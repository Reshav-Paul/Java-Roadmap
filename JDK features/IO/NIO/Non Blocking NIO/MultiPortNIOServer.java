import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

//Use EchoClient.java under Socket Programming\EchoApp\ as the client provider
public class MultiPortNIOServer {
    InetAddress address;
    int port[];
    boolean serverShouldClose;
    Map<SocketChannel, List<byte[]>> dataMap;

    MultiPortNIOServer(InetAddress address, int port[]) {
        this.address = address;
        this.port = new int[port.length];
        System.arraycopy(port, 0, this.port, 0, port.length);
        serverShouldClose = false;
        dataMap = new HashMap<>();
        startServer();
    }

    private void startServer() {
        try (Selector taskSelector = Selector.open()) {
            for (int i = 0; i < this.port.length; ++i) {
                ServerSocketChannel serverChannel = ServerSocketChannel.open();
                serverChannel.configureBlocking(false);
                serverChannel.socket().bind(new InetSocketAddress(this.address, port[i]));
                serverChannel.register(taskSelector, SelectionKey.OP_ACCEPT);
            }
            System.out.println("Server Started");
            while (!serverShouldClose) {
                taskSelector.select();
                Set<SelectionKey> selectedKeys = taskSelector.selectedKeys();
                Iterator<SelectionKey> keysIter = selectedKeys.iterator();
                while (keysIter.hasNext()) {
                    SelectionKey key = keysIter.next();
                    keysIter.remove();
                    if (!key.isValid())
                        continue;

                    if (key.isAcceptable()) {
                        accept(key, taskSelector);
                    } else if (key.isReadable()) {
                        read(key);
                    } else if(key.isWritable()){
                        write(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            System.out.println("Server closed.");
        }
    }

    private void accept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) (key.channel());
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        dataMap.put(clientChannel, new ArrayList<>());
        clientChannel.register(selector, SelectionKey.OP_READ);
        Socket clientSocket = clientChannel.socket();
        System.out.println("Connected to: " + clientSocket.getInetAddress() + " at port: " + clientSocket.getPort());
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        int numRead = clientChannel.read(buffer);
        if (numRead == -1) {
            Socket clientSocket = clientChannel.socket();
            System.out.println(
                    "Connection closed by: " + clientSocket.getInetAddress() + " at port: " + clientSocket.getPort());
            clientChannel.close();
            key.cancel();
            return;
        }
        byte data[] = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        String message = new String(data);
        int pos = message.lastIndexOf(System.getProperty("line.separator"));
        message = message.substring(0, pos);
        System.out.println("Got at port " + clientChannel.socket().getPort() + ": \"" + message + "\"");
        echoToClient(data, key);

    }

    private void echoToClient(byte data[], SelectionKey key){
        SocketChannel clientChannel = (SocketChannel)key.channel();
        List<byte[]> pendingData = dataMap.get(clientChannel);
        pendingData.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException{
        SocketChannel clientChannel = (SocketChannel)key.channel();
        List<byte[]> pendingData = dataMap.get(clientChannel);
        Iterator<byte[]> iter = pendingData.iterator();
        while(iter.hasNext()){
            byte data[] = iter.next();
            iter.remove();
            String preText = "Server says from port " + clientChannel.socket().getPort() + ": ";
            clientChannel.write(ByteBuffer.wrap((preText + new String(data)).getBytes()));
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        new MultiPortNIOServer(null, new int[]{9800, 9900});
    }
}