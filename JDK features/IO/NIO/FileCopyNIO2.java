import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//Paths, Path and Files were included in jdk7 and are sometimes called as NIO.2

public class FileCopyNIO2 {
    public static void main(String[] args) {
        // program to copy the content of InFile.txt into OutFile.txt
        Path inFilePath = Paths.get("Text Files\\InFile.txt");
        Path outFilePath = Paths.get("Text Files\\OutFile.txt");

        try (SeekableByteChannel inChannel = Files.newByteChannel(inFilePath, StandardOpenOption.READ);
                SeekableByteChannel outChannel = Files.newByteChannel(outFilePath, StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE)) {

            System.out.println("Some properties of the file being read:");
            System.out.println("File Path: " + inFilePath.toAbsolutePath());
            System.out.println("is Executable? " + Files.isExecutable(inFilePath));
            System.out.println("is Readable? " + Files.isReadable(inFilePath));
            System.out.println("is Writable? " + Files.isWritable(inFilePath));
            System.out.println("is a Regular File? " + Files.isRegularFile(inFilePath, LinkOption.NOFOLLOW_LINKS));
            System.out
                    .println("Last Modified Time: " + Files.getLastModifiedTime(inFilePath, LinkOption.NOFOLLOW_LINKS));

            ByteBuffer buffer = ByteBuffer.allocate(256);
            int nBytesRead = 0;
            while (nBytesRead != -1) {
                buffer.clear();
                nBytesRead = inChannel.read(buffer);
                if (nBytesRead == -1)
                    break;

                buffer.flip();
                outChannel.write(buffer);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}