package kr.ac.ajou.oop.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.launcher.Game;
import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.state.State;

@SuppressWarnings("serial")
public class Input extends JPanel implements ActionListener {

	private JButton btnCheckMyAnswer;
	private Game g;
	private JLabel[] inputs;
	private JTextField[] tfAnswer;
	private JComboBox[] ishas;
	private ArrayList arr;
	
	public Input(Game g) {
		this.g = g;

		setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		btnCheckMyAnswer = new JButton("Check my Answer");
		btnCheckMyAnswer.addActionListener(this);

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	public void setComponents() {
		removeAll();
		add(btnCheckMyAnswer);
		inputs = new JLabel[FileManager.getTFAmount(g.getUser().getLevel())];
		tfAnswer = new JTextField[FileManager.getTFAmount(g.getUser().getLevel())];
		ishas = new JComboBox[FileManager.getTFAmount(g.getUser().getLevel())];
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = new JLabel("Answer" + (i + 1) + ":\n");
			add(inputs[i]);
			if (g.getUser().getLevel() == 2) {
				String[] s = { "is", "has" };
				ishas[i] = new JComboBox(s);
				add(ishas[i]);
			} else {
				tfAnswer[i] = new JTextField(10);
				add(tfAnswer[i]);
			}

		}
	}

	public boolean compare(int level) {
		try {
			arr = FileManager.answers(level);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (compare(g.getUser().getLevel()) == true) {
			g.setID(State.STATE_ANSWER_CORRECT);
			g.update();
		} else {
			g.setID(State.STATE_ANSWER_INCORRECT);
			g.update();
		}
	}

}
