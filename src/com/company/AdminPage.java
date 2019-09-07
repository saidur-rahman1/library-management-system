package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends JFrame {

    static AdminPage frame;
    JPanel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new AdminPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminPage() {

        super("Admin Page");
        this.setSize(470,500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Admin Page");
        title.setFont(new  Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(150,25, 200, 50);
        panel.add(title);

        JButton addLibrarian = new JButton("Add Librarian");
        addLibrarian.setFont(new Font("Arial", Font.PLAIN, 15));
        addLibrarian.setBounds(125, 100, 200, 40);
        addLibrarian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddLibrarian.main(new String[]{});
            }
        });
        panel.add(addLibrarian);

        JButton viewLibrarians = new JButton("View Librarians");
        viewLibrarians.setFont(new Font("Arial", Font.PLAIN, 15));
        viewLibrarians.setBounds(125, 160, 200, 40);
        viewLibrarians.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewLibrarian.main(new String[]{});
            }
        });
        panel.add(viewLibrarians);

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 15));
        logout.setBounds(125, 220, 200, 40);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Start.main(new String[]{});
                frame.dispose();
            }
        });
        panel.add(logout);

        this.add(panel);

    }

}
