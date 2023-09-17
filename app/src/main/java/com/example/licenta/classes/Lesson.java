package com.example.licenta.classes;

import java.util.List;

public class Lesson {
    private String level;
    private List<Quiz> quiz;
    private int noLesson;
    private int lessonId;

    public Lesson() {

    }

    public Lesson(String level, List<Quiz> quiz, int noLesson, int lessonId) {
        this.level = level;
        this.quiz = quiz;
        this.noLesson = noLesson;
        this.lessonId = lessonId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<Quiz> quiz) {
        this.quiz = quiz;
    }

    public int getNoLesson() {
        return noLesson;
    }

    public void setNoLesson(int noLesson) {
        this.noLesson = noLesson;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "level='" + level + '\'' +
                ", quiz=" + quiz +
                '}';
    }
}
