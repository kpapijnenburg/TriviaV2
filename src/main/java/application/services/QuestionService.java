package application.services;

import api.interfaces.IQuestionService;
import com.google.gson.*;
import org.apache.commons.lang.StringEscapeUtils;
import application.model.Answer;
import application.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class QuestionService implements IQuestionService {

    private String baseUrl = "http://localhost:8090/question";
    private Gson jsonConverter = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public QuestionService() {

    }

    public List<Question> getQuestions(int categoryId, String difficulty) {
        try {

            URL url = new URL(baseUrl + "s?categoryId=" + categoryId + "&difficulty=" + difficulty);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            JsonArray jsonArray = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), JsonArray.class);

            List<Question> questions = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                questions.add(jsonConverter.fromJson(element, Question.class));
            }

            for (Question question : questions) {
                for (Answer answer : question.getAnswers()) {
                    StringEscapeUtils.unescapeHtml(answer.getAnswer());
                }
            }

            return questions;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public boolean checkAnswer(int questionId, String answer)  {
        try {
            String paramValue = "/check?questionId=" + questionId + "&answer=" + URLEncoder.encode(answer, "UTF-8");
            String urlString = baseUrl + paramValue;

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), boolean.class);
        } catch (IOException e ){
            e.printStackTrace();
            return false;
        }

    }


    public Question getQuestion(int categoryId, String difficulty)  {
        try {
            URL url = new URL(baseUrl + "?categoryId=" + categoryId + "&difficulty=" + difficulty);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Question question = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), Question.class);

            for (Answer answer : question.getAnswers()) {
                StringEscapeUtils.unescapeHtml(answer.getAnswer());
            }

            return question;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

}
