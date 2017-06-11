package kr.ac.ajou.oop.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	
	private final static int LAST_LEVEL = 6;

	private JPanel contentPane;
	private JDialog dialog;
	private JButton btnSave, btnCheckMyAnswer;
	private JTextField tfName;
	private JFrame frame;
	private JLabel lblUsername, lblLevel, lblScore;
	private JTextArea lblGuidance, lblCode;
	
	private Code code;
	private Suggestion suggestion;
	private Input input;
	private Guidance guidance;
	private User user;
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game launcher = new Game();
					launcher.update();
					launcher.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Game() {
		frame = new JFrame("OOP Education Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(50, 50, 900, 800);
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
		
		JPanel userinfo = new JPanel();
		userinfo.setLayout(new BorderLayout(0, 0));
		
		lblScore = new JLabel();
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setText("Score: " + getUser().getScore());
		userinfo.add(lblScore, BorderLayout.WEST);
		
		lblLevel = new JLabel();
		lblLevel.setText("Level: " + getUser().getLevel());
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		userinfo.add(lblLevel, BorderLayout.CENTER);
		
		lblUsername = new JLabel();
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
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
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(userinfo, GroupLayout.DEFAULT_SIZE, 1258, Short.MAX_VALUE).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(guidance, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE).addComponent(input, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE).addComponent(situation, GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.RELATED).addComponent(code, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))).addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(userinfo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(guidance, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(situation, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(input, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)).addComponent(code, GroupLayout.DEFAULT_SIZE, 835, Short.MAX_VALUE)).addContainerGap()));
		
		lblCode = new JTextArea();
		lblCode.setEditable(false);
		lblCode.setHighlighter(null);
		lblCode.setBackground(SystemColor.window);
		code.add(lblCode);
		
		lblGuidance = new JTextArea();
		lblGuidance.setEditable(false);
		lblGuidance.setHighlighter(null);
		lblGuidance.setBackground(SystemColor.window);
		guidance.add(lblGuidance);
		
		btnCheckMyAnswer = new JButton("Check my Answer");
		btnCheckMyAnswer.addActionListener(this);
		GroupLayout gl_input = new GroupLayout(input);
		gl_input.setHorizontalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_input.createSequentialGroup().addContainerGap(287, Short.MAX_VALUE).addComponent(btnCheckMyAnswer)));
		gl_input.setVerticalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_input.createSequentialGroup().addContainerGap(110, Short.MAX_VALUE).addComponent(btnCheckMyAnswer)));
		input.setLayout(gl_input);
		contentPane.setLayout(gl_contentPane);
		
		dialog.getContentPane().add(userInfo);
		dialog.setBounds(100, 100, 450, 300);
		dialog.setSize(300, 60);
		dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	private void gameOver() {
		getUser().setGameOver(true);
		JOptionPane.showMessageDialog(null, "Game ended!", "Game Over", JOptionPane.OK_OPTION);
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
	
	private Input getInput() {
		return input;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnSave)){
			getUser().setName(tfName.getText().toString());
			getUser().setLevel(1);
			getUser().setScore(0);
			getUser().setGameOver(false);

			setUser(user);
			lblUsername.setText("Name: " + getUser().getName());
			dialog.setVisible(false);

			render();
			
			// Set Game state
			setID(State.STATE_GAME_INITIALIZE);
			update();
		}else if(e.getSource().equals(btnCheckMyAnswer)){
			if(getInput().compare(getUser().getLevel())) {
//				setID(State.STATE_ANSWER_CORRECT);
				setID(State.STATE_ANSWER_INCORRECT);
				update();
			}else{
//				setID(State.STATE_ANSWER_INCORRECT);
				setID(State.STATE_ANSWER_CORRECT);
				update();
			}
				
		}
	}

	@Override
	public void render() {
		getCode().load(getUser());
		getGuidance().load(getUser());
		lblGuidance.setText(getGuidance().getHint());
		lblCode.setText(getCode().getCode());
		lblLevel.setText("Level: " + getUser().getLevel());
		lblScore.setText("Score: " + getUser().getScore());
	}

	@Override
	public void update() {
		switch (getID()) {
		case State.STATE_GAME_INITIALIZE:
			JOptionPane.showMessageDialog(null, "Welcome! You can play game by reading our guidance, and just typing your answer in the panel. That is all. Fighting.", "How to Play", JOptionPane.OK_OPTION);
			setID(State.STATE_GAME_PLAY);
			break;
		case State.STATE_GAME_PLAY:
			break;
		case State.STATE_ANSWER_CORRECT:
			JOptionPane.showMessageDialog(null, "You're correct. Go to the next level.", "Great!", JOptionPane.OK_OPTION);
			getUser().setScore(getUser().getScore() + getUser().getLevel() * new Random().nextInt(100));
			setID(State.STATE_NEXT_LEVEL);
			break;
		case State.STATE_ANSWER_INCORRECT:
			JOptionPane.showMessageDialog(null, "Incorrect! Try Again.", "Incorrect!", JOptionPane.OK_OPTION);
			setID(State.STATE_GAME_PLAY);
			break;
		case State.STATE_HIGH_SCORE:
			JOptionPane.showMessageDialog(null, "You make the best score in this game ever before!", "Congrats!", JOptionPane.OK_OPTION);
			setID(State.STATE_GAME_OVER);
			break;
		case State.STATE_NEXT_LEVEL:
			if(getUser().getLevel() < Game.LAST_LEVEL){
				getUser().setLevel(getUser().getLevel() + 1);
				render();
				setID(State.STATE_GAME_PLAY);
			} else {
				if(FileManager.userDataExists() && FileManager.getUserScore() < getUser().getScore()) setID(State.STATE_HIGH_SCORE);
				else setID(State.STATE_GAME_OVER);
			}
			break;
		case State.STATE_GAME_OVER:
			gameOver();
			try {
				FileManager.saveUser(getUser());
			} catch (IOException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Your Information is saved in the directory.", "End!", JOptionPane.OK_OPTION);
			setID(State.STATE_EXIT);
			break;
		case State.STATE_EXIT:
			resetContent();
			break;
		}
		if(getID() != State.STATE_GAME_PLAY) update();
	}

	@Override
	public void resetContent() {
		System.exit(1);
	}

}