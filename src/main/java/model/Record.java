package model;

import java.util.Objects;

public class Record {
    private final String value;
    private final long index;

    public Record(String value, long index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public long getIndex() {
        return index;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return index == record.index && Objects.equals(value, record.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, index);
    }
}
