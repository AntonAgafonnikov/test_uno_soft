package file.reader;

import service.ProcessingLineService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileBufferedReader {

    public static void readFromFile(File file) {
        System.out.println("Чтение файла");
        try (
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            System.out.println("Обработка строк...");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ProcessingLineService.processingLine(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Обработка строк завершена.");
    }
}
