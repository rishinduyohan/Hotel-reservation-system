package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.LoginAccDTO;

import java.awt.*;
import java.io.IOException;

public class SignUpController extends Component {
    Stage stage = new Stage();
    @FXML
    private CheckBox checkSelect;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnBackOnAction(ActionEvent event) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml"))));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    public void clear(){
        txtUsername.setText("");
        txtPassword.setText("");
    }
    @FXML
    void btnSignUpAction(ActionEvent event) {
        if (true != checkData()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter username and password!");
            alert.setContentText("SignUp Failed!");
            alert.showAndWait();
        } else {
            LoginAccDTO newAcc = new LoginAccDTO(txtUsername.getText(), Integer.parseInt(txtPassword.getText()));
            boolean isAdded = LoginFormController.isAdded(newAcc);
            if(isAdded) {
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

    @FXML
    void checkPasswordOnAction(ActionEvent event) {
    }

    private boolean checkData() {
        if ("".equals(txtUsername.getText()) && "".equals(txtPassword.getText())) {
            return false;
        }
        return true;
    }

}