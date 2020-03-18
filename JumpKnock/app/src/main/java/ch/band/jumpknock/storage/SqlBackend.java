package ch.band.jumpknock.storage;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SqlBackend implements StorageBackendInterface {
    private ArrayList<Record> records = new ArrayList<>();

    private  Context context;
    //TODO useless
    private static String dbTable = "Records";
    private static String dbName = "knockJumpDB";

    public SqlBackend(Context context)
    {
        this.context = context;
    }

    /**
     * speichert den mitgeben Record in der Datenbank
     * @param record
     * @return false
     */
    @Override
    public boolean addRecord(Record record) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.saveRecord(record);
        //TODO speicher erfolg überprüfungsmeldung
        return false;
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
     * gibt false zurück
     * @param id
     * @return
     */
    @Override
    public boolean removeRecord(int id) {
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
        records = dbHelper.getAllRecords();
        return records;
    }
}
