package question;

import api.question.QuestionRepository;
import org.junit.Assert;
import org.junit.Test;
import application.model.Enums.Difficulty;
import application.model.Question;

import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionRepositoryTests {
    ArrayList<Question> questions;
    QuestionRepository repository = new QuestionRepository(new question.QuestionTestContext(1, Difficulty.EASY));

    @Test
    public void getQuestions_ShouldReturnThreeQuestions_WhenInvoked() throws SQLException, ClassNotFoundException {
        ArrayList<Question> questions = (ArrayList<Question>) repository.getQuestions(1, "easy");

        Assert.assertEquals(3, questions.size());
    }

    @Test
    public void checkAnswer_ShouldReturnTrue_WhenGivenCorrectAnswer(){
        String answer = "testTrue";
        boolean result;

        result = repository.checkAnswer(1, answer);

        Assert.assertTrue(result);

    }

    @Test
    public void checkAnswer_ShouldReturnTrue_WhenGivenIncorrectAnswer(){
        String answer = "testFalse";
        boolean result;

        result = repository.checkAnswer(1, answer);

        Assert.assertFalse(result);
    }

}

//    @Test
//    public void Login_ShouldLoginUser_WhenUsingStandardUser() {
//        Exception e = null;
//
//        try {
//            userRepository.login(user.getName(), user.getPassword());
//        } catch (Exception ex) {
//            e = ex;
//        }
//
//        Assert.assertNull(e);
//
//    }
