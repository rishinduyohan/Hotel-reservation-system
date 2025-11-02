package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.dto.LoginAccDTO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController extends Component implements Initializable {

    public TextField txtUsername;
    public PasswordField txtPassword;
    public AnchorPane signUpPane;
    public AnchorPane loginPane;
    public AnchorPane loginPagePane;
    public AnchorPane signUpPagePane;
    public CheckBox checkSelect;
    public PasswordField txtPassword1;
    public TextField txtUsername1;
    Stage stage = new Stage();


    static ObservableList<LoginAccDTO> loginAccDTOS = FXCollections.observableArrayList(
            new LoginAccDTO("Admin", 1234),
            new LoginAccDTO("User", 1234),
            new LoginAccDTO("Rishindu", 2005)
    );

    public void btnSignInOnAction(ActionEvent actionEvent) {
        signUpPane.setVisible(true);
        signUpPagePane.setVisible(true);
    }

    public boolean checkPassword(String username, String password) {
        for (LoginAccDTO acc : loginAccDTOS) {
            if (acc.getUsername().equals(username) && acc.getPassword() == Integer.parseInt(password)) {
                return true;
            }
        }
        return false;
    }

    public void cleanText() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        boolean isChecked = checkPassword(txtUsername.getText(), txtPassword.getText());
        if (isChecked) {
            DashboardController.temp = txtUsername.getText();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Dashboard");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid User");
            alert.setContentText("Login Failed!");
            alert.showAndWait();
            cleanText();
        }
    }

    public void checkPasswordOnAction(ActionEvent actionEvent) {
        btnLogInOnAction(actionEvent);
    }

    public boolean isAdded(LoginAccDTO user) {
        loginAccDTOS.add(user);
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpPane.setVisible(false);
        signUpPagePane.setVisible(false);
    }

    @FXML
    void btnSignUpAction(ActionEvent event) {
        if (!checkData()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter username and password!");
            alert.setContentText("SignUp Failed!");
            alert.showAndWait();
        } else {
            LoginAccDTO newAcc = new LoginAccDTO(txtUsername1.getText(), Integer.parseInt(txtPassword1.getText()));
            if(isAdded(newAcc)) {
                clear();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("success");
                alert.setHeaderText("Username and Password added to the system!");
                alert.setContentText("SignUp Success!");
                alert.showAndWait();
                btnBackOnAction(event);
            }
        }
    }
    public void clear(){
        txtUsername1.setText("");
        txtPassword1.setText("");
    }
    private boolean checkData() {
        if ("".equals(txtUsername1.getText()) && "".equals(txtPassword1.getText())) {
            return false;
        }
        return true;
    }
    public void btnBackOnAction(ActionEvent actionEvent) {
        signUpPane.setVisible(false);
        signUpPagePane.setVisible(false);
    }
}
