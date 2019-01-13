package application.model;

public enum GameState {
    NOT_STARTED ("Waiting for player...") ,
    FINISHED ("Game over"),
    PLAYER_A_TURN ("Player A's turn"),
    PLAYER_B_TURN ("Player B's turn");

    private final String name;

    GameState(String s){
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
