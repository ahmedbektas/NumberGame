package ba.number.game.math;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ba.number.game.R;
import ba.number.game.math.PlayActivity;

/**
 * Created by Admin on 23.4.2015..
 */
public class QuestionFragment extends Fragment {

    TextView questionTxt;
    EditText answerEt;
    Button nextBtn;
    ArrayList<Integer> listaFunkcija;
    int numberLimit;
    Random rn = new Random();
    int function=0,x=0,y=0;
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;
    Vibrator vibrator;
    Toast toast;
    SharedPreferences prefs;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mActivity=(ActionBarActivity)activity;
       //Tip ide velikim slovom, a ime varijable malim slovom
        Bundle bundle = getArguments();
        listaFunkcija = bundle.getIntegerArrayList("listaFunkcija");
        numberLimit = Integer.valueOf(bundle.getString("numberLimit"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("QuestionFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mActivity.getSupportActionBar().setTitle("Answer question " + PlayActivity.questionCounter);
        mActivity.getSupportActionBar().setSubtitle("Score: " + (((PlayActivity) mActivity).getTrueCounter()*10));

        questionTxt = (TextView)rootView.findViewById(R.id.questionTxt);
        answerEt = (EditText)rootView.findViewById(R.id.answerEt);
        nextBtn = (Button)rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

        vibrator = ((Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        toast = Toast.makeText(getActivity(), "" ,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        int randomFunctionIndex=0;
        if (listaFunkcija.size()>1)
            randomFunctionIndex = rn.nextInt(listaFunkcija.size());

        function = listaFunkcija.get(randomFunctionIndex);

        switch (function){
            case 1:
                //numberLimit is the maximum and the 1 is our minimum
                x = rn.nextInt(numberLimit - 1) + 1;
                y = generateYforSum(x);

                questionTxt.setText(x+" + "+ y + " = ?");
                break;
            case 2:
                x = rn.nextInt(numberLimit) + 1;
                y = generateYforSubtraction(x);

                questionTxt.setText(x+" - "+ y + " = ?");

                break;
            case 3:
                x = rn.nextInt((int) Math.sqrt(numberLimit)) + 1;
                y = generateYforMultiplication(x);

                questionTxt.setText(x+" * "+ y + " = ?");
                break;
            case 4:
                x = rn.nextInt(numberLimit) + 1;
                y = generateYforDivision(x);

                questionTxt.setText(x+" / "+ y + " = ?");
                break;
        }

        //ctrl+shift+space
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (function){
                    case 1:
                        String answer = answerEt.getText().toString();
                        if (x+y==Integer.valueOf(answer)){
                            toast.setText("TRUE");
                            toast.show();
                            ((PlayActivity) mActivity).increaseTrueCounter();
                        }else {
                            toast.setText("FALSE");
                            toast.show();
                            vibrator.vibrate(800);// vibration for 800 milliseconds
                        }
                        break;
                    case 2:
                        String answer2 = answerEt.getText().toString();
                        if (x-y==Integer.valueOf(answer2)){
                            toast.setText("TRUE");
                            toast.show();
                            ((PlayActivity) mActivity).increaseTrueCounter();
                        }else {
                            toast.setText("FALSE");
                            toast.show();
                            vibrator.vibrate(800);
                        }
                        break;
                    case 3:
                        String answer3 = answerEt.getText().toString();
                        if (x*y==Integer.valueOf(answer3)){
                            toast.setText("TRUE");
                            toast.show();
                            ((PlayActivity) mActivity).increaseTrueCounter();
                        }else {
                            toast.setText("FALSE");
                            toast.show();
                            vibrator.vibrate(800);
                        }
                    case 4:
                        String answer4 = answerEt.getText().toString();
                        if (x/y==Integer.valueOf(answer4)){
                            toast.setText("TRUE");
                            toast.show();
                            ((PlayActivity) mActivity).increaseTrueCounter();
                        }else {
                            toast.setText("FALSE");
                            toast.show();
                            vibrator.vibrate(800);
                        }
                        break;
                }
                if (PlayActivity.questionCounter<10) {
                    ((PlayActivity) mActivity).ubaciFragment();
                    Log.i("QuestionFragment", "insert fragment");
                }else{
                    Log.i("QuestionFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + ((PlayActivity) mActivity).getTrueCounter()*10);
                    scoreDialog.setMessage("True answers: " + ((PlayActivity) mActivity).getTrueCounter() + "\nFalse answers: " + (10-((PlayActivity) mActivity).getTrueCounter()));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlayActivity.questionCounter=0;
                            ((PlayActivity) mActivity).setTrueCounter(0);
                            int oldScore = prefs.getInt("mathQuestionScore", 0);
                            prefs.edit().putInt("mathQuestionScore", oldScore + (((PlayActivity) mActivity).getTrueCounter()*10));

                            getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("score", oldScore + (((PlayActivity) mActivity).getTrueCounter()*10)));
                            mActivity.finish();
                        }
                    });
                    scoreDialog.show();
                }

            }
        });

        answerEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0)
                    nextBtn.setEnabled(true);
                else
                    nextBtn.setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return rootView;
    }

    public int generateYforSum(int x) {
        int y = 0;
        do{
            y = rn.nextInt(numberLimit) + 1;
        }while(x + y > numberLimit);

        return y;
    }

    public int generateYforSubtraction (int x){
        int  y=0;
        do{
            y = rn.nextInt(numberLimit) + 1;
        }while(y > x);

        return y;
    }

    public int generateYforMultiplication (int x){
        int y = rn.nextInt((int) Math.sqrt(numberLimit)) + 1;

        return y;
    }

    public int generateYforDivision (int x){
        int  y=0;
        do{
            y = rn.nextInt(numberLimit) + 1;
        }while(x % y > 0 || x < y);
        return y;
    }

}