package ba.number.game.math;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import ba.number.game.R;

public class TwoNumberComparisonActivity extends ActionBarActivity {
    Bundle bundle;
    public static int questionCounter = 0;
    private int trueCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_comparison);

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
        //ovdje ubacujem fragment u PlayActivity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, TwoNumberComparisonFragment.newInstance(Integer.valueOf(bundle.getString("numberLimit"))))
                .commit();
    }

}