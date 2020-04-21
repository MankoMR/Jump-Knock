package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * startet die activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * öffnet die calibrateActivity
     * @param view
     */
    public void btnStartGameClicked(android.view.View view)
    {
        Intent game = new Intent(this, CalibrateActivity.class);
        startActivity(game);
    }

    /**
     * öffnet die ShowRecords Activity
     * @param view
     */
    public void btnShowRecordsClicked(android.view.View view) {

        Intent showRecord = new Intent(this, ShowRecordsActivity.class);
        startActivity(showRecord);
    }
}
