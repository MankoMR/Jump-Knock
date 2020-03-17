package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void btnStartGameClicked(android.view.View view)
    {
        Intent game = new Intent(this, CalibrateActivity.class);
        startActivity(game);
    }

    public void btnShowRecordsClicked(android.view.View view) {



        Intent showRecord = new Intent(this, ShowRecordsActivity.class);
        startActivity(showRecord);
    }
}
