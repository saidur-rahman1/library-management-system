package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class AddLibrarian extends JFrame {

    static AddLibrarian frame;
    JPanel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new AddLibrarian();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AddLibrarian() {

        super("Add Librarian");
        this.setSize(500,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Add Librarian");
        title.setFont(new  Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(150,25, 200, 50);
        panel.add(title);

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        nameLabel.setBounds(50, 100, 200, 20);
        panel.add(nameLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameLabel.setBounds(50, 140, 200, 20);
        panel.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordLabel.setBounds(50, 180, 200, 20);
        panel.add(passwordLabel);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emailLabel.setBounds(50, 220, 200, 20);
        panel.add(emailLabel);

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        addressLabel.setBounds(50, 260, 200, 20);
        panel.add(addressLabel);

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneLabel.setBounds(50, 300, 200, 20);
        panel.add(phoneLabel);

        JTextField nameTf = new JTextField();
        nameTf.setFont(new Font("Arial", Font.PLAIN, 15));
        nameTf.setBounds(200, 100, 200, 25);
        panel.add(nameTf);

        JTextField usernameTf = new JTextField();
        usernameTf.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameTf.setBounds(200, 140, 200, 25);
        panel.add(usernameTf);

        JTextField passwordTf = new JTextField();
        passwordTf.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordTf.setBounds(200, 180, 200, 25);
        panel.add(passwordTf);

        JTextField emailTf = new JTextField();
        emailTf.setFont(new Font("Arial", Font.PLAIN, 15));
        emailTf.setBounds(200, 220, 200, 25);
        panel.add(emailTf);

        JTextField addressTf = new JTextField();
        addressTf.setFont(new Font("Arial", Font.PLAIN, 15));
        addressTf.setBounds(200, 260, 200, 25);
        panel.add(addressTf);

        JTextField phoneTf = new JTextField();
        phoneTf.setFont(new Font("Arial", Font.PLAIN, 15));
        phoneTf.setBounds(200, 300, 200, 25);
        panel.add(phoneTf);

        JLabel noteLabel = new JLabel("Note: All fields must be filled in");
        noteLabel.setFont(new Font("Arial", Font.BOLD, 15));
        noteLabel.setBounds(100, 340, 300, 20);
        panel.add(noteLabel);

        JButton add = new JButton("Add");
        add.setFont(new Font("Arial", Font.PLAIN, 15));
        add.setBounds(190, 400, 100, 40);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTf.getText().trim();
                String username = usernameTf.getText().trim();
                String password = passwordTf.getText().trim();
                String email = emailTf.getText().trim();
                String address = addressTf.getText().trim();
                String phone = phoneTf.getText().trim();
                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(AddLibrarian.this, "Please fill out all fields");
                } else if ((password.length() < 6) || (!password.matches(".*[a-zA-Z].*")) || (!password.matches(".*[0-9].*"))) {
                    JOptionPane.showMessageDialog(AddLibrarian.this, "Invalid password format, " +
                            "make sure the password is atleast 6 characters and has both letters and numbers");
                } else if (!emailCheck(email)) {
                    JOptionPane.showMessageDialog(AddLibrarian.this, "Invalid email address");
                } else if (!phone.matches("[0-9]+") || phone.length() != 10) {
                    JOptionPane.showMessageDialog(AddLibrarian.this, "Invalid phone number. Make sure it has 10 digits (numbers).");
                } else {
                    int i = LibrarianWdb.save(name, username, password, email, address, phone);
                    switch (i) {
                        case 1:
                            JOptionPane.showMessageDialog(AddLibrarian.this, "Successfully added");
                            //AdminPage.main(new String[]{});
                            frame.dispose();
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(AddLibrarian.this, "Username already " +
                                    "taken. Please try a different username.");
                            break;
                        case 3:
                            JOptionPane.showMessageDialog(AddLibrarian.this, "The email address " +
                                    "entered is already being used by another librarian. Please use a different email address.");
                            break;
                        case 4:
                            JOptionPane.showMessageDialog(AddLibrarian.this, "The phone number " +
                                    "entered is already being used by another librarian. Please use a different number.");
                            break;
                        default:
                            JOptionPane.showMessageDialog(AddLibrarian.this, "Error, could not " +
                                    "add librarian. Please try again or contact the system administrator");
                            break;
                    }
                } /*------xxx------- */
            }
        });
        panel.add(add);

        this.add(panel);

    }

    public static boolean emailCheck(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
