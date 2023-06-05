package com.aptech.project2.Controller;


import com.aptech.project2.DAO.MemberDAO;
import com.aptech.project2.DAO.PaymentDAO;
import com.aptech.project2.Model.Payment;
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
    private Label txtDiscount;

    @FXML
    private Label txtHaveToPay;

    @FXML
    private Label txtPricePerDay;

    @FXML
    private Label txtTotal;

    @FXML
    private Label txtTotalDay;

    private Alert alert;
    @FXML
    private Label txtMemberId;

    public void setData(String memberId, long totalDay, double pricePerDay, double Discount){
        this.txtTotalDay.setText(String.valueOf(totalDay)+"days");
        this.txtMemberId.setText(memberId);
        this.txtPricePerDay.setText(String.valueOf(pricePerDay)+"$");
        this.txtDiscount.setText(String.valueOf(Discount)+"$");
        float haveToPay = (float) (pricePerDay*totalDay-Discount);
        this.txtHaveToPay.setText(String.valueOf(haveToPay)+"$");
        float subtotal = (float) (pricePerDay*totalDay);
        this.txtTotal.setText(String.valueOf(subtotal) + "$");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setBtnPay(){
            String perOneDay = txtPricePerDay.getText().replace("$","");
            String discount = txtDiscount.getText().replace("$" ,"");
            long daysBetween = Long.valueOf(txtTotalDay.getText().replace("days", ""));
            String memberId = txtMemberId.getText();
            double Discount = Double.valueOf(discount);
            double subtotal = Float.valueOf(perOneDay)*daysBetween;
            double haveToPay = subtotal-Discount;
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
