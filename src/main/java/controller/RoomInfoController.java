package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.RoomInfoDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RoomInfoController implements Initializable {
    Stage stage = new Stage();
    private ObservableList<RoomInfoDTO> roomInfoDTOS = FXCollections.observableArrayList();

    @FXML
    private Button btnReload;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colRoomId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<RoomInfoDTO> tblRoomInfo;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtRoomId;

    @FXML
    private TextField txtType;

    private RoomInfoDTO getCurrentCus(){
        return new RoomInfoDTO(txtRoomId.getText(), txtType.getText(), txtDescription.getText(), txtPrice.getText());
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(isAdded(getCurrentCus())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Room Added!");
            alert.setContentText("New room added to the system!");
            alert.showAndWait();
        }
        loadTable();
        clear();
    }
    public boolean isAdded(RoomInfoDTO room){
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO ROOMS VALUES(?,?,?,?)");
            statement.setObject(1,room.getRoomID());
            statement.setObject(2,room.getType());
            statement.setObject(3,room.getDescription());
            statement.setObject(4,room.getPrice());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rooms NOT Added!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        RoomInfoDTO selectItem = tblRoomInfo.getSelectionModel().getSelectedItem();
        if (isDeleted(selectItem.getRoomID())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Room Deleted!");
            alert.setContentText("Your selected room deleted successfully!");
            alert.showAndWait();
        }
        clear();
        loadTable();
    }
    private boolean isDeleted(String id){
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM ROOMS WHERE room_id='"+id+"'");
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rooms NOT Deleted!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        RoomInfoDTO selectItem = tblRoomInfo.getSelectionModel().getSelectedItem();
        if (isUpdated(selectItem.getRoomID())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Room Updated!");
            alert.setContentText("Room details updated successfully!");
            alert.showAndWait();
        }
        clear();
        loadTable();
    }
    private boolean isUpdated(String id){
        RoomInfoDTO current = getCurrentCus();
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE ROOMS SET type=?,description=?,price=? WHERE room_id='"+id+"'");
            statement.setObject(1,current.getType());
            statement.setObject(2,current.getDescription());
            statement.setObject(3,current.getPrice());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Rooms NOT Updated!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }
    public void clear() {
        txtRoomId.setText("");
        txtType.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadTable();
        tblRoomInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtRoomId.setText(newValue.getRoomID());
                txtType.setText(newValue.getType());
                txtDescription.setText(newValue.getDescription());
                txtPrice.setText(newValue.getPrice());
            }
        });
    }

    private void loadTable() {
        roomInfoDTOS.clear();
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = statement.executeQuery("SELECT * FROM rooms");
            while (rst.next()) {
                roomInfoDTOS.add(new RoomInfoDTO(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4)
                ));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        tblRoomInfo.setItems(roomInfoDTOS);
    }

    public void btnCustomerInfoOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Customer_info.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Customer");
        stage.show();
    }

    public void btnStaffInfoOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Staff_info.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Staff Info");
        stage.show();
    }

    public void btnUserDetailsOnAction(ActionEvent actionEvent) {
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

    public void btnDashboardOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Dashboard");
        stage.show();
    }
}
