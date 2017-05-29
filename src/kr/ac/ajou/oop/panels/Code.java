package kr.ac.ajou.oop.panels;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Code extends JPanel {

    private String code;

    public boolean compare(int level) {
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
