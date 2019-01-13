package application.model;

import application.model.Enums.Difficulty;

import java.util.ArrayList;

public class SinglePlayerGame extends Game {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private static SinglePlayerGame instance = null;

    public static SinglePlayerGame getInstance() {
        if (instance == null) {
            instance = new SinglePlayerGame();
        }

        return instance;
    }

    private SinglePlayerGame() {

    }

    public SinglePlayerGame(int id, Difficulty difficulty, ArrayList<Question> questions, Player player) {
        super(id, difficulty, questions);
        this.player = player;
    }


}
