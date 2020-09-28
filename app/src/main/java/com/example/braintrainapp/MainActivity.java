package com.example.braintrainapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button playAgainButton;
    Button buttonAns0;
    Button buttonAns1;
    Button buttonAns2;
    Button buttonAns3;
    TextView resultTextView;
    TextView timerTextView;
    TextView scoreTextView;
    TextView questionTextView;
    TextView expTextView;
    TextView timeInputTextView;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    GridLayout gridLayout2;
    Boolean isPlay =  false;
    int locCorrect;
    int score = 0;
    int numberOfQ = 0;
    int timeRound = 0;
    String percentageString;
    EditText timeEditText;

    public void startTrain (View view) {
        if(timeEditText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Input Time", Toast.LENGTH_SHORT).show();
        }
        else {
            button.setVisibility(View.GONE);
            timerTextView.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);
            gridLayout2.setVisibility(View.VISIBLE);
            playAgain(button);
        }
    }

    public void playAgain (View view) {
        if(timeEditText.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Input Time", Toast.LENGTH_SHORT).show();
        }
        else {
            isPlay = true;
            score = 0;
            numberOfQ = 0;
            questionTextView.setVisibility(View.VISIBLE);
            resultTextView.setVisibility(View.INVISIBLE);
            playAgainButton.setVisibility(View.INVISIBLE);
            expTextView.setVisibility(View.INVISIBLE);
            timeEditText.setVisibility(View.INVISIBLE);
            timeInputTextView.setVisibility(View.INVISIBLE);
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQ));
            newQuestion();
            timeRound = Integer.parseInt(timeEditText.getText().toString());

            new CountDownTimer((timeRound * 1000) + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + "s"));
                }

                @Override
                public void onFinish() {
                    resultTextView.setText("Times Up!!!");
                    isPlay = false;
                    float percentage = (float) score * 100 / (float) numberOfQ;
                    if (score == 0) {
                        percentageString = "0.00";
                    } else {
                        percentageString = String.format("%.2f", percentage);
                    }
                    expTextView.setText("You got " + score + " correct answers out of " + numberOfQ + " questions\n " + percentageString + "%");
                    expTextView.setVisibility(View.VISIBLE);
                    resultTextView.setVisibility(View.VISIBLE);
                    resultTextView.setTextColor(Color.parseColor("#f4c430"));
                    playAgainButton.setVisibility(View.VISIBLE);
                    timeEditText.setVisibility(View.VISIBLE);
                    questionTextView.setVisibility(View.INVISIBLE);
                    timeInputTextView.setVisibility(View.VISIBLE);
                }
            }.start();
        }
    }

    public void chooseAnswer (View view) {
        if(isPlay) {
            Log.i("info", view.getTag().toString());
            if (Integer.toString(locCorrect).equalsIgnoreCase(view.getTag().toString())) {
                resultTextView.setText("Correct");
                resultTextView.setTextColor(Color.parseColor("#0BA511"));
                score++;
            } else {
                resultTextView.setText("Wrong");
                resultTextView.setTextColor(Color.parseColor("#E50000"));
            }
            numberOfQ++;
            resultTextView.setVisibility(View.VISIBLE);
            newQuestion();
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQ));
        }
    }

    public void newQuestion() {
        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        int score = 0;
        int questionNumber = 0;

        locCorrect = rand.nextInt(4);

        questionTextView.setText(Integer.toString(a)  + " + " + Integer.toString(b));

        answers.clear();

        for(int i = 0; i < 4; i++){
            if(i == locCorrect){
                answers.add(a+b);
            }
            else{
                int wrongAnswer = rand.nextInt(41);
                while(wrongAnswer == a+b){
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        buttonAns0.setText(Integer.toString(answers.get(0)));
        buttonAns1.setText(Integer.toString(answers.get(1)));
        buttonAns2.setText(Integer.toString(answers.get(2)));
        buttonAns3.setText(Integer.toString(answers.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        gridLayout2 = findViewById(R.id.gridLayout2);
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        questionTextView = findViewById(R.id.questionTextView);
        resultTextView = findViewById(R.id.resultTextView);
        buttonAns0 = findViewById(R.id.buttonAns0);
        buttonAns1 = findViewById(R.id.buttonAns1);
        buttonAns2 = findViewById(R.id.buttonAns2);
        buttonAns3 = findViewById(R.id.buttonAns3);
        playAgainButton = findViewById(R.id.playAgainButton);
        expTextView = findViewById(R.id.expTextView);
        timeEditText = findViewById(R.id.timeEditText);
        timeInputTextView = findViewById(R.id.inputTextView);

    }
}
