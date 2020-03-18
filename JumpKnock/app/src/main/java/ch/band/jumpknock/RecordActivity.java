package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

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
        Record curRec = new Record(0,null,reachedHeight);
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


    public void btnBackToStartGameClicked(android.view.View view)
    {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);


        String point = "" + tv_points.getText();//todo code convertet
        String name = "" + tv_name.getText();
        Record record = new Record(1,name,Integer.parseInt(point));


        mydatabase = openOrCreateDatabase(dbName,MODE_PRIVATE,null);
        //mydatabase.execSQL("DROP TABLE IF EXISTS " + dbTable);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS " + dbTable + " (Name VARCHAR, Height INTEGER);");
        updateDB(record);
    }

    public void updateDB(Record newRecord)
    {
        RecordRepository recordRepository = new RecordRepository(this);
        Random random = new Random();
        newRecord.height = random.nextInt(10000);
        recordRepository.Save(newRecord);
        /*
        Random random = new Random();
        int height = random.nextInt(10000);
        mydatabase.execSQL("INSERT INTO " + dbTable + " VALUES('" + newRecord.getNickname() + "','" + height + "');");
        //mydatabase.execSQL("INSERT INTO " + dbTable + " VALUES('" + newRecord.getNickname() + "','" + newRecord.getHeight() + "');");
    */
    }
}
