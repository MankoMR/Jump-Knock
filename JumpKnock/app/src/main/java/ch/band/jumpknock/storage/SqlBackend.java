package ch.band.jumpknock.storage;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SqlBackend implements StorageBackendInterface {

    private  Context context;

    public SqlBackend(Context context)
    {
        this.context = context;
    }

    /**
     * speichert den mitgeben Record in der Datenbank
     * @param record
     * @return ture falls zeug gespeichert wurde
     */
    @Override
    public boolean addRecord(Record record) {
        DBHelper dbHelper = new DBHelper(context);
        boolean saved = dbHelper.saveRecord(record);
        dbHelper.close();
        return saved;
    }

    /**
     * gibt false zurück
     * @param record
     * @return
     */
    @Override
    public boolean removeRecord(Record record) {
        return false;
        //TODO useless
    }

    /**
     * gibt eine Liste mit allen Records aus der Datenbank zurück
     * @return
     */
    @Override
    public List<Record> getRecords() {
        DBHelper dbHelper = new DBHelper(context);
        ArrayList<Record> records = dbHelper.getAllRecords();
        dbHelper.close();
        return records;
    }
}
