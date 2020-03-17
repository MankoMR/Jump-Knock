package ch.band.jumpknock.storage;

import java.util.List;

public interface StorageBackendInterface {
	boolean addRecord(Record record);
	boolean removeRecord(Record record);
	boolean removeRecord(int id);
	List<Record> getRecords();
}
