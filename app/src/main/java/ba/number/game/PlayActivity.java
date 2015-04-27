package ba.number.game;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;


public class PlayActivity extends ActionBarActivity {

    Bundle bundle;
    public static int questionCounter = 0;
    private int trueCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Log.i("PlayActivity", "onCreate");

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
        QuestionFragment mQuestionFragment = new QuestionFragment();
        //ovdje vrijednosti iz bundle ubacujem u mQuestionFragment
        mQuestionFragment.setArguments(bundle);

        //ovdje ubacujem fragment u PlayActivity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mQuestionFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
