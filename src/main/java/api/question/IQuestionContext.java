package api.question;

import application.model.Answer;
import application.model.Question;

import java.util.List;

public interface IQuestionContext {
    List<Question> getQuestions(int categoryId, String difficulty);
    Answer getCorrectAnswer(int questionId);
    Question getQuestion(int categoryId, String difficulty);
}
