package ch.band.jumpknock.storage;

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
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RECORD_TABLE_NAME + " (" + RECORD_NICKNAME + " VARCHAR, " + RECORD_HEIGHT + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECORD_TABLE_NAME);
        onCreate(db);
    }

    public void saveRecord(Record record)
    {
        //TODO daten hier speichern
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("INSERT INTO " + RECORD_TABLE_NAME + " VALUES('" + record.getNickname() + "','" + record.getHeight() + "');");
    }

    /**
     * gibt alle Daten aus der Datenbank
     * @return gibt alle Daten aus der Datenbank
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