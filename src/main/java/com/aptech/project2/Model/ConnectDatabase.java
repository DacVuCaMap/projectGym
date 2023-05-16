package com.aptech.project2.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    public static ConnectDatabase getInstance(){
        return new ConnectDatabase();
    }

    public Connection getConnect(){
        Connection connection = null;
        String databaseUser = "namvu";
        String databasePass = "namnam123";
        String url = "jdbc:mysql://localhost:3306/gymmanagement";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,databaseUser,databasePass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnect(Connection con){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
