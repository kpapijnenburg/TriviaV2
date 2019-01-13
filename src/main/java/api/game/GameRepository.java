package api.game;

public class GameRepository {
    private final IGameContext context;

    public GameRepository(IGameContext context) {
        this.context = context;
    }

    public void saveSinglePlayerGame(int score, int userId) {
        context.saveSinglePlayerGame(score, userId);
    }

    public void saveMultiPlayerGame(int playerAId, int playerBId, int playerAscore, int playerBScore, int winnerId) {
        context.saveMultiPlayerGame(playerAId, playerBId, playerAscore, playerBScore,winnerId);
    }
}
