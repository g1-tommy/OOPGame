package kr.ac.ajou.oop.panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Code extends JPanel {

	private JPanel panel;
	private JLabel lblCode;
    private String code;
    
    public Code() {
    	setLayout(new BorderLayout());
    	
    	panel = new JPanel();
    	panel.setLayout(new BorderLayout());
    	
    	lblCode = new JLabel();
    	
        TitledBorder titled = new TitledBorder("Code");
        setBorder(titled);
        
        lblCode.setText("TEST");
        
    	panel.add(lblCode, BorderLayout.CENTER);
    	add(panel, BorderLayout.CENTER);
    	setVisible(true);
    }

    public boolean compare(int level) {
    	lblCode.setText(getCode());
        return false;
    }

    public void load(User u) {
    	try{
        	setCode(FileManager.loadAnswerCode(u.getLevel()));
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    }
}
