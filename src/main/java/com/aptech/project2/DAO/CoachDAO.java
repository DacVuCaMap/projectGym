package com.aptech.project2.DAO;

import com.aptech.project2.Model.Coach;
import com.aptech.project2.Model.ConnectDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;


public class CoachDAO {
    public static CoachDAO getInstance(){
        return new CoachDAO();
    }

    private Connection con = ConnectDatabase.getInstance().getConnect();

    public ObservableList<Coach> getList(){
        ObservableList<Coach> coaches = FXCollections.observableArrayList();
        String sql = "SELECT * FROM coach";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                Coach coach = new Coach();
                coach.setId(rs.getString("id"));
                coach.setName(rs.getString("coach_name"));
                coach.setAddress(rs.getString("coach_address"));
                coach.setGender(rs.getString("coach_gender"));
                coach.setPhone(rs.getString("coach_phone"));
                coach.setStatus(rs.getString("coach_status"));
                coach.setRank(rs.getString("coach_rank"));
                coaches.add(0, coach);
            }
            ConnectDatabase.getInstance().closeConnect(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coaches;
    }

    public void insertCoach(Coach coach){
        String sql = "INSERT INTO coach VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, coach.getId());
            ptm.setString(2, coach.getName());
            ptm.setString(3, coach.getGender());
            ptm.setString(4, coach.getPhone());
            ptm.setString(5, coach.getAddress());
            ptm.setString(6, coach.getStatus());
            ptm.setString(7, coach.getRank());
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
    }

    public void updateCat(String id, Coach coach){
        String sql = "UPDATE coach SET id = ?, coach_name = ?, coach_gender = ?, coach_phone = ?," +
                " coach_address = ?, coach_status = ?, coach_rank=? WHERE id =? ";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, coach.getId());
            ptm.setString(2, coach.getName());
            ptm.setString(3, coach.getGender());
            ptm.setString(4, coach.getPhone());
            ptm.setString(5, coach.getAddress());
            ptm.setString(6, coach.getStatus());
            ptm.setString(7, coach.getRank());
            ptm.setString(8, id);
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
    }

    public void deleteCoach(Coach coach){
        String sql = "DELETE FROM coach WHERE id = ?";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, coach.getId());
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
    }


    public Coach findById(String coachId) {
        String sql = "SELECT * FROM coach WHERE id = ?";
        Coach coach = null;
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, coachId);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                coach = new Coach();
                coach.setId(rs.getString("id"));
                coach.setName(rs.getString("coach_name"));
                coach.setAddress(rs.getString("coach_address"));
                coach.setGender(rs.getString("coach_gender"));
                coach.setPhone(rs.getString("coach_phone"));
                coach.setStatus(rs.getString("coach_status"));
                coach.setRank(rs.getString("coach_rank"));
            }
            ConnectDatabase.getInstance().closeConnect(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(coach + " in coachDAO");
        return coach;
    }

    public Coach findByPhone(String phone){
        String sql = "SELECT * FROM coach WHERE coach_phone = ?";
        Coach coach = null;
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, phone);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                coach = new Coach();
                coach.setId(rs.getString("id"));
                coach.setName(rs.getString("coach_name"));
                coach.setAddress(rs.getString("coach_address"));
                coach.setGender(rs.getString("coach_gender"));
                coach.setPhone(rs.getString("coach_phone"));
                coach.setStatus(rs.getString("coach_status"));
                coach.setRank(rs.getString("coach_rank"));
            }
            ConnectDatabase.getInstance().closeConnect(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(coach + " in coachDAO");
        return coach;
    }
}
