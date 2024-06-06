package org.app.controllers;

import org.app.Load;
import org.app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.app.validation.Alerts;

public class LoginController {

    @FXML
    public BorderPane loginPane;
    @FXML
    public TextField txtUsername, txtPassword;
    public final User admin = new User("Admin","1234");
    public Button loginButton;

    @FXML
    public void loggInn(ActionEvent event) {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        if (isValidUser()) {
            Load.window("adminView.fxml", "Admin", stage);
        } else {
            Alerts.warning("Invalid username or password!\n" +
                    "Username: Admin\n" +
                    "Password: 1234");
        }
    }

    public boolean isValidUser() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean usernameMatches = username.equals(admin.getUserName());
        boolean passwordMatches = password.equals(admin.getPassword());
        return passwordMatches && usernameMatches;
    }
}
