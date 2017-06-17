package kr.ac.ajou.oop.gamebuilder;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Button {

	Image img, imgClicked;
	int x, y, SizeX, SizeY;
	int Type; //0: Mouse, 1: IS-A, 2: HAS-A
	boolean Selected;
	
	
	//Constructor
	public Button( int x, int y, int Type ){
			
		this.x = x; 
		this.y = y; 
		this.Type = Type;
		
		SizeY = 50;
		
		try {
			if (Type == 0){ //Mouse
				img = new Image("resB/Button Mouse.png"); imgClicked = new Image("resB/ButtonC Mouse.png"); 
				Selected = true;
				SizeX = 48;
			}
			else if (Type == 1){ //IS-A
				img = new Image("resB/Button ISA.png"); imgClicked = new Image("resB/ButtonC ISA.png"); 
				Selected = false;
				SizeX = 80;
			}
			else if (Type == 2){ //HAS-A
				img = new Image("resB/Button HASA.png"); imgClicked = new Image("resB/ButtonC HASA.png");
				Selected = false;
				SizeX = 80;
				
			}
		} catch (SlickException e) {e.printStackTrace();}
		
	}
	
	
	public boolean update(int MX, int MY){
		if ( x <= MX && MX <= x + SizeX && y <= MY && MY <= y + SizeY ){ Selected = !Selected; return true;}
		return false;
	}
	
	
	public void render(GameContainer gc, org.newdawn.slick.Graphics g){
		if (!Selected) {img.draw( x, y);}
		else if (Selected){ imgClicked.draw( x, y);}
	}
	
}
