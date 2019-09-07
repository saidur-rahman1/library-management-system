package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBook extends JFrame {

    static AddBook frame;
    JPanel panel;
    JTextField codeTf, nameTf, authorTf, publisherTf, quantityTf;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new AddBook();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AddBook() {

        super("Add Book(s)");
        this.setSize(500,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Add Book(s)");
        title.setFont(new  Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(150,25, 200, 50);
        panel.add(title);

        JLabel codeLabel = new JLabel("Code");
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        codeLabel.setBounds(50, 100, 200, 20);
        panel.add(codeLabel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setBounds(50, 140, 200, 20);
        panel.add(nameLabel);

        JLabel authorLabel = new JLabel("Author");
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        authorLabel.setBounds(50, 180, 200, 20);
        panel.add(authorLabel);

        JLabel publisherLabel = new JLabel("Publisher");
        publisherLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        publisherLabel.setBounds(50, 220, 200, 20);
        panel.add(publisherLabel);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityLabel.setBounds(50, 260, 200, 20);
        panel.add(quantityLabel);


        codeTf = new JTextField();
        codeTf.setFont(new Font("Arial", Font.PLAIN, 15));
        codeTf.setBounds(200, 100, 200, 25);
        panel.add(codeTf);

        nameTf = new JTextField();
        nameTf.setFont(new Font("Arial", Font.PLAIN, 15));
        nameTf.setBounds(200, 140, 200, 25);
        panel.add(nameTf);

        authorTf = new JTextField();
        authorTf.setFont(new Font("Arial", Font.PLAIN, 15));
        authorTf.setBounds(200, 180, 200, 25);
        panel.add(authorTf);

        publisherTf = new JTextField();
        publisherTf.setFont(new Font("Arial", Font.PLAIN, 15));
        publisherTf.setBounds(200, 220, 200, 25);
        panel.add(publisherTf);

        quantityTf = new JTextField();
        quantityTf.setFont(new Font("Arial", Font.PLAIN, 15));
        quantityTf.setBounds(200, 260, 200, 25);
        panel.add(quantityTf);


        JButton add = new JButton("Add");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setBounds(90, 360, 100, 40);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookcode = codeTf.getText().trim();
                String name = nameTf.getText().trim();
                String author = authorTf.getText().trim();
                String publisher = publisherTf.getText().trim();
                String quantity = quantityTf.getText().trim();
                int quantityInt = 0;
                if (!bookcode.isEmpty() && !name.isEmpty() && !author.isEmpty() && !publisher.isEmpty() && !quantity.isEmpty()) {
                    try {
                        if ((Integer.parseInt(quantity) > 0) && (quantity.matches("[0-9]+"))) {
                            quantityInt = Integer.parseInt(quantity);
                            if (BookWdb.save(bookcode, name, author, publisher, quantityInt, 0) == 2) {
                                int option = JOptionPane.showConfirmDialog(AddBook.this, "An entry with this book " +
                                        "code already exists. So, this new quantity will be added to the code's existing quantity. Are " +
                                        "you sure you want to go ahead?");
                                if (option == 0) {
                                    int updateStatus = BookWdb.updateExistingEntry(bookcode, quantityInt);
                                    if (updateStatus > 0) {
                                        JOptionPane.showMessageDialog(AddBook.this, "Successfully " +
                                                "updated existing entry");
                                    } else {
                                        JOptionPane.showMessageDialog(AddBook.this, "Could not " +
                                                "update existing entry");
                                    }
                                }
                            } else {
                                int i = BookWdb.save(bookcode, name, author, publisher, quantityInt, 0);
                                if (i > 0) {
                                    JOptionPane.showMessageDialog(AddBook.this, "Successfully added");
                                } else {
                                    JOptionPane.showMessageDialog(AddBook.this, "Could not be added");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(AddBook.this, "Invalid quantity");
                        }
                    } catch (Exception ex) {
                        //System.out.println(ex);
                        JOptionPane.showMessageDialog(AddBook.this, "Invalid quantity");
                    }

                } else {
                    JOptionPane.showMessageDialog(AddBook.this, "Please fill out all fields");
                }
            }
        });
        panel.add(add);

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
                nameTf.setText("");
                authorTf.setText("");
                publisherTf.setText("");
                quantityTf.setText("");
            }
        });
        panel.add(refresh);

        this.add(panel);

    }

}
