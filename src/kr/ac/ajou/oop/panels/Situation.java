package kr.ac.ajou.oop.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class Situation extends JPanel
{
	private Image img;
	public Situation(){}
	
	public void load(User u)
	{
		try {
			img = FileManager.loadImage(u.getLevel());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(new ImageIcon(img).getImage(), 0, 0, getWidth(), getHeight(), null);
	}
	
}
