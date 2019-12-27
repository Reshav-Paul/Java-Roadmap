import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;

public class DirScanner {
    public static void main(String[] args) {

        Path path = Paths.get(".\\");
        if (args.length == 1)
            path = Paths.get(args[0]);
        try {
            if (Files.isDirectory(path)) {
                Files.newDirectoryStream(path).forEach(e -> printDetails(e));
            } else {
                System.out.println("Please enter a proper directory.");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void printDetails(Path path) {
        String type = Files.isDirectory(path)? "Directory" : "File";
        try {
            System.out.println("_____________________________________________________________________");
            System.out.println("Name: " + path.getFileName() + " (" + type + ")");
            System.out.println("Path: " + path.toAbsolutePath());
            System.out.println("r / w / x : " + Files.isReadable(path)
                 + " / " + Files.isWritable(path)
                 + " / " + Files.isExecutable(path));
            System.out.println("is a Regular File? " + Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS));
            System.out.println("is hidden? " + Files.isHidden(path));
            System.out.println("Last Modified Time: " + Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}