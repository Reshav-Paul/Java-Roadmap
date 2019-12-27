import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.io.BufferedReader;

public class StreamNIO {
    public static void main(String[] args) {
        // program to copy the content of InFile.txt into OutFile.txt
        Path inFilePath = Paths.get("Text Files\\InFile.txt");
        Path outFilePath = Paths.get("Text Files\\OutFile.txt");
        //Use of InputStream and OutputStream
        try (InputStream in = Files.newInputStream(inFilePath, StandardOpenOption.READ);
                OutputStream out = Files.newOutputStream(outFilePath, StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE)) {

            int ch;
            while((ch = in.read()) != -1){
                out.write(ch);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        //Use of BufferedReader
        System.out.println("\n----------Copied Content----------\n");
        try(BufferedReader in = Files.newBufferedReader(outFilePath)){
            String line = null;
            do{
                line = in.readLine();
                System.out.println(line != null? line : "\n");
            }while(line != null);
        } catch(IOException e){
            System.out.println(e);
        }
    }
}