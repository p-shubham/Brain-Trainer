package com.personal.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int counter = 30, result;
    int marks = 0, totalQuestions = 0;
    boolean playTheGame = false;

    public void hide(View view){
        view.setVisibility(View.INVISIBLE);
    }

    public void hideByAnimating(View view){
        view.animate().setDuration(1000).alpha(0);
    }
    public void showByAnimating(View view){
        view.animate().setDuration(1000).alpha(1);
    }
    public void show(View view){
        view.setVisibility(View.VISIBLE);
    }
    public void questionAndOptions(){
        totalQuestions += 1;

        TextView question = findViewById(R.id.question);
        TextView opOne = findViewById(R.id.optionOne);
        TextView opTwo = findViewById(R.id.optionTwo);
        TextView opThree = findViewById(R.id.optionThree);
        TextView opFour = findViewById(R.id.optionFour);
        ArrayList<TextView> allOptions = new ArrayList<>();
        ArrayList<Integer> wrongAnswers = new ArrayList<>();
        allOptions.add(opOne);
        allOptions.add(opTwo);
        allOptions.add(opThree);
        allOptions.add(opFour);
        Random rand = new Random();
        int firstNumber = rand.nextInt(50);
        int secondNumber = rand.nextInt(60);
        int randomCorrectOption = rand.nextInt(4);

        result = firstNumber + secondNumber;
        for(int i = 0;i<4;i++){
            int wrongOption = rand.nextInt(result + 10);
            if(wrongOption == result){
                while(wrongOption == result)
                    wrongOption = rand.nextInt(result + 10);
                wrongAnswers.add(wrongOption);
            }
            else
                wrongAnswers.add(wrongOption);
        }
        int last = 2;

        if(randomCorrectOption == 1){
            opOne.setText(Integer.toString(result));
        }
        else if (randomCorrectOption == 2){
            opTwo.setText(Integer.toString(result));
        }
        else if (randomCorrectOption == 3){
            opThree.setText(Integer.toString(result));
        }
        else if (randomCorrectOption == 4){
            opFour.setText(Integer.toString(result));
        }
        question.setText(firstNumber + "+" + secondNumber);
        for(int i = 0;i<4;i++){
            TextView temp = allOptions.get(i);
            if(temp.getText() != Integer.toString(result)) {
                String toSet = wrongAnswers.get(i).toString();
                temp.setText(toSet);
            }
        }
    }
    public void startTheGame(View view){
        final TextView countdownTextview = findViewById(R.id.timer);
        final Button goButton = findViewById(R.id.goButton);
        final TextView question = findViewById(R.id.question);
        final TextView score = findViewById(R.id.score);
        final TextView correct = findViewById(R.id.correct);
        final TextView opOne = findViewById(R.id.optionOne);
        final TextView opTwo = findViewById(R.id.optionTwo);
        final TextView opThree = findViewById(R.id.optionThree);
        final TextView opFour = findViewById(R.id.optionFour);

        showByAnimating(question);
        showByAnimating(score);
        showByAnimating(countdownTextview);
        showByAnimating(opFour);
        showByAnimating(opOne);
        showByAnimating(opThree);
        showByAnimating(opTwo);

        hideByAnimating(goButton);
        hide(goButton);

        playTheGame = true;
        new CountDownTimer(counter * 1000 + 250,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = ((int)millisUntilFinished / 1000);
                String setTextValue = Integer.toString(seconds) + "s";
                countdownTextview.setText(setTextValue);
            }

            @Override
            public void onFinish() {
                goButton.setVisibility(View.VISIBLE);
                showByAnimating(goButton);
                counter = 30;
                countdownTextview.setText("30s");
                playTheGame = false;

                hideByAnimating(score);
                hideByAnimating(countdownTextview);
                hideByAnimating(opOne);
                hideByAnimating(opThree);
                hideByAnimating(opTwo);
                hideByAnimating(opFour);

                hide(correct);
                question.setText(score.getText());
                marks = 0;
                totalQuestions = 0;
                goButton.setText("Play Again!");
            }
        }.start();
        questionAndOptions();
    }

    public void checkAnswer(View view){
        if(playTheGame) {
            TextView temp = (TextView) view;
            TextView score = findViewById(R.id.score);
            TextView correct = findViewById(R.id.correct);
            show(score);
            if (Integer.parseInt(temp.getText().toString()) == result) {
                marks += 1;
                correct.setVisibility(View.VISIBLE);
            }
            else
                correct.setVisibility(View.INVISIBLE);
            score.setText(marks + "/" + totalQuestions);
            questionAndOptions();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView question = findViewById(R.id.question);
        TextView countdownTextview = findViewById(R.id.timer);
        TextView score = findViewById(R.id.score);

        question.setAlpha(0f);
        countdownTextview.setAlpha(0f);
        score.setAlpha(0f);
    }
}
