package ch.band.jumpknock.storage;

import java.util.List;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public interface StorageBackendInterface {
	boolean addRecord(Record record);
	List<Record> getRecords();
	Record getNewestRecord();
}
