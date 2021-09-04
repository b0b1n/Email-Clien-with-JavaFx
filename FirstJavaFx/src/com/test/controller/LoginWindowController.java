package com.test.controller;

import com.test.EmailManager;
import com.test.controller.services.LoginService;
import com.test.model.EmailAccount;
import com.test.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {


    @FXML
    private Button LoginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button exitButton;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName, TextField emailAddressField, PasswordField passwordField) {
        super(emailManager, viewFactory, fxmlName);
        this.emailAddressField = emailAddressField;
        this.passwordField = passwordField;
    }

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void exitActionButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @FXML
    void loginActionButton() {
        if (fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {

                EmailLoginResult emailLoginResult = loginService.getValue();
                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login succesfull!!!" + emailAccount);
                        Stage stage = (Stage) exitButton.getScene().getWindow();
                        viewFactory.closeStage(stage);
                        if(!viewFactory.isMainViewInitialized()) {
                            viewFactory.showMainWindow();
                        }
                        return;
                    case FAIL_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials!!!");
                        return;
                    case FAIL_BY_UNEXPECTED_ERROR:
                        errorLabel.setText("unexpected error!!!");
                        return;

                }
            });
        }


    }

    private boolean fieldsAreValid() {
        if (emailAddressField.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }

        return true;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAddressField.setText("Please Enter an email ");
        passwordField.setText("");
    }
}
