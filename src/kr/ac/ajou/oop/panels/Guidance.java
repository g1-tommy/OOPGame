package kr.ac.ajou.oop.panels;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Guidance extends JPanel {
	
	private JTextArea lblGuidance;
	private String hint;
    
    public Guidance() {
    	setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Guidance", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "Guidance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblGuidance = new JTextArea();
		lblGuidance.setEditable(false);
		lblGuidance.setHighlighter(null);
		lblGuidance.setBackground(SystemColor.window);
		add(lblGuidance);
    }

    public void load(User u) {
    	try{
        	setHint(FileManager.loadGuidance(u.getLevel()));
    	}catch(IOException e) {
			JOptionPane.showMessageDialog(Game.this,
					"Cannot read the input",
					JOptionPane.ERROR_MESSAGE);;
		}
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
    
    public JTextArea getLblGuidance() {
		return lblGuidance;
	}

}