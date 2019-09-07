package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnBook extends JFrame {

    static ReturnBook frame;
    JPanel panel;
    JTextField issueIdTf, codeTf, quantityTf, idTf;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new ReturnBook();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ReturnBook() {

        super("Return Book(s)");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Return Book(s)");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(130, 25, 200, 50);
        panel.add(title);

        JLabel issueIdLabel = new JLabel("Issue ID");
        issueIdLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        issueIdLabel.setBounds(50, 100, 200, 20);
        panel.add(issueIdLabel);

        JLabel codeLabel = new JLabel("Book Code");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        codeLabel.setBounds(50, 140, 200, 20);
        panel.add(codeLabel);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityLabel.setBounds(50, 180, 200, 20);
        panel.add(quantityLabel);

        JLabel idLabel = new JLabel("Student ID");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        idLabel.setBounds(50, 220, 200, 20);
        panel.add(idLabel);

        issueIdTf = new JTextField();
        issueIdTf.setFont(new Font("Arial", Font.PLAIN, 15));
        issueIdTf.setBounds(200, 100, 200, 25);
        panel.add(issueIdTf);

        codeTf = new JTextField();
        codeTf.setFont(new Font("Arial", Font.PLAIN, 15));
        codeTf.setBounds(200, 140, 200, 25);
        panel.add(codeTf);

        quantityTf = new JTextField();
        quantityTf.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityTf.setBounds(200, 180, 200, 25);
        panel.add(quantityTf);

        idTf = new JTextField();
        idTf.setFont(new Font("Arial", Font.PLAIN, 15));
        idTf.setBounds(200, 220, 200, 25);
        panel.add(idTf);

        JButton returnBook = new JButton("Return");
        returnBook.setFont(new Font("Arial", Font.PLAIN, 15));
        returnBook.setBounds(90, 360, 100, 40);
        returnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String issueId = issueIdTf.getText();
                String bookCode = codeTf.getText();
                String quantity = quantityTf.getText();
                String studentId = idTf.getText();
                if (!issueId.isEmpty() && !bookCode.isEmpty() && !quantity.isEmpty() && !studentId.isEmpty()) {
                    if (issueId.matches("[0-9]+")) {
                        if (studentId.matches("[0-9]+") && studentId.length() == 8) {
                            if (quantity.matches("[0-9]+")) {
                                int issueIdInt = Integer.parseInt(issueIdTf.getText());
                                int quantityInt = Integer.parseInt(quantityTf.getText());
                                int statusCheckIssued = ReturnWdb.checkIssued(issueIdInt, bookCode, quantityInt, studentId);
                                switch (statusCheckIssued) {
                                    case 1:
                                        int statusReturnWdb = ReturnWdb.updateIssued(issueIdInt, bookCode, quantityInt, studentId);
                                        if (statusReturnWdb == 2) {
                                            JOptionPane.showMessageDialog(ReturnBook.this, "Return Successful");
                                            break;
                                        }

                                    case 2:
                                        JOptionPane.showMessageDialog(ReturnBook.this, "Error, return quantity higher than issued quantity");
                                        break;
                                    case 3:
                                        JOptionPane.showMessageDialog(ReturnBook.this, "Invalid return quantity");
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(ReturnBook.this, "No records found");
                                }
                            } else {
                                JOptionPane.showMessageDialog(ReturnBook.this, "Invalid quantity");
                            }
                        } else {
                            JOptionPane.showMessageDialog(ReturnBook.this, "Invalid student ID. Make sure it has 8 digits (numbers).");
                        }
                    } else {
                        JOptionPane.showMessageDialog(ReturnBook.this, "Invalid issue ID");
                    }
                } else {
                    JOptionPane.showMessageDialog(ReturnBook.this, "Please fill out all fields");
                }

            }
        });
        panel.add(returnBook);

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
                issueIdTf.setText("");
                codeTf.setText("");
                quantityTf.setText("");
                idTf.setText("");
            }
        });
        panel.add(refresh);

        this.add(panel);
    }

}
