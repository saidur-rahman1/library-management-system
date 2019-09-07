package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BookWdb {
    private static int dbQuantity = 0;

    public static int save(String bookcode, String name, String author, String publisher, int quantity, int issued) {
        int status = 0;
        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT Quantity FROM books WHERE Code=?");
            preparedStatement1.setString(1,bookcode);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                dbQuantity = resultSet.getInt("Quantity");
                status = 2;
            } else {
                PreparedStatement preparedStatement3 =
                        connection.prepareStatement("insert into books(code, name, author, publisher, quantity, Issued) " +
                                "values(?,?,?,?,?,?)");
                preparedStatement3.setString(1, bookcode);
                preparedStatement3.setString(2, name);
                preparedStatement3.setString(3, author);
                preparedStatement3.setString(4, publisher);
                preparedStatement3.setInt(5, quantity);
                preparedStatement3.setInt(6, issued);
                status = preparedStatement3.executeUpdate();
            }
            connection.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return status;
    }

    public static int updateExistingEntry(String bookcode, int quantity) {
        int updateStatus = 0;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE books SET Quantity=? WHERE Code=?");
            preparedStatement2.setInt(1,dbQuantity+quantity);
            preparedStatement2.setString(2, bookcode);
            updateStatus = preparedStatement2.executeUpdate();
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return updateStatus;
    }

    public static int deleteBook(String bookCode) {
        int status = 0, issuedCheck = 0;
        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT Issued FROM books WHERE Code=?");
            preparedStatement1.setString(1, bookCode);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) issuedCheck = resultSet.getInt("Issued");
            if (issuedCheck==0) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM books WHERE Code=?");
                preparedStatement2.setString(1, bookCode);
                preparedStatement2.executeUpdate();
                status = 2;
            } else {
                status = 1;
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return status;
    }

}
