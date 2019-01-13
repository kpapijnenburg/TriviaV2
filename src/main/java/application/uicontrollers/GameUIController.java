package application.uicontrollers;

import application.Application;
import application.services.GameService;
import application.services.QuestionService;
import application.model.SinglePlayerGame;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import application.model.Enums.Difficulty;
import application.model.Question;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class GameUIController {
    public Label lb_score;
    public Label lb_strikes;
    public Button btn_quit;
    public TextArea txt_question;
    public Button btn_answerA;
    public Button btn_answerB;
    public Button btn_answerC;
    public Button btn_answerD;

    private SinglePlayerGame game;
    private Application application;
    private GameService gameService;
    private QuestionService questionService;

    private ArrayList<Button> buttons;
    private ArrayList<Question> questions;
    private Question currentQuestion;

    public GameUIController() {
        this.application = Application.getInstance();
        this.game = SinglePlayerGame.getInstance();
        this.gameService = new GameService();
        this.questionService = new QuestionService();

        buttons = new ArrayList<>();
        questions = new ArrayList<>();
    }

    public void initialize() throws IOException {
        if (game.getQuestions().size() == 0) {
            questions = (ArrayList<Question>) questionService.getQuestions(game.getCategory().getId(), game.getDifficulty().toString());
        }

        buttons.add(btn_answerA);
        buttons.add(btn_answerB);
        buttons.add(btn_answerC);
        buttons.add(btn_answerD);

        getQuestion();
        updateLabels();

    }

    public void btnQuitClicked(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("Are you sure you want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            gameService.saveSinglePlayer(game.getPlayer().getScore(), game.getPlayer().getPlayerId());
            application.openStage("homepage_ui.fxml");

            Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
            stageToClose.close();
        }
    }

    public void btnAnswerAClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerA.getText());

    }

    public void btnAnswerBClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerB.getText());

    }

    public void btnAnswerCClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerC.getText());

    }

    public void btnAnswerDClicked(ActionEvent actionEvent) throws IOException {
        checkAnswer(btn_answerD.getText());
    }

    private void getQuestion() throws IOException {
        if (questions.size() == 0) {
            application.openStage("category_ui.fxml");

            Stage stageToClose = (Stage) btn_answerA.getScene().getWindow();
            stageToClose.close();

        } else {
            currentQuestion = questions.get(0);
            setButtons();
            setQuestionText(currentQuestion.getQuestion());
            questions.remove(0);
        }
    }

    private void setButtons() {
        resetButtons();
        Collections.shuffle(buttons);

        for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
            buttons.get(i).setText(currentQuestion.getAnswers().get(i).getAnswer());
        }

    }

    private void resetButtons() {
        for (Button button : buttons) {
            button.setText("");
        }
    }

    private void setQuestionText(String text) {
        txt_question.setText(text);
    }


    private void updateLabels() {
        lb_strikes.setText("" + game.getPlayer().getStrikes());
        lb_score.setText("" + game.getPlayer().getScore());
    }


    private void checkAnswer(String answer) throws IOException {
        boolean result = questionService.checkAnswer(currentQuestion.getId(), answer);

        if (result) {
            awardPoints();
        } else {
            awardStrike();
        }

        updateLabels();
        getQuestion();
    }

    private void awardStrike() throws IOException {
        game.getPlayer().setStrikes(1);

        JOptionPane.showMessageDialog(null, "False answer!");

        if (game.getPlayer().getStrikes() >= 3) {
            JOptionPane.showMessageDialog(null, "Game over!");
            gameService.saveSinglePlayer(game.getPlayer().getScore(), game.getPlayer().getPlayerId());

            application.openStage("homepage_ui.fxml");

            Stage stageToClose = (Stage) lb_strikes.getScene().getWindow();
            stageToClose.close();
        }

    }


    private void awardPoints() {
        int points = 0;

        if (game.getDifficulty() == Difficulty.EASY) {
            points = 1;
        }
        if (game.getDifficulty() == Difficulty.MEDIUM) {
            points = 2;
        }
        if (game.getDifficulty() == Difficulty.HARD) {
            points = 3;
        }

        game.getPlayer().setScore(points);

        JOptionPane.showMessageDialog(null, "Correct answer!");

    }
}



