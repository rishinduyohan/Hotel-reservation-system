package controller;

import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.dto.CustomerInfoDTO;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class DashboardController extends Component implements Initializable {
    public Text txtuserName;
    protected static String temp;
    public Text txtStaffmembers;
    public Text txtCustomers;
    public Text txtRooms;
    Stage stage = new Stage();

    public void btnRoomInfoOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Room_info.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Room Service");
        stage.show();
    }
    public void btnCustomerInfoOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Customer_info.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Customer Service");
        stage.show();
    }

    public void btnUserDetailsOnAction(ActionEvent actionEvent) {
    }

    public void btnStaffInfoOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Staff_info.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Staff Service");
        stage.show();
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Log In");
        stage.show();
    }
    private int getCount(String SQL){
        int count=0;
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                count++;
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtuserName.setText(temp+" user");
        txtStaffmembers.setText(getCount("Select * from staff")+"");
        txtCustomers.setText(getCount("Select * from customer")+"");
        txtRooms.setText(getCount("select * from rooms")+"");

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }
}
