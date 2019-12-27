import java.nio.file.Files;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MemoryMappedNIO {
    public static void main(String[] args) {
        Path path = Paths.get("Text Files\\MemoryMappedInput.txt");
        try (FileChannel channel = (FileChannel) Files.newByteChannel(path, StandardOpenOption.READ,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            MappedByteBuffer buffer = channel.map(MapMode.READ_WRITE, 0, 50);
            while (buffer.hasRemaining()) {
                int position = buffer.position();
                byte b = buffer.get();
                if (b >= 65 && b <= 90)
                    b += 32;
                else if (b >= 97 && b <= 122)
                    b -= 32;
                buffer.put(position, b);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}