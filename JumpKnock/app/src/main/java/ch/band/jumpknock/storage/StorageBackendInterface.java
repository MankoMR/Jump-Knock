package ch.band.jumpknock.storage;

import java.util.List;

public interface StorageBackendInterface {
	boolean addRecord(Record record);
	boolean removeRecord(Record record);
	//TODO braucht es ein removeRecord?
	List<Record> getRecords();
}
