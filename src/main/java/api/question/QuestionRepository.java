package api.question;

import application.model.Answer;
import application.model.Question;

import java.sql.SQLException;
import java.util.List;

public class QuestionRepository {
    private IQuestionContext context;

    public QuestionRepository(IQuestionContext context) {
        this.context = context;
    }

    public List<Question> getQuestions(int categoryId, String difficulty) {
        return context.getQuestions(categoryId, difficulty);
    }

    public boolean checkAnswer(int questionId, String answer){
        Answer answerFromDb = context.getCorrectAnswer(questionId);

        return answer.equals(answerFromDb.getAnswer());
    }


    public Question getQuestion(int categoryId, String difficulty){
        return context.getQuestion(categoryId, difficulty);
    }
}
