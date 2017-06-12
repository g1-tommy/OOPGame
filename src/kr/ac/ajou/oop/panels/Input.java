package kr.ac.ajou.oop.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.launcher.Game;
import kr.ac.ajou.oop.state.State;

@SuppressWarnings("serial")
public class Input extends JPanel implements ActionListener {

	private JButton btnCheckMyAnswer;
	private Game g;

	public Input(Game g) {
		this.g = g;

		setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		btnCheckMyAnswer = new JButton("Check my Answer");
		btnCheckMyAnswer.addActionListener(this);

		GroupLayout gl_input = new GroupLayout(this);
		gl_input.setHorizontalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_input.createSequentialGroup().addContainerGap(287, Short.MAX_VALUE).addComponent(btnCheckMyAnswer)));
		gl_input.setVerticalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_input.createSequentialGroup().addContainerGap(110, Short.MAX_VALUE).addComponent(btnCheckMyAnswer)));

		setLayout(gl_input);
	}

	public boolean compare(int level) {

		if (level == 1) {

			FileReader fReader;
			BufferedReader br;
			try {
				fReader = new FileReader("text.txt");
				br = new BufferedReader(fReader);

				JLabel lblAnswer = new JLabel("Answer1:");
				JTextField answer_1 = new JTextField();
				answer_1.setColumns(10);

				JLabel lblAnswer_1 = new JLabel("Answer2:");
				JTextField answer_2 = new JTextField();
				answer_2.setColumns(10);

				String s;
				while ((s = br.readLine()) != null) {

					if (answer_1.getText() == s && answer_2.getText() == s) {
						return true;
					} else
						return false;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // level 1ÀÇ answer txt
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (level == 2) {

			JTextArea textArea = new JTextArea(5, 30);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.add(textArea);

			setPreferredSize(new Dimension(450, 110));

		}

		else if (level == 3) {

			JLabel lblAnswer = new JLabel("Answer1:");
			JTextField answer_1 = new JTextField();
			answer_1.setColumns(10);

			JLabel lblAnswer_1 = new JLabel("Answer2:");
			JTextField answer_2 = new JTextField();
			answer_2.setColumns(10);

		}

		else if (level == 3) {
			JLabel lblAnswer = new JLabel("Answer1:");
			JTextField answer_1 = new JTextField();
			answer_1.setColumns(10);

			JLabel lblAnswer_1 = new JLabel("Answer2:");
			JTextField answer_2 = new JTextField();
			answer_2.setColumns(10);
		}

		else if (level == 4) {
			JLabel lblAnswer = new JLabel("Answer1:");
			JTextField answer_1 = new JTextField();
			answer_1.setColumns(10);

			JLabel lblAnswer_1 = new JLabel("Answer2:");
			JTextField answer_2 = new JTextField();
			answer_2.setColumns(10);

		}

		else if (level == 5) {
			JLabel lblAnswer = new JLabel("Answer1:");
			JTextField answer_1 = new JTextField();
			answer_1.setColumns(10);

			JLabel lblAnswer_1 = new JLabel("Answer2:");
			JTextField answer_2 = new JTextField();
			answer_2.setColumns(10);
		}

		else if (level == 6) {
			JLabel lblAnswer = new JLabel("Answer1:");
			JTextField answer_1 = new JTextField();
			answer_1.setColumns(10);

			JLabel lblAnswer_1 = new JLabel("Answer2:");
			JTextField answer_2 = new JTextField();
			answer_2.setColumns(10);
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
