package com.mohammadsamandari.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayoutTop;
    GridLayout gridLayoutAnswers;
    TextView txtTime, txtQuestion, txtScore, txtToast;
    Button btn0, btn1, btn2, btn3, btnStart;
    CountDownTimer countDownTimer;

    ArrayList<Integer> answersArraylist;
    MediaPlayer mediaPlayerRight, mediaPlayerWrong;
    int correctAnswerPosition, noCorrectAnswer, noAllQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Declaring The variables on the activity.
        linearLayoutTop = findViewById(R.id.linearLayoutTop);
        gridLayoutAnswers = findViewById(R.id.gridLayoutAnswers);

        txtTime = findViewById(R.id.txtTime);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtScore = findViewById(R.id.txtScore);
        txtToast = findViewById(R.id.txtToast);

        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btnStart = findViewById(R.id.btnStart);

        answersArraylist = new ArrayList<>();

        noAllQuestions = 0;
        noCorrectAnswer = 0;

//        Preparing 2 sounds for the right and wrong answers.
        mediaPlayerRight = MediaPlayer.create(this, R.raw.right);
        mediaPlayerWrong = MediaPlayer.create(this, R.raw.wrong);

//        Declaring the countdown timer for game.
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
//                updating the timer on the activity.
                txtTime.setText(String.valueOf(millisUntilFinished / 1000) + " s");
            }

            @Override
            public void onFinish() {
//                resetting the Game structure
                txtToast.setText("Correct Answers: " + String.valueOf(noCorrectAnswer) + "\n All Questions: " + String.valueOf(noAllQuestions));
                resetGame();

            }
        };

    }


    public void startFunction(View view) {

        createQuestion();

        view.setVisibility(View.INVISIBLE);

        txtToast.setText("");

        updateScore();

        btn0.setEnabled(true);
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);

        countDownTimer.start();
    }

    public void answerFunction(View view) {
        if (Integer.parseInt(view.getTag().toString()) == correctAnswerPosition) {
            noCorrectAnswer++;
            noAllQuestions++;
            txtToast.setText("CORRECT!");

            mediaPlayerRight.seekTo(0);
            mediaPlayerRight.start();
        } else {
            noAllQuestions++;
            txtToast.setText("WRONG!");

            mediaPlayerWrong.seekTo(0);
            mediaPlayerWrong.start();
        }

        updateScore();
        createQuestion();
    }

    private void updateScore() {
        txtScore.setText(new DecimalFormat("00").format(noCorrectAnswer) +
                " / " +
                new DecimalFormat("00").format(noAllQuestions));
    }

    public void createQuestion() {
        //this method is going to create a question and update all the text on the activity.

//        Creating two Random Number Between 1 and 20
        int randA = new Random().nextInt(20) + 1;
        int randB = new Random().nextInt(20) + 1;

//        Updating The Question TextView
        txtQuestion.setText(randA + " + " + randB);

//        Calculating The Correct Answer
        int correctAnswer = randA + randB;

//        Creating Answers Array
        correctAnswerPosition = new Random().nextInt(4);

        answersArraylist.clear();

        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerPosition) {
                answersArraylist.add(correctAnswer);

            } else {
                answersArraylist.add(new Random().nextInt(40) + 1);
            }
        }

//        Updating The Answers Button Text.
        btn0.setText(answersArraylist.get(0).toString());
        btn1.setText(answersArraylist.get(1).toString());
        btn2.setText(answersArraylist.get(2).toString());
        btn3.setText(answersArraylist.get(3).toString());
    }

    private void resetGame() {
        btn0.setText("0");
        btn1.setText("0");
        btn2.setText("0");
        btn3.setText("0");

        btn0.setEnabled(false);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);

        txtTime.setText("30 s");
        txtScore.setText("00 / 00");

        btnStart.setText("Try Again?");
        btnStart.setVisibility(View.VISIBLE);

        noCorrectAnswer = 0;
        noAllQuestions = 0;
    }
}
