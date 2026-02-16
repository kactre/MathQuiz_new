package com.example.mathquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionGenerator {

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public static List<Question> generate(int count, Difficulty difficulty) {
        List<Question> questions = new ArrayList<>();
        Random random = new Random();

        int min = 1, max = 10;
        switch (difficulty) {
            case EASY:
                min = 1; max = 10;
                break;
            case MEDIUM:
                min = 5; max = 30;
                break;
            case HARD:
                min = 10; max = 80;
                break;
        }

        for (int i = 0; i < count; i++) {
            int a = random.nextInt(max - min + 1) + min;
            int b = random.nextInt(max - min + 1) + min;

            int op = random.nextInt(3); // 0:+, 1:-, 2:*
            String text;
            int answer;

            if (op == 0) {
                text = a + " + " + b + " = ?";
                answer = a + b;
            } else if (op == 1) {
                if (difficulty != Difficulty.HARD && a < b) {
                    int tmp = a; a = b; b = tmp;
                }
                text = a + " - " + b + " = ?";
                answer = a - b;
            } else {
                if (difficulty == Difficulty.EASY) {
                    a = random.nextInt(9) + 1;
                    b = random.nextInt(9) + 1;
                }
                text = a + " × " + b + " = ?";
                answer = a * b;
            }

            questions.add(new Question(text, answer));
        }

        return questions;
    }
}
