package ba.number.game.math;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

import ba.number.game.R;


public class SevenFinalLessonActivity extends ActionBarActivity {

    CheckBox sumCheckBox, subtractionCheckBox, multiplicationCheckBox, divisionCheckBox;
    Spinner numberLimit;
    Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_lecture);
        sumCheckBox = (CheckBox)findViewById(R.id.sumCheckBox);
        subtractionCheckBox = (CheckBox)findViewById(R.id.subtractionCheckBox);
        multiplicationCheckBox = (CheckBox)findViewById(R.id.multiplicationCheckBox);
        divisionCheckBox = (CheckBox)findViewById(R.id.divisionCheckBox);
        numberLimit = (Spinner)findViewById(R.id.number_limit);
        playBtn = (Button)findViewById(R.id.playBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity", "play");
                PlayActivity.questionCounter=0;

                Intent intent = new Intent(SevenFinalLessonActivity.this, PlayActivity.class);
                ArrayList<Integer> listaFunkcija = new ArrayList<>();
                if (sumCheckBox.isChecked())
                    listaFunkcija.add(1);
                if (subtractionCheckBox.isChecked())
                    listaFunkcija.add(2);
                if (multiplicationCheckBox.isChecked())
                    listaFunkcija.add(3);
                if (divisionCheckBox.isChecked())
                    listaFunkcija.add(4);

                intent.putExtra("listaFunkcija", listaFunkcija);
                                //ovo je KEY                ovo je VALUE
                intent.putExtra("numberLimit", (String)numberLimit.getSelectedItem());

                if(listaFunkcija.size()>0)
                    startActivity(intent);
                Log.i("MainActivity", "playDone");
            }
        });
    }
}
