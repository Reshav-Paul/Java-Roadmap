import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//Use EchoClient.java under Socket Programming\EchoApp\ as the client provider
public class SinglePortNIOServer {
    InetAddress address;
    int port;
    Map<SocketChannel, List<byte[]>> dataMap;
    boolean serverShouldClose;
    SinglePortNIOServer(InetAddress address, int port){
        this.address = address;
        this.port = port;
        dataMap = new HashMap<>();
        serverShouldClose = false;
        startServer();
    }

    private void startServer(){
        try (Selector taskSelector = Selector.open(); ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            System.out.println("Server Started...");
            InetSocketAddress listenAddress = new InetSocketAddress(this.address, this.port);
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(listenAddress);
            serverChannel.register(taskSelector, SelectionKey.OP_ACCEPT);

            while (!serverShouldClose) {
                int nKeys = taskSelector.selectNow();
                if (nKeys == 0)
                    continue;

                Set<SelectionKey> selectedKeys = taskSelector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectedKeys.iterator();

                while (keysIterator.hasNext()) {
                    SelectionKey key = keysIterator.next();
                    keysIterator.remove();
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
            //System.out.println(e);
            e.printStackTrace();
        } finally {
            System.out.println("Server closed...");
        }
    }

    private void accept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        Socket socket = socketChannel.socket();

        InetAddress socketAddress = socket.getInetAddress();
        System.out.println("Connected to: " + socketAddress);
        dataMap.put(socketChannel, new ArrayList<byte[]>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(128);
        int numRead = socketChannel.read(buffer);
        if (numRead == -1) {
            this.dataMap.remove(socketChannel);
            System.out.println("Connection Closed By: " + socketChannel.socket().getInetAddress());
            socketChannel.close();
            key.cancel();
            return;
        }
        byte data[] = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead); 
        System.out.print("got: " + new String(data));
        echoToClient(key, ("Server says: " + new String(data)).getBytes());
    }

    private void write(SelectionKey key) throws IOException{
        SocketChannel socketChannel = (SocketChannel)key.channel();
        List<byte[]> pendingData = this.dataMap.get(socketChannel);
        Iterator<byte[]> iterator = pendingData.iterator();
        while(iterator.hasNext()){
            byte data[] = iterator.next();
            iterator.remove();
            socketChannel.write(ByteBuffer.wrap(data));
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    private void echoToClient(SelectionKey key, byte data[]){
        SocketChannel channel = (SocketChannel)key.channel();
        List<byte[]> pendingData = this.dataMap.get(channel);
        pendingData.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    public static void main(String[] args) {
        new SinglePortNIOServer(null, 9800);
    }
}