package com.aptech.project2.Controller;

import com.aptech.project2.DAO.CoachDAO;
import com.aptech.project2.DAO.MemberDAO;
import com.aptech.project2.Model.Coach;
import com.aptech.project2.Model.Member;
import com.aptech.project2.Validation.Validate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnPay;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Member, LocalDate> columEndDate;

    @FXML
    private TableColumn<Member, String> columId;

    @FXML
    private TableColumn<Member, String> columName;

    @FXML
    private TableColumn<Member, LocalDate> columStartDate;

    @FXML
    private TableColumn<Member, String> columStatus;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TableView<Member> tableMember;

    @FXML
    private Label txtHaveToPay;

    @FXML
    private TextField txtPricePerDay;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label txtTotal;

    @FXML
    private Label txtTotalDay;
    private Alert alert;


    public void showTable(){
        ObservableList<Member> members = MemberDAO.getInstance().getAll();
        columId.setCellValueFactory(new PropertyValueFactory<Member, String>("id"));
        columName.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
        columEndDate.setCellValueFactory(new PropertyValueFactory<Member, LocalDate>("endDate"));
        columStartDate.setCellValueFactory(new PropertyValueFactory<Member, LocalDate>("startDate"));
        columStatus.setCellValueFactory(new PropertyValueFactory<Member, String>("status"));
        tableMember.setItems(members);
        tableMember.setOnMouseClicked(event->{
            Member newSelection = tableMember.getSelectionModel().getSelectedItem();
            if(newSelection!=null){
                long daysBetween = ChronoUnit.DAYS.between(newSelection.getStartDate(), newSelection.getEndDate());
                txtTotalDay.setText(String.valueOf(daysBetween));
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTable();
        btnSearch.setOnAction(event -> {
            String keyword = txtSearch.getText();
            ObservableList<Member> members1 = MemberDAO.getInstance().getAllBySearch(keyword);
            tableMember.setItems(members1);
        });

        btnPay.setOnAction(event -> {
            Member newSelection = tableMember.getSelectionModel().getSelectedItem();
            if(newSelection==null){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning message");
                alert.setContentText("Please choose 1 row on the Customer table to pay");
                alert.showAndWait();
            }else if(newSelection.getStatus().equals("Paid")){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setContentText("This customer has already paid.");
                alert.showAndWait();
            } else {
                String perOneDay = txtPricePerDay.getText();
                String discount = txtDiscount.getText();
                long daysBetween = ChronoUnit.DAYS.between(newSelection.getStartDate(), newSelection.getEndDate());
                boolean flat = true;
                if(perOneDay.isBlank()==true || discount.isBlank()==true){
                    flat = false;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setContentText("Enter Price per day and discount");
                    alert.showAndWait();
                }else {
                    if(!Validate.checkPriceProduct(perOneDay)){
                        flat = false;
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setContentText("Price per day is Invalid.");
                        alert.showAndWait();
                    }
                    if(!Validate.checkPriceProduct(discount)){
                        flat = false;
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setContentText("Discount is Invalid.");
                        alert.showAndWait();
                    }
                    if(flat==true){
                        float haveToPay = Float.valueOf(perOneDay)*daysBetween - Float.valueOf(discount);
                        txtHaveToPay.setText(String.valueOf(haveToPay));
                        alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Message");
                        alert.setContentText("The amount to be paid is: "+haveToPay+"$\n"+"Are you sure.");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get()==ButtonType.OK) {
                            MemberDAO.getInstance().updateStatus(newSelection);
                            showTable();
                        }
                    }
                }
            }
        });
    }
}
