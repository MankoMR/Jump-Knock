package ch.band.jumpknock.storage;

import java.util.List;

//TODO delete?
public class SqliteStorageBackend implements StorageBackendInterface {
	@Override
	public boolean addRecord(Record record) {
		return false;
	}

	@Override
	public boolean removeRecord(Record record) {
		return false;
	}

	@Override
	public boolean removeRecord(int id) {
		return false;
	}

	@Override
	public List<Record> getRecords() {
		return null;
	}
}
