import file.reader.FileBufferedReader;
import file.writer.FileBufferedWriter;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class Main {
    private static final String PATH_INPUT_FILE = "src/main/resources/input/lng.txt";
    private static final String PATH_OUTPUT_FILE = "src/main/resources/output/result.txt";

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.err.println("Invalid arguments");
//        }
//        var fileOutput = new File(args[0]);

        System.out.println("Запуск программы");
        var start = Instant.now();

        //var linesList = FileBufferedReader.readFromFile(new File(PATH_INPUT_FILE));
        FileBufferedReader.readFromFile(new File(PATH_INPUT_FILE));
        //ProcessingLineService.processingLine(linesList);
        FileBufferedWriter.writeToFile(new File(PATH_OUTPUT_FILE));

        var finish = Instant.now();
        var elapsed = Duration.between(start, finish).toSeconds();
        System.out.println("Время выполнения: " + elapsed + "с");

    }
}
