package application.uicontrollers;

import application.Application;
import application.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.model.User;

import javax.swing.*;
import java.io.IOException;

public class LoginUIController {

    private Application application;

    public TextField txt_name;
    public Button btn_login;
    public Button btn_register;
    public PasswordField txt_password;

    public void initialize() {
        this.application = Application.getInstance();
    }

    @FXML
    public void btnLoginClicked(ActionEvent actionEvent) throws IOException {
        // Get user input from fields
        String name = txt_name.getText();
        String password = txt_password.getText();

        // Create necessary service.
        UserService userService = new UserService();

        // Check if fields have been filled in.
        if (name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username or password is empty");
        } else {
            // Try to login the user with the specified username and password.
            try {
                User user = userService.login(name, password);
                // Set the currentUser to the logged in user.
                Application.currentUser = user;
                JOptionPane.showMessageDialog(null, "Welcome " + user.getName() + "!");

                // After succesful login the homepage UI scene is created and loaded.
                application.openStage("homepage_ui.fxml");

                // the current stage is closed.
                Stage stageToClose = (Stage) btn_login.getScene().getWindow();
                stageToClose.close();

                // Catch incorrect user info errors.
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Username or password incorrect.");
            }

        }
    }

    public void btnRegisterClicked(ActionEvent actionEvent) throws IOException {
        application.openStage("register_ui.fxml");

        Stage stageToClose = (Stage) btn_login.getScene().getWindow();
        stageToClose.close();
    }
}
