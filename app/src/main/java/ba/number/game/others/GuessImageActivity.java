package ba.number.game.others;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

import ba.number.game.R;
import ba.number.game.math.NumberComparisonFragment;

public class GuessImageActivity extends ActionBarActivity {
    Bundle bundle;
    public static int questionCounter = 0;
    private int trueCounter=0;

    public static ArrayList<String> animalImages = new ArrayList<>(Arrays.asList("bird","cat","chicken","cow","dog","duck"));
    public static ArrayList<String> vehicleImages = new ArrayList<>(Arrays.asList("airplane","bicycle","bus","car","motorcycle","train"));
    public static ArrayList<String> plantImages = new ArrayList<>(Arrays.asList("pineapple","apple","banana","pear","strawberry","orange"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_image);

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
                .replace(R.id.container, GuessImageFragment.newInstance(bundle.getString("questionType")))
                .commit();
    }
}
