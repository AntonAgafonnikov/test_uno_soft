package file.reader;

import service.ProcessingLineService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileBufferedReader {
    public static final ProcessingLineService processingLineService = new ProcessingLineService();

    public static void readFromFile(File file) {
        try (
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            System.out.println("Обработка строк из файла...");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                processingLineService.processingLine(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Обработка строк завершена.");
    }
}
