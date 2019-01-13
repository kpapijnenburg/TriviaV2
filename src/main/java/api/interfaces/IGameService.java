package api.interfaces;

import application.model.MultiPlayerGame;

import java.io.IOException;

public interface IGameService {
    /**
     * Saves a single player game
     * @Param userId
     */
    void saveSinglePlayer(int userId, int score) throws IOException;
    /**
     * Saves a multiplayer game
     */
    void saveMultiPlayer(int playerAId, int playerBId, int playerAScore, int playerBScore, int winnerId);
}
