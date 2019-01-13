package application.model;


import application.model.Enums.Difficulty;

import java.util.ArrayList;

public class Game {

    private int gameId;
    private Difficulty difficulty;
    private ArrayList<Question> questions;
    private Category category;
    //region Getter/Setter


    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public static Game getInstance() {
        return instance;
    }

    public static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //endregion

    private static Game instance = null;

    Game() {
        questions = new ArrayList<>();
    }

    Game(int gameId, Difficulty difficulty, ArrayList<Question> questions) {
        this.gameId = gameId;
        this.difficulty = difficulty;
        this.questions = questions;
    }

}
