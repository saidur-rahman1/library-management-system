package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IssueBook extends JFrame {

    static IssueBook frame;
    JPanel panel;
    JTextField codeTf, quantityTf, idTf, nameTf, numberTf;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new IssueBook();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public IssueBook() {

        super("Issue Book(s)");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Issue Book(s)");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(150, 25, 200, 50);
        panel.add(title);

        JLabel codeLabel = new JLabel("Book Code");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        codeLabel.setBounds(50, 100, 200, 20);
        panel.add(codeLabel);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityLabel.setBounds(50, 140, 200, 20);
        panel.add(quantityLabel);

        JLabel idLabel = new JLabel("Student ID");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        idLabel.setBounds(50, 180, 200, 20);
        panel.add(idLabel);

        JLabel nameLabel = new JLabel("Student Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setBounds(50, 220, 200, 20);
        panel.add(nameLabel);

        JLabel numberLabel = new JLabel("Student Contact No.");
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        numberLabel.setBounds(50, 260, 200, 20);
        panel.add(numberLabel);


        codeTf = new JTextField();
        codeTf.setFont(new Font("Arial", Font.PLAIN, 15));
        codeTf.setBounds(200, 100, 200, 25);
        panel.add(codeTf);

        quantityTf = new JTextField();
        quantityTf.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityTf.setBounds(200, 140, 200, 25);
        panel.add(quantityTf);

        idTf = new JTextField();
        idTf.setFont(new Font("Arial", Font.PLAIN, 15));
        idTf.setBounds(200, 180, 200, 25);
        panel.add(idTf);

        nameTf = new JTextField();
        nameTf.setFont(new Font("Arial", Font.PLAIN, 15));
        nameTf.setBounds(200, 220, 200, 25);
        panel.add(nameTf);

        numberTf = new JTextField();
        numberTf.setFont(new Font("Arial", Font.PLAIN, 15));
        numberTf.setBounds(200, 260, 200, 25);
        panel.add(numberTf);


        JButton issue = new JButton("Issue");
        issue.setFont(new Font("Arial", Font.PLAIN, 15));
        issue.setBounds(90, 360, 100, 40);
        issue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookCode = codeTf.getText().trim();
                String bookQuantity = quantityTf.getText().trim();
                String studentId = idTf.getText().trim();
                String studentName = nameTf.getText().trim();
                String studentContact = numberTf.getText().trim();
                //int quantityInt = 0;
                if (!bookCode.isEmpty() && !bookQuantity.isEmpty() && !studentId.isEmpty() && !studentName.isEmpty() && !studentContact.isEmpty()) {
                    try {
                        if ((Integer.parseInt(bookQuantity) > 0) && (bookQuantity.matches("[0-9]+"))) {
                            //quantityInt = Integer.parseInt(bookQuantity);
                            if (studentContact.matches("[0-9]+") && studentContact.length() == 10) {
                                if (studentId.matches("[0-9]+") && studentId.length() == 8) {
                                    if (IssueWdb.checkBook(bookCode)) {
                                        int bookQuantityInt = Integer.parseInt(bookQuantity);
                                        if (IssueWdb.checkQuantity(bookCode) >= bookQuantityInt) {
                                            int status = IssueWdb.saveToissued(bookCode, bookQuantityInt, studentId, studentName, studentContact);
                                            if (status > 0) {
                                                JOptionPane.showMessageDialog(IssueBook.this, "Book(s) successfully issued");
                                            } else {
                                                JOptionPane.showMessageDialog(IssueBook.this, "Could not issue book(s)");
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(IssueBook.this, "Not enough quantity");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(IssueBook.this, "Book not in records");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(IssueBook.this, "Invalid student ID. Make sure it has 8 digits (numbers).");
                                }
                            } else {
                                JOptionPane.showMessageDialog(IssueBook.this, "Invalid phone number. Make sure it has 10 digits (numbers).");
                            }
                        } else {
                            JOptionPane.showMessageDialog(IssueBook.this, "Invalid quantity");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(IssueBook.this, "Invalid quantity");
                    }
                } else {
                    JOptionPane.showMessageDialog(IssueBook.this, "Please fill out all fields");
                }
            }
        });
        panel.add(issue);

        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 15));
        cancel.setBounds(200, 360, 100, 40);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel.add(cancel);

        JButton refresh = new JButton("Refresh");
        refresh.setFont(new Font("Arial", Font.PLAIN, 15));
        refresh.setBounds(310, 360, 100, 40);
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                codeTf.setText("");
                quantityTf.setText("");
                idTf.setText("");
                nameTf.setText("");
                numberTf.setText("");

            }
        });
        panel.add(refresh);

        this.add(panel);
    }
}
