package api.game;

public interface IGameContext {
    void saveSinglePlayerGame(int score, int userId);
    void saveMultiPlayerGame(int playerAId, int playerBId, int playerAScore, int playerBScore, int winnerId);
}
