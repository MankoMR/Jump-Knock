package ch.band.jumpknock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.band.jumpknock.storage.Record;
import ch.band.jumpknock.storage.RecordRepository;

/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class ShowRecordsActivity extends AppCompatActivity {

    TextView[] tv_firstToThird = new TextView[3];
    LinearLayout ll_fourthToTenth;

    /**
     * setzt die view passend den anzahl datensätze an und zeigt ensprechend so viele Pokale, Badges
     * @param savedInstanceState
     */
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_rekord);

        tv_firstToThird[0] = findViewById(R.id.tv_first);
        tv_firstToThird[1] = findViewById(R.id.tv_second);
        tv_firstToThird[2] = findViewById(R.id.tv_third);


        ll_fourthToTenth = findViewById(R.id.ll_fourthToTenth);

        RecordRepository recordRepository = new RecordRepository(this);
        Record[] foundRecords = recordRepository.GetTopTen();

        //damit es keine probleme bei der weiterverarbeitung gibt muss topTen aus 10 Record datenstätze bestenen
        Record[] topTen = new Record[10];
        for(int i = 0; i < topTen.length; i++)
        {
            if(i < foundRecords.length)
            {
                topTen[i] = foundRecords[i];
            }
        }

        //loop über die ersten 3 Angezeigten Ergebnisse
        for(int i = 0; i < 3; i++)
        {
            if(topTen[i] == null)
            {
                setInvisble(i);
                return;
            }
            else
            {
                tv_firstToThird[i].setText(topTen[i].toString());
            }
        }

        //loop über alle Elemente die im LinearLayout sind
        for(int i = 0; i < 7; i++)
        {
            if(topTen[i + 3] == null)
            {
                ll_fourthToTenth.getChildAt(i).setVisibility(View.INVISIBLE);
            }
            else
            {
                ((TextView)((ConstraintLayout) ll_fourthToTenth.getChildAt(i)).getChildAt(0)).setText(topTen[i + 3].getNickname());
                ((TextView)((ConstraintLayout) ll_fourthToTenth.getChildAt(i)).getChildAt(1)).setText(String.format("%d",topTen[i + 3].getHeight()));
            }
        }
    }

    /**
     * loop für die Views zum sie Unsichtbar zu machen, falls es keine Daten hat
     * @param nbr nummer der View 1-3
     */
    public void setInvisble(int nbr) {
            for (int i = nbr; i < 3; i++) {
                tv_firstToThird[i].setVisibility(View.INVISIBLE);
            }
        ll_fourthToTenth.setVisibility(View.INVISIBLE);
    }

}