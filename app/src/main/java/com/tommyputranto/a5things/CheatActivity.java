package com.tommyputranto.a5things;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.tommy.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.tommy.geoquiz.answer.shown";



    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private static final String ANSWER_SHOWN ="cheat";
    private int data;
    int[] dataBenarSalah = new int[]{
            R.string.tidak_benar_salah,
            R.string.salah_button,
            R.string.benar_button

    };
    int i;
    boolean j=false;
    private TextView apiLevel;
    private int api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue){
                    i=2;
                    data =dataBenarSalah[i];

                }else {
                    i=1;
                    data =dataBenarSalah[i];

                }
                j=true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                    mAnswerTextView.setText(data);
                    setAnswerShownResult(j);
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerTextView.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }else {
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);


                }
            }
        });
        if (savedInstanceState!=null){
            j = savedInstanceState.getBoolean(ANSWER_SHOWN, true);
            i= savedInstanceState.getInt(ANSWER_SHOWN, 0);
        }
        setAnswerShownResult(j);
        mAnswerTextView.setText(dataBenarSalah[i]);
        apiLevel = (TextView)findViewById(R.id.apiLevel_TextView);

        if (apiLevel != null) {
            apiLevel.setText(getString(R.string.api_textView) +" "+ Build.VERSION.SDK_INT);
        }
    }
    private void setAnswerShownResult(boolean isAnswerTrue){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerTrue);
        setResult(RESULT_OK, data);
    }
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_SHOWN, j);
        outState.putInt(ANSWER_SHOWN, i);
    }
}
