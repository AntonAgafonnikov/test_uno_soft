import file.reader.FileBufferedReader;
import file.writer.FileBufferedWriter;

import java.io.File;

public class Main {
    private static final String PATH_INPUT_FILE = "lng.txt";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Invalid arguments");
        }
        File fileOutput = new File(args[0]);

        FileBufferedReader.readFromFile(new File(PATH_INPUT_FILE));
        FileBufferedWriter.writeToFile(fileOutput);
    }
}
