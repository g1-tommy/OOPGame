package kr.ac.ajou.oop.panels;

import java.awt.Font;
import java.awt.SystemColor;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Guidance extends JPanel {
	
	private JTextArea lblGuidance;
	private JScrollPane scroll;
	private String hint;
    
    public Guidance() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBorder(new TitledBorder(null, "Guidance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblGuidance = new JTextArea();
		lblGuidance.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
		lblGuidance.setEditable(false);
		lblGuidance.setHighlighter(null);
		lblGuidance.setBackground(SystemColor.control);
		
		scroll = new JScrollPane(lblGuidance);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(scroll);
    }

    public void load(User u) {
    	try{
        	setHint(FileManager.loadGuidance(u.getLevel()));
    	}catch(IOException e) {
    		e.printStackTrace();
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