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
import model.dto.CustomerInfoDTO;
import model.dto.StaffInfoDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StaffController implements Initializable {
    Stage stage = new Stage();
    static ObservableList<StaffInfoDTO> staffInfoDTOS = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colReception;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTelno;

    @FXML
    private ComboBox<String> comboRole;

    @FXML
    private TableColumn<?, ?> staffId;

    @FXML
    private TableView<StaffInfoDTO> tblStaffInfo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtStaffId;

    @FXML
    private TextField txtTelno;

    private StaffInfoDTO getCurrentStaff(){
        return new StaffInfoDTO(txtStaffId.getText(), txtName.getText(), txtTelno.getText(), txtEmail.getText(), comboRole.getValue(), Double.parseDouble(txtSalary.getText()));

    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (isAdded(getCurrentStaff())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Staff member added!");
            alert.setContentText("New staff member successfully added!");
            alert.showAndWait();
        }
        loadTable();
        clear();
    }
    private boolean isAdded(StaffInfoDTO staff){
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO STAFF VALUES(?,?,?,?,?,?)");
            statement.setObject(1,staff.getStaffId());
            statement.setObject(2,staff.getName());
            statement.setObject(3,staff.getTelNo());
            statement.setObject(4,staff.getEmail());
            statement.setObject(5,staff.getRole());
            statement.setObject(6,staff.getSalary());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Staff member NOT added!");
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
        StaffInfoDTO selectedCustomer = tblStaffInfo.getSelectionModel().getSelectedItem();
        if (isDeleted(selectedCustomer.getStaffId())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Staff member deleted!");
            alert.setContentText("Staff member successfully deleted from the system");
            alert.showAndWait();
        }
        clear();
        loadTable();
    }
    public boolean isDeleted(String id){
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM STAFF WHERE staffId='"+id+"'");
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Staff member NOT deleted!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        StaffInfoDTO selectedMember = tblStaffInfo.getSelectionModel().getSelectedItem();
        if (isUpdated(selectedMember.getStaffId())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Staff member Updated!");
            alert.setContentText("Staff member successfully updated to the system.");
            alert.showAndWait();
        }
        clear();
        loadTable();
    }
    public boolean isUpdated(String id){
        StaffInfoDTO staff = getCurrentStaff();
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE STAFF SET name=?,telNo=?,email=?,role=?,salary=? WHERE staffId='"+id+"'");
            statement.setObject(1,staff.getName());
            statement.setObject(2,staff.getTelNo());
            statement.setObject(3,staff.getEmail());
            statement.setObject(4,staff.getRole());
            statement.setObject(5,staff.getSalary());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Staff member NOT Updated!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }
    public void clear() {
        txtStaffId.setText("");
        txtName.setText("");
        txtTelno.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelno.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colReception.setCellValueFactory(new PropertyValueFactory<>("role"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        loadTable();
        tblStaffInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtStaffId.setText(newValue.getStaffId());
                txtName.setText(newValue.getName());
                txtTelno.setText(newValue.getTelNo());
                txtEmail.setText(newValue.getEmail());
                comboRole.setValue(newValue.getRole());
                txtSalary.setText(String.valueOf(newValue.getSalary()));
            }
        });
    }

    private void loadTable() {
        staffInfoDTOS.clear();
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = statement.executeQuery("SELECT * FROM STAFF");
            while (rst.next()) {
                staffInfoDTOS.add(new StaffInfoDTO(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getDouble(6)
                ));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        tblStaffInfo.setItems(staffInfoDTOS);

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
    public void btnRoomInfoOnAction(ActionEvent actionEvent) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Room_info.fxml"))));
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Room Info");
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
