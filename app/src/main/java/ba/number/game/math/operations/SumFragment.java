package ba.number.game.math.operations;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ba.number.game.R;
import ba.number.game.util.Prefs;

/**
 * Created by Admin on 10.5.2015..
 */
public class SumFragment extends Fragment {

    TextView questionTxt;
    EditText answerEt;
    Button nextBtn;
    int numberLimit;
    Random rn = new Random();
    int x=0,y=0, scoreMultiplier = 5;
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;
    Vibrator vibrator;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mActivity=(ActionBarActivity)activity;
        //Tip ide velikim slovom, a ime varijable malim slovom
        Bundle bundle = getArguments();
        numberLimit = Integer.valueOf(bundle.getString("numberLimit"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("SumFragment", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

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

        mActivity.getSupportActionBar().setTitle("Answer question " + SumActivity.questionCounter);

        int oldScore = Prefs.getInstance(getActivity()).getScore(Prefs.SUM_QUESTION_SCORE);

        Log.i("SumFragment", "oldScore: " + oldScore);

        mActivity.getSupportActionBar().setSubtitle("Score: " + (((SumActivity) mActivity).getTrueCounter() * scoreMultiplier) + "     Total score: " + (oldScore + (((SumActivity) mActivity).getTrueCounter() * scoreMultiplier)));

        questionTxt = (TextView)rootView.findViewById(R.id.questionTxt);
        answerEt = (EditText)rootView.findViewById(R.id.answerEt);
        nextBtn = (Button)rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

        vibrator = ((Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        x = rn.nextInt(numberLimit - 1) + 1;
        y = generateYforSum(x);

        questionTxt.setText(x+" + "+ y + " = ?");
        //ctrl+shift+space
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answerEt.getText().toString();
                if (x+y==Integer.valueOf(answer)){
                    showToast(true, "TRUE");
                    ((SumActivity) mActivity).increaseTrueCounter();
                }else {
                    showToast(false, "FALSE");
                    vibrator.vibrate(800);// vibration for 800 milliseconds
                }

                if (SumActivity.questionCounter<10) {
                    ((SumActivity) mActivity).ubaciFragment();
                    Log.i("SumFragment", "insert fragment");
                }else{
                    Log.i("SumFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + ((SumActivity) mActivity).getTrueCounter()*scoreMultiplier);
                    scoreDialog.setMessage("True answers: " + ((SumActivity) mActivity).getTrueCounter() + "\nFalse answers: " + (10-((SumActivity) mActivity).getTrueCounter()));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SumActivity.questionCounter=0;

                            int oldScore = Prefs.getInstance(getActivity()).getScore(Prefs.SUM_QUESTION_SCORE);
                            int totalScore = oldScore + (((SumActivity) mActivity).getTrueCounter()*scoreMultiplier);

                            Prefs.getInstance(getActivity()).insertScore(Prefs.SUM_QUESTION_SCORE, totalScore);

                            getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("score", totalScore));

                            ((SumActivity) mActivity).setTrueCounter(0);

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