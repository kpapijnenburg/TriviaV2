package application.model;

import application.model.Enums.Difficulty;

import java.util.ArrayList;

public class MultiPlayerGame extends Game {

    private String gameName;
    private Player playerA;
    private Player PlayerB;
    private GameState gameState = GameState.NOT_STARTED;
    private Question currentQuestion;
    private static MultiPlayerGame instance = null;

    public String getGameName() {
        if (gameName == null) {
            setGameName();
            return gameName;
        } else {
            return gameName;
        }
    }

    private void setGameName() {
        if (playerA != null) {
            this.gameName = playerA.getName() + "'s game";
        }
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public void setPlayerA(Player playerA) {
        this.playerA = playerA;
    }

    public Player getPlayerB() {
        return PlayerB;
    }

    public void setPlayerB(Player playerB) {
        PlayerB = playerB;
    }

    public static void setInstance(MultiPlayerGame instance){
        MultiPlayerGame.instance = instance;
    }

    public static MultiPlayerGame getInstance() {
        if (instance == null) {
            instance = new MultiPlayerGame();
        }

        return instance;
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public MultiPlayerGame() {

    }

    public MultiPlayerGame(int gameId, Difficulty difficulty, ArrayList<Question> questions, String gameName, Player playerA, Player playerB, GameState gameState) {
        super(gameId, difficulty, questions);
        this.gameName = gameName;
        this.playerA = playerA;
        PlayerB = playerB;
        this.gameState = gameState;
    }

    @Override
    public String toString() {
        if (playerA != null && this.getCategory() != null) {
            try {
                return this.playerA.getName() + "'s game " + " | Difficulty: " + this.getDifficulty() + " | Category: " + this.getCategory().getName();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error with game.";
            }
        }
        else return "";
    }
}
