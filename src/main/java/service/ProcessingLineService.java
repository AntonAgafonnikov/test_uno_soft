package service;

import model.Record;
import repository.ValueRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessingLineService {
    private static final ValueRepository valueRepository = new ValueRepository();

    public ValueRepository getValueRepository() {
        return valueRepository;
    }

    public static void processingLine() {
        //todo
        System.out.println("Чтение файла...");

        List<String> lines = new ArrayList<>();
        try (
                var fileReader = new FileReader(new File("lng.txt"));
                var bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //todo
        System.out.println("Чтение файла закончено. Количество строк: " + lines.size());

        for (String line : lines) {
            if (valueRepository.getLinesHashSet().contains(line) || valueRepository.getBlackListLinesHashSet().contains(line)) {
                //todo was return
                continue;
            } else {
                valueRepository.getLinesHashSet().add(line);
            }

            var recordsArray = line.split(";");
            var length = recordsArray.length;
            var newGroupUUID = UUID.randomUUID().toString();
            var flagOverwritingKeyNextMatches = false;
            String firstGroupMatch = null;
            for (int i = 0; i < length; i++) {
                var currentValueRecord = recordsArray[i];
                if (currentValueRecord.length() < 3) {
                    continue;
                }
                // Валидация значения
                if (currentValueRecord.substring(1, currentValueRecord.length() - 1).contains("\"")) {
                    valueRepository.getLinesHashSet().remove(line);
                    valueRepository.getBlackListLinesHashSet().add(line);
                    //todo was return
                    continue;
                }

                var currentRecord = new Record(currentValueRecord, i);
                if (valueRepository.getRecordsHashSet().contains(currentRecord)) {

                    if (firstGroupMatch == null) {
                        firstGroupMatch = valueRepository.getRecordAndGroupHashMap().get(currentRecord);
                        flagOverwritingKeyNextMatches = true;
                        i = -1;
                    } else {
                        var group = valueRepository.getRecordAndGroupHashMap().get(currentRecord);

                        if (!group.equals(firstGroupMatch) && valueRepository.getGroupAndLinesHashMap().containsKey(group)) {
                            var transferList = valueRepository.getGroupAndLinesHashMap().remove(group);
                            var basicList = new ArrayList<>(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
                            basicList.addAll(transferList);
                            valueRepository.getGroupAndLinesHashMap().put(firstGroupMatch, basicList);
                        } else {
                            valueRepository.getRecordAndGroupHashMap().put(currentRecord, firstGroupMatch);
                        }

                    }

                } else {
                    valueRepository.getRecordsHashSet().add(currentRecord);

                    if (flagOverwritingKeyNextMatches) {
                        valueRepository.getRecordAndGroupHashMap().put(currentRecord, firstGroupMatch);
                    } else {
                        valueRepository.getRecordAndGroupHashMap().put(currentRecord, newGroupUUID);
                    }
                }


                if (!flagOverwritingKeyNextMatches) {
                    valueRepository.getGroupAndLinesHashMap().put(newGroupUUID, List.of(line));
                } else {
                    var basicList = new ArrayList<>(valueRepository.getGroupAndLinesHashMap().get(firstGroupMatch));
                    basicList.add(line);
                    valueRepository.getGroupAndLinesHashMap().put(firstGroupMatch, basicList);
                }
            }
        }
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