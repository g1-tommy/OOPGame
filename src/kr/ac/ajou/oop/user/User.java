package kr.ac.ajou.oop.user;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class User extends JFrame implements MouseListener {

    private int score;
    private int level;
    private String name;
    private boolean isGameOver;

    private JLabel lblName;
    private JButton btnSave;
    private JTextField tfName;

    public User() {
        setTitle("Type your info");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblName = new JLabel("Your name:");
        tfName = new JTextField(10);
        btnSave = new JButton("Save");

        btnSave.addMouseListener(this);

        add(lblName, BorderLayout.WEST);
        add(tfName, BorderLayout.CENTER);
        add(btnSave, BorderLayout.EAST);

        setSize(400,100);
        setVisible(true);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score < 0) System.out.println("Score cannot be negative.");
        else this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if(level < 0) System.out.println("Level cannot be negative.");
        else this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length() <= 0) System.out.println("Plz type your name.");
        else this.name = name;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setName(tfName.getText());
        setLevel(1);
        setScore(0);
        setGameOver(false);
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
}
