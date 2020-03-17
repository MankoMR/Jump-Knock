package ch.band.jumpknock.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TestBackend implements StorageBackendInterface {
	private ArrayList<Record> records = new ArrayList<>();
	private static int IdCounter = 1;
	TestBackend(){
		int testAmount = 15;
		Random random = new Random();
		for(int i = 0; i < testAmount; i++){
			Record r = new Record(IdCounter,"Tester "+i,random.nextInt(3000));
			IdCounter++;
			records.add(r);
		}
	}
	@Override
	public boolean addRecord(Record record) {
		if (record == null)
			return false;
		else {
			record.id = IdCounter;
			IdCounter++;
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
	public boolean removeRecord(int id) {
		if (id != 0){
			for (int i = 0; i < records.size();i++){
				if (records.get(i).id == id){
					records.remove(i);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<Record> getRecords() {
		return records;
	}
}
