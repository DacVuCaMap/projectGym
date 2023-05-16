package com.aptech.project2.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnPay;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<?, ?> columEndDate;

    @FXML
    private TableColumn<?, ?> columId;

    @FXML
    private TableColumn<?, ?> columName;

    @FXML
    private TableColumn<?, ?> columStartDate;

    @FXML
    private TableColumn<?, ?> columStatus;

    @FXML
    private TextField txtDiscount;

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
