package ch.band.jumpknock.storage;

import android.content.Context;

import java.util.Collections;
import java.util.List;

public class RecordRepository {
	private StorageBackendInterface storageBackend;

	public RecordRepository(Context context){
		storageBackend = new SqlBackend(context);
	}

	/**
	 *
	 * @param record
	 * @return
	 */
	public boolean  IsInTopTen(Record record){
		List<Record> records = storageBackend.getRecords();
		Collections.sort(records);//sortiert liste
		List<Record> topTen = records.subList(0, records.size() >= 10 ? 10 : records.size());
		if (topTen.contains(record))
			return true;
		else {
			for (Record r : topTen) {
				if(r.getNickname() == record.getNickname() && r.getHeight() == record.getHeight())
				{
					return  true;
				}
			}
		}
		return false;
	}

	/**
	 *
	 * @return
	 */
	public Record[] GetTopTen(){
		List<Record> records = storageBackend.getRecords();
		Collections.sort(records);
		List<Record> topten = records.subList(0, records.size() >= 10 ? 10 : records.size());
		Record[] recordArray = topten.toArray(new Record[topten.size()]);
		return recordArray;
	}

	/**
	 * speichert den Record in der Datenbank
	 * @param record
	 * @return true falls gespeichert wurde
	 */
	public boolean Save(Record record){
		return storageBackend.addRecord(record);
	}
}
