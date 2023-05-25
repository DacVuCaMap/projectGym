package com.aptech.project2.Controller;
import com.aptech.project2.DAO.MemberDAO;
import com.aptech.project2.DAO.PaymentDAO;
import com.aptech.project2.Model.ConnectDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashBroadController implements Initializable {
    private Connection con = ConnectDatabase.getInstance().getConnect();
    @FXML
    private BarChart<?, ?> chartMonthIncome;

    @FXML
    private Label txtMember;

    @FXML
    private Label txtMonthIncome;

    @FXML
    private Label txtPayment;

    public void showChart(){
        chartMonthIncome.getData().clear();
        String sql = "SELECT MONTH(create_at) AS month, SUM(total) AS total FROM tblpayment " +
                "GROUP BY MONTH(create_at)";
        XYChart.Series chart = new XYChart.Series();
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                chart.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
            }
            chartMonthIncome.getData().add(chart);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ConnectDatabase.getInstance().closeConnect(con);
    }

    public void showDashBoard(){
        int members = MemberDAO.getInstance().countMember();
        int payments = PaymentDAO.getInstance().countPayment();
        double total = PaymentDAO.getInstance().getTotal();
        txtMonthIncome.setText(String.valueOf(total+"$"));
        txtMember.setText(String.valueOf(members));
        txtPayment.setText(String.valueOf(payments));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showChart();
        showDashBoard();
    }
}
