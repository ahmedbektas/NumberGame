package ba.number.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

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

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mActivity.getSupportActionBar().setTitle("Answer question " + PlayActivity.questionCounter);
        mActivity.getSupportActionBar().setSubtitle("Score: " + (PlayActivity.trueCounter*10));

        questionTxt = (TextView)rootView.findViewById(R.id.questionTxt);
        answerEt = (EditText)rootView.findViewById(R.id.answerEt);
        nextBtn = (Button)rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

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
                            Toast.makeText(getActivity(),"TRUE", Toast.LENGTH_LONG).show();
                            PlayActivity.trueCounter++;
                        }else {
                            Toast.makeText(getActivity(), "FALSE", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        String answer2 = answerEt.getText().toString();
                        if (x-y==Integer.valueOf(answer2)){
                            Toast.makeText(getActivity(),"TRUE", Toast.LENGTH_LONG).show();
                            PlayActivity.trueCounter++;
                        }else {
                            Toast.makeText(getActivity(), "FALSE", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 3:
                        String answer3 = answerEt.getText().toString();
                        if (x*y==Integer.valueOf(answer3)){
                            Toast.makeText(getActivity(),"TRUE", Toast.LENGTH_LONG).show();
                            PlayActivity.trueCounter++;
                        }else {
                            Toast.makeText(getActivity(), "FALSE", Toast.LENGTH_LONG).show();
                        }
                    case 4:
                        String answer4 = answerEt.getText().toString();
                        if (x/y==Integer.valueOf(answer4)){
                            Toast.makeText(getActivity(),"TRUE", Toast.LENGTH_LONG).show();
                            PlayActivity.trueCounter++;
                        }else {
                            Toast.makeText(getActivity(), "FALSE", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                if (PlayActivity.questionCounter<10) {
                    ((PlayActivity) mActivity).ubaciFragment();
                    Log.i("QuestionFragment", "insert fragment");
                }else{
                    Log.i("QuestionFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + (PlayActivity.trueCounter)*10);
                    scoreDialog.setMessage("True answers: " + PlayActivity.trueCounter + "\nFalse answers: " + (10-(PlayActivity.trueCounter)));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PlayActivity.questionCounter=0;
                            PlayActivity.trueCounter=0;
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