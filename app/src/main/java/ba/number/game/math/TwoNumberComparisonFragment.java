package ba.number.game.math;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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

import java.util.Random;

import ba.number.game.R;
import ba.number.game.util.Prefs;

public class TwoNumberComparisonFragment extends Fragment {

    TextView questionTxt;
    EditText answerEt;
    Button nextBtn;
    int numberLimit;
    int a, b, c;
    Random rn = new Random();
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;
    Vibrator vibrator;
    Toast toast;

    public static TwoNumberComparisonFragment newInstance(int numberLimit) {
        TwoNumberComparisonFragment fragment = new TwoNumberComparisonFragment();
        Bundle args = new Bundle();
        args.putInt("numberLimit", numberLimit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=(ActionBarActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            numberLimit = getArguments().getInt("numberLimit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_number_learning, container, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mActivity.getSupportActionBar().setTitle("Answer question " + TwoNumberComparisonActivity.questionCounter);

        int oldScore = Prefs.getInstance(getActivity()).getScore(Prefs.NUMBER_COMPARISON_SCORE);
        Log.i("NumberCmpsnFragment", "oldScore: " + oldScore);
        mActivity.getSupportActionBar().setSubtitle("Score: " + (((TwoNumberComparisonActivity) mActivity).getTrueCounter() * 10) + "     Total score: " + (oldScore + (((TwoNumberComparisonActivity) mActivity).getTrueCounter() * 10)));

        questionTxt = (TextView) rootView.findViewById(R.id.questionTxt);
        answerEt = (EditText) rootView.findViewById(R.id.answerEt);
        nextBtn = (Button) rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

        vibrator = ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        a=rn.nextInt(numberLimit) + 1;
        generateNumber();

        questionTxt.setText("Which number is largest?\n" + a + "   " + b + "   " + c);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int greatestNumber = a;
                if (b>a)
                    greatestNumber=b;
                if (c>b)
                    greatestNumber=c;

                if(greatestNumber==Integer.valueOf(answerEt.getText().toString())){
                    toast.setText("TRUE");
                    toast.show();
                    ((TwoNumberComparisonActivity) mActivity).increaseTrueCounter();
                }else {
                    toast.setText("FALSE");
                    toast.show();
                    vibrator.vibrate(800);// vibration for 800 milliseconds
                }
                if (TwoNumberComparisonActivity.questionCounter < 10) {
                    ((TwoNumberComparisonActivity) mActivity).ubaciFragment();
                    Log.i("NumberCmpsnFragment", "insert fragment");
                }else{
                    Log.i("NumberCmpsnFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + ((TwoNumberComparisonActivity) mActivity).getTrueCounter()*10);
                    scoreDialog.setMessage("True answers: " + ((TwoNumberComparisonActivity) mActivity).getTrueCounter() + "\nFalse answers: " + (10-((TwoNumberComparisonActivity) mActivity).getTrueCounter()));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TwoNumberComparisonActivity.questionCounter=0;

                            int oldScore = Prefs.getInstance(getActivity()).getScore(Prefs.NUMBER_COMPARISON_SCORE);
                            int totalScore = oldScore + (((TwoNumberComparisonActivity) mActivity).getTrueCounter()*10);

                            Prefs.getInstance(getActivity()).insertScore(Prefs.NUMBER_COMPARISON_SCORE, totalScore);

                            getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("score", totalScore));

                            ((TwoNumberComparisonActivity) mActivity).setTrueCounter(0);

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

    private void generateNumber (){
        b=rn.nextInt(numberLimit) + 1;
        c=rn.nextInt(numberLimit) + 1;
        if (a==b || b==c || a==c)
            generateNumber();
    }
}
