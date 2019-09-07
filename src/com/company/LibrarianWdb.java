package com.company;

import com.company.SQLdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibrarianWdb {

    public static int save(String name, String username, String password, String email, String address, String phone) {
        int status = 0;
        String dbUsername = null, dbEmail = null, dbPhone = null;
        try {
            Connection connection = SQLdb.getconnection();

            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT Username FROM librarians WHERE Username=?");
            preparedStatement1.setString(1, username);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet1.next()) dbUsername = resultSet1.getString("Username");

            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT Email FROM librarians WHERE Email=?");
            preparedStatement2.setString(1, email);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet2.next()) dbEmail = resultSet2.getString("Email");

            PreparedStatement preparedStatement3 = connection.prepareStatement("SELECT Phone FROM librarians WHERE Phone=?");
            preparedStatement3.setString(1, phone);
            ResultSet resultSet3 = preparedStatement3.executeQuery();
            if (resultSet3.next()) dbPhone = resultSet3.getString("Phone");

            if ((dbUsername != null) || (dbEmail != null) || (dbPhone != null)) {
                if (dbUsername != null) {
                    status = 2;
                } else if (dbEmail != null) {
                    status = 3;
                } else if (dbPhone != null) {
                    status = 4;
                }
            } else {
                PreparedStatement preparedStatement4 = connection.prepareStatement("insert into librarians(name, username, password, email, address, phone) values(?,?,?,?,?,?)");
                preparedStatement4.setString(1, name);
                preparedStatement4.setString(2, username);
                preparedStatement4.setString(3, password);
                preparedStatement4.setString(4, email);
                preparedStatement4.setString(5, address);
                preparedStatement4.setString(6, phone);
                int result = preparedStatement4.executeUpdate();
                if (result > 0) status = 1;
            }
            connection.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return status;
    }

    public static boolean checkCredentials(String username, String password) {
        boolean status = false;
        String dbPassword = null;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Password FROM librarians WHERE Username=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                dbPassword = resultSet.getString("Password");
                if (dbPassword.equals(password)) status = true;
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

    public static int deleteLibrarian(String username) {
        int status = 0, issuedCheck = 0;
        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM librarians WHERE Username=?");
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            status = 1;
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

}
