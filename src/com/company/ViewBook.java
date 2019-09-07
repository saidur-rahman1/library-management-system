package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ViewBook extends JFrame {

    private JButton delete;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewBook frame = new ViewBook();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ViewBook() {

        super("View Books");
        this.setSize(1000,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel1 = new JPanel();
        panel1.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel1.setLayout(new BorderLayout());
        ViewBook.this.getContentPane().add(BorderLayout.CENTER,panel1);

        String data[][]=null;
        String columnNames[]=null;

        try {
            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();
            columnNames = new String[columnCount];

            for (int i=1 ; i<=columnCount ; i++) {
                String nameModify = resultSetMetaData.getColumnName(i);
                nameModify = nameModify.replace("_"," ");
                columnNames[i-1] = nameModify;
            }

            resultSet.last();
            int rows = resultSet.getRow();
            resultSet.beforeFirst();

            data = new String[rows][columnCount];
            int count = 0;
            while(resultSet.next()) {
                for(int i=1;i<=columnCount;i++){
                    data[count][i-1]=resultSet.getString(i);
                }
                count++;
            }

            connection.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        JTable table = new JTable(data, columnNames);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                delete.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        JScrollPane jScrollPane = new JScrollPane(table);
        panel1.add(jScrollPane);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());

        JButton search = new JButton("Search");
        search.setFont(new Font("Arial", Font.PLAIN, 15));
        search.setBounds(75,0,100,40);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBook.this.getContentPane().remove(panel1);
                searchBook();
            }
        });
        panel2.add(search);

        delete = new JButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 15));
        delete.setBounds(195,0,100,40);
        delete.setEnabled(false);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String codeValue = table.getModel().getValueAt(row, 1).toString();
                int option = JOptionPane.showConfirmDialog(ViewBook.this,"Are you sure you want to delete?");
                if (option == 0) {
                    int status = BookWdb.deleteBook(codeValue);
                    switch (status) {
                        case 2: JOptionPane.showMessageDialog(ViewBook.this, "Deleted successfully");
                                ViewBook.this.dispose();
                                ViewBook.main(new String[]{});
                            break;
                        case 1: JOptionPane.showMessageDialog(ViewBook.this,"Cannot delete entry, issued units not yet returned");
                            break;
                        default: JOptionPane.showMessageDialog(ViewBook.this, "Oops, something went wrong");
                            break;
                    }
                }
            }
        });
        panel2.add(delete);

        JButton refresh = new JButton("Refresh");
        refresh.setFont(new Font("Arial", Font.PLAIN, 15));
        refresh.setBounds(315,0,100,40);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBook.this.dispose();
                ViewBook.main(new String[]{});
            }
        });
        panel2.add(refresh);

        ViewBook.this.getContentPane().add(BorderLayout.SOUTH,panel2);

    }

    public void searchBook() {

        JPanel panel3 = new JPanel();
        panel3.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel3.setLayout(new BorderLayout());

        String dataSearch[][]=null;
        String columnNamesSearch[]=null;

        try {
            String bookNameSearch = JOptionPane.showInputDialog(ViewBook.this,"Please enter book name").trim();

            Connection connection = SQLdb.getconnection();
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM books WHERE Name=?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement2.setString(1,bookNameSearch);
            ResultSet resultSet = preparedStatement2.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();
            columnNamesSearch = new String[columnCount];

            for (int i=1 ; i<=columnCount ; i++) {
                String nameModify = resultSetMetaData.getColumnName(i);
                nameModify = nameModify.replace("_"," ");
                columnNamesSearch[i-1] = nameModify;
            }

            resultSet.last();
            int rows = resultSet.getRow();
            resultSet.beforeFirst();

            dataSearch = new String[rows][columnCount];
            int count = 0;
            while(resultSet.next()) {
                for(int i=1;i<=columnCount;i++){
                    dataSearch[count][i-1]=resultSet.getString(i);
                }
                count++;
            }
            connection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        JTable table2 = new JTable(dataSearch, columnNamesSearch);
        JScrollPane jScrollPane = new JScrollPane(table2);
        panel3.add(jScrollPane);

        ViewBook.this.getContentPane().add(BorderLayout.CENTER,panel3);
        ViewBook.this.validate();

    }

}
