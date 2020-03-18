package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ch.band.jumpknock.storage.Record;
import ch.band.jumpknock.storage.RecordRepository;

public class RecordActivity extends AppCompatActivity {

    private SQLiteDatabase mydatabase;
    private static String dbTable = "Records";
    private static String dbName = "knockJumpDB";
    private int reachedHeight;

    TextView tv_name;
    TextView tv_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekord);

        tv_name = findViewById(R.id.tv_name);
        tv_points = findViewById(R.id.tv_points);
        //TODO Test

        reachedHeight = getIntent().getIntExtra(GameActivity.REACHED_HEIGHT,-1);
        RecordRepository recordRepository = new RecordRepository(getApplicationContext());
        Record curRec = new Record(null,reachedHeight);
        boolean isInTopTen = recordRepository.IsInTopTen(curRec);
        Record[] topTen = recordRepository.GetTopTen();
        HashMap<String,Integer> usedNames = new HashMap<>();
        for (Record rec:topTen){
            Integer nameCount = usedNames.get(rec.getNickname());
            if(nameCount == null)
                nameCount = new Integer(0);
            nameCount++;
            usedNames.put(rec.getNickname(),nameCount);
        }
        String mostUsedName = "Friedrich der Schnössel Berger";
        int usedCount = -1;
        //Todo: get Name whose name is most used from usedNames;

        tv_name.setText(mostUsedName);
        tv_points.setText(reachedHeight +"");
    }

    /**
     * öffnet wieder die Startview und speichert zuerst noch den Rekord daten satz in der DB
     * @param view
     */
    public void btnBackToStartGameClicked(android.view.View view)
    {

        Integer points = Integer.parseInt("" + tv_points.getText());
        String name = "" + tv_name.getText();
        Record record = new Record(name,points);


        mydatabase = openOrCreateDatabase(dbName,MODE_PRIVATE,null);
        //mydatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + dbTable + " (Name VARCHAR, Height INTEGER);");
        updateDB(record);

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    /**
     * speicher den Record in der Datenbank
     * @param newRecord
     */
    public void updateDB(Record newRecord)
    {
        RecordRepository recordRepository = new RecordRepository(this);
        Random random = new Random();
        newRecord.setHeight(random.nextInt(10000));
        if(recordRepository.Save(newRecord))
        {
            Toast.makeText(this,"Daten wurden gespeichert",Toast.LENGTH_SHORT);
        }
        else
        {
            Toast.makeText(this,"Fehler beim speichern der Daten",Toast.LENGTH_SHORT);
        }
        /*
        Random random = new Random();
        int height = random.nextInt(10000);
        mydatabase.execSQL("INSERT INTO " + dbTable + " VALUES('" + newRecord.getNickname() + "','" + height + "');");
        //mydatabase.execSQL("INSERT INTO " + dbTable + " VALUES('" + newRecord.getNickname() + "','" + newRecord.getHeight() + "');");
    */
    }
}
