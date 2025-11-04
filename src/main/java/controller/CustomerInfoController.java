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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.CustomerInfoDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerInfoController implements Initializable {
    Stage stage = new Stage();
    private ObservableList<CustomerInfoDTO> customerInfoDTOS = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTelno;

    @FXML
    private TableView<CustomerInfoDTO> tblCustomerInfo;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTelno;

    private CustomerInfoDTO getCurrentCustomer(){
        return new CustomerInfoDTO(txtCusId.getText(),txtName.getText(),txtTelno.getText(),Integer.parseInt(txtAge.getText()),txtCity.getText());
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {
        if(isAdded(getCurrentCustomer())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Customer Added!");
            alert.setContentText("Customer added successfully to the system");
            alert.showAndWait();
        }
        loadTable();
        clear();
    }
    private boolean isAdded(CustomerInfoDTO customer){
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO CUSTOMER VALUES(?,?,?,?,?)");
            statement.setObject(1,customer.getCusId());
            statement.setObject(2,customer.getName());
            statement.setObject(3,customer.getPno());
            statement.setObject(4,customer.getAge());
            statement.setObject(5,customer.getCity());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer NOT Added!");
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
        CustomerInfoDTO selectedCustomer = tblCustomerInfo.getSelectionModel().getSelectedItem();
        if (isDeleted(selectedCustomer.getCusId())){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Customer Deleted!");
            alert.setContentText("Customer deleted successfully");
            alert.showAndWait();
        }
        loadTable();
        clear();
    }
    private boolean isDeleted(String id){
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM CUSTOMER WHERE cus_Id='"+id+"'");
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer NOT Deleted!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        CustomerInfoDTO selectedCustomer = tblCustomerInfo.getSelectionModel().getSelectedItem();
        if (isUpdated(selectedCustomer)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Success!");
            alert.setHeaderText("Customer Updated!");
            alert.setContentText("Customer updated successfully");
            alert.showAndWait();
        }
        loadTable();
        clear();
    }
    private boolean isUpdated(CustomerInfoDTO select){
        CustomerInfoDTO customer = getCurrentCustomer();
        try {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE CUSTOMER SET name=?,pno=?,age=?,city=? WHERE cus_Id=?");
            statement.setObject(5,select.getCusId());
            statement.setObject(1,customer.getName());
            statement.setObject(2,customer.getPno());
            statement.setObject(3,customer.getAge());
            statement.setObject(4,customer.getCity());
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Customer NOT Updated!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        return false;
    }

    public void clear(){
        txtCusId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtTelno.setText("");
        txtCity.setText("");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelno.setCellValueFactory(new PropertyValueFactory<>("pno"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        loadTable();
        tblCustomerInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                txtCusId.setText(newValue.getCusId());
                txtName.setText(newValue.getName());
                txtTelno.setText(newValue.getPno());
                txtAge.setText(String.valueOf(newValue.getAge()));
                txtCity.setText(newValue.getCity());
            }
        });
    }
    private void loadTable(){
        try {
            Statement statement = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from customer");
            while(resultSet.next()){
                customerInfoDTOS.add(new CustomerInfoDTO(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                ));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database error!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        tblCustomerInfo.setItems(customerInfoDTOS);
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
        stage.setTitle("LogIn");
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
