package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtuserName.setText(temp+" user");
        txtStaffmembers.setText(StaffController.countStaff()+"");
        txtCustomers.setText(CustomerInfoController.countCustomers()+"");
        txtRooms.setText(RoomInfoController.countRooms()+"");

    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
    }
}
