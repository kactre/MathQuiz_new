package com.example.mathquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResultScore, tvResultDetails, tvBest;
    private Button btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResultScore = findViewById(R.id.tvResultScore);
        tvResultDetails = findViewById(R.id.tvResultDetails);
        tvBest = findViewById(R.id.tvBest);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 10);
        int maxStreak = getIntent().getIntExtra("maxStreak", 0);

        tvResultScore.setText(score + "/" + total);

        String grade;
        if (score <= 3) grade = "Ćwicz dalej 🙂";
        else if (score <= 7) grade = "Dobry wynik 👍";
        else grade = "Świetnie! 🔥";


        tvResultDetails.setText("Najdłuższa seria: " + maxStreak + "\nOcena: " + grade);

        SharedPreferences prefs = getSharedPreferences("math_trainer_prefs", MODE_PRIVATE);
        int best = prefs.getInt("best_score", 0);
        if (score > best) {
            best = score;
            prefs.edit().putInt("best_score", best).apply();
        }
        tvBest.setText("Rekord: " + best);


        btnPlayAgain.setOnClickListener(v -> {
            Intent i = new Intent(ResultActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }
}
