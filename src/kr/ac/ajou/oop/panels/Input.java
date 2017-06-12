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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.launcher.Game;
import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.state.State;

@SuppressWarnings("serial")
public class Input extends JPanel implements ActionListener {

	private JPanel answers;
	private JButton btnCheckMyAnswer;
	private Game g;
	private JLabel[] inputs;
	private JTextField[] tfAnswer;
	private JComboBox<String>[] ishas;
	private ArrayList<String> arr;

	public Input(Game g) {
		setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.g = g;

		answers = new JPanel();
		answers.setLayout(new BoxLayout(answers, BoxLayout.PAGE_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(answers);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
	}

	public void setComponents() {
		answers.removeAll();
		repaint();
		
		btnCheckMyAnswer = new JButton("Check my Answer");
		btnCheckMyAnswer.addActionListener(this);
		
		answers.add(btnCheckMyAnswer);
		
		int elements = FileManager.getTFAmount(g.getUser().getLevel());
		
		inputs = new JLabel[elements];
		tfAnswer = new JTextField[elements];
		ishas = new JComboBox[elements];
		
		for (int i = 0; i < elements; i++) {
			inputs[i] = new JLabel("Answer" + (i + 1) + ":");
			answers.add(inputs[i]);
			if (g.getUser().getLevel() == 2) {
				ishas[i] = new JComboBox<String>(new String[]{"is", "has"});
				answers.add(ishas[i]);
			} else {
				tfAnswer[i] = new JTextField(10);
				answers.add(tfAnswer[i]);
			}
		}
		
	}

	public boolean compare(int level) {
		boolean correct = false;
		try {
			arr = FileManager.answers(level);
			if (g.getUser().getLevel() == 2) {
				for (int i = 0; i < arr.size(); i++) {
					if (ishas[i].getSelectedItem().equals(arr.get(i)))
						correct = true;
				}
			} else {
				for (int i = 0; i < arr.size(); i++) {
					if (!tfAnswer[i].getText().equals(arr.get(i)))
						correct = true;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return correct;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (g.getUser().getLevel() == 1 || compare(g.getUser().getLevel())) {
			g.setID(State.STATE_ANSWER_CORRECT);
		} else {
			g.setID(State.STATE_ANSWER_INCORRECT);
		}
		g.update();
	}

}
