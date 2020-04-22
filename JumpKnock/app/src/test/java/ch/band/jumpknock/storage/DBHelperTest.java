package ch.band.jumpknock.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import ch.band.jumpknock.RecordActivity;

import static org.junit.Assert.*;

/*
 *Copyright (c) 2020 Fredy Stalder, All rights reserved.
 */
public class DBHelperTest {

    @Test
    public void saveRecord() {
        DBHelper dbHelper = new DBHelper(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Helsinki", 43,timestamp);
        assertTrue(dbHelper.saveRecord(record));
    }

    @Test
    public void getAllRecords() {
        DBHelper dbHelper = new DBHelper(RecordActivity.getAppContext());
        ArrayList<Record> recordsArrayList = dbHelper.getAllRecords();
        Record[] recordArray = (Record[])recordsArrayList.toArray();
        assertTrue(recordArray.length > 1);
    }

    @Test
    public void getNewestRecord() {
        DBHelper dbHelper = new DBHelper(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Rio", 1,timestamp);
        Record newRecord = dbHelper.getNewestRecord();
        assertTrue(newRecord.getNickname() == "Rio");
    }

    @Before
    public void setUp() throws Exception {
        DBHelper dbHelper = new DBHelper(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Tokio", 5,timestamp);
        dbHelper.saveRecord(record);
        record = new Record("Berlin",10,timestamp);
        dbHelper.saveRecord(record);
    }

    /*
    private TestDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
        userDao = db.getUserDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }
     */
}