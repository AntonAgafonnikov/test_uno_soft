package repository;

import model.Record;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ValueRepository {
    private static final HashSet<String> linesHashSet = new HashSet<>();
    private static final HashSet<String> blackListLinesHashSet = new HashSet<>();
    private static final HashSet<Record> recordsHashSet = new HashSet<>();

    private static final HashMap<Record, String> recordAndGroupHashMap = new HashMap<>();
    private static final HashMap<String, List<String>> groupAndLinesHashMap = new HashMap<>();


    public HashSet<String> getLinesHashSet() {
        return linesHashSet;
    }

    public HashSet<String> getBlackListLinesHashSet() {
        return blackListLinesHashSet;
    }

    public HashSet<Record> getRecordsHashSet() {
        return recordsHashSet;
    }

    public HashMap<Record, String> getRecordAndGroupHashMap() {
        return recordAndGroupHashMap;
    }

    public HashMap<String, List<String>> getGroupAndLinesHashMap() {
        return groupAndLinesHashMap;
    }
}
