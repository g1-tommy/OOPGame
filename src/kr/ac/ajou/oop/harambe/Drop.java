package kr.ac.ajou.oop.harambe;

//import org.newdawn.slick.Graphics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//Meds, Ammo, and Laser dropped by bad guys when they die
public class Drop {
	
	int x, y; 
	//Coordonates
	int Type;
	//Type: 0: Meds; 1: Bullets; 2: Lasers
	
	Image img;
	
	float Time; int Delay;
	boolean Left, StillThere;
	
	//CONSTRUCTOR
	public Drop(int x, int y, int Type, boolean Left){
		this.x = x;
		this.y = y;
		this.Type = Type;
		this.Left = Left;
		
		//Now pick the image
		try{
			if (Type == 0){ img = new Image("res/MedsDrop.png"); }
			else if (Type == 1){ img = new Image("res/AmmoDrop.png"); }
			else if (Type == 2){ img = new Image("res/Laser Ammo.png"); }
		} catch (SlickException e) { e.printStackTrace(); }
		
		//Put it on the correct side
		if (Left){x -= 20;}
		else if (!Left){ img = img.getFlippedCopy(true, false); x += 20; }
		
		Time = 0; Delay = 5000;
		StillThere = true;
	}
	
	
	public void update(GameContainer gc, int delta){
		
		if (Time > Delay){ StillThere = false; }
		
		Time += delta;
	}
	
	public void render(GameContainer gc, org.newdawn.slick.Graphics g){
		g.drawImage(img, x, y, null);
	}
	
	
	

}
