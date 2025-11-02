package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.RoomInfoDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomInfoController implements Initializable {

    static ObservableList<RoomInfoDTO> roomInfoDTOS = FXCollections.observableArrayList(
            new RoomInfoDTO("R001", "Single", "Single room with two beds.", "Rs.15000.00"),
            new RoomInfoDTO("R002", "Double", "Double room with three beds.", "Rs.75000.00"),
            new RoomInfoDTO("R003", "VIP", "VIP room with 8 beds.Full Access.", "Rs.250 000.00"),
            new RoomInfoDTO("R004", "Single", "Single room with one bed.", "Rs.14000.00")
    );

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

    @FXML
    void btnAddOnAction(ActionEvent event) {
        RoomInfoDTO roomInfoDto = new RoomInfoDTO(txtRoomId.getText(),txtType.getText(),txtDescription.getText(),txtPrice.getText());
        roomInfoDTOS.add(roomInfoDto);

        tblRoomInfo.refresh();
        clear();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        RoomInfoDTO selectItem = tblRoomInfo.getSelectionModel().getSelectedItem();
        roomInfoDTOS.remove(selectItem);
        tblRoomInfo.refresh();
        clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        RoomInfoDTO selectItem = tblRoomInfo.getSelectionModel().getSelectedItem();

        selectItem.setRoomID(txtRoomId.getText());
        selectItem.setType(txtType.getText());
        selectItem.setDescription(txtDescription.getText());
        selectItem.setPrice(txtPrice.getText());
        tblRoomInfo.refresh();
        clear();
    }
    public void clear(){
        txtRoomId.setText("");
        txtType.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
    }
    public void btnReloadOnAction(ActionEvent actionEvent) {
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblRoomInfo.setItems(roomInfoDTOS);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnReloadOnAction(new ActionEvent());
        
        tblRoomInfo.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            if(newValue!=null){
                txtRoomId.setText(newValue.getRoomID());
                txtType.setText(newValue.getType());
                txtDescription.setText(newValue.getDescription());
                txtPrice.setText(newValue.getPrice());
            }
        });
    }
    public static int countRooms(){
        int count = 0;
        for (int i = 0; i < roomInfoDTOS.size(); i++) {
            count++;
        }
        return count;
    }

    public void btnCustomerInfoOnAction(ActionEvent actionEvent) {
    }

    public void btnStaffInfoOnAction(ActionEvent actionEvent) {
    }

    public void btnUserDetailsOnAction(ActionEvent actionEvent) {
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) {
    }
}
