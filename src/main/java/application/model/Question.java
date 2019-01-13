package application.model;

import java.util.ArrayList;

public class Question {
    private int id;
    private int categoryId;
    private String type;
    private String difficulty;
    private String question;
    private ArrayList<Answer> answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public Question() {

    }

    public Question(int categoryId, String type, String difficulty, String question, ArrayList<Answer> answers) {
        this.categoryId = categoryId;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.answers = answers;
    }

    public Question(int id, int categoryId, String type, String difficulty, String question, ArrayList<Answer> answers) {
        this.id = id;
        this.categoryId = categoryId;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.answers = answers;
    }
}
