package ba.number.game;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ba.number.game.math.MathActivity;
import ba.number.game.math.NumberComparisonActivity;
import ba.number.game.others.GuessImageActivity;


public class MainActivity extends ActionBarActivity {

    Button animalsBtn, plantsBtn, vehiclesBtn, mathBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animalsBtn = (Button)findViewById(R.id.animalsBtn);
        plantsBtn = (Button)findViewById(R.id.plantsBtn);
        vehiclesBtn = (Button)findViewById(R.id.vehiclesBtn);
        mathBtn = (Button)findViewById(R.id.mathBtn);

        mathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MathActivity.class));
            }
        });

        animalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuessImageActivity.class);
                intent.putExtra("questionType", "animals");
                startActivity(intent);
            }
        });

        plantsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuessImageActivity.class);
                intent.putExtra("questionType", "plants");
                startActivity(intent);
            }
        });

        vehiclesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuessImageActivity.class);
                intent.putExtra("questionType", "vehicles");
                startActivity(intent);
            }
        });
    }


    }