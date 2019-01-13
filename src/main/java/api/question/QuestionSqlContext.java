package api.question;

import api.User.UserSqlContext;
import application.model.Answer;
import application.model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuestionSqlContext implements IQuestionContext {

    private String dbPassword = "wachtwoord";

    private static final Logger LOGGER = Logger.getLogger(QuestionSqlContext.class.getName());


    @Override
    public List<Question> getQuestions(int categoryId, String difficulty) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT TOP 3 * FROM Trivia.Question WHERE Trivia.Question.Categoryid = ? AND Trivia.Question.difficulty = ? ORDER BY NEWID()"
            );

            statement.setInt(1, categoryId);
            statement.setString(2, difficulty);

            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                questions.add(new Question(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        connectAnswers(result.getInt(1))

                ));
            }

            //Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }

//        connectAnswers(questions);

        return questions;
    }

    private ArrayList<Answer> connectAnswers(int id) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);

        ArrayList<Answer> answers = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Trivia.Answer WHERE Trivia.Answer.Questionid = ?"
            );

            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                answers.add(new Answer(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getBoolean("isCorrect")
                ));
            }

            Collections.shuffle(answers);

            //Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }

        return answers;
    }


    @Override
    public Answer getCorrectAnswer(int questionId) {
        Answer answer = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Trivia.Answer WHERE Trivia.Answer.Questionid = ? AND Trivia.Answer.IsCorrect = 1"
            );

            statement.setInt(1, questionId);

            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                answer = new Answer(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getBoolean(4)
                );
            }

            //Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }

        return answer;
    }

    @Override
    public Question getQuestion(int categoryId, String difficulty) {
        Question question = null;

        try {
            //Set up connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection("jdbc:sqlserver://mssql.fhict.local;database=dbi388613", "dbi388613", dbPassword);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT TOP 1 * FROM Trivia.Question WHERE Trivia.Question.Categoryid = ? AND Trivia.Question.difficulty = ? ORDER BY NEWID()"
            );

            statement.setInt(1, categoryId);
            statement.setString(2, difficulty);

            ResultSet result = statement.executeQuery();

            // Add items to list
            while (result.next()) {
                question = new Question(
                        result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        connectAnswers(result.getInt(1))

                );
            }

            //Close connections
            result.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.FINEST, e.toString());
        }


        return question;
    }
}





