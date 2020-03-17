package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.band.jumpknock.storage.Record;
import ch.band.jumpknock.storage.RecordRepository;
import ch.band.jumpknock.storage.SqliteStorageBackend;

public class ShowRecordsActivity extends AppCompatActivity {

    TextView[] firSecThir = new TextView[3];
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rekord);

        RecordRepository recordRepository = new RecordRepository(this);
        Record[] records = recordRepository.GetTopTen();

       // RecordRepository recordRepository = RecordRepository.getRecordRepository();
        //List<Record> recordList  = recordRepository.GetTopTen();

        firSecThir[0] = findViewById(R.id.tv_first);
        firSecThir[1] = findViewById(R.id.tv_second);
        firSecThir[2] = findViewById(R.id.tv_third);

        linearLayout = findViewById(R.id.ll_fourthToTenth);
        ((ConstraintLayout) linearLayout.getChildAt(0)).getChildAt(0).setBackgroundColor(Color.MAGENTA);

        Record[] topTen = new Record[10];
        for(int i = 0; i < topTen.length - 1; i++)
        {
            if(i < records.length)
            {
                topTen[i] = records[i];
            }
        }

        //TODO ??
        //SqliteStorageBackend backend = new SqliteStorageBackend();
        //backend.getRecords();

        for(int i = 0; i < 3; i++)
        {
            if(topTen[i] == null)
            {
                setInvisble(i);
                return;
            }
            else
            {
                firSecThir[i].setText(topTen[i].toString());
            }
        }

        //loop über alle Elemente die im LinearLayout sind
        for(int i = 0; i < 7; i++)
        {
            if(topTen[i + 3] == null)
            {
                linearLayout.getChildAt(i).setVisibility(View.INVISIBLE);
            }
            else
            {
                ((TextView)((ConstraintLayout)linearLayout.getChildAt(i)).getChildAt(0)).setText(topTen[i + 3].getNickname());
                ((TextView)((ConstraintLayout)linearLayout.getChildAt(i)).getChildAt(1)).setText(Integer.toString(topTen[i + 3].getHeight()));
            }
        }
    }

    //loop für die Views zum sie Unsichtbar zu machen, falls es keine Daten hat
    public void setInvisble(int nbr) {
            for (int i = nbr; i < 3; i++) {
                firSecThir[i].setVisibility(View.INVISIBLE);
            }
        linearLayout.setVisibility(View.INVISIBLE);
    }


}
