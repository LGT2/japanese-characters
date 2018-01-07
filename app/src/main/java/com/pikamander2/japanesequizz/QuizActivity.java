package com.pikamander2.japanesequizz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class QuizActivity extends Activity {
    TextView textViewRomaji;
    TextView textViewScore;
    TextView textViewCorrect;
    TextView textViewCorrectAnswer;

    int numCorrect = 0;
    int numWrong = 0;
    int quizID;
    int numAnswerChoices = 8;

    Random random = new Random();
    ExpandableHeightGridView gridViewQuestions;
    String currentAnswer = "";
    String currentQuestion = "";
    Question question;
    QuestionAdapter questionAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        intent = getIntent();

        quizID = intent.getIntExtra("com.example.myfirstapp.MODE_ID", 1);
        question = new Question(quizID);

        questionAdapter = new QuestionAdapter(this, question);

        textViewRomaji = (TextView) findViewById(R.id.textViewRomaji);

        textViewScore = (TextView) findViewById(R.id.textViewScore);

        textViewCorrect = (TextView) findViewById(R.id.textViewCorrect);

        textViewCorrectAnswer = (TextView) findViewById(R.id.textViewCorrectAnswer);

        gridViewQuestions = (ExpandableHeightGridView) findViewById(R.id.gridViewQuestions);

        Button buttonFlipQuestions = (Button) findViewById(R.id.buttonFlipQuestions);
        buttonFlipQuestions.setOnClickListener(flipQuestionsOnClick);

        updateScore();

        for (int i = 0; i < questionAdapter.buttons.size(); i++) {
            ((Button) questionAdapter.getItem(i)).setOnClickListener(answerOnClick);
        }

        gridViewQuestions.setAdapter(questionAdapter);
        gridViewQuestions.setExpanded(true);

        switchQuestion();
    }

    public OnClickListener answerOnClick = new OnClickListener() {
        public void onClick(View view) {
            guess(view);
            switchQuestionAndAnswers();
        }
    };

    public OnClickListener flipQuestionsOnClick = new OnClickListener() {
        public void onClick(View view) {
            question.switchRomajiFirst();
            switchQuestionAndAnswers();
        }
    };

    public void switchQuestionAndAnswers() {
        switchQuestion();
        questionAdapter.changeAnswers();
        questionAdapter.notifyDataSetChanged();
    }

    public void guess(View view) {
        Button tempButton = (Button) view;

        if (tempButton.getText().equals(currentAnswer)) {
            numCorrect++;
            updateScore();
            textViewCorrect.setText("Correct!");
            textViewCorrect.setTextColor(Color.WHITE);
        } else {
            numWrong++;
            updateScore();
            textViewCorrect.setText("Incorrect.");
            textViewCorrect.setTextColor(Color.RED);
        }

        textViewCorrectAnswer.setText(currentQuestion + " = " + currentAnswer);

        switchQuestion();
    }

    private String getPercentCorrect() {
        if ((numCorrect + numWrong) == 0) {
            return "0";
        }

        DecimalFormat df = new DecimalFormat("#");
        return df.format((100.0 / (numCorrect + numWrong)) * numCorrect);
    }

    public void updateScore() {
        textViewScore.setText("Score: " + numCorrect + " (" + getPercentCorrect() + "%)");
    }

    public void switchQuestion() {
        question.shuffleQuestions();
        int randNum = random.nextInt(numAnswerChoices);

        currentQuestion = question.getQuestion(randNum);
        currentAnswer = question.getAnswer(randNum);

        question.setCurrentAnswer(randNum);

        textViewRomaji.setText(currentQuestion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
