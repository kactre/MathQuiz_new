package com.example.mathquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgDifficulty;
    private RadioButton rbEasy, rbMedium, rbHard;
    private TextView tvBestMain;
    private Button btnStart;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgDifficulty = findViewById(R.id.rgDifficulty);
        rbEasy = findViewById(R.id.rbEasy);
        rbMedium = findViewById(R.id.rbMedium);
        rbHard = findViewById(R.id.rbHard);
        tvBestMain = findViewById(R.id.tvBestMain);
        btnStart = findViewById(R.id.btnStart);

        prefs = getSharedPreferences("math_trainer_prefs", MODE_PRIVATE);
        int best = prefs.getInt("best_score", 0);
        tvBestMain.setText("Najlepszy wynik: " + best);

        btnStart.setOnClickListener(v -> {
            String difficulty = "EASY";
            if (rbMedium.isChecked()) difficulty = "MEDIUM";
            else if (rbHard.isChecked()) difficulty = "HARD";

            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        });
    }
}
