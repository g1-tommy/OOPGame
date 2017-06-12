package kr.ac.ajou.oop.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Situation extends JPanel
{
	private Image img;
	public Situation(){
		setBorder(new TitledBorder(null, "Situation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}
	
	public void load(User u)
	{
		try {
			setImg(FileManager.loadImage(u.getLevel()));
		} catch (IOException e) {
			e.printStackTrace();//
		}
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(new ImageIcon(img).getImage(), 0, 0, null);
	}
	
}
