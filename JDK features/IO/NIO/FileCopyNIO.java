import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopyNIO{
    public static void main(String[] args) throws IOException {
        //program to copy the content of InFile.txt into OutFile.txt
        FileInputStream fin = new FileInputStream("Text Files\\InFile.txt");
        FileChannel inChannel = fin.getChannel();
        FileOutputStream fout = new FileOutputStream("Text Files\\OutFile.txt");
        FileChannel outChannel = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(100);

        while(true){
            //clear buffer before reading data into it from the channel
            buffer.clear();
            
            int bytesRead = inChannel.read(buffer);
            //check to see if all data has been read
            if(bytesRead == -1)
                break;
            
            //flip buffer to read from the beginning
            buffer.flip();
            //write into the output channel
            outChannel.write(buffer);
        }

        inChannel.close();
        outChannel.close();
        fin.close();
        fout.close();
    }
}