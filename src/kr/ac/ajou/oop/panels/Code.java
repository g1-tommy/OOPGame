package kr.ac.ajou.oop.panels;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Code extends JPanel {

	private JLabel lblCode;
    private String code;
    
    public Code() {
    	lblCode = new JLabel();
    	setLayout(new FlowLayout());
    	
        TitledBorder titled = new TitledBorder("Code");
        setBorder(titled);
        
    	add(lblCode);
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
