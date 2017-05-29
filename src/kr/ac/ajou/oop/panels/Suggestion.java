package kr.ac.ajou.oop.panels;

import javax.swing.*;

public class Suggestion extends JPanel {
	private JLabel suggestion = new JLabel();
	private JPanel panel = new JPanel();

	public Suggestion() {

	}

	public void load(int level) {
		switch (level) {
		case 1:
			suggestion.setText("Before we make main character, we have to describe what gorilla is.\n"
					+ "And we called it a ¡®Class¡¯. Class is blueprint of object.\n"
					+ "If we want to create a gorilla, what we have to make before? ___________ class\n"
					+ "Now we made just gorilla. Let¡¯s make real gorilla object.\n"
					+ "Decide name of gorilla character : ");
			panel.add(suggestion);
			break;
		case 2:
			suggestion.setText("Choose the right answers:\n"
					+ "\n"
					+ "(example)\n"
					+ "-enemy (is) a character.\n"
					+ "-enemy (has) a baton\n"
					+ "-enemy (has) a knife\n"
					+ "-enemy (has) a blaster\n"
					+ "-Gorila (is/has) a character.\n"
					+ "-Gorila (is/has) a booster\n"
					+ "-Gorila (is/has) a gun");

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		case 6:

			break;

		}
	}
}
