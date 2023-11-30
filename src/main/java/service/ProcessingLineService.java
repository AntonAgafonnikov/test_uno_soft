package service;

import model.Record;
import repository.ValueRepository;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;


public class ProcessingLineService {
    private static final ValueRepository valueRepository = new ValueRepository();

    public static void processingLine(String line){//List<String> linesList) {
        //System.out.println("Обработка строк...");

        //for (String line : linesList) {
            if (!valueRepository.getLinesHashSet().add(line)){ //|| valueRepository.getBlackListLinesHashSet().contains(line)) {
                return;//continue;
            }

            List<String> recordsArray = Arrays.stream(line.split(";")).toList();
            var length = recordsArray.size();
            var newGroupUUID = UUID.randomUUID().toString();
            var flagOverwritingKeyNextMatches = false;
            String firstGroupMatch = null;

            for (int i = 0; i < length; i++) {

                //var currentValueRecord = recordsArray[i];

                if (recordsArray.get(i).length() < 3) {
                    continue;
                }

//                if (currentValueRecord.substring(1, currentValueRecord.length() - 1).contains("\"")) {
//                    valueRepository.getLinesHashSet().remove(line);
//                    //valueRepository.getBlackListLinesHashSet().add(line);
//                    return;//continue;
//                }

                var currentRecord = new Record(recordsArray.get(i), i);
                if (!valueRepository.getRecordsHashSet().add(currentRecord)) {

                    if (firstGroupMatch == null) {
                        firstGroupMatch = valueRepository.getRecordAndGroupHashMap().get(currentRecord);
                        flagOverwritingKeyNextMatches = true;
                        i = -1;
                    } else {
                        //var group = valueRepository.getRecordAndGroupHashMap().get(currentRecord);

                        if (!valueRepository.getRecordAndGroupHashMap().get(currentRecord).equals(firstGroupMatch) && valueRepository.getGroupAndLinesHashMap().get(valueRepository.getRecordAndGroupHashMap().get(currentRecord)) != null){ //&& valueRepository.getGroupAndLinesHashMap().containsKey(group)) {
                            var transferList = valueRepository.getGroupAndLinesHashMap().remove(valueRepository.getRecordAndGroupHashMap().get(currentRecord));
                            var basicList = new TreeSet<>(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
                            basicList.addAll(transferList);
                            valueRepository.getGroupAndLinesHashMap().put(firstGroupMatch, basicList);
                        } else {
                            valueRepository.getRecordAndGroupHashMap().put(currentRecord, firstGroupMatch);
                        }
                    }

                } else {

                    if (flagOverwritingKeyNextMatches) {
                        valueRepository.getRecordAndGroupHashMap().put(currentRecord, firstGroupMatch);
                    } else {
                        valueRepository.getRecordAndGroupHashMap().put(currentRecord, newGroupUUID);
                    }
                }

                var transferTreeSet = new TreeSet<String>();
                if (!flagOverwritingKeyNextMatches) {
                    //var transferTreeSet = new TreeSet<String>();
                    transferTreeSet.add(line);
                    valueRepository.getGroupAndLinesHashMap().put(newGroupUUID, transferTreeSet);
                } else {
                    //var basicList = new TreeSet<>(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
                    transferTreeSet.addAll(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
                    transferTreeSet.add(line);
                    valueRepository.getGroupAndLinesHashMap().put(firstGroupMatch, transferTreeSet);
                }
            }
        //}
        //System.out.println("Обработка строк завершена ");
    }
}


//    public static void processingLine(String line) {
//        if (valueRepository.getLinesHashSet().contains(line) || valueRepository.getBlackListLinesHashSet().contains(line)) {
//            return;
//        } else {
//            valueRepository.getLinesHashSet().add(line);
//        }
//
//        var recordsArray = line.split(";");
//        var length = recordsArray.length;
//        var newGroupUUID = UUID.randomUUID().toString();
//        var flagOverwritingKeyNextMatches = false;
//        String firstGroupMatch = null;
//        for (int i = 0; i < length; i++) {
//            var currentValueRecord = recordsArray[i];
//            if (currentValueRecord.length() < 3) {
//                continue;
//            }
//            // Валидация значения
//            if (currentValueRecord.substring(1, currentValueRecord.length() - 1).contains("\"")) {
//                valueRepository.getLinesHashSet().remove(line);
//                valueRepository.getBlackListLinesHashSet().add(line);
//                return;
//            }
//
//            var currentRecord = new Record(currentValueRecord, i);
//            if (valueRepository.getRecordsHashSet().contains(currentRecord)) {
//
//                if (firstGroupMatch == null) {
//                    firstGroupMatch = valueRepository.getRecordAndGroupHashMap().get(currentRecord);
//                    flagOverwritingKeyNextMatches = true;
//                    i = -1;
//                } else {
//                    var group = valueRepository.getRecordAndGroupHashMap().get(currentRecord);
//
//                    if (!group.equals(firstGroupMatch) && valueRepository.getGroupAndLinesHashMap().containsKey(group)) {
//                        var transferList = valueRepository.getGroupAndLinesHashMap().remove(group);
//                        var basicList = new ArrayList<>(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
//                        basicList.addAll(transferList);
//                        valueRepository.getGroupAndLinesHashMap().put(firstGroupMatch, basicList);
//                    } else {
//                        valueRepository.getRecordAndGroupHashMap().put(currentRecord, firstGroupMatch);
//                    }
//
//                }
//
//            } else {
//                valueRepository.getRecordsHashSet().add(currentRecord);
//
//                if (flagOverwritingKeyNextMatches) {
//                    valueRepository.getRecordAndGroupHashMap().put(currentRecord, firstGroupMatch);
//                } else {
//                    valueRepository.getRecordAndGroupHashMap().put(currentRecord, newGroupUUID);
//                }
//            }
//        }
//
//        if (!flagOverwritingKeyNextMatches) {
//            valueRepository.getGroupAndLinesHashMap().put(newGroupUUID, List.of(line));
//        } else {
//            var basicList = new ArrayList<>(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
//            basicList.add(line);
//            valueRepository.getGroupAndLinesHashMap().put(firstGroupMatch, basicList);
//        }
//    }
//}