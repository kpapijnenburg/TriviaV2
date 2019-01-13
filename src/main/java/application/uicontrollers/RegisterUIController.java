package application.uicontrollers;

import api.exceptions.NonUniqueUsernameException;
import application.Application;
import application.services.UserService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.model.User;

import javax.swing.*;
import java.io.IOException;

public class RegisterUIController {
    public TextField txt_name;
    public PasswordField txt_password;
    public Button btn_register;
    public Button btn_back;

    private Application application;

    public void initialize(){
        this.application = Application.getInstance();
    }

    public void btnRegisterClicked(ActionEvent actionEvent) {
        String name = txt_name.getText();
        String password = txt_password.getText();

        User user = new User(name, password);

        UserService userService = new UserService();

        try {
            userService.register(user.getName(), user.getPassword());
            JOptionPane.showMessageDialog(null, "User successfully registered.");

            application.openStage("login_ui.fxml");

            Stage stageToClose = (Stage) btn_back.getScene().getWindow();
            stageToClose.close();

        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                NonUniqueUsernameException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void btnBackClicked(ActionEvent actionEvent) throws IOException {
        application.openStage("login_ui.fxml");

        Stage stageToClose = (Stage) btn_back.getScene().getWindow();
        stageToClose.close();
    }
}
