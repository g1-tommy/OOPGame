package kr.ac.ajou.oop.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input extends JPanel implements ActionListener {

    private JTextField[] field;
    private JButton check;

    public Input() {
        super();
        check = new JButton("Check Answer");
        check.addActionListener(this);
        add(check);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
