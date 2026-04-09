package edu.moravian;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String categoryName;
    private List<String> questions;
    private List<Integer> values;
    private List<String> answers;
    private List<Boolean> answered;

    public Category() {
        this.questions = new ArrayList<>(4);
        this.answers = new ArrayList<>(4);
        this.answered = new ArrayList<>(4);
        this.values = new ArrayList<>(4);
    }

    public Category(String categoryName) {
        this();
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getQuestion(int index) {
        return questions.get(index);
    }

    public int getValue(int index) {
        return values.get(index);
    }

    public String getAnswer(int index) {
        return answers.get(index);
    }

    public boolean isAnswered(int index) {
        return answered.get(index);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setQuestion(int index, String question) {
        questions.set(index, question);
    }

    public void setValue(int index, int value) {
        values.set(index, value);
    }

    public void setAnswer(int index, String answer) {
        answers.set(index, answer);
    }

    public void markAnswered(int index) {
        answered.set(index, true);
    }

    public void addQuestionSet(String question, int value, String answer) {
        questions.add(question);
        values.add(value);
        answers.add(answer);
        answered.add(false);
    }

    public int getSize() {
        return questions.size();
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
