package file.reader;

import service.ProcessingLineService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileBufferedReader {

    public static List<String> readFromFile(File file) {
        System.out.println("Чтение файла...");

        List<String> linesList = new ArrayList<>();
        try (
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                linesList.add(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Чтение файла завершено. Количество строк: " + linesList.size());
        return linesList;
    }
}
