package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import application.model.User;

import java.io.IOException;
import java.util.Objects;


public class Application extends javafx.application.Application {

    public static User currentUser;

    private static Application instance = null;

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }

        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        getInstance();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("login_ui.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to Trivia!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void openStage(String fxmlResource) throws IOException {
        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/"+fxmlResource));
        root = loader.load();

        Stage stage = new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
