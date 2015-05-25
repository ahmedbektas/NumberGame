package ba.number.game.others;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ba.number.game.R;

public class GuessImageFragment extends Fragment {

    ImageView imageView;
    RadioGroup radioGroup;
    Button nextBtn;
    String questionType = "";
    int a, b, c;
    Random rn = new Random();
    //ovo je instanca, tj. objekat
    ActionBarActivity mActivity;
    Vibrator vibrator;
    Toast toast;
    String randomImage;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;

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
        final View rootView = inflater.inflate(R.layout.fragment_guess_image, container, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mActivity.getSupportActionBar().setTitle("Answer question " + GuessImageActivity.questionCounter);
        mActivity.getSupportActionBar().setSubtitle("Score: " + (((GuessImageActivity) mActivity).getTrueCounter() * 10));

        imageView = (ImageView) rootView.findViewById(R.id.image);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) rootView.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) rootView.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) rootView.findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) rootView.findViewById(R.id.radioButton4);
        nextBtn = (Button) rootView.findViewById(R.id.nextBtn);

        vibrator = ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE));

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);

        ArrayList<String> answers = new ArrayList<>();

        switch (questionType){
            case "animals":
                randomImage = GuessImageActivity.animalImages.get(rn.nextInt(5));
                int res = getResources().getIdentifier(randomImage, "drawable", getActivity().getPackageName());
                imageView.setImageResource(res);
                answers = generateFalseAnswersAnimals(randomImage);
                break;
            case "plants":
                randomImage = GuessImageActivity.plantImages.get(rn.nextInt(5));
                int res2 = getResources().getIdentifier(randomImage, "drawable", getActivity().getPackageName());
                imageView.setImageResource(res2);

                answers = generateFalseAnswersPlants(randomImage);
                break;
            case "vehicles":
                randomImage = GuessImageActivity.vehicleImages.get(rn.nextInt(5));
                int res3 = getResources().getIdentifier(randomImage, "drawable", getActivity().getPackageName());
                imageView.setImageResource(res3);

                answers = generateFalseAnswersVehicles(randomImage);
                break;
        }

        Collections.shuffle(answers);

        radioButton1.setText(answers.get(0));
        radioButton2.setText(answers.get(1));
        radioButton3.setText(answers.get(2));
        radioButton4.setText(answers.get(3));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton answerRB = (RadioButton)rootView.findViewById(radioGroup.getCheckedRadioButtonId());

                if (randomImage.equals(answerRB.getText().toString().toLowerCase())){
                    showToast(true, "TRUE");
                    ((GuessImageActivity) mActivity).increaseTrueCounter();
                }else {
                    showToast(false, "FALSE, The correct answer is " + randomImage.toUpperCase());
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton1:
                        nextBtn.setEnabled(true);
                        break;
                    case R.id.radioButton2:
                        nextBtn.setEnabled(true);
                        break;
                    case R.id.radioButton3:
                        nextBtn.setEnabled(true);
                        break;
                    case R.id.radioButton4:
                        nextBtn.setEnabled(true);
                        break;

                }
            }
        });

        return rootView;
    }

    public ArrayList<String> generateFalseAnswersAnimals (String trueAnswer){
        ArrayList<String> randomAnswers = new ArrayList<>();
        randomAnswers.add(trueAnswer);
        do{
            String falseAnswer = GuessImageActivity.animalImages.get(rn.nextInt(5));
            if (!randomAnswers.contains(falseAnswer))
                randomAnswers.add(falseAnswer);
        }while(randomAnswers.size() < 4);

        return randomAnswers;
    }

    public ArrayList<String> generateFalseAnswersPlants (String trueAnswer){
        ArrayList<String> randomAnswers = new ArrayList<>();
        randomAnswers.add(trueAnswer);
        do{
            String falseAnswer = GuessImageActivity.plantImages.get(rn.nextInt(5));
            if (!randomAnswers.contains(falseAnswer))
                randomAnswers.add(falseAnswer);
        }while(randomAnswers.size() < 4);

        return randomAnswers;
    }

    public ArrayList<String> generateFalseAnswersVehicles (String trueAnswer){
        ArrayList<String> randomAnswers = new ArrayList<>();
        randomAnswers.add(trueAnswer);
        do{
            String falseAnswer = GuessImageActivity.vehicleImages.get(rn.nextInt(5));
            if (!randomAnswers.contains(falseAnswer))
                randomAnswers.add(falseAnswer);
        }while(randomAnswers.size() < 4);

        return randomAnswers;
    }

    public void showToast (boolean isCorrect, String text){
        Toast toast = new Toast(mActivity);

        View toastView = mActivity.getLayoutInflater().inflate(R.layout.custom_toast, null);
        ImageView toastImage = (ImageView) toastView.findViewById(R.id.toastImage);
        TextView toastText = (TextView) toastView.findViewById(R.id.toastText);

        toastText.setText(text);
        toastText.setTextColor(isCorrect ? Color.GREEN : Color.RED);
        toastImage.setImageResource(isCorrect ? R.drawable.correct : R.drawable.incorrect);


        toast.setGravity(Gravity.CENTER, 0, 17);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);

        toast.show();
    }
}
