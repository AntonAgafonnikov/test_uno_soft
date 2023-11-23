package file.writer;

import service.ProcessingLineService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBufferedWriter {
    private static final ProcessingLineService processingLineService = new ProcessingLineService();
    private static long numberGroups;

    public static void writeToFile(File file) {
        var hashMap = new HashMap<>(processingLineService.getValueRepository().getGroupAndLinesHashMap());
        long maxSizeList = findMaxSize();
        long count = 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            //writer.write("Число групп с более чем одним элементом: " + numberGroups);
            while (maxSizeList > 0) {
                for (Map.Entry<String, List<String>> entry : hashMap.entrySet()) {
                    var value = entry.getValue();

                    if (value.size() < maxSizeList) {
                        continue;
                    }
                    writer.write("\nГруппа " + count + "\n");
                    for (String s : value) {
                        writer.write(s + "\n");
                    }
                    count++;
                }
                maxSizeList--;
                if (maxSizeList == 1) {
                    numberGroups = count - 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Число групп с более чем одним элементом: " + numberGroups);
    }

    private static long findMaxSize() {
        long maxSize = 1;
        HashMap<String, List<String>> hashMap = processingLineService.getValueRepository().getGroupAndLinesHashMap();

        for (Map.Entry<String, List<String>> entry : hashMap.entrySet()) {
            List<String> value = entry.getValue();
            long currentValueSize = value.size();

            if (currentValueSize > maxSize) {
                maxSize = currentValueSize;
            }
        }
        return maxSize;
    }
}
