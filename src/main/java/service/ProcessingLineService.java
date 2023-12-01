package service;

import model.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProcessingLineService {
    public static final HashMap<Record, Integer> recordAndGroupHashMap = new HashMap<>();
    public static final HashMap<Integer, ArrayList<String>> groupAndLinesHashMap = new HashMap<>();
    private static int newGroupID = 0;

    public void processingLine(String line) {

        var recordsArray = line.split(";");
        var flagOverwritingKeyNextMatches = false;
        int firstGroupMatch = 0;
        newGroupID++;

        for (int i = 0; i < recordsArray.length; i++) {
            var currentRecordValue = recordsArray[i];

            if (currentRecordValue.substring(1, currentRecordValue.length() - 1).contains("\"")) {
                return;
            }

            if (currentRecordValue.length() < 3) {
                continue;
            }

            var currentRecord = new Record(currentRecordValue, i);
            if (recordAndGroupHashMap.containsKey(currentRecord)) {

                if (firstGroupMatch == 0) {
                    firstGroupMatch = recordAndGroupHashMap.get(currentRecord);
                    flagOverwritingKeyNextMatches = true;
                    i = -1;

                } else {
                    int group = recordAndGroupHashMap.get(currentRecord);

                    if (group != firstGroupMatch && groupAndLinesHashMap.get(group) != null) {

                        var transferList = new ArrayList<>(groupAndLinesHashMap.get(firstGroupMatch));
                        transferList.addAll(groupAndLinesHashMap.remove(group));
                        groupAndLinesHashMap.put(firstGroupMatch, transferList);
                    } else {
                        recordAndGroupHashMap.put(currentRecord, firstGroupMatch);
                    }
                }

            } else {
                if (flagOverwritingKeyNextMatches) {
                    recordAndGroupHashMap.put(currentRecord, firstGroupMatch);
                } else {
                    recordAndGroupHashMap.put(currentRecord, newGroupID);
                }
            }
        }

        if (!flagOverwritingKeyNextMatches) {
            groupAndLinesHashMap.put(newGroupID, new ArrayList<>(List.of(line)));
        } else {
            var transferList = new ArrayList<>(groupAndLinesHashMap.get(firstGroupMatch));
            transferList.add(line);
            groupAndLinesHashMap.put(firstGroupMatch, transferList);
        }
    }
}