package ba.number.game.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Admin on 10.5.2015..
 */
public class Prefs {
    private static Prefs instance = null;
    SharedPreferences prefs;

    public static  String NUMBER_LEARNING_SCORE = "number_learning_score";
    public static  String NUMBER_COMPARISON_SCORE = "number_comparison_score";
    public static  String SUM_QUESTION_SCORE = "sum_question_score";
    public static  String SUBTRACTION_QUESTION_SCORE = "subtraction_question_score";
    public static  String MULTIPLICATION_QUESTION_SCORE = "multiplication_question_score";
    public static  String DIVISION_QUESTION_SCORE = "division_question_score";
    public static  String MATH_QUESTION_SCORE = "math_question_score";

    public static Prefs getInstance(Context context) {
        if(instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public Prefs(Context context){
//        prefs = context.getApplicationContext().getSharedPreferences("scorePrefs", Context.MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public void insertScore(String whichScore, int score){
        prefs.edit().putInt(whichScore, score).apply();
    }

    public int getScore(String whichScore){
        return prefs.getInt(whichScore, 0);
    }
}
