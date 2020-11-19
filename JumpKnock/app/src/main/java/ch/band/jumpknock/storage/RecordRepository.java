package ch.band.jumpknock.storage;

import android.content.Context;

import java.util.Collections;
import java.util.List;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class RecordRepository {
	private final StorageBackendInterface storageBackend;

	public RecordRepository(Context context){
		storageBackend = new SqlBackend(context);
	}

	/**
	 * gibt die Position des neuen Rekords zurück, bester, zweitbester usw..
	 * @param height Höhe des Rekords
	 * @return Position des Rekords als int
	 */
	public int getPositionOfHeight(int height)
	{
		List<Record> records = storageBackend.getRecords();
		Collections.sort(records);
		List<Record> topTen = records.subList(0, records.size() >= 10 ? 10 : records.size());
		Record[] topTenArray = topTen.toArray(new Record[topTen.size()]);
		for(int i = 0; i < topTenArray.length; i++)
		{
			if(topTenArray[i].getHeight() < height)
			{
				return i + 1;
			}
		}
		if(topTenArray.length < 10)
		{
			if(topTenArray.length == 0)
			{
				return 1;
			}
			return topTenArray.length + 1;
		}
		return -1;
	}


	/**
	 *überprüft ob die höche ein neuer Rekord ist
	 * @param height höhe des rekordes
	 * @return true falls die Höche höcher ist als die Höhe des 10. Platzes
	 */
	public boolean IsHigherThan10Place(int height)
	{
		List<Record> records = storageBackend.getRecords();
		Collections.sort(records);
		List<Record> topTen = records.subList(0, records.size() >= 10 ? 10 : records.size());
		Record[] topTenArray = topTen.toArray(new Record[topTen.size()]);
		if(topTenArray.length < 10)
		{
			return true;
		}
		else
		{
			if(height > topTenArray[9].getHeight())
			{
				return true;
			}
			else
			{
				return false;
			}

		}
	}


	/**
	 * gibt den zuletzt gespeicherten Rekord zurück
	 * @return
	 */
	public Record GetNewestRecord()
	{
		Record record = storageBackend.getNewestRecord();
		return record;
	}

	/**
	 * gibt eine liste mit den besten 10 Rekorden zurück
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
