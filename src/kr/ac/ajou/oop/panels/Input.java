package kr.ac.ajou.oop.panels;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Input extends JPanel{

    public Input() {
    	setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    }
    
    public boolean compare(int level) {
    	return false;
    }
    
}
