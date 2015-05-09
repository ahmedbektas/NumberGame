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

import java.util.Random;

import ba.number.game.R;
import ba.number.game.util.NumberToWordsConverter;

public class NumberLearningFragment extends Fragment {

    TextView questionTxt;
    EditText answerEt;
    Button nextBtn;
    int numberLimit;
    Random rn = new Random();
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;
    Vibrator vibrator;
    Toast toast;
    SharedPreferences prefs;

    public static NumberLearningFragment newInstance(int numberLimit) {
        NumberLearningFragment fragment = new NumberLearningFragment();
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

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mActivity.getSupportActionBar().setTitle("Answer question " + NumberLearningActivity.questionCounter);
        mActivity.getSupportActionBar().setSubtitle("Score: " + (((NumberLearningActivity) mActivity).getTrueCounter() * 10));

        questionTxt = (TextView) rootView.findViewById(R.id.questionTxt);
        answerEt = (EditText) rootView.findViewById(R.id.answerEt);
        nextBtn = (Button) rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

        vibrator = ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        final int questionNumber = rn.nextInt(numberLimit) + 1;

        questionTxt.setText(NumberToWordsConverter.convert(questionNumber));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionNumber==Integer.valueOf(answerEt.getText().toString())){
                    toast.setText("TRUE");
                    toast.show();
                    ((NumberLearningActivity) mActivity).increaseTrueCounter();
                }else {
                    toast.setText("FALSE");
                    toast.show();
                    vibrator.vibrate(800);// vibration for 800 milliseconds
                }
                if (NumberLearningActivity.questionCounter<10) {
                    ((NumberLearningActivity) mActivity).ubaciFragment();
                    Log.i("NumberLearningFragment", "insert fragment");
                }else{
                    Log.i("NumberLearningFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + ((NumberLearningActivity) mActivity).getTrueCounter()*10);
                    scoreDialog.setMessage("True answers: " + ((NumberLearningActivity) mActivity).getTrueCounter() + "\nFalse answers: " + (10-((NumberLearningActivity) mActivity).getTrueCounter()));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NumberLearningActivity.questionCounter=0;
                            ((NumberLearningActivity) mActivity).setTrueCounter(0);
                            int oldScore = prefs.getInt("numberLearningScore", 0);
                            prefs.edit().putInt("numberLearningScore", oldScore + (((NumberLearningActivity) mActivity).getTrueCounter()*10));

                            getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("score", oldScore + (((NumberLearningActivity) mActivity).getTrueCounter()*10)));
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
}
