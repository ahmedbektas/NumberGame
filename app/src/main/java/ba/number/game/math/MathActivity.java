package ba.number.game.math;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Iterator;
import java.util.Set;

import ba.number.game.R;
import ba.number.game.math.operations.DivisionActivity;
import ba.number.game.math.operations.MultiplicationActivity;
import ba.number.game.math.operations.SubtractionActivity;
import ba.number.game.math.operations.SumActivity;
import ba.number.game.util.Prefs;

public class MathActivity extends ActionBarActivity {

    Button numberLearningBtn, numberComparisonBtn, sumBtn, subtractionBtn, multiplicationBtn, divisionBtn, finalLessonBtn;
    Spinner levels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_);

        numberLearningBtn = (Button)findViewById(R.id.numberLearningBtn);
        numberComparisonBtn = (Button)findViewById(R.id.numberComparisonBtn);
        sumBtn = (Button)findViewById(R.id.sumBtn);
        subtractionBtn = (Button)findViewById(R.id.subtractionBtn);
        multiplicationBtn = (Button)findViewById(R.id.multiplicationBtn);
        divisionBtn = (Button)findViewById(R.id.divisionBtn);
        finalLessonBtn = (Button)findViewById(R.id.finalLessonBtn);
        levels = (Spinner)findViewById(R.id.levels);

        numberLearningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneNumberLearningActivity.questionCounter=0;
                startActivityForResult(makeIntent(OneNumberLearningActivity.class), 1);
            }
        });

        int numberLearningScore = Prefs.getInstance(this).getScore(Prefs.NUMBER_LEARNING_SCORE);
        int numberComparisonScore = Prefs.getInstance(this).getScore(Prefs.NUMBER_COMPARISON_SCORE);
        int sumScore = Prefs.getInstance(this).getScore(Prefs.SUM_QUESTION_SCORE);
        int subtractionScore = Prefs.getInstance(this).getScore(Prefs.SUBTRACTION_QUESTION_SCORE);
        int multiplicationScore = Prefs.getInstance(this).getScore(Prefs.MULTIPLICATION_QUESTION_SCORE);
        int divisionScore = Prefs.getInstance(this).getScore(Prefs.DIVISION_QUESTION_SCORE);

        if(numberLearningScore > 99)
            numberComparisonBtn.setEnabled(true);

        if(numberComparisonScore > 99)
            sumBtn.setEnabled(true);

        if(sumScore > 99)
            subtractionBtn.setEnabled(true);
        if(subtractionScore > 99)
            multiplicationBtn.setEnabled(true);
        if(multiplicationScore > 99)
            divisionBtn.setEnabled(true);
        if(divisionScore > 99)
            finalLessonBtn.setEnabled(true);

        numberComparisonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwoNumberComparisonActivity.questionCounter = 0;
                startActivityForResult(makeIntent(TwoNumberComparisonActivity.class), 2);
            }
        });

        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SumActivity.questionCounter = 0;
                Intent intent = makeIntent(SumActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        subtractionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubtractionActivity.questionCounter = 0;
                Intent intent = makeIntent(SubtractionActivity.class);
                startActivityForResult(intent, 4);
            }
        });

        multiplicationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiplicationActivity.questionCounter = 0;
                Intent intent = makeIntent(MultiplicationActivity.class);
                startActivityForResult(intent, 5);
            }
        });

        divisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DivisionActivity.questionCounter = 0;
                Intent intent = makeIntent(DivisionActivity.class);
                startActivityForResult(intent, 6);
            }
        });

        finalLessonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MathActivity.this, SevenFinalLessonActivity.class), 7);
            }
        });
    }


   private Intent makeIntent (Class<?> classs){
       Intent intent = new Intent(MathActivity.this, classs);
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

            dumpIntent(data);

            Log.i("MathActivity", "requestCode: " + requestCode);
            Log.i("MathActivity", "score: " + score);

            switch (requestCode) {
                case 1:
                    if(score > 99)
                        numberComparisonBtn.setEnabled(true);
                    break;
                case 2:
                    if(score > 99)
                        sumBtn.setEnabled(true);
                    break;
                case 3:
                    if(score > 99)
                        subtractionBtn.setEnabled(true);
                    break;
                case 4:
                    if(score > 99)
                        multiplicationBtn.setEnabled(true);
                    break;
                case 5:
                    if(score > 99) {
                        divisionBtn.setEnabled(true);
                    }
                    break;
                case 6:
                    if(score > 99) {
                        finalLessonBtn.setEnabled(true);
                    }
                    break;
            }
        }
    }

    public static void dumpIntent(Intent i){
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e("MathActivity","Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e("MathActivity","[" + key + "=" + bundle.get(key)+"]");
            }
            Log.e("MathActivity","Dumping Intent end");
        }
    }

}
