package ch.band.jumpknock.storage;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SqlBackend implements StorageBackendInterface {
    private ArrayList<Record> records = new ArrayList<>();

    //TODO funktion für Datenspeichern hollen bei View schliessen oder öffnen
    private  Context context;
    private static String dbTable = "Records";
    private static String dbName = "knockJumpDB";

    public SqlBackend(Context context)
    {
        this.context = context;
    }

    @Override
    public boolean addRecord(Record record) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.saveRecord(record);
        return false;
    }

    @Override
    public boolean removeRecord(Record record) {
        return false;
        //TODO useless
    }

    @Override
    public boolean removeRecord(int id) {
        return false;
        //TODO useless
    }

    @Override
    public List<Record> getRecords() {
        DBHelper dbHelper = new DBHelper(context);
        records = dbHelper.getAllRecords();
        return records;
    }
}
