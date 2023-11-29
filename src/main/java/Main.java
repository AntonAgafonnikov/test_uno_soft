import file.reader.FileBufferedReader;
import file.writer.FileBufferedWriter;
import service.ProcessingLineService;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class Main {
    //private static final String PATH_INPUT_FILE = "src/main/resources/lng.txt";
    private static final String PATH_INPUT_FILE = "lng.txt";
    private static final String PATH_OUTPUT_FILE = "result.txt";

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.err.println("Invalid arguments");
//        }
//        File fileOutput = new File(args[0]);

        System.out.println("Запуск программы...");
        Instant start = Instant.now();

        //todo FileBufferedReader.readFromFile(new File(PATH_INPUT_FILE));
        ProcessingLineService.processingLine();
        FileBufferedWriter.writeToFile(new File(PATH_OUTPUT_FILE));

        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toSeconds();
        System.out.println("Программа завершена");
        System.out.println("Прошло времени: " + elapsed);

    }
}
