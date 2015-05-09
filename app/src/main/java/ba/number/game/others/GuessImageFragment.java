package ba.number.game.others;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.Random;

import ba.number.game.R;

public class GuessImageFragment extends Fragment {

    ImageView imageView;
    EditText answerEt;
    Button nextBtn;
    String questionType = "";
    int a, b, c;
    Random rn = new Random();
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;
    Vibrator vibrator;
    Toast toast;
    String randomImage;

    public static GuessImageFragment newInstance(String questionType) {
        GuessImageFragment fragment = new GuessImageFragment();
        Bundle args = new Bundle();
        args.putString("questionType", questionType);
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
            questionType = getArguments().getString("questionType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guess_image, container, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mActivity.getSupportActionBar().setTitle("Answer question " + GuessImageActivity.questionCounter);
        mActivity.getSupportActionBar().setSubtitle("Score: " + (((GuessImageActivity) mActivity).getTrueCounter() * 10));

        imageView = (ImageView) rootView.findViewById(R.id.image);
        answerEt = (EditText) rootView.findViewById(R.id.answerEt);
        nextBtn = (Button) rootView.findViewById(R.id.nextBtn);
        nextBtn.setEnabled(false);

        vibrator = ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        switch (questionType){
            case "animals":
                randomImage = GuessImageActivity.animalImages.get(rn.nextInt(5));
                int res = getResources().getIdentifier(randomImage, "drawable", getActivity().getPackageName());
                imageView.setImageResource(res);
                break;
            case "plants":
                randomImage = GuessImageActivity.plantImages.get(rn.nextInt(5));
                int res2 = getResources().getIdentifier(randomImage, "drawable", getActivity().getPackageName());
                imageView.setImageResource(res2);
                break;
            case "vehicles":
                randomImage = GuessImageActivity.vehicleImages.get(rn.nextInt(5));
                int res3 = getResources().getIdentifier(randomImage, "drawable", getActivity().getPackageName());
                imageView.setImageResource(res3);
                break;
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(randomImage.equals(answerEt.getText().toString().toLowerCase())){
                    toast.setText("TRUE");
                    toast.show();
                    ((GuessImageActivity) mActivity).increaseTrueCounter();
                }else {
                    toast.setText("FALSE, The correct answer is " + randomImage.toUpperCase());
                    toast.show();
                    vibrator.vibrate(800);// vibration for 800 milliseconds
                }

                if (GuessImageActivity.questionCounter<10) {
                    ((GuessImageActivity) mActivity).ubaciFragment();
                    Log.i("GuessImageFragment", "insert fragment");
                }else{
                    Log.i("GuessImageFragment", "show score");
                    AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getActivity());
                    scoreDialog.setTitle("Score: " + ((GuessImageActivity) mActivity).getTrueCounter()*10);
                    scoreDialog.setMessage("True answers: " + ((GuessImageActivity) mActivity).getTrueCounter() + "\nFalse answers: " + (10-((GuessImageActivity) mActivity).getTrueCounter()));
                    scoreDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GuessImageActivity.questionCounter=0;
                            ((GuessImageActivity) mActivity).setTrueCounter(0);
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
