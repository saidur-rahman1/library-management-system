package com.company;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IssueWdb {

    public static boolean checkBook(String bookCode) {
        boolean status = false;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE code=?");
            preparedStatement.setString(1, bookCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            status = resultSet.next();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

    public static int checkQuantity(String bookCode) {
        int quantity = 0;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT Quantity FROM books WHERE code=?");
            preparedStatement.setString(1, bookCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) quantity = resultSet.getInt("Quantity");
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return quantity;
    }

    public static int saveToissued(String bookcode, int bookQuantity, String studentId, String studentName, String studentContact) {
        int status = 0;
        try {
            Connection connection = SQLdb.getconnection();

            updateBooks(bookcode, bookQuantity);

            //if (status > 0) {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("insert into issued(Book_Code, Quantity, Student_ID, Student_Name, " +
                                "Student_Phone_No) values(?,?,?,?,?)");
                preparedStatement.setString(1, bookcode);
                preparedStatement.setInt(2, bookQuantity);
                preparedStatement.setString(3, studentId);
                preparedStatement.setString(4, studentName);
                preparedStatement.setString(5, studentContact);
                status = preparedStatement.executeUpdate();
            //}
            connection.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return status;
    }

    public static int updateBooks(String bookcode, int issueRequest) {
        int status = 0, quantity = 0, issued = 0;
        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement1 =
                    connection.prepareStatement("SELECT Quantity, Issued FROM books WHERE Code = ?");
            preparedStatement1.setString(1, bookcode);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                quantity = resultSet.getInt("Quantity");
                issued = resultSet.getInt("Issued");
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE books SET Quantity = ?, " +
                    "Issued = ? WHERE Code = ?");
            preparedStatement2.setInt(1, quantity-issueRequest);
            preparedStatement2.setInt(2, issued+issueRequest);
            preparedStatement2.setString(3, bookcode);
            status = preparedStatement2.executeUpdate();
            connection.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return status;
    }

}
