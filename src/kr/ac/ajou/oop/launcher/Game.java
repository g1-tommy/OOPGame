package kr.ac.ajou.oop.launcher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.panels.Code;
import kr.ac.ajou.oop.panels.Guidance;
import kr.ac.ajou.oop.panels.Input;
import kr.ac.ajou.oop.panels.Suggestion;
import kr.ac.ajou.oop.state.GameState;
import kr.ac.ajou.oop.state.State;
import kr.ac.ajou.oop.user.User;

public class Game extends GameState implements MouseListener {

	private JFrame frame;
	private JDialog dialog;
	private JPanel panel;
	private JLabel lblName, lblScore, lblLevel, childlblName;
	private JButton btnSave;
	private JTextField tfName;

	private Code code;
	private Suggestion suggestion;
	private Input input;
	private Guidance guidance;

	private User user;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
					window.update();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Game() {
		
		setID(State.STATE_GAME_READY);

		frame = new JFrame("OOP Education Game");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		user = new User();
		
		code = new Code();
		suggestion = new Suggestion();
		input = new Input();
		guidance = new Guidance();
		
		dialog = new JDialog(frame, true);
		JPanel userInfo = new JPanel();

		childlblName = new JLabel("Your name:");
		tfName = new JTextField(10);
		btnSave = new JButton("Save");
		btnSave.addMouseListener(this);

		userInfo.add(childlblName, BorderLayout.WEST);
		userInfo.add(tfName, BorderLayout.CENTER);
		userInfo.add(btnSave, BorderLayout.EAST);

		dialog.getContentPane().add(userInfo);
		dialog.setBounds(100, 100, 450, 300);
		dialog.setSize(300, 60);
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);

		init();

		frame.setSize(1280, 960);
		frame.setVisible(true);
	}

	@Override
	public void render() {

	}

	@Override
	public void update() {
		System.out.println(getID());
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

	}

	private void init() {

		// User panel
		panel = new JPanel();

		lblScore = new JLabel();
		lblScore.setText("Score: " + getUser().getScore());

		lblLevel = new JLabel();
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setText("Level: " + getUser().getLevel());

		lblName = new JLabel();
		lblName.setText("Name: " + getUser().getName());

		panel.setLayout(new BorderLayout());
		
		panel.add(lblScore, BorderLayout.WEST);
		panel.add(lblLevel, BorderLayout.CENTER);
		panel.add(lblName, BorderLayout.EAST);

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(suggestion, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(guidance, GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(input, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))).addPreferredGap(ComponentPlacement.RELATED).addComponent(code, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addGroup(groupLayout.createSequentialGroup().addGap(115).addComponent(suggestion, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)).addGroup(groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addComponent(guidance, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 350, Short.MAX_VALUE).addComponent(input, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)).addComponent(code, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)))).addContainerGap(351, Short.MAX_VALUE)));
		
		GroupLayout gl_code = new GroupLayout(code);
		gl_code.setHorizontalGroup(gl_code.createParallelGroup(Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		gl_code.setVerticalGroup(gl_code.createParallelGroup(Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		code.setLayout(gl_code);
		
		GroupLayout gl_input = new GroupLayout(input);
		gl_input.setHorizontalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		gl_input.setVerticalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		input.setLayout(gl_input);
		
		GroupLayout gl_suggestion = new GroupLayout(suggestion);
		gl_suggestion.setHorizontalGroup(gl_suggestion.createParallelGroup(Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		gl_suggestion.setVerticalGroup(gl_suggestion.createParallelGroup(Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		suggestion.setLayout(gl_suggestion);
		
		GroupLayout gl_guidance = new GroupLayout(guidance);
		gl_guidance.setHorizontalGroup(gl_guidance.createParallelGroup(Alignment.LEADING).addGap(0, 1155, Short.MAX_VALUE));
		gl_guidance.setVerticalGroup(gl_guidance.createParallelGroup(Alignment.LEADING).addGap(0, 773, Short.MAX_VALUE));
		guidance.setLayout(gl_guidance);
		
		frame.getContentPane().setLayout(groupLayout);
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
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
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
