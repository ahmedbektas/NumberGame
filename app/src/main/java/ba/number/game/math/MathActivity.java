package ba.number.game.math;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

import ba.number.game.R;

public class MathActivity extends ActionBarActivity {

    Button numberLearningBtn, numberComparisonBtn, sumBtn, subtractionBtn, lesson5Btn, lesson6Btn, lesson7Btn;
    Spinner levels;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_);

        numberLearningBtn = (Button)findViewById(R.id.numberLearningBtn);
        numberComparisonBtn = (Button)findViewById(R.id.numberComparisonBtn);
        sumBtn = (Button)findViewById(R.id.sumBtn);
        subtractionBtn = (Button)findViewById(R.id.subtractionBtn);
        lesson5Btn = (Button)findViewById(R.id.lesson5Btn);
        lesson6Btn = (Button)findViewById(R.id.lesson6Btn);
        lesson7Btn = (Button)findViewById(R.id.lesson7Btn);
        levels = (Spinner)findViewById(R.id.levels);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        numberLearningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(makeNumberLearningIntent(), 1);
            }
        });

        int numberLearningScore = prefs.getInt("numberLearningScore", 0);
        if(numberLearningScore > 100)
            numberComparisonBtn.setEnabled(true);

        numberComparisonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(makeNumberComparisonIntent(), 2);
            }
        });

        int numberComparisonScore = prefs.getInt("numberComparisonScore", 0);
        if(numberComparisonScore > 350)
            sumBtn.setEnabled(true);

        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                ArrayList<Integer> listaFunkcija = new ArrayList<>();
                listaFunkcija.add(1);
                intent.putExtra("listaFunkcija", listaFunkcija);
                startActivityForResult(intent, 3);
            }
        });

        int mathScore = prefs.getInt("mathQuestionScore", 0);
        if(mathScore > 350)
            subtractionBtn.setEnabled(true);

        subtractionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                ArrayList<Integer> listaFunkcija = new ArrayList<>();
                listaFunkcija.add(2);
                intent.putExtra("listaFunkcija", listaFunkcija);
                startActivityForResult(intent, 4);
            }
        });

        lesson5Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                ArrayList<Integer> listaFunkcija = new ArrayList<>();
                listaFunkcija.add(3);
                intent.putExtra("listaFunkcija", listaFunkcija);
                startActivityForResult(intent, 5);
            }
        });

        lesson6Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = makeIntent();
                ArrayList<Integer> listaFunkcija = new ArrayList<>();
                listaFunkcija.add(4);
                intent.putExtra("listaFunkcija", listaFunkcija);
                startActivityForResult(intent, 6);
            }
        });

        lesson7Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MathActivity.this, FinalLesson.class), 7);
            }
        });
    }


   private Intent makeIntent (){
       PlayActivity.questionCounter=0;
       Intent intent = new Intent(MathActivity.this, PlayActivity.class);

       //ovo je KEY                ovo je VALUE
       switch ((String)levels.getSelectedItem()){
           case "1":
               intent.putExtra("numberLimit", "10");
               break;
           case "2":
               intent.putExtra("numberLimit", "20");
               break;
           case "3":
               intent.putExtra("numberLimit", "100");
               break;
           case "4":
               intent.putExtra("numberLimit", "1000");
               break;
       }

       return intent;
   }

    private Intent makeNumberLearningIntent (){
        NumberLearningActivity.questionCounter=0;
        Intent intent = new Intent(MathActivity.this, NumberLearningActivity.class);

        //ovo je KEY                ovo je VALUE
        switch ((String)levels.getSelectedItem()){
            case "1":
                intent.putExtra("numberLimit", "10");
                break;
            case "2":
                intent.putExtra("numberLimit", "20");
                break;
            case "3":
                intent.putExtra("numberLimit", "100");
                break;
            case "4":
                intent.putExtra("numberLimit", "1000");
                break;
        }

        return intent;
    }

    private Intent makeNumberComparisonIntent (){
        NumberComparisonActivity.questionCounter=0;
        Intent intent = new Intent(MathActivity.this, NumberComparisonActivity.class);

        //ovo je KEY                ovo je VALUE
        switch ((String)levels.getSelectedItem()){
            case "1":
                intent.putExtra("numberLimit", "10");
                break;
            case "2":
                intent.putExtra("numberLimit", "20");
                break;
            case "3":
                intent.putExtra("numberLimit", "100");
                break;
            case "4":
                intent.putExtra("numberLimit", "1000");
                break;
        }

        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            int score = data.getIntExtra("score", 0);
            switch (requestCode) {
                case 1:
                    if(score > 350)
                        numberComparisonBtn.setEnabled(true);
                    break;
                case 2:
                    if(score > 350)
                        sumBtn.setEnabled(true);
                    break;
                case 3:
                    if(score > 350)
                        subtractionBtn.setEnabled(true);
                    break;
                case 4:
                    if(score > 350)
                        lesson5Btn.setEnabled(true);
                    break;
                case 5:
                    if(score > 350) {
                        lesson6Btn.setEnabled(true);
                        lesson7Btn.setEnabled(true);
                    }
                    break;
            }
        }
    }
}
