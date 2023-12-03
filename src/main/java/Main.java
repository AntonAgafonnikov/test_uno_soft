import file.reader.FileBufferedReader;
import file.writer.FileBufferedWriter;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class Main {
    private static final String PATH_OUTPUT_FILE = "result.txt";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Invalid arguments");
        }
        var fileInput = new File(args[0]);

        var start = Instant.now();

        FileBufferedReader.readFromFile(fileInput);
        FileBufferedWriter.writeToFile(new File(PATH_OUTPUT_FILE));

        var finish = Instant.now();
        var elapsed = Duration.between(start, finish).toSeconds();
        System.out.println("Время выполнения: " + elapsed + "с");
    }
}
