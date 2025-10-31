package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.CustomerInfoDTO;
import model.dto.StaffInfoDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    static ObservableList<StaffInfoDTO> staffInfoDTOS = FXCollections.observableArrayList(
            new StaffInfoDTO("S0001","Arunika","0716112728","arunika@gmail.com","manager",87000.00),
            new StaffInfoDTO("S0002", "Nuwan", "0771234567", "nuwan@hotel.com", "receptionist", 55000.00),
            new StaffInfoDTO("S0003", "Dilani", "0759876543", "dilani@hotel.com", "housekeeping", 48000.00),
            new StaffInfoDTO("S0004", "Kasun", "0723344556", "kasun@hotel.com", "chef", 75000.00),
            new StaffInfoDTO("S0005", "Tharushi", "0761122334", "tharushi@hotel.com", "accountant", 68000.00),
            new StaffInfoDTO("S0006", "Ravindu", "0789988776", "ravindu@hotel.com", "security", 42000.00)
    );
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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        StaffInfoDTO newMember = new StaffInfoDTO(txtStaffId.getText(),txtName.getText(),txtTelno.getText(),txtEmail.getText(),comboRole.getValue(),Double.parseDouble(txtSalary.getText()));
        staffInfoDTOS.add(newMember);
        tblStaffInfo.refresh();
        clear();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        StaffInfoDTO selectedCustomer = tblStaffInfo.getSelectionModel().getSelectedItem();
        staffInfoDTOS.remove(selectedCustomer);
        tblStaffInfo.refresh();
        clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        StaffInfoDTO selectedMember = tblStaffInfo.getSelectionModel().getSelectedItem();
        selectedMember.setStaffId(txtStaffId.getText());
        selectedMember.setName(txtName.getText());
        selectedMember.setTelNo(txtTelno.getText());
        selectedMember.setEmail(txtEmail.getText());
        selectedMember.setReception(comboRole.getValue());
        selectedMember.setSalary(Double.parseDouble(txtSalary.getText()));
        tblStaffInfo.refresh();
        clear();
    }
    public void clear(){
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
        colReception.setCellValueFactory(new PropertyValueFactory<>("reception"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        tblStaffInfo.setItems(staffInfoDTOS);

        tblStaffInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                txtStaffId.setText(newValue.getStaffId());
                txtName.setText(newValue.getName());
                txtTelno.setText(newValue.getTelNo());
                txtEmail.setText(newValue.getEmail());
                comboRole.setValue(newValue.getReception());
                txtSalary.setText(String.valueOf(newValue.getSalary()));
            }
        });
    }
    public static int countStaff(){
        int count = 0;
        for (int i = 0; i < staffInfoDTOS.size(); i++) {
            count++;
        }
        return count;
    }
}
