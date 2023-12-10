package service;

import java.io.*;
import java.util.*;

public class ProcessingLineService {
    private final Set<String> listLines = new TreeSet<>();
    private static int numberGroups = 0;

    private final TreeMap<String, Integer> recordAndGroupHashMap = new TreeMap<>();
    private final TreeMap<Integer, String> groupAndLinesListHashMap = new TreeMap<>();
    private final TreeMap<Integer, TreeSet<Integer>> groupAndMergeList = new TreeMap<>();
    private final TreeMap<Integer, Integer> correctlyGroupAndMergeList = new TreeMap<>();
    public final TreeMap<Integer, TreeSet<String>> correctlyGroupAndLinesListHashMap = new TreeMap<>();


    private static int newGroupID = 0;

    public void readFromFile(File file) {
        try (
                var fileReader = new FileReader(file);
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            System.out.println("Обработка строк из файла...");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listLines.add(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (String s : listLines) {
            processingLine(s);
        }

        mergeGroup();
        //findMaxSize();
    }

    public void processingLine(String line) {
        var isCorrectRecordsArray = line.replace(";", "");
        if (isCorrectRecordsArray.isEmpty())
            return;

        var recordsArray = line.split(";");
        newGroupID++;

        for (int i = 0; i < recordsArray.length; i++) {
            var currentRecordValue = recordsArray[i];

            if (currentRecordValue.length() < 3)
                continue;

            if (currentRecordValue.substring(1, currentRecordValue.length() - 1).contains("\""))
                return;

            var currentRecord = currentRecordValue + i;
            if (recordAndGroupHashMap.containsKey(currentRecord)) {

                var group = recordAndGroupHashMap.get(currentRecord);

                if (groupAndMergeList.containsKey(newGroupID)) {
                    var tranSet = groupAndMergeList.get(newGroupID);
                    tranSet.add(group);
                    groupAndMergeList.put(newGroupID, tranSet);
                    continue;
                }

                if (groupAndMergeList.containsKey(group)) {
                    var tranSet = groupAndMergeList.get(group);
                    tranSet.add(group);
                    groupAndMergeList.put(newGroupID, tranSet);
                    continue;
                }

                groupAndMergeList.put(newGroupID, new TreeSet<>(Set.of(group)));

            } else {
                recordAndGroupHashMap.put(currentRecord, newGroupID);
            }
        }
        groupAndLinesListHashMap.put(newGroupID, line);
    }

    public void mergeGroup() {

//        var groupAndMergeListKeySet = new TreeSet<>(groupAndMergeList.keySet());
//
//        for (Integer group : groupAndMergeListKeySet) {
//            //var group = entry;
//            var setGroup = groupAndMergeList.get(group);
//
//            if (setGroup.isEmpty())
//                continue;
//
//            var setGroup2 = new TreeSet<Integer>(Comparator.naturalOrder());
//            setGroup2.addAll(setGroup);
//            for (Integer groupFromSet : setGroup2) {
//                if (correctlyGroupAndMergeList.containsKey(groupFromSet)) {
//                    var mergeGroup = correctlyGroupAndMergeList.get(groupFromSet);
//                    correctlyGroupAndMergeList.put(groupFromSet, group);
//                    correctlyGroupAndMergeList.put(mergeGroup, group);
//                } else if (correctlyGroupAndMergeList.containsKey(group)) {
//                    var mergeGroup = correctlyGroupAndMergeList.get(group);
//                    correctlyGroupAndMergeList.put(groupFromSet, group);
//                    correctlyGroupAndMergeList.put(group, mergeGroup);
//                } else {
//                    correctlyGroupAndMergeList.put(groupFromSet, group);
//                }
//            }
//        }

        for (Map.Entry<Integer, TreeSet<Integer>> entry : groupAndMergeList.entrySet()) {
            var group = entry.getKey();
            var setGroup = entry.getValue();

            if (setGroup.isEmpty())
                continue;

            for (Integer groupFromSet : setGroup) {
                if (correctlyGroupAndMergeList.containsKey(groupFromSet)) {
                    var mergeGroup = correctlyGroupAndMergeList.get(groupFromSet);
                    correctlyGroupAndMergeList.put(groupFromSet, group);
                    correctlyGroupAndMergeList.put(mergeGroup, group);
                } else if (correctlyGroupAndMergeList.containsKey(group)) {
                    var mergeGroup = correctlyGroupAndMergeList.get(group);
                    correctlyGroupAndMergeList.put(groupFromSet, group);
                    correctlyGroupAndMergeList.put(group, mergeGroup);
                } else {
                    correctlyGroupAndMergeList.put(groupFromSet, group);
                }
            }
        }

//        var groupAndMergeListKeySet2 = new TreeSet<>(groupAndLinesListHashMap.keySet());
//        System.out.println(groupAndMergeListKeySet2);
//
//        for (Integer entry : groupAndMergeListKeySet2) {
//            var group = entry;
//            var transferSet = new TreeSet<>(Set.of(groupAndLinesListHashMap.get(group)));
//
//            if (correctlyGroupAndMergeList.containsKey(group)) {
//                var transferKeySet = correctlyGroupAndMergeList.get(group);
//
//                if (correctlyGroupAndLinesListHashMap.containsKey(transferKeySet)) {
//                    var tranSet = correctlyGroupAndLinesListHashMap.get(transferKeySet);
//                    tranSet.addAll(transferSet);
//                    correctlyGroupAndLinesListHashMap.put(transferKeySet, tranSet);
//                } else {
//                    correctlyGroupAndLinesListHashMap.put(transferKeySet, transferSet);
//                }
//
//            } else {
//
//                if (correctlyGroupAndLinesListHashMap.containsKey(group)) {
//                    var tranSet = correctlyGroupAndLinesListHashMap.get(group);
//                    tranSet.addAll(transferSet);
//                    correctlyGroupAndLinesListHashMap.put(group, tranSet);
//                } else {
//                    correctlyGroupAndLinesListHashMap.put(group, transferSet);
//                }
//
//
//            }
//        }

        for (Map.Entry<Integer, String> entry : groupAndLinesListHashMap.entrySet()) {
            var group = entry.getKey();
            var transferSet = new TreeSet<>(Set.of(entry.getValue()));

            if (correctlyGroupAndMergeList.containsKey(group)) {
                var transferKeySet = correctlyGroupAndMergeList.get(group);


                if (correctlyGroupAndLinesListHashMap.containsKey(transferKeySet)) {
                    var tranSet = correctlyGroupAndLinesListHashMap.get(transferKeySet);
                    tranSet.addAll(transferSet);
                    correctlyGroupAndLinesListHashMap.put(transferKeySet, tranSet);
                } else {
                    correctlyGroupAndLinesListHashMap.put(transferKeySet, transferSet);
                }

            } else {

                if (correctlyGroupAndLinesListHashMap.containsKey(group)) {
                    var tranSet = correctlyGroupAndLinesListHashMap.get(group);
                    tranSet.addAll(transferSet);
                    correctlyGroupAndLinesListHashMap.put(group, tranSet);
                } else {
                    correctlyGroupAndLinesListHashMap.put(group, transferSet);
                }


            }
        }
    }

    private int findMaxSize() {
        var maxSize = 1;

        for (Map.Entry<Integer, TreeSet<String>> entry : correctlyGroupAndLinesListHashMap.entrySet()) {
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

                for (Map.Entry<Integer, TreeSet<String>> entry : correctlyGroupAndLinesListHashMap.entrySet()) {


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
}