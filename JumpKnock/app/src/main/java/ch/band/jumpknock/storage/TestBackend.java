package ch.band.jumpknock.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */

/**
 * testklasee
 * //TODO löschen
 */
class TestBackend implements StorageBackendInterface {
	private ArrayList<Record> records = new ArrayList<>();
	//TODO in den tescht verschieben?
	//TODO dateAndTime null wert anpassen
	TestBackend(){
		int testAmount = 15;
		Random random = new Random();
		for(int i = 0; i < testAmount; i++){
			Record r = new Record("Tester "+i,random.nextInt(3000),null);
			records.add(r);
		}
	}
	@Override
	public boolean addRecord(Record record) {
		if (record == null)
			return false;
		else {
			records.add(record);
			return true;
		}
	}

	@Override
	public boolean removeRecord(Record record) {
		if (record != null){
			return records.remove(record);
		}
		return false;
	}

	@Override
	public List<Record> getRecords() {
		return records;
	}

	@Override
	public Record getNewestRecord() {
		return null;
	}
}
