package kr.ac.ajou.oop.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.panels.Code;
import kr.ac.ajou.oop.panels.Guidance;
import kr.ac.ajou.oop.panels.Input;
import kr.ac.ajou.oop.panels.Suggestion;
import kr.ac.ajou.oop.state.GameState;
import kr.ac.ajou.oop.state.State;
import kr.ac.ajou.oop.user.User;

public class Game extends GameState implements ActionListener {

	private JPanel contentPane;
	private JDialog dialog;
	private JButton btnSave;
	private JTextField tfName;
	private JFrame frame;
	
	private Code code;
	private Suggestion suggestion;
	private Input input;
	private Guidance guidance;
	private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game launcher = new Game();
					launcher.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game() {
		frame = new JFrame("OOP Education Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		user = new User();
		dialog = new JDialog(frame, true);
		JPanel userInfo = new JPanel();
		JLabel childlblName = new JLabel("Your name:");

		tfName = new JTextField(10);
		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
	
		userInfo.add(childlblName, BorderLayout.WEST);
		userInfo.add(tfName, BorderLayout.CENTER);
		userInfo.add(btnSave, BorderLayout.EAST);

		code = new Code();
		suggestion = new Suggestion();
		input = new Input();
		guidance = new Guidance();
		
		dialog.getContentPane().add(userInfo);
		dialog.setBounds(100, 100, 450, 300);
		dialog.setSize(300, 60);
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
		
		
		JPanel userinfo = new JPanel();
		userinfo.setLayout(new BorderLayout(0, 0));
		
		JLabel lblScore = new JLabel();
		lblScore.setText("Score: " + getUser().getScore());
		userinfo.add(lblScore, BorderLayout.WEST);
		
		JLabel lblLevel = new JLabel();
		lblLevel.setText("Level: " + getUser().getLevel());
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		userinfo.add(lblLevel, BorderLayout.CENTER);
		
		JLabel lblUsername = new JLabel();
		lblUsername.setText("Name: " + getUser().getName());
		userinfo.add(lblUsername, BorderLayout.EAST);
		
		JPanel guidance = new JPanel();
		guidance.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Guidance", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Guidance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel code = new JPanel();
		code.setBorder(new TitledBorder(null, "Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel situation = new JPanel();
		situation.setBorder(new TitledBorder(null, "Situation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel input = new JPanel();
		input.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(input, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(situation, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(guidance, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(code, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE))
						.addComponent(userinfo, GroupLayout.PREFERRED_SIZE, 678, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(userinfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(guidance, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(situation, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(input, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE))
						.addComponent(code, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JButton btnCheckMyAnswer = new JButton("Check my Answer");
		GroupLayout gl_input = new GroupLayout(input);
		gl_input.setHorizontalGroup(
			gl_input.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_input.createSequentialGroup()
					.addContainerGap(287, Short.MAX_VALUE)
					.addComponent(btnCheckMyAnswer))
		);
		gl_input.setVerticalGroup(
			gl_input.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_input.createSequentialGroup()
					.addContainerGap(110, Short.MAX_VALUE)
					.addComponent(btnCheckMyAnswer))
		);
		input.setLayout(gl_input);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void gameOver() {
		getUser().setGameOver(true);
	}

	private void prepareLevel() throws IOException, ClassNotFoundException {
		getCode().load(getUser());
		getGuidance().load(getUser());
	}

	private User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

	private Code getCode() {
		return code;
	}

	private Guidance getGuidance() {
		return guidance;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		getUser().setName(tfName.getText().toString());
		getUser().setLevel(1);
		getUser().setScore(0);
		getUser().setGameOver(false);

		setUser(user);
		dialog.setVisible(false);

		try {
			FileManager.saveUser(getUser());
			prepareLevel();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		// Set Game state
		setID(State.STATE_GAME_INITIALIZE);
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		switch (getID()) {
		case State.STATE_GAME_INITIALIZE:
			setID(State.STATE_GAME_PLAY);
			break;
		case State.STATE_GAME_PLAY:

			break;
		case State.STATE_ANSWER_CORRECT:

			setID(State.STATE_NEXT_LEVEL);
			break;
		case State.STATE_ANSWER_INCORRECT:

			break;
		case State.STATE_HIGH_SCORE:

			setID(State.STATE_GAME_OVER);
			break;
		case State.STATE_NEXT_LEVEL:
			try {
				prepareLevel();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case State.STATE_GAME_OVER:
			gameOver();
			setID(State.STATE_EXIT);
			break;
		case State.STATE_EXIT:
			System.exit(1);
			break;
		}
	}

	@Override
	public void resetContent() {
		// TODO Auto-generated method stub
		
	}
}
