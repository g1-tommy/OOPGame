package kr.ac.ajou.oop.gamebuilder;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Palette {
	Image img;
	int x, x0, y, y0, SizeX, SizeY;
	String Name;
	boolean Grabbed;
	
	public Palette( int x, int y, String Name ){
		
		this.x = x; x0 = x;
		this.y = y; y0 = y;
		this.Name = Name;
		
		
		
		try {
			if (Name == "Harambe"){ img = new Image("resB/Icon Harambe.png"); }
			else if (Name == "Baton"){ img = new Image("resB/Icon Baton.png"); }
			else if (Name == "Rifle"){ img = new Image("resB/Icon Rifle.png"); }
			else if (Name == "Blaster"){ img = new Image("resB/Icon Blaster.png"); }
			else if (Name == "Bad Guy"){ img = new Image("resB/Icon Bad Guy.png"); }
			else if (Name == "Engine"){ img = new Image("resB/Icon Engine.png"); }
			//else if (Name == "Projectile"){ img = new Image("resB/Icon Projectile.png"); }
			else if (Name == "Character"){ img = new Image("resB/Icon Character.png"); }
		} catch (SlickException e) { e.printStackTrace(); }
		
		SizeX = img.getWidth(); SizeY = img.getHeight();
		
		Grabbed = false;
	}
	
	
	public void update(GameContainer gc, int delta, int MX, int MY){
		
		//Everything going well
		if (!(MX == -1 && MY == -1)){
			if ( !Grabbed && x < MX && MX < x + SizeX && y < MY && MY < y + SizeY){
				Grabbed = true;
			}
			
			if (Grabbed){
				x = MX - 35; y = MY - 17;
			}
		}
		
		//Code for when we drop it
		else if (MX == -1 && MY == -1){
			x = x0; y = y0;
		}
		
		
	}
	
	
	
	public void render(GameContainer gc, org.newdawn.slick.Graphics g){
		//img.draw( x, y, (int)(0.7*img.getWidth()), (int)(0.7*img.getHeight()));
		img.draw( x, y);
	}

			
	
}
