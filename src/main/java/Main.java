import file.reader.FileBufferedReader;
import file.writer.FileBufferedWriter;
import service.ProcessingLineService;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class Main {
    private static final String PATH_OUTPUT_FILE = "result.txt";

    public static void main(String[] args) {
        ProcessingLineService processingLineService = new ProcessingLineService();
//        if (args.length < 1) {
//            System.err.println("Invalid arguments");
//        }
//        var fileInput = new File(args[0]);

        //var fileInput = (new File("artifacts/lng.txt"));
        var fileInput = (new File("artifacts/lng-big.csv"));
        //var fileInput = (new File("artifacts/test.txt"));

        var start = Instant.now();

        processingLineService.readFromFile(fileInput);

//        //TODO
//        for (Map.Entry<Integer, Integer> entry : ProcessingLineService.groupAndMergeList.entrySet()) {
//            System.out.println(entry.toString());
//        }
//        //TODO

        processingLineService.writeToFile(new File(PATH_OUTPUT_FILE));

        var finish = Instant.now();
        var elapsed = Duration.between(start, finish).toSeconds();
        System.out.println("Время выполнения: " + elapsed + "с");
    }
}
