package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianLogin extends JFrame {

    static LibrarianLogin frame;
    JPanel panel;
    JButton login, back;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new LibrarianLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LibrarianLogin() {

        super("Librarian Login Page");
        this.setSize(470,350);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Librarian Login Page");
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(110,25, 500, 50);
        panel.add(title);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLabel.setBounds(50, 100, 200, 40);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setBounds(50, 150, 200, 40);
        panel.add(passwordLabel);

        JTextField userName = new JTextField();
        userName.setFont(new Font("Arial", Font.PLAIN, 15));
        userName.setBounds(150, 100, 200, 40);
        panel.add(userName);

        JPasswordField passWord = new JPasswordField();
        //passWord.setFont(new Font("Arial", Font.PLAIN, 15));
        passWord.setBounds(150, 150, 200, 40);
        panel.add(passWord);

        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setBounds(100, 230, 100, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.main(new String[]{});
                LibrarianLogin.this.dispose();
            }
        });
        panel.add(back);

        login = new JButton("Login");
        login.setFont(new Font("Arial", Font.PLAIN, 15));
        login.setBounds(220, 230, 100, 40);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameCheck = userName.getText();
                String passwordCheck = String.valueOf(passWord.getPassword());
                boolean status = LibrarianWdb.checkCredentials(usernameCheck, passwordCheck);
                if (status) {
                    LibrarianPage.main(new String[]{});
                    LibrarianLogin.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(LibrarianLogin.this,
                            "Invalid/Incorrect credentials", "Login Error",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        panel.add(login);

        this.add(panel);

    }

}
