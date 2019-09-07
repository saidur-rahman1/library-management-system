package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReturnWdb {

    public static int checkIssued(int issueId, String bookCode, int quantity, String studentId) {
        int status = 0, quantityCheck = 0, issueIdCheck = 0;
        String studentIdCheck = null;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, Quantity, Student_ID FROM issued WHERE ID=? AND Student_ID=? AND Book_Code=?");
            preparedStatement.setInt(1,issueId);
            preparedStatement.setString(2,studentId);
            preparedStatement.setString(3,bookCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                issueIdCheck = resultSet.getInt("ID");
                quantityCheck = resultSet.getInt("Quantity");
                studentIdCheck = resultSet.getString("Student_ID");
            }
            if (issueIdCheck == issueId && studentIdCheck.equals(studentId)) {
                if (quantity>0 && quantity<=quantityCheck) {
                    status = 1;
                }  else if (quantity > quantityCheck) {
                    status = 2;
                } else {
                    status = 3;
                }
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

    public static int updateIssued(int issueId, String bookCode, int quantity, String studentId) {
        int dbQuantity = 0, status = 0, statusupdateBook = 0;
        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT Quantity FROM issued WHERE ID=? AND Student_ID=? AND Book_Code=?");
            preparedStatement1.setInt(1, issueId);
            preparedStatement1.setString(2, studentId);
            preparedStatement1.setString(3, bookCode);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) dbQuantity = resultSet.getInt("Quantity");
            dbQuantity-=quantity;
            if (dbQuantity == 0) {
                PreparedStatement preparedStatement2 = connection.prepareStatement("DELETE FROM issued WHERE ID=? AND Student_ID=?");
                preparedStatement2.setInt(1, issueId);
                preparedStatement2.setString(2, studentId);
                preparedStatement2.executeUpdate();
                status = 1;
            } else {
                PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE issued SET Quantity=? WHERE ID=? AND Student_ID=?");
                preparedStatement3.setInt(1, dbQuantity);
                preparedStatement3.setInt(2, issueId);
                preparedStatement3.setString(3, studentId);
                preparedStatement3.executeUpdate();
                status = 1;
            }
            statusupdateBook = updateBooks(bookCode, quantity);
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return status+statusupdateBook;
    }

    public static int updateBooks(String bookCode, int quantity) {
        int dbQuantity = 0, dbIssued = 0, status = 0;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT Quantity, Issued FROM books WHERE Code=?");
            preparedStatement1.setString(1, bookCode);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                dbQuantity = resultSet.getInt("Quantity");
                dbIssued = resultSet.getInt("Issued");
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE books SET Quantity=?, Issued=? WHERE Code=?");
            preparedStatement2.setInt(1, dbQuantity+quantity);
            preparedStatement2.setInt(2, dbIssued-quantity);
            preparedStatement2.setString(3, bookCode);
            preparedStatement2.executeUpdate();
            connection.close();
            status = 1;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return status;
    }

}
