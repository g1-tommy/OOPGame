package kr.ac.ajou.oop.panels;

import java.awt.Font;
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
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));

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
		btnCheckMyAnswer.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));

		answers.add(btnCheckMyAnswer);

		int elements = FileManager.getTFAmount(g.getUser().getLevel());

		inputs = new JLabel[elements];
		tfAnswer = new JTextField[elements];
		ishas = new JComboBox[elements];

		for (int i = 0; i < elements; i++) {
			inputs[i] = new JLabel("Answer" + (i + 1) + ":");
			inputs[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
			answers.add(inputs[i]);
			if (g.getUser().getLevel() == 2) {
				ishas[i] = new JComboBox<String>(new String[] { "is", "has" });
				ishas[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
				answers.add(ishas[i]);
			} else {
				tfAnswer[i] = new JTextField(10);
				tfAnswer[i].addActionListener(this);
				tfAnswer[i].setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
				answers.add(tfAnswer[i]);
			}
		}

	}

	public boolean compare(int level) {
<<<<<<< HEAD

		

		if (level == 1) {

			FileReader fReader;
			BufferedReader br;
			try {
				fReader = new FileReader("answer_1.txt");
				br = new BufferedReader(fReader);

				JLabel lblAnswer = new JLabel("Answer1:");
				JTextField answer_1 = new JTextField();
				answer_1.setColumns(10);

				JLabel lblAnswer_1 = new JLabel("Answer2:");
				JTextField answer_2 = new JTextField();
				answer_2.setColumns(10);

				this.add(lblAnswer);
				this.add(answer_1);
				this.add(lblAnswer_1);
				this.add(answer_2);
				
				String s;
				while ((s = br.readLine()) != null) {

					if (answer_1.getText() == s && answer_2.getText() == s) {
						return true;
					} else
						return false;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"File is not found",
						JOptionPane.ERROR_MESSAGE);
			} // level 1의 answer txt
			catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"Cannot read the input",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		else if (level == 2) {

			JTextArea textArea = new JTextArea(5, 30);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.add(textArea);

			setPreferredSize(new Dimension(450, 110));
			
		
			
			
			

		}

		else if (level == 3) {

			FileReader fReader;
			BufferedReader br;
			try {
				fReader = new FileReader("answer_2.txt");
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
				JOptionPane.showMessageDialog(Game.this,
						"File is not found",
						JOptionPane.ERROR_MESSAGE);
			} // level 1의 answer txt
			catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"Cannot read the input",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		else if (level == 4) {
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
=======
		try {
			arr = FileManager.answers(level);
			
			if(g.getUser().getLevel()==1) {
				if (!tfAnswer[0].getText().equals(arr.get(0)))
					return false;
			}
			if (g.getUser().getLevel() == 2) {
				for (int i = 0; i < arr.size(); i++) {
					if (!ishas[i].getSelectedItem().equals(arr.get(i)))
>>>>>>> branch 'master' of https://github.com/jwurbane97/oop_projects.git
						return false;

				}
<<<<<<< HEAD
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"File is not found",
						JOptionPane.ERROR_MESSAGE);
			} // level 1의 answer txt
			catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"Cannot read the input",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (level == 5) {
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
				JOptionPane.showMessageDialog(Game.this,
						"File is not found",
						JOptionPane.ERROR_MESSAGE);;
			} // level 1의 answer txt
			catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"Cannot read the input",
						JOptionPane.ERROR_MESSAGE);
=======
			} else {
				for (int i = 0; i < arr.size(); i++) {
					if (!tfAnswer[i].getText().equals(arr.get(i)))
						return false;

				}
>>>>>>> branch 'master' of https://github.com/jwurbane97/oop_projects.git
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD

		else if (level == 6) {
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
				JOptionPane.showMessageDialog(Game.this,
						"File is not found",
						JOptionPane.ERROR_MESSAGE);;
			} // level 1의 answer txt
			catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Game.this,
						"Cannot read the input",
						JOptionPane.ERROR_MESSAGE);
			}
		}

=======
		return true;
>>>>>>> branch 'master' of https://github.com/jwurbane97/oop_projects.git
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCheckMyAnswer) {
			if (compare(g.getUser().getLevel())) {
				g.setID(State.STATE_ANSWER_CORRECT);
			} else {
				g.setID(State.STATE_ANSWER_INCORRECT);
			}
			g.update();
		}
		else if(e.getSource() == tfAnswer[0]) {
			//System.out.println("aa");
		}
	}

}