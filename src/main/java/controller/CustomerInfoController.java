package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.CustomerInfoDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerInfoController implements Initializable {

    ObservableList<CustomerInfoDTO> customerInfoDTOS = FXCollections.observableArrayList(
            new CustomerInfoDTO("C001","Kamal","0716584218",25,"Kaluthara"),
            new CustomerInfoDTO("C002","Amal","0715151218",60,"Mathara"),
            new CustomerInfoDTO("C003","Hamal","0726894658",35,"Kegalle"),
            new CustomerInfoDTO("C004","Shamal","0714568945",70,"Kandy")
    );
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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        CustomerInfoDTO newCustomer = new CustomerInfoDTO(txtCusId.getText(),txtName.getText(),txtTelno.getText(),Integer.parseInt(txtAge.getText()),txtCity.getText());
        customerInfoDTOS.add(newCustomer);
        tblCustomerInfo.refresh();
        clear();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        CustomerInfoDTO selectedCustomer = tblCustomerInfo.getSelectionModel().getSelectedItem();
        customerInfoDTOS.remove(selectedCustomer);
        tblCustomerInfo.refresh();
        clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        CustomerInfoDTO selectedCustomer = tblCustomerInfo.getSelectionModel().getSelectedItem();
        selectedCustomer.setCusId(txtCusId.getText());
        selectedCustomer.setName(txtName.getText());
        selectedCustomer.setPno(txtTelno.getText());
        selectedCustomer.setAge(Integer.parseInt(txtAge.getText()));
        selectedCustomer.setCity(txtCity.getText());
        tblCustomerInfo.refresh();
        clear();
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

        tblCustomerInfo.setItems(customerInfoDTOS);

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
}
