package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    private final static String URL="jdbc:mysql://localhost:3306/expo";
    private final static String USERNAME="root";
    private final static String PASSWORD="B@laji143";
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("class loaded successfully");
            Connection connection= DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connection established successfully");
            return connection;
        } catch (Exception e) {
            System.out.println("class not found");
            System.out.println("connection not established");
        }
        return null;
    }

}
