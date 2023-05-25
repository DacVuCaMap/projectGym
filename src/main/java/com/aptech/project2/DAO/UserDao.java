package com.aptech.project2.DAO;

import com.aptech.project2.Model.ConnectDatabase;
import com.aptech.project2.Model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public static UserDao getInstance(){
        return new UserDao();
    }
    private Connection con = ConnectDatabase.getInstance().getConnect();
    public static User userSession;
    public User findByUserName(String userName){
       User user = null;
        String sql = "SELECT * FROM admin WHERE BINARY username = ?";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1, userName);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String pass = rs.getString("password");
                user = new User(id,username, pass);
            }
            ConnectDatabase.getInstance().closeConnect(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    //get register
    public void insertUser(User user){
        String sql = "Insert into admin(username,password) values (?,?)";
        try {
            PreparedStatement ptm = con.prepareStatement(sql);
            ptm.setString(1,user.getUsername());
            ptm.setString(2, user.getPassword());
            ptm.executeUpdate();
            ConnectDatabase.getInstance().closeConnect(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
