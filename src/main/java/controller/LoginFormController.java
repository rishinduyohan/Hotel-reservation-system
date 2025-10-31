package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.LoginAccDTO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFormController extends Component {

    public TextField txtUsername;
    public PasswordField txtPassword;
    Stage stage = new Stage();

    static ObservableList<LoginAccDTO> loginAccDTOS = FXCollections.observableArrayList(
            new LoginAccDTO("Admin", 1234),
            new LoginAccDTO("User", 1234),
            new LoginAccDTO("Rishindu", 2005)
    );

    public void btnSignInOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SignUp_form.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
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
            //Alert box
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

    public static boolean isAdded(LoginAccDTO user) {
        loginAccDTOS.add(user);
        return true;
    }
}
