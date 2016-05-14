package com.tommyputranto.a5things;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionNextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.pertanyaan_surabaya, true),
            new Question(R.string.pertanyaan_yogyakarta, false),
            new Question(R.string.pertanyaan_ITS, false),
            new Question(R.string.pertanyaan_IAK, true),
            new Question(R.string.pertannyaan_proklamator, true)
    };

    private int mCrrentIndex = 0;
    private static final String KEY_INDEX = "index";
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String KEY_CHEAT="Cheat";

    private Button mCheatButton;
    private boolean mIsCheater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);

            }
        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);

            }
        });

        mQuestionNextView = (TextView)findViewById(R.id.question_text_view);
        int question = mQuestionsBank[mCrrentIndex].getTextResId();
        mQuestionNextView.setText(question);
        mQuestionNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCrrentIndex += 1;
                if (mCrrentIndex >= mQuestionsBank.length){
                    mCrrentIndex = 0;
                }
                updateQuestion();
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCrrentIndex += 1;
                if (mCrrentIndex >= mQuestionsBank.length){
                    mCrrentIndex = 0;
                }
                mIsCheater=false;
               updateQuestion();

            }
        });

        mPrevButton=(Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCrrentIndex -= 1;
                if (mCrrentIndex < 0){
                    mCrrentIndex = 4;
                }
                mIsCheater=false;
                updateQuestion();

            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionsBank[mCrrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        if (savedInstanceState!= null){
            mCrrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT, true);
        }
        updateQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_INDEX, mCrrentIndex);
        outState.putBoolean(KEY_CHEAT, mIsCheater);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data==null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "On Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "On pause create");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"On Resume create");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"On Start create");
    }

    private void updateQuestion(){
        int question = mQuestionsBank[mCrrentIndex].getTextResId();
        mQuestionNextView.setText(question);
    }

    private void checkAnswer(boolean userPressTrue){
        boolean answerIsTrue = mQuestionsBank[mCrrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (mIsCheater){
            messageResId=R.string.judment_toast;
        }else {
            if (userPressTrue ==answerIsTrue){
                messageResId = R.string.correct_toast;
            }else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
    }







    /*public void correct(View v){
        Toast.makeText(getApplication(), R.string.correct_toast,Toast.LENGTH_LONG).show();

    }
    public void incorrect(View v){
        Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_LONG).show();
    }*/
}
