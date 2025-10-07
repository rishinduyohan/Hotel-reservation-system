package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFormController extends Component {

    public TextField txtUsername;
    public PasswordField txtPassword;
    Stage stage = new Stage();

    public void btnCancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public boolean checkPassword(String username,String password){
        if (username.equals("Admin") && password.equals("1234")) {
            return true;
        }
        return false;
    }
    public void cleanText(){
        txtUsername.setText("");
        txtPassword.setText("");
    }
    public void btnLogInOnAction(ActionEvent actionEvent) {
        boolean isChecked = checkPassword(txtUsername.getText(),txtPassword.getText());
        if(isChecked) {
            JOptionPane.showMessageDialog(this,"Login Succuss!","Conform",JOptionPane.INFORMATION_MESSAGE);
            cleanText();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerDashboard.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Dashboard");
            stage.show();
        }else{
            //Alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid User");
            alert.setContentText("Login Failed!");
            alert.showAndWait();
            cleanText();
        }
    }
}
