package com.aptech.project2.Controller;


import com.aptech.project2.DAO.CoachDAO;
import com.aptech.project2.Model.Coach;
import com.aptech.project2.Model.Member;
import com.aptech.project2.Validation.Validate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoachController implements Initializable {
    @FXML
    private TableColumn<Coach, String> ColumRank;

    @FXML
    private ComboBox<String> comboxRank;
    @FXML
    private TableColumn<Coach, String> ColumStatus;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Coach, String> columAdress;

    @FXML
    private TableColumn<Coach, String> columGender;

    @FXML
    private TableColumn<Coach, String> columPhone;

    @FXML
    private TableColumn<Coach, String> columnId;

    @FXML
    private TableColumn<Coach, String> columnName;

    @FXML
    private ComboBox<String> comboxGender;

    @FXML
    private ComboBox<String> comboxStatus;

    @FXML
    private TableView<Coach> tableCat;

    @FXML
    private TextArea txtAdress;

    @FXML
    private TextField txtId;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;
    private Alert alert;
    private ObservableList<Coach> coaches = CoachDAO.getInstance().getList();

    public void showComBoxes(){
        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female", "Others");
        ObservableList<String> status = FXCollections.observableArrayList("Active", "Inactive");
        ObservableList<String> ranks = FXCollections.observableArrayList("Grade A", "Grade B" , "Grade C");
        comboxGender.setItems(genders);
        comboxStatus.setItems(status);
        comboxRank.setItems(ranks);
    }

    public void showTableCoach(){
        columnId.setCellValueFactory(new PropertyValueFactory<Coach, String>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<Coach, String>("name"));
        columAdress.setCellValueFactory(new PropertyValueFactory<Coach, String>("address"));
        columGender.setCellValueFactory(new PropertyValueFactory<Coach, String>("gender"));
        columPhone.setCellValueFactory(new PropertyValueFactory<Coach, String>("phone"));
        ColumStatus.setCellValueFactory(new PropertyValueFactory<Coach, String>("status"));
        ColumRank.setCellValueFactory(new PropertyValueFactory<Coach, String>("rank"));
        tableCat.setItems(coaches);
        tableCat.setOnMouseClicked(event->{
            Coach newSelection = tableCat.getSelectionModel().getSelectedItem();
            if(newSelection!=null){
                txtId.setDisable(true);
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtAdress.setText(newSelection.getAddress());
                txtPhone.setText(newSelection.getPhone());
                comboxGender.setValue(newSelection.getGender());
                comboxStatus.setValue(newSelection.getStatus());
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showComBoxes();
        showTableCoach();

        btnInsert.setOnAction(event -> {
            txtId.setDisable(false);
            if(txtId.getText().isBlank()==true
                    ||txtName.getText().isBlank()==true
                    ||comboxStatus.getValue()==null
                    ||comboxGender.getValue()==null
                    ||txtAdress.getText().isBlank()==true
                    ||txtPhone.getText().isBlank()==true
                    ||comboxRank.getValue()==null){
                txtMessage.setText("Please enter full textFiled.");
            }else {
                boolean f = true;
                String id = txtId.getText();
                String phone = txtPhone.getText();
                String name = txtName.getText();
                String address = txtAdress.getText();
                String gender = comboxGender.getValue();
                String status = comboxStatus.getValue();
                String rank = comboxRank.getValue();
                Coach coach = new Coach(id, name, gender, phone, address, status, rank);
                if(!Validate.checkCoachId(id)){
                    f=false;
                    txtMessage.setText("Coach ID is invalid.(CID-000)");
                }
                if(CoachDAO.getInstance().findById(id)!=null){
                    f = false;
                    txtMessage.setText("Coach ID is already.");
                }
                if(CoachDAO.getInstance().findByPhone(phone)!=null){
                    f=false;
                    txtMessage.setText("Number Phone is already.");
                }
                if(!Validate.checkPhone(phone)){
                    f = false;
                    txtMessage.setText("Number Phone is invalid.");
                }
                if(f==true){
                    txtMessage.setText("");
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Message");
                    alert.setContentText("Are you sure.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()==ButtonType.OK) {
                    CoachDAO.getInstance().insertCoach(coach);
                    coaches.add(0, coach);
                    tableCat.setItems(coaches);
                    }
                }

            }
        });

        btnDelete.setOnAction(event -> {
            if(txtId.getText().isBlank()==true){
            txtMessage.setText("Please enter the id of the coach you want to delete.");
        }else {
            txtMessage.setText("");
            String id = txtId.getText();
            Optional<Coach> coachOptional = coaches.stream().filter(x->x.getId().equals(id)).findFirst();
            if(coachOptional.isPresent()){
                Coach coach = coachOptional.get();
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Message");
                    alert.setContentText("Do you want to delete this Coach!");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()==ButtonType.OK){
                        CoachDAO.getInstance().deleteCoach(coach);
                        coaches.remove(coach);
                        tableCat.setItems(coaches);
                        setBtnClear();
                    }
                } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setContentText("Not found coach");
                alert.showAndWait();
            }
        }
        });

        btnClear.setOnAction(event -> {
            setBtnClear();
        });

        btnUpdate.setOnAction(event -> {
            Coach newSelection = tableCat.getSelectionModel().getSelectedItem();
            if(newSelection==null){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning message");
                alert.setContentText("Please choose 1 row on the product table to update!");
                alert.showAndWait();
            }else {
                String id = txtId.getText();
                String phone = txtPhone.getText();
                String name = txtName.getText();
                String address = txtAdress.getText();
                String gender = comboxGender.getValue();
                String status = comboxStatus.getValue();
                String rank = comboxRank.getValue();
                Coach coach = new Coach(id, name, gender, phone, address, status, rank);
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Message");
                alert.setContentText("Do you want to update this coach!");
                Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()==ButtonType.OK){
                        CoachDAO.getInstance().updateCat(newSelection.getId(), coach);
                        newSelection.setId(coach.getId());
                        newSelection.setName(coach.getName());
                        newSelection.setStatus(coach.getStatus());
                        newSelection.setGender(coach.getGender());
                        newSelection.setAddress(coach.getAddress());
                        newSelection.setPhone(coach.getPhone());
                        newSelection.setRank(coach.getRank());
                        tableCat.refresh();
            }
        }
        });

    }

    public void setBtnClear(){
        tableCat.getSelectionModel().clearSelection();
        txtMessage.setText("");
        txtAdress.setText("");
        txtId.setText("");
        txtName.setText("");
        comboxGender.setValue(null);
        txtPhone.setText("");
        comboxStatus.setValue(null);
        comboxRank.setValue(null);
    }

}
