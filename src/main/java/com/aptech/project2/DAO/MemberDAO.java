package com.aptech.project2.DAO;

import com.aptech.project2.Generic.IGeneric;
import com.aptech.project2.Model.Coach;
import com.aptech.project2.Model.ConnectDatabase;
import com.aptech.project2.Model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberDAO implements IGeneric<Member> {
    private Connection con = ConnectDatabase.getInstance().getConnect();
    public static MemberDAO getInstance(){
        return new MemberDAO();
    }
    public void insert(Member member) {
        System.out.println("get here");
        String sql = "INSERT into tblmember(memberName,address,gender,phone,schedule,startDate,endDate,status,coachId,id) values " +
                "(?,?,?,?,?,?,?,?,?,?)";
        System.out.println("get here1");
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, member.getName());
            ptm.setString(2, member.getAddress());
            ptm.setString(3, member.getGender());
            ptm.setString(4, member.getPhone());
            ptm.setString(5, member.getSchedule());
            ptm.setDate(6, java.sql.Date.valueOf(member.getStartDate()));
            ptm.setDate(7, java.sql.Date.valueOf(member.getEndDate()));
            ptm.setString(8,member.getStatus());
            String coachId = null;
            if (member.getCoach()!=null){
                coachId=member.getCoach().getId();
            }
            ptm.setString(9,coachId);
            ptm.setString(10,member.getId());
            ptm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(Member member) {
        String sql = "DELETE FROM tblmember WHERE id = ?";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, member.getId());
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Member> getAll() {
        ObservableList<Member> members = FXCollections.observableArrayList();
        String sql = "SELECT * FROM tblmember";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("memberName");
                String address = rs.getString("address");
                String gender = rs.getString("gender" );
                String phone = rs.getString("phone");
                String schedule = rs.getString("schedule");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String status = rs.getString("status");
                String coachId = rs.getString("coachId");
                Coach coach = new CoachDAO().findById(coachId);
                Member member = new Member(id,name,address,gender,phone,schedule,startDate,endDate,status,coach);
                members.add(0, member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void update(Member member) {
        String sql = "UPDATE tblmember SET memberName = ?," +
                " address = ?, gender = ?, phone = ?, schedule = ?, startDate = ?, endDate=?, status=?,coachId=? WHERE id = ?";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, member.getName());
            ptm.setString(2, member.getAddress());
            ptm.setString(3, member.getGender());
            ptm.setString(4, member.getPhone());
            ptm.setString(5, member.getSchedule());
            ptm.setDate(6, java.sql.Date.valueOf(member.getStartDate()));
            ptm.setDate(7, java.sql.Date.valueOf(member.getEndDate()));
            ptm.setString(8,member.getStatus());
            System.out.println(member.getCoach()+ " in DAO");
            String coachId = null;
            if (member.getCoach()!=null){
                coachId=member.getCoach().getId();
            }
            ptm.setString(9,coachId);
            ptm.setString(10,member.getId());
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean findById(String id){
        ObservableList<Member> list = getAll();
        if (list==null){
            return true;
        }
        if (list.size()==0){
            return true;
        }
        for (Member member:list){
            if (member.getId().equals(id)){
                return false;
            }
        }
        return true;
    }

    public ObservableList<Member> getAllBySearch(String keyword) {
        ObservableList<Member> members = FXCollections.observableArrayList();
        String condition = "";
        if(!keyword.isEmpty()){
            condition = " AND id LIKE ? OR memberName LIKE ? OR status LIKE ?";
        }
        String sql = "SELECT * FROM tblmember WHERE 1=1" + condition;
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            if(!keyword.isEmpty()){
                ptm.setString(1, "%"+keyword+"%");
                ptm.setString(2, "%"+keyword+"%");
                ptm.setString(3, "%"+keyword+"%");
            }
            ResultSet rs = ptm.executeQuery();
            while (rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("memberName");
                String address = rs.getString("address");
                String gender = rs.getString("gender" );
                String phone = rs.getString("phone");
                String schedule = rs.getString("schedule");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String status = rs.getString("status");
                String coachId = rs.getString("coachId");
                Coach coach = new CoachDAO().findById(coachId);
                Member member = new Member(id,name,address,gender,phone,schedule,startDate,endDate,status,coach);
                members.add(0, member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
        return members;
    }

    public void updateStatus(String memberId){
        String sql = "UPDATE tblmember SET status = ? WHERE id = ?";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, "Paid");
            ptm.setString(2, memberId);
            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
    }

    public int countMember(){
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tblmember";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                count = rs.getInt("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectDatabase.getInstance().closeConnect(con);
        return count;
    }
    public Member getMemberById(String id){
        ObservableList<Member> memberObservableList = getAll();
        for (Member member : memberObservableList){
            if (member.getId().equals(id)){
                return member;
            }
        }
        return null;
    }
    public boolean checkPhoneNumber(String str){
        ObservableList<Member> memberObservableList = getAll();
        for (Member member : memberObservableList){
            if (member.getPhone().equals(str)){
                return false;
            }
        }
        return true;
    }
}