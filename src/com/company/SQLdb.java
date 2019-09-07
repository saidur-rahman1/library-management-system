package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLdb {

    public static Connection getconnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mgmtsystem","root","root");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;

    }

}
