package file.writer;

import service.ProcessingLineService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FileBufferedWriter {
    private static int numberGroups = 0;
    ProcessingLineService processingLineService = new ProcessingLineService();

    public void writeToFile(File file) {
        System.out.println("Запись результатов в файл...");

        var maxSizeList = findMaxSize();
        var count = 1;
        var result = "Число групп с более чем одним элементом: " + numberGroups;

        try (
                var fileWriter = new FileWriter(file);
                var bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(result);

            while (maxSizeList > 0) {

                for (Map.Entry<Integer, TreeSet<String>> entry : processingLineService.correctlyGroupAndLinesListHashMap.entrySet()) {


                    if (entry.getValue().size() == maxSizeList) {

                        bufferedWriter.write("\nГруппа " + count + "\n");
                        for (String s : entry.getValue()) {
                            bufferedWriter.write(s + "\n");
                        }
                        count++;
                    }
                }
                maxSizeList--;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Запись завершена. " + result);
    }

    private int findMaxSize() {
        var maxSize = 1;

        for (Map.Entry<Integer, TreeSet<String>> entry : processingLineService.correctlyGroupAndLinesListHashMap.entrySet()) {
                var currentValueSize = entry.getValue().size();

                if (currentValueSize > 1) {
                    numberGroups++;

                    if (currentValueSize > maxSize) {
                        maxSize = currentValueSize;
                    }
                }
            }
        return maxSize;
    }

}