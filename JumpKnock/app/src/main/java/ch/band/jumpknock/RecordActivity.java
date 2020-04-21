package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import ch.band.jumpknock.storage.Record;
import ch.band.jumpknock.storage.RecordRepository;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class RecordActivity extends AppCompatActivity {

    private Record record;
    private EditText eT_name;
    private TextView tv_name;
    private TextView tv_points;
    private ConstraintLayout cl_root;
    private TextView tv_title;
    private ImageView iv_pokal;
    private TextView tv_nameOnTrophy;
    private TextView tv_badgeNumber;
    private boolean hasNoDataToSave = false;
    public static Context context;

    /**
     * passt falls es kein neuer Rekord ist die View beim Start an.
     */
    @Override
    protected void onStart() {
        super.onStart();

        tv_name = findViewById(R.id.tv_name);
        eT_name = findViewById(R.id.eT_name);
        tv_points = findViewById(R.id.tv_points);
        cl_root = findViewById(R.id.cl_record_root);
        tv_title = findViewById(R.id.tv_NewRecordGameEnded);
        iv_pokal = findViewById(R.id.iv_trophy);
        tv_nameOnTrophy = findViewById(R.id.tv_nameOnTrophy);

        int reachedHeight = getIntent().getIntExtra(GameActivity.REACHED_HEIGHT,-1);
        RecordRepository recordRepository = new RecordRepository(getApplicationContext());
        Record[] topTen = recordRepository.GetTopTen();

        if(!recordRepository.IsHigherThan10Place(reachedHeight))
        {
            this.setTheme(R.style.BadTheme);
            getWindow().setStatusBarColor(Color.RED);
            cl_root.setBackground(getDrawable(R.color.colorBad));
            hasNoDataToSave = true;
            tv_title.setText("Kein neuer Rekord.");
            iv_pokal.setVisibility(View.INVISIBLE);
            tv_nameOnTrophy.setVisibility(View.INVISIBLE);
            tv_name.setText("Platzt 10 liegt bei einer Höhe von: " + topTen[9].getHeight());
            tv_points.setText("Sie erreichten eine Höhe von: " + reachedHeight);
            tv_name.setVisibility(View.VISIBLE);
            eT_name.setVisibility(View.INVISIBLE);
            hasNoDataToSave = true;
        }
    }

    /**
     * zeig die ensprechenden Bilder und Text für den ensprechenden Rang an.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekord);

        //TODO vereinfachen
        eT_name = findViewById(R.id.eT_name);
        tv_points = findViewById(R.id.tv_points);
        cl_root = findViewById(R.id.cl_record_root);
        tv_title = findViewById(R.id.tv_NewRecordGameEnded);
        tv_nameOnTrophy = findViewById(R.id.tv_nameOnTrophy);
        iv_pokal = findViewById(R.id.iv_trophy);
        tv_badgeNumber = findViewById(R.id.tv_badgeNumber);

        eT_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_nameOnTrophy.setText(eT_name.getText());
            }
        });

        int reachedHeight = getIntent().getIntExtra(GameActivity.REACHED_HEIGHT,-1);
        RecordRepository recordRepository = new RecordRepository(getApplicationContext());

        if(recordRepository.IsHigherThan10Place(reachedHeight)) {
           switch (recordRepository.getPositionOfHeight(reachedHeight))
           {
               case 1:
                   setTrophyVisible("Erster Platz!", R.drawable.goldpokal);
                   break;
               case 2:
                   setTrophyVisible("Zweiter Platz!", R.drawable.silverpokal);
                   break;
               case 3:
                   setTrophyVisible("Dritter Platz!", R.drawable.bronzepokal);
                   break;
               case 4:
                   setBadgeVisible("Vierter Platz!", "4");
                   break;
               case 5:
                   setBadgeVisible("Fünfter Platz!", "5");
                   break;
               case 6:
                   setBadgeVisible("Sechter Platz!", "6");
                   break;
               case 7:
                   setBadgeVisible("Siebter Platz!", "7");
                   break;
               case 8:
                   setBadgeVisible("Achter Platz!", "8");
                   break;
               case 9:
                   setBadgeVisible("Neunter Platz!", "9");
                   break;
               case 10:
                   setBadgeVisible("Zehnter Platz!", "10");
                   break;
               case -1:
               default:
                   iv_pokal.setVisibility(View.INVISIBLE);
                   tv_title.setText("Ein Fehler ist aufgetreten.");
                   break;

           }
        }

        Record lastRecord = recordRepository.GetNewestRecord();
        String mostUsedName = lastRecord.getNickname();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        record = new Record(mostUsedName,reachedHeight,timestamp);
        eT_name.setText(mostUsedName);
        tv_points.setText(reachedHeight +"");
        //TODO anpassen das der zuletzt verwendete name gebraucht wird. es macht keinen Sinn einen anderen namen zu verwenden
    }

    /**
     * zeigt den passenden Pokal auf der view an
     * @param text
     * @param revTrophy
     */
    private void setTrophyVisible(String text, int revTrophy)
    {
        iv_pokal.setImageDrawable(getDrawable(revTrophy));
        tv_title.setText(text);
        tv_nameOnTrophy.setVisibility(View.VISIBLE);
    }

    /**
     * zeigt den Badge auf der View an
     * @param text
     * @param number
     */
    private void setBadgeVisible(String text,String number)
    {
        tv_title.setText(text);
        iv_pokal.setImageDrawable(getDrawable(R.drawable.badge));
        tv_badgeNumber.setVisibility(View.VISIBLE);
        tv_badgeNumber.setText(number);
    }

    /**
     * öffnet wieder die Startview und speichert zuerst noch den Rekord daten satz in der DB
     * @param view
     */
    public void btnBackToStartGameClicked(View view)
    {
        if(!hasNoDataToSave)
        {
            record.setNickname(eT_name.getText().toString());
            updateDB(record);
        }
        else
        {
            hasNoDataToSave = false;
        }
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

    /**
     * für context in Test
     * @return
     */
    public static Context getAppContext() {
        return context;
    }
}
