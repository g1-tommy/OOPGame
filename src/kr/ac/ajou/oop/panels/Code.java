package kr.ac.ajou.oop.panels;

import java.awt.Font;
import java.awt.SystemColor;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Code extends JPanel {

	private JTextArea lblCode;
	private String code;
    
    public Code() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    	setBorder(new TitledBorder(null, "Code", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lblCode = new JTextArea();
		lblCode.setFont(new Font("Helvetica Neue", Font.PLAIN, 15));
		lblCode.setEditable(false);
		lblCode.setHighlighter(null);
		lblCode.setBackground(SystemColor.control);
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
        lblCode.setText(this.code);
    }
    
    public JTextArea getLblCode() {
		return lblCode;
	}

}
