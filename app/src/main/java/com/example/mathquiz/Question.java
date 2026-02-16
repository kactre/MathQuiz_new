package com.example.mathquiz;

public class Question {
    private final String text;
    private final int correctAnswer;

    public Question(String text, int correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
