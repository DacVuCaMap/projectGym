package com.aptech.project2.Controller;

import com.aptech.project2.DAO.PaymentDAO;
import com.aptech.project2.Model.Member;
import com.aptech.project2.Model.Payment;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListPaymentController implements Initializable {
    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Payment, LocalDateTime> columCreateAt;

    @FXML
    private TableColumn<Payment, Double> columDiscount;

    @FXML
    private TableColumn<Payment, Integer> columId;

    @FXML
    private TableColumn<Payment, String> columMemberId;

    @FXML
    private TableColumn<Payment, Double> columSubTotal;

    @FXML
    private TableColumn<Payment, Double> columTotal;

    @FXML
    private TableView<Payment> tablePayment;

    @FXML
    private TextField txtSearch;
    private ObservableList<Payment> payments = PaymentDAO.getInstance().getAll("");
    public void showTable(){
        columId.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("id"));
        columMemberId.setCellValueFactory(new PropertyValueFactory<Payment, String>("memberId"));
        columSubTotal.setCellValueFactory(new PropertyValueFactory<Payment, Double>("subtotal"));
        columDiscount.setCellValueFactory(new PropertyValueFactory<Payment, Double>("discount"));
        columTotal.setCellValueFactory(new PropertyValueFactory<Payment, Double>("total"));
        columCreateAt.setCellValueFactory(new PropertyValueFactory<Payment, LocalDateTime>("dateTime"));
        tablePayment.setItems(payments);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showTable();
        btnSearch.setOnAction(event -> {
            String keyword = txtSearch.getText();
            ObservableList<Payment> paymentObservableList = PaymentDAO.getInstance().getAll(keyword);
            tablePayment.setItems(paymentObservableList);
        });
    }
}
