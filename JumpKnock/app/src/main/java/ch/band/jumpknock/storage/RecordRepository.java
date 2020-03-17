package ch.band.jumpknock.storage;

import android.content.Context;

import java.util.Collections;
import java.util.List;

public class RecordRepository {
	//private static final RecordRepository ourRecordRepository = new RecordRepository();
	private StorageBackendInterface storageBackend;

	//TODO changet to public
	public RecordRepository(Context context){
	//	storageBackend = new TestBackend();
		storageBackend = new SqlBackend(context);
	}

	//public static RecordRepository getRecordRepository(){
	//	return ourRecordRepository;
	//}

	public boolean  IsInTopTen(Record record){
		List<Record> records = storageBackend.getRecords();
		Collections.sort(records);//sortiert liste
		List<Record> topTen = records.subList(0, records.size() >= 10 ? 10 : records.size());
		if (topTen.contains(record))
			return true;
		else {
			for (Record r : topTen) {
				if (r.nickname == record.nickname && r.height == record.height){
					return  true;
				}
			}
		}
		return false;
	}
	public Record[] GetTopTen(){
		List<Record> records = storageBackend.getRecords();
		Collections.sort(records);
		List<Record> topten = records.subList(0, records.size() >= 10 ? 10 : records.size());
		Record[] recordArray = topten.toArray(new Record[topten.size()]);
		return recordArray;
	}
	public boolean Save(Record record){
		return storageBackend.addRecord(record);
	}
}
