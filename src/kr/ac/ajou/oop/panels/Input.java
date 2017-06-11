package kr.ac.ajou.oop.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
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
		gl_input.setHorizontalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_input.createSequentialGroup().addContainerGap(287, Short.MAX_VALUE).addComponent(btnCheckMyAnswer)));
		gl_input.setVerticalGroup(gl_input.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_input.createSequentialGroup().addContainerGap(110, Short.MAX_VALUE).addComponent(btnCheckMyAnswer)));
		
    	setLayout(gl_input);
    }
    
    public boolean compare(int level) {
    	return false;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(compare(g.getUser().getLevel())) {
//			g.setID(State.STATE_ANSWER_CORRECT);
			g.setID(State.STATE_ANSWER_INCORRECT);
			g.update();
		}else{
//			g.setID(State.STATE_ANSWER_INCORRECT);
			g.setID(State.STATE_ANSWER_CORRECT);
			g.update();
		}
	}
    
}
