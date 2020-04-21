package ch.band.jumpknock.storage;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class RecordTest {

    private Date date;
    private Timestamp timestamp;
    private Record record;
    private Timestamp savedTimestamp;

    @Test
    public void testToString() {
        assertEquals("Naroibi 55",record.toString());
    }

    @Test
    public void getNickname() {
        assertEquals("Naroibi", record.getNickname());
    }

    @Test
    public void setNickname() {
        Record setRecord = record;
        setRecord.setNickname("Stockholm");
        assertEquals("Stockholm",setRecord.getNickname());
    }

    @Test
    public void getHeight() {
        assertEquals(55, record.getHeight());
    }

    @Test
    public void setHeight() {
        Record setRecord = record;
        setRecord.setHeight(66);
        assertEquals(66,setRecord.getHeight());
    }

    @Test
    public void getDateAndTime() {
        assertEquals(savedTimestamp,record.getDateAndTime());
    }

    @Test
    public void setDateAndTime() {
        date = new Date();
        timestamp = new Timestamp(date.getTime());
        Record setRecord = record;
        setRecord.setDateAndTime(timestamp);
        assertEquals(timestamp, record.getDateAndTime());
    }

    @Before
    public void setUp() throws Exception {
        date = new Date();
        timestamp = new Timestamp(date.getTime());
        record = new Record("Naroibi",55,timestamp);
        savedTimestamp = timestamp;
    }
}