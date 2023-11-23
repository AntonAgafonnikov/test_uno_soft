package file.reader;

import service.ProcessingLineService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileBufferedReader {

    public static void readFromFile(File file) {
        try (
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ProcessingLineService.processingLine(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
