package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianPage extends JFrame {

    static LibrarianPage frame;
    JPanel panel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new LibrarianPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LibrarianPage() {

        super("Librarian Page");
        this.setSize(470,600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Librarian Page");
        title.setFont(new  Font("Comic Sans MS", Font.PLAIN, 26));
        title.setBounds(130,25, 200, 50);
        panel.add(title);

        JButton addBook = new JButton("Add Book");
        addBook.setFont(new Font("Arial", Font.PLAIN, 15));
        addBook.setBounds(125, 100, 200, 25);
        addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBook.main(new String[]{});
            }
        });
        panel.add(addBook);

        JButton viewBooks = new JButton("View Books");
        viewBooks.setFont(new Font("Arial", Font.PLAIN, 15));
        viewBooks.setBounds(125, 140, 200, 25);
        viewBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBook.main(new String[]{});
            }
        });
        panel.add(viewBooks);

        JButton issueBook = new JButton("Issue Book");
        issueBook.setFont(new Font("Arial", Font.PLAIN, 15));
        issueBook.setBounds(125, 180, 200, 25);
        issueBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IssueBook.main(new String[]{});
            }
        });
        panel.add(issueBook);

        JButton viewIssuedBooks = new JButton("View Issued Books");
        viewIssuedBooks.setFont(new Font("Arial", Font.PLAIN, 15));
        viewIssuedBooks.setBounds(125, 220, 200, 25);
        viewIssuedBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewIssued.main(new String[]{});
            }
        });
        panel.add(viewIssuedBooks);

        JButton returnBook = new JButton("Return Book");
        returnBook.setFont(new Font("Arial", Font.PLAIN, 15));
        returnBook.setBounds(125, 260, 200, 25);
        returnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReturnBook.main(new String[]{});
            }
        });
        panel.add(returnBook);

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 15));
        logout.setBounds(125, 300, 200, 25);
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
