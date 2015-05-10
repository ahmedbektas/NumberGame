package ba.number.game.math.operations;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ba.number.game.R;
import ba.number.game.math.QuestionFragment;

public class MultiplicationActivity extends ActionBarActivity {

    Bundle bundle;
    public static int questionCounter = 0;
    private int trueCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);
        Log.i("SumActivity", "onCreate");

        //u bundle snimam podatke iz MainActivity-a
        bundle = getIntent().getExtras();

        if (savedInstanceState == null) {
            ubaciFragment();
        }
    }

    public int getTrueCounter() {
        return trueCounter;
    }

    public void setTrueCounter(int trueCounter) {
        this.trueCounter = trueCounter;
    }
    public void increaseTrueCounter (){
        this.trueCounter++;
    }

    public void ubaciFragment(){
        questionCounter++;
        //ovdje pravim instancu(objekat) od QuestionFragmenta
        MultiplicationFragment mQuestionFragment = new MultiplicationFragment();
        //ovdje vrijednosti iz bundle ubacujem u mQuestionFragment
        mQuestionFragment.setArguments(bundle);

        //ovdje ubacujem fragment u PlayActivity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mQuestionFragment)
                .commit();
    }
}