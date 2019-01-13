package api.interfaces;

import application.model.Question;

import java.sql.SQLException;
import java.util.List;

public interface IQuestionService {
    /**
     * Gets three questions with specified categoryId and difficulty
     */
    List<Question> getQuestions(int categoryId, String difficulty) throws SQLException, ClassNotFoundException;
    /**
     * Checks if he given answer is correct.
     */
    boolean checkAnswer(int questionId, String answer);
    /**
     * Gets a single question.
     */
    Question getQuestion(int categoryId, String difficulty);

}
