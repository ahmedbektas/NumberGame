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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import ba.number.game.R;
import ba.number.game.util.NumberToWordsConverter;
import ba.number.game.util.Prefs;

public class OneNumberLearningFragment extends Fragment {

    TextView questionTxt;
    EditText answerEt;
    Button nextBtn;
    int numberLimit, scoreMultiplier = 5;
    Random rn = new Random();
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;

    Vibrator vibrator;

    public static OneNumberLearningFragment newInstance(int numberLimit) {
        OneNumberLearningFragment fragment = new OneNumberLearningFragment();
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

        switch (numberLimit){
            case 10:
                scoreMultiplier  = 5;
                break;
            case 20:
                scoreMultiplier  = 10;
                break;
            case 100:
                scoreMultiplier  = 15;
                break;
            case 1000:
                scoreMultiplier  = 20;
                break;
        }

        mActivity.getSupportActionBar().setTitle("Answer question " + OneNumberLearningActivity.questionCounter);

        int oldScore = Prefs.getInstance(getActivity()).getScore(Prefs.NUMBER_LEARNING_SCORE);
        Log.i("NumberLearningFragment", "oldScore: " + oldScore);

        mActivity.getSupportActionBar().setSubtitle("Score: " + (((OneNumberLearningActivity) mActivity).getTrueCounter() * scoreMultiplier) + "     Total score: " + (oldScore + (((OneNumberLearningActivity) mActivity).getTrueCounter() * scoreMultiplier)));

        questionTxt = (TextView) rootView.findViewById(R.id.questionTxt);
        answerEt = (EditText) rootView.findViewById(R.id.answerEt);
        nextBtn = (Button) rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

        vibrator = ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        final int questionNumber = rn.nextInt(numberLimit) + 1;

        questionTxt.setText(NumberToWordsConverter.convert(questionNumber));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionNumber==Integer.valueOf(answerEt.getText().toString())){
                     showToast(true, "TRUE");
                    ((OneNumberLearningActivity) mActivity).increaseTrueCounter();
                }else {
                    showToast(false, "FALSE");
                    vibrator.vibrate(800);// vibration for 800 milliseconds
                }
                if (OneNumberLearningActivity.questionCounter<10) {
                    ((OneNumberLearningActivity) mActivity).ubaciFragment();
                    Log.i("NumberLearningFragment", "insert fragment");
                }else{
                    Log.i("NumberLearningFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + ((OneNumberLearningActivity) mActivity).getTrueCounter()*scoreMultiplier);
                    scoreDialog.setMessage("True answers: " + ((OneNumberLearningActivity) mActivity).getTrueCounter() + "\nFalse answers: " + (10-((OneNumberLearningActivity) mActivity).getTrueCounter()));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            OneNumberLearningActivity.questionCounter=0;

                            int oldScore = Prefs.getInstance(getActivity()).getScore(Prefs.NUMBER_LEARNING_SCORE);
                            int totalScore = oldScore + (((OneNumberLearningActivity) mActivity).getTrueCounter()*scoreMultiplier);

                            Prefs.getInstance(getActivity()).insertScore(Prefs.NUMBER_LEARNING_SCORE, totalScore);

                            getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("score", totalScore));

                            ((OneNumberLearningActivity) mActivity).setTrueCounter(0);

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
    public void showToast (boolean isCorrect, String text){
        Toast toast = new Toast(mActivity);

        View toastView = mActivity.getLayoutInflater().inflate(R.layout.custom_toast, null);
        ImageView toastImage = (ImageView) toastView.findViewById(R.id.toastImage);
        TextView toastText = (TextView) toastView.findViewById(R.id.toastText);

        toastText.setText(text);
        toastImage.setImageResource(isCorrect ? R.drawable.correct : R.drawable.incorrect);

        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);

        toast.show();
    }
}
