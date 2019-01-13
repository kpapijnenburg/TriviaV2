package application.model;


public class Player extends User {
    private int playerId;
    private int strikes;
    private int score;

    //region Getter/Setter


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playeId) {
        this.playerId = playeId;
    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes += strikes;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }
    //endregion


    public Player() {

    }

    public Player(int id, String name) {
        super(id, name);
        this.playerId = id;
        this.strikes = 0;
        this.score = 0;
    }

    public Player(int playerId, int strikes, int score) {
        this.playerId = playerId;
        this.strikes = strikes;
        this.score = score;
    }

    public Player(String name,  int strikes, int score) {
        super(name);
        this.playerId = super.getId();
        this.strikes = strikes;
        this.score = score;
    }
}
