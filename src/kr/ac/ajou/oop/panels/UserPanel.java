package kr.ac.ajou.oop.panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class UserPanel extends JPanel {
	
	private JLabel lblUsername, lblLevel, lblScore;
	
	public UserPanel(User u) {
		setLayout(new BorderLayout(0, 0));
		
		lblScore = new JLabel();
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setText("Score: " + u.getScore());
		
		lblLevel = new JLabel();
		lblLevel.setText("Level: " + u.getLevel());
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUsername = new JLabel();
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.setText("Name: " + u.getName());
		
		add(lblScore, BorderLayout.WEST);
		add(lblLevel, BorderLayout.CENTER);
		add(lblUsername, BorderLayout.EAST);
	}

	public JLabel getLblUsername() {
		return lblUsername;
	}

	public JLabel getLblLevel() {
		return lblLevel;
	}

	public JLabel getLblScore() {
		return lblScore;
	}
	
}
