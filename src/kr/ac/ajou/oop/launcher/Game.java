package kr.ac.ajou.oop.launcher;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.panels.Code;
import kr.ac.ajou.oop.panels.Guidance;
import kr.ac.ajou.oop.panels.Input;
import kr.ac.ajou.oop.panels.Suggestion;
import kr.ac.ajou.oop.state.GameState;
import kr.ac.ajou.oop.state.State;
import kr.ac.ajou.oop.user.User;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Game extends GameState {

	private JFrame frame;
	private JLabel lblName, lblScore, lblLevel;

	private User user;

	private Code code;
	private Suggestion suggestion;
	private Input input;
	private Guidance guidance;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Game() {

		frame = new JFrame("OOP Education Game");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		init();
		
		panel = new JPanel();
		lblScore = new JLabel();
		lblScore.setText("Score: " + getUser().getScore());
		lblLevel = new JLabel();
		lblLevel.setText("Level: " + getUser().getLevel());
		
				lblName = new JLabel();
				
						lblName.setText("Name: " + getUser().getName());
						GroupLayout gl_panel = new GroupLayout(panel);
						gl_panel.setHorizontalGroup(
							gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(5)
									.addComponent(lblScore)
									.addGap(260)
									.addComponent(lblLevel)
									.addPreferredGap(ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
									.addComponent(lblName))
						);
						gl_panel.setVerticalGroup(
							gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(5)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblScore)
											.addComponent(lblLevel))
										.addComponent(lblName)))
						);
						panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(suggestion, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(guidance, GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(input, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(code, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(115)
							.addComponent(suggestion, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(guidance, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 350, Short.MAX_VALUE)
									.addComponent(input, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
								.addComponent(code, GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE))))
					.addContainerGap(351, Short.MAX_VALUE))
		);
		GroupLayout gl_code = new GroupLayout(code);
		gl_code.setHorizontalGroup(
			gl_code.createParallelGroup(Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
		);
		gl_code.setVerticalGroup(
			gl_code.createParallelGroup(Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
		);
		code.setLayout(gl_code);
		GroupLayout gl_input = new GroupLayout(input);
		gl_input.setHorizontalGroup(
			gl_input.createParallelGroup(Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
		);
		gl_input.setVerticalGroup(
			gl_input.createParallelGroup(Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
		);
		input.setLayout(gl_input);
		GroupLayout gl_suggestion = new GroupLayout(suggestion);
		gl_suggestion.setHorizontalGroup(
			gl_suggestion.createParallelGroup(Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
		);
		gl_suggestion.setVerticalGroup(
			gl_suggestion.createParallelGroup(Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE)
		);
		suggestion.setLayout(gl_suggestion);
						GroupLayout gl_guidance = new GroupLayout(guidance);
						gl_guidance.setHorizontalGroup(
							gl_guidance.createParallelGroup(Alignment.LEADING)
								.addGap(0, 1155, Short.MAX_VALUE)
						);
						gl_guidance.setVerticalGroup(
							gl_guidance.createParallelGroup(Alignment.LEADING)
								.addGap(0, 773, Short.MAX_VALUE)
						);
						guidance.setLayout(gl_guidance);
		frame.getContentPane().setLayout(groupLayout);

		frame.setSize(1280, 960);
		frame.setVisible(true);
	}

	@Override
	public void render() {

	}

	@Override
	public void update() {
		switch (getID()) {
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
		code = new Code();
		suggestion = new Suggestion();
		input = new Input();
		guidance = new Guidance();

		setUser(new User());

		try {
			FileManager.saveUser(getUser());
			prepareLevel();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		setID(State.STATE_GAME_INITIALIZE);

	}

	private void gameOver() {
		getUser().setGameOver(true);
	}

	private void prepareLevel() throws IOException, ClassNotFoundException {
		getCode().setCode(FileManager.loadAnswerCode(getUser().getLevel()));
		getGuidance().setHint(FileManager.loadGuidance(getUser().getLevel()));
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

}
