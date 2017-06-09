package kr.ac.ajou.oop.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Input extends JPanel implements ActionListener {

    private JTextField[] fields;
    private JButton check;

    public Input() {
        setLayout(new BorderLayout());
        TitledBorder titled = new TitledBorder("Your answer:");
        setBorder(titled);

        check = new JButton("Check Answer");
        check.addActionListener(this);

        add(check, BorderLayout.CENTER);
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
