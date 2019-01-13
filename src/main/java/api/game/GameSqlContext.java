package api.game;

import api.User.UserSqlContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameSqlContext implements IGameContext {

    private static final Logger LOGGER = Logger.getLogger(GameSqlContext.class.getName());

    private String dbPassword = "wachtwoord";

    @Override
    public void saveSinglePlayerGame(int score, int userId) {
        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Trivia.SingleplayerGame" +
                            " VALUES (?, ?)"
            );

            statement.setInt(1, score);
            statement.setInt(2, userId);

            statement.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }

    }

    @Override
    public void saveMultiPlayerGame(int playerAId, int playerBId, int playerAScore, int playerBScore, int winnerId) {
        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Trivia.MultiplayerGame" +
                            " VALUES (?, ?,?,?,?)"
            );

            statement.setInt(1, playerAId);
            statement.setInt(2, playerBId);
            statement.setInt(3, playerAScore);
            statement.setInt(4, playerBScore);
            statement.setInt(5, winnerId);

            statement.executeUpdate();


        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }
    }
}
