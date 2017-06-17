package kr.ac.ajou.oop.harambe;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//A bullet or a laser
public class Projectile {
	
	boolean Bullet; 
	//Determines the type of projectile: Bullet or Laser
	
	Image img;
	
	double Time; int Delay;
	
	int x, y, LastX;
	int Speed, OffsetY;
	
	boolean FacingRight;
	
	boolean Friendly;
	//To know if the bullet is fired by Harambe or a baddie
	
	boolean StillThere;
	
	//Bullet friendly numbers: Y: 115; XL: 135 ; XR: 300; OffsetY: 215
	
	//Laser friendly numbers: Y: 110; XL: 105 ; XR: 355; OffsetY: 215
	
	//Bullet hostile numbers: Y: 117, XL: 150 ; XR: 285, OffsetY: 220
	
	//Laser hostile numbers: Y: 117 ; XL: 120 ; XR: 335 ; OffsetY: 220
	
	// CONSTRUCTOR
	public Projectile(int x0, int y0, boolean FR, boolean B, boolean F){
		this.x = x0; LastX = x;
		this.y = y0;
		this.FacingRight = FR;
		this.Bullet = B;
		this.Friendly = F;
		
		Initialize();
		
	}
	
	
//====================================================================================================	
	
	
	public void Initialize(){
		
		Time = 0;
		Delay = 10;
		Speed = 50;
		
		StillThere = true;
		
		
		
			try {
				if (Bullet){ img = new Image("res/Speeding Bullet.png"); }
				else { img = new Image("res/Laser Shot.png"); }
			} catch (SlickException e) {e.printStackTrace();}
		
		
		
		//Fix offset...
		if (Friendly && Bullet){ //...for Harambe's bullet
			y += 115;
			if (FacingRight){ x+= 300; }
			else if (!FacingRight){ x+= 135; img = img.getFlippedCopy(true, false);}
			OffsetY = 215 - 115; //FORGOT: what is OffsetY
		}
		else if (Friendly && !Bullet){ //...for Harambe's lasers
			y += 110;
			if (FacingRight){ x+= 360  ; }
			else if (!FacingRight){ x+= 105; img = img.getFlippedCopy(true, false);}
			OffsetY = 215 - 110;
		}
		else if (!Friendly && Bullet){ //...for Rifle's bullet
			y += 122;
			if (FacingRight){ x += 290; }
			else if (!FacingRight){ x+= 150; img = img.getFlippedCopy(true, false);}
			OffsetY = 220 - 122;
		}
		else if (!Friendly && !Bullet){ //...for Blast's laser
			y += 122;
			if (FacingRight){ x+= 340; }
			else if (!FacingRight){ x+= 150; img = img.getFlippedCopy(true, false);}
			OffsetY = 220 - 122;
		}
		
		
		
		if (!Friendly){
			img = img.getScaledCopy( img.getWidth()/2 , img.getHeight()/2  );
		}
		
	}
	
	
//====================================================================================================	
	
	public void update(GameContainer gc, int delta){
		
		if (Time > Delay){ 
			LastX = x;
			if (FacingRight){ x += Speed; }
			else if (!FacingRight){ x -= Speed; }
		}
		
		//Destroy if it goes out of the screen
		if ( x + img.getWidth() < 0 || 1200 < x){ StillThere = false; }
		
		Time += delta;
	}
	
//====================================================================================================		
	
	public void render(GameContainer gc, org.newdawn.slick.Graphics g){
		g.drawImage(img, x, y, null);
	}
	
}
