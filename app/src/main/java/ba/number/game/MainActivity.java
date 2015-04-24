package ba.number.game;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    CheckBox sumCheckBox, subtractionCheckBox, multiplicationCheckBox, divisionCheckBox;
    Spinner numberLimit;
    Button playBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                PlayActivity.trueCounter=0;

                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
