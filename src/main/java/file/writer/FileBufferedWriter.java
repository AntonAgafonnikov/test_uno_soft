package file.writer;

import repository.ValueRepository;
import service.ProcessingLineService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBufferedWriter {
    private static final ValueRepository valueRepository = new ValueRepository();
    private static long numberGroups = 0;

    public static void writeToFile(File file) {
        System.out.println("Запись результатов в файл...");

        var hashMap = new HashMap<>(valueRepository.getGroupAndLinesHashMap());
        long maxSizeList = findMaxSize();
        long count = 1;
        var result = "Число групп с более чем одним элементом: " + numberGroups;

        try (
                var fileWriter = new FileWriter(file);
                var bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(result);

            while (maxSizeList > 0) {

                for (Map.Entry<String, List<String>> entry : hashMap.entrySet()) {
                    var value = entry.getValue();

                    if (value.size() < maxSizeList) {
                        count++;
                        continue;
                    }
                    bufferedWriter.write("\nГруппа " + count + "\n");
                    for (String s : value) {
                        bufferedWriter.write(s + "\n");
                    }
                    //count++;
                }
                maxSizeList--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Запись завершена. " + result + "-----> COUNT " + count);
    }

    private static long findMaxSize() {
        long maxSize = 0;
        HashMap<String, List<String>> hashMap = valueRepository.getGroupAndLinesHashMap();

        for (Map.Entry<String, List<String>> entry : hashMap.entrySet()) {
            List<String> value = entry.getValue();
            long currentValueSize = value.size();

            if (currentValueSize > maxSize) {
                maxSize = currentValueSize;
            }

            if (currentValueSize > 1) {
                numberGroups++;
            }
        }
        return maxSize;
    }
}
