package ch.band.jumpknock.storage;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import ch.band.jumpknock.RecordActivity;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/*
 *Copyright (c) 2020 Fredy Stalder, All rights reserved.
 */
public class SqlBackendTest {

    @Test
    public void addRecord() {
        SqlBackend sqlBackend = new SqlBackend(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Helsinki", 43,timestamp);
        assertTrue(sqlBackend.addRecord(record));
    }

    @Test
    public void getRecords() {
        SqlBackend sqlBackend = new SqlBackend(RecordActivity.getAppContext());
        List<Record> recordsList = sqlBackend.getRecords();
        Record[] recordArray = (Record[])recordsList.toArray();
        assertTrue(recordArray.length > 1);
    }

    @Test
    public void getNewesRecord() {
        SqlBackend sqlBackend = new SqlBackend(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Rio", 1,timestamp);
        Record newRecord = sqlBackend.getNewestRecord();
        assertTrue(newRecord.getNickname() == "Rio");
    }

    @Before
    public void setUp() throws Exception {
        SqlBackend sqlBackend = new SqlBackend(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Tokio", 5,timestamp);
        sqlBackend.addRecord(record);
        record = new Record("Berlin",10,timestamp);
        sqlBackend.addRecord(record);
    }
}