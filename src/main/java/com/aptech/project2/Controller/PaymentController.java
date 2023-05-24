package com.aptech.project2.Controller;


import com.aptech.project2.DAO.MemberDAO;
import com.aptech.project2.DAO.PaymentDAO;
import com.aptech.project2.Model.Payment;
import com.aptech.project2.Validation.Validate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private Button btnPay;

    @FXML
    private TextField txtDiscount;

    @FXML
    private Label txtHaveToPay;

    @FXML
    private TextField txtPricePerDay;

    @FXML
    private Label txtTotal;

    @FXML
    private Label txtTotalDay;

    private Alert alert;
    @FXML
    private Label txtMemberId;

    public void setData(String memberId, long totalDay){
        this.txtTotalDay.setText(String.valueOf(totalDay));
        this.txtMemberId.setText(memberId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setBtnPay(){
            String perOneDay = txtPricePerDay.getText();
            String discount = txtDiscount.getText();
            long daysBetween = Long.valueOf(txtTotalDay.getText());
            String memberId = txtMemberId.getText();
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
                    double Discount = Double.valueOf(discount);
                    double subtotal = Float.valueOf(perOneDay)*daysBetween;
                    txtTotal.setText(String.valueOf(subtotal));
                    float haveToPay = Float.valueOf(perOneDay)*daysBetween - Float.valueOf(discount);
                    txtHaveToPay.setText(String.valueOf(haveToPay));
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Message");
                    alert.setContentText("The amount to be paid is: "+haveToPay+"$\n"+"Are you sure.");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()==ButtonType.OK) {
                        Stage stage = (Stage) btnPay.getScene().getWindow();
                        stage.close();
                        MemberDAO.getInstance().updateStatus(memberId);
                        Payment payment = new Payment(0, memberId, subtotal, Discount, haveToPay, LocalDateTime.now());
                        PaymentDAO.getInstance().insert(payment);
                    }
                }
            }
        }
}
