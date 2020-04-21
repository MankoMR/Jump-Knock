package ch.band.jumpknock.storage;

import java.util.List;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public interface StorageBackendInterface {
	boolean addRecord(Record record);
	boolean removeRecord(Record record);
	//TODO braucht es ein removeRecord?
	List<Record> getRecords();
	Record getNewestRecord();
}
