import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.stream.IntStream;
import java.nio.ByteBuffer;

public class ScatterGatherTutorial {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("Text Files\\InFile.txt");
        FileChannel inChannel = fin.getChannel();
        FileOutputStream fout = new FileOutputStream("Text Files\\OutFileScattered.txt");
        FileChannel outChannel = fout.getChannel();

        ByteBuffer bufferArray[] = new ByteBuffer[5];
        IntStream.range(0, 5).forEach(e -> bufferArray[e] = ByteBuffer.allocate(1024));
        while(true){
            //clear buffers before reading data into it from the channel
            IntStream.range(0, 5).forEach(e -> bufferArray[e].clear());

            //check to see if all data has been read
            long bytesRead = inChannel.read(bufferArray);
            if(bytesRead == -1)
                break;
            //flip the buffers to read from the beginning
            IntStream.range(0, 5).forEach(e -> bufferArray[e].flip());

            //to write the buffers in the same order
            //outChannel.write(bufferArray);

            //To write the buffers in reverse order
            for(int i = bufferArray.length - 1; i >= 0; --i)
                outChannel.write(bufferArray[i]);
        }


        
        inChannel.close();
        outChannel.close();
        fin.close();
        fout.close();
    }    
}