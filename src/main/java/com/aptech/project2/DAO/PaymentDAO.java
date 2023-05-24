package com.aptech.project2.DAO;


import com.aptech.project2.Model.ConnectDatabase;
import com.aptech.project2.Model.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

public class PaymentDAO {
    private Connection con = ConnectDatabase.getInstance().getConnect();
    public static PaymentDAO getInstance(){
        return new PaymentDAO();
    }

    public void insert(Payment payment) {
        String sql = "INSERT into tblpayment(memberId,subtotal,discount,total,create_at) values " +
                "(?,?,?,?,?)";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, payment.getMemberId());
            ptm.setDouble(2, payment.getSubtotal());
            ptm.setDouble(3, payment.getDiscount());
            ptm.setDouble(4, payment.getTotal());
            ptm.setTimestamp(5, Timestamp.valueOf(payment.getDateTime()));
            ptm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectDatabase.getInstance().closeConnect(con);
    }

    public ObservableList<Payment> getAll(String keyword) {
        ObservableList<Payment> payments = FXCollections.observableArrayList();
        String condition = "";
        if(!keyword.isEmpty()){
            condition = " AND id LIKE ? OR memberId LIKE ? OR create_at LIKE ?";
        }
        String sql = "SELECT * FROM tblpayment WHERE 1=1" + condition;
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            if(!keyword.isEmpty()){
                ptm.setString(1, "%"+keyword+"%");
                ptm.setString(2, "%"+keyword+"%");
                ptm.setString(3, "%"+keyword+"%");
            }
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String memberId = rs.getString("memberId");
                double subtotal = rs.getDouble("subtotal");
                double discount = rs.getDouble("discount" );
                double total = rs.getDouble("total");
                LocalDateTime dateTime = rs.getTimestamp("create_at").toLocalDateTime();
                Payment payment = new Payment(id, memberId, subtotal, discount, total, dateTime);
                payments.add(0, payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
        return payments;
    }


}
