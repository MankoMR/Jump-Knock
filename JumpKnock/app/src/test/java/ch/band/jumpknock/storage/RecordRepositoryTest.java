package ch.band.jumpknock.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import ch.band.jumpknock.RecordActivity;

import static org.junit.Assert.*;

/*
 *Copyright (c) 2020 Fredy Stalder, All rights reserved.
 */
public class RecordRepositoryTest {

    private Date date;
    private Timestamp timestamp;
    RecordRepository recordRepository;

    @Before
    public void setUp() throws Exception {
        recordRepository = new RecordRepository(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Tokio", 5,timestamp);
        recordRepository.Save(record);
        record = new Record("Berlin",10,timestamp);
        recordRepository.Save(record);
    }


    @Test
    public void getPositionOfHeight() {
        recordRepository = new RecordRepository(RecordActivity.getAppContext());
        Record[] recordArray = recordRepository.GetTopTen();
        if (recordArray.length > 6)
        {
            int numberSix = recordArray[5].getHeight();
        assertEquals(6, recordRepository.getPositionOfHeight(numberSix + 1));
        }
        if(recordArray.length == 10) {
            int numberEleven = recordArray[9].getHeight();
            assertEquals(-1, recordRepository.getPositionOfHeight(numberEleven - 1));
        }
    }

    @Test
    public void isHigherThan10Place() {
        recordRepository = new RecordRepository(RecordActivity.getAppContext());
        Record[] recordArray = recordRepository.GetTopTen();
        if(recordArray.length == 10)
        {
            int numberTenth =recordArray[9].getHeight();
            assertTrue(recordRepository.IsHigherThan10Place(numberTenth + 1));
            assertTrue(!recordRepository.IsHigherThan10Place(numberTenth - 1));
        }
    }

    @Test
    public void getNewestRecord() {
        recordRepository = new RecordRepository(RecordActivity.getAppContext());
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Record record = new Record("Denver", 5,timestamp);
        recordRepository.Save(record);
        assertEquals("Denver", recordRepository.GetNewestRecord().getNickname());
    }

    @Test
    public void getTopTen() {
        recordRepository = new RecordRepository(RecordActivity.getAppContext());
        assertTrue(recordRepository.GetTopTen().length < 11);
    }

    @Test
    public void save() {
        recordRepository = new RecordRepository(RecordActivity.getAppContext());
        date = new Date();
        timestamp = new Timestamp(date.getTime());
        Record record = new Record("Denver", 20, timestamp);
        assertTrue(recordRepository.Save(record));
    }

}

