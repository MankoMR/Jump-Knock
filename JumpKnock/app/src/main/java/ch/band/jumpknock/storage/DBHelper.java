package ch.band.jumpknock.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "knockJumpDB";
    public static final String RECORD_TABLE_NAME = "Records";
    public static final String RECORD_NICKNAME = "NICKNAME";
    public static final String RECORD_HEIGHT = "HEIGHT";

    public  DBHelper(Context context)
    {
        super(context, DATABASE_NAME,null,1);
    }

    /**
     * erschaft die tabelle Records
     * mit den Felder NICKNAME und HEIGHT
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RECORD_TABLE_NAME + " (" + RECORD_NICKNAME + " VARCHAR, " + RECORD_HEIGHT + " INTEGER);");
    }

    /**
     * löscht und erstellt die benutzen tabellen neu
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE_NAME);
        onCreate(db);
    }

    /**
     * speichert den mitgebenen Record datensatz in der Datenbank
     * @param record den zu speichernden Record datensatzt
     * @return true falls der Datensatz gespeichert wurde
     */
    public boolean saveRecord(Record record)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("INSERT INTO " + RECORD_TABLE_NAME + " VALUES('" + record.getNickname() + "','" + record.getHeight() + "');");
        ContentValues values = new ContentValues();
        values.put(RECORD_NICKNAME,record.getNickname());
        values.put(RECORD_HEIGHT, record.getHeight());

        long countRows = db.insert(RECORD_TABLE_NAME, null,values);

        if(countRows == -1)
        {
            return false;
        }
        return true;
    }

    /**
     * gibt alle Daten aus der Datenbank
     * @return eine Record Liste mit allen Daten aus der Datenbank
     */
    public ArrayList<Record> getAllRecords()
    {
        ArrayList<Record> recordArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultData = db.rawQuery("Select * from " + RECORD_TABLE_NAME,null);

        resultData.moveToFirst();
        resultData.move(-1);
        while (resultData.moveToNext()){
            Record record = new Record(resultData.getString(0),resultData.getInt(1));
            recordArrayList.add(record);
        }

        return recordArrayList;
    }
}
