package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ch.band.jumpknock.storage.Record;
import ch.band.jumpknock.storage.RecordRepository;

/*
 *Copyright (c) 2020 Freddy Stalder, All rights reserved.
 */
public class RecordActivity extends AppCompatActivity {

    private Record record;

    private TextView tv_name;
    private TextView tv_points;
    private ConstraintLayout cl_root;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekord);

        tv_name = findViewById(R.id.tv_name);
        tv_points = findViewById(R.id.tv_points);
        cl_root = findViewById(R.id.cl_record_root);
        tv_title = findViewById(R.id.tv_NewRecordGameEnded);
        //TODO Test
        int reachedHeight = getIntent().getIntExtra(GameActivity.REACHED_HEIGHT,-1);
        RecordRepository recordRepository = new RecordRepository(getApplicationContext());
        Record[] topTen = recordRepository.GetTopTen();
        HashMap<String,Integer> usedNames = new HashMap<>();
        int rang = 11;
        int count = 1;
        for (Record rec:topTen){
            Integer nameCount = usedNames.get(rec.getNickname());
            if(nameCount == null)
                nameCount = new Integer(0);
            nameCount++;
            usedNames.put(rec.getNickname(),nameCount);

            if(reachedHeight >= rec.getHeight()){
                rang = count;
            }
            count++;
        }
        }
        String mostUsedName = "Jumper";
        int usedCount = -1;
        for(HashMap.Entry<String,Integer> entry:usedNames.entrySet()){
            if(entry.getValue() >= usedCount)
                mostUsedName = entry.getKey();
        }

        record = new Record(mostUsedName,reachedHeight);
        tv_name.setText(mostUsedName);
        tv_points.setText(reachedHeight +"");
    }

    /**
     * öffnet wieder die Startview und speichert zuerst noch den Rekord daten satz in der DB
     * @param view
     */
    public void btnBackToStartGameClicked(View view)
    {
        record.setNickname(tv_name.getText().toString());
        updateDB(record);
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

    /**
     * speicher den Record in der Datenbank
     * @param newRecord
     */
    public void updateDB(Record newRecord)
    {
        RecordRepository recordRepository = new RecordRepository(this);
        recordRepository.Save(newRecord);
    }
}
