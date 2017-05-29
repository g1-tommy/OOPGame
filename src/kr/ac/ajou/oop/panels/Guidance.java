package kr.ac.ajou.oop.panels;

import java.io.IOException;

import javax.swing.JPanel;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Guidance extends JPanel {
    private String hint;

    public void load(User u) {
    	try{
        	setHint(FileManager.loadAnswerCode(u.getLevel()));
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

}