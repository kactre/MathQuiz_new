package com.example.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private static final int TOTAL_QUESTIONS = 10;
    private static final int TIME_PER_QUESTION_SEC = 10;

    private TextView tvHeader, tvTimer, tvQuestion;
    private EditText etAnswer;
    private Button btnCheck;
    private ProgressBar pbQuestions;

    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;
    private int streak = 0;
    private int maxStreak = 0;

    private CountDownTimer timer;
    private int timeLeft = TIME_PER_QUESTION_SEC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvHeader = findViewById(R.id.tvHeader);
        tvTimer = findViewById(R.id.tvTimer);
        tvQuestion = findViewById(R.id.tvQuestion);
        etAnswer = findViewById(R.id.etAnswer);
        btnCheck = findViewById(R.id.btnCheck);
        pbQuestions = findViewById(R.id.pbQuestions);

        String difficultyStr = getIntent().getStringExtra("difficulty");
        QuestionGenerator.Difficulty difficulty = QuestionGenerator.Difficulty.EASY;
        if ("MEDIUM".equals(difficultyStr)) difficulty = QuestionGenerator.Difficulty.MEDIUM;
        else if ("HARD".equals(difficultyStr)) difficulty = QuestionGenerator.Difficulty.HARD;

        questions = QuestionGenerator.generate(TOTAL_QUESTIONS, difficulty);

        btnCheck.setOnClickListener(v -> submitAnswer());
        showQuestion();
    }

    private void showQuestion() {
        if (currentIndex >= TOTAL_QUESTIONS) {
            finishQuiz();
            return;
        }

        Question q = questions.get(currentIndex);
        tvQuestion.setText(q.getText());
        tvHeader.setText("Pytanie " + (currentIndex + 1) + "/" + TOTAL_QUESTIONS +
                " | Punkty: " + score + " | Seria: " + streak);
        pbQuestions.setProgress(currentIndex + 1);
        etAnswer.setText("");

        startTimer();
    }

    private void startTimer() {
        if (timer != null) timer.cancel();

        timeLeft = TIME_PER_QUESTION_SEC;
        tvTimer.setText("Czas: " + timeLeft);

        timer = new CountDownTimer(TIME_PER_QUESTION_SEC * 1000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000L);
                tvTimer.setText("Czas: " + timeLeft);
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizActivity.this, "Czas minął", Toast.LENGTH_SHORT).show();
                streak = 0;
                currentIndex++;
                showQuestion();
            }
        };
        timer.start();
    }

    private void submitAnswer() {
        if (timer != null) timer.cancel();

        String input = etAnswer.getText().toString().trim();
        if (input.isEmpty()) {
            Toast.makeText(this, "Wpisz odpowiedź", Toast.LENGTH_SHORT).show();
            startTimer();
            return;
        }

        int userAnswer;
        try {
            userAnswer = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Niepoprawna liczba", Toast.LENGTH_SHORT).show();
            startTimer();
            return;
        }

        int correct = questions.get(currentIndex).getCorrectAnswer();

        if (userAnswer == correct) {
            score++;
            streak++;
            if (streak > maxStreak) maxStreak = streak;
            Toast.makeText(this, "Dobrze", Toast.LENGTH_SHORT).show();
        } else {
            streak = 0;
            Toast.makeText(this, "Źle. Poprawna: " + correct, Toast.LENGTH_SHORT).show();
        }

        currentIndex++;
        showQuestion();
    }

    private void finishQuiz() {
        Intent i = new Intent(QuizActivity.this, ResultActivity.class);
        i.putExtra("score", score);
        i.putExtra("total", TOTAL_QUESTIONS);
        i.putExtra("maxStreak", maxStreak);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) timer.cancel();
        super.onDestroy();
    }
}
