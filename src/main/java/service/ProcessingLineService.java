package service;

import model.Record;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class ProcessingLineService {
    public static final HashMap<Record, Integer> recordAndGroupHashMap = new HashMap<>();
    public static final HashMap<Integer, TreeSet<String>> groupAndLinesHashMap = new HashMap<>();
    private int newGroupID = 0;

    public void processingLine(String line) {
        var isCorrectRecordsArray = line.replace(";", "");
        if (isCorrectRecordsArray.equals(""))
            return;

        var recordsArray = line.split(";");
        var flagOverwritingKeyNextMatches = false;
        var firstGroupMatch = 0;
        newGroupID++;

        for (int i = 0; i < recordsArray.length; i++) {
            var currentRecordValue = recordsArray[i];

            if (currentRecordValue.length() < 3)
                continue;

            if (currentRecordValue.substring(1, currentRecordValue.length() - 1).contains("\""))
                return;

            var currentRecord = new Record(currentRecordValue, i);
            if (recordAndGroupHashMap.containsKey(currentRecord)) {

                if (firstGroupMatch == 0) {
                    firstGroupMatch = recordAndGroupHashMap.get(currentRecord);
                    flagOverwritingKeyNextMatches = true;
                    i = -1;

                } else {
                    int group = recordAndGroupHashMap.get(currentRecord);

                    if (group != firstGroupMatch && groupAndLinesHashMap.get(group) != null && groupAndLinesHashMap.get(firstGroupMatch) != null) {
                        var transferTreeSet = new TreeSet<>(groupAndLinesHashMap.get(firstGroupMatch));
                        transferTreeSet.addAll(groupAndLinesHashMap.remove(group));
                        groupAndLinesHashMap.put(firstGroupMatch, transferTreeSet);
                        return;
                    }
                    recordAndGroupHashMap.put(currentRecord, firstGroupMatch);
                }

            } else {
                if (flagOverwritingKeyNextMatches) {
                    recordAndGroupHashMap.put(currentRecord, firstGroupMatch);
                } else {
                    recordAndGroupHashMap.put(currentRecord, newGroupID);
                }
            }
        }

        if (!flagOverwritingKeyNextMatches || groupAndLinesHashMap.get(firstGroupMatch) == null) {
            groupAndLinesHashMap.put(newGroupID, new TreeSet<>(Set.of(line)));
        } else {
            var transferTreeSet = new TreeSet<>(groupAndLinesHashMap.get(firstGroupMatch));
            transferTreeSet.add(line);
            groupAndLinesHashMap.put(firstGroupMatch, transferTreeSet);
        }
    }
}