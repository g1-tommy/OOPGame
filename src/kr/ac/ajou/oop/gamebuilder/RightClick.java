package kr.ac.ajou.oop.gamebuilder;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class RightClick {
	
	Image img0, img1;
	int x, y, LineSelected;
	boolean OnDelete;
	
	public RightClick(){
		OnDelete = false; LineSelected = -1;
		try {
			img0 = new Image("resB/RightClick0.png"); img1 = new Image("resB/RightClick1.png");
		} catch (SlickException e) {e.printStackTrace();}
	}
	
	
	
	//Change image if the cursor is on the Delete button
	public boolean update(int MX, int MY, boolean Click){
		
		//Keeping in mind: SizeX = 80; SizeY = 26;
		
		if (x < MX && MX < x + 80 && y < MY && MY < y + 26){
			OnDelete = true;
			if (Click){
				return true;
			}
		}
		else {OnDelete = false;}
		
		return false;
	}
	
	
	
	public void render(GameContainer gc, org.newdawn.slick.Graphics g){
		if (!OnDelete){ img0.draw( x, y); }
		else if (OnDelete){ img1.draw( x, y); }
		
	}
	
}
