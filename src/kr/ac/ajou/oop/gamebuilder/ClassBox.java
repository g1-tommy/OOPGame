package kr.ac.ajou.oop.gamebuilder;


import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class ClassBox {

	Image img, Checkbox, Minibox; //Current Image
	int x, y, OffsetX, OffsetY;
	String Name;
	
	Font f;
	UnicodeFont font;
	String Text;
	boolean Abstract, Class, Interface, Grabbed, Changed;
	
	int Connected; //-1: Parent, 0: Not connected, 1: Child; 10: BOTH
	int ParentIndex; //Index of the parent class
	int LineIndex; //If connected as child, gives the index of the line that connects
	
	int DetailsActivated; //0: Not; 1: Variables; 2: Methods
	
	String Variables, Methods;
	
	//Positions for checkboxes: Y: 7 ; X1: 11 ; X2: 78  ; X3: 125
	
	
	
	// == METHODS =============================
	
	//Constructor
	public ClassBox( int x, int y, String Name ){
		
		this.x = x; 
		this.y = y; 
		this.Name = Name;
		
		Abstract = false; Class = true; Interface = false; Grabbed = false; Changed = false;
		
		Connected = 0; ParentIndex = -1;
		
		
		//Initialize background and foreground
		try { //Load Image
			img = new Image("resB/ClassBox.png");
			Checkbox = new Image("resB/Checkbox.png");
			Minibox = new Image("resB/Minibox.png");
		} catch (SlickException e) { e.printStackTrace(); }
		
		//Initialize font
		f = new Font("resB/tahoma.ttf",Font.PLAIN, 12);
	    font = new UnicodeFont(f);
	    font.getEffects().add(new ColorEffect(java.awt.Color.black));
	    font.addAsciiGlyphs();
		
	    OffsetX = 0; OffsetY = y;
	}
	
	
	public void update(GameContainer gc, int delta, int MX, int MY, int Selection){
		
		//Positions for checkboxes: Y: 7 ; X1: 11 ; X2: 78  ; X3: 125
		//Size: 10x10
		
		MX -= x; MY -= y;
		
		
		//Click some button
		if (!Grabbed && !Changed && 0 < MX && MX < img.getWidth() && 0 < MY && MY < img.getHeight() ){
			
			if ( 7 <= MY && MY <= 7+ 10 ){
				
				if ( 11 <= MX && MX <= 11+ 10 ){ Abstract = !Abstract; Changed = true; }
				else if ( (78 <= MX && MX <= 78 + 10) || ( 125 <= MX && MX <= 125+ 10 ) ){ Class = !Class; Interface = !Interface; Changed = true;}                  
			}
			
			else if (Selection == 0 ){ Grabbed = true; OffsetX = MX; OffsetY = MY;}
			
			
			//Details
			if ( 56 <= MX && MX <= 73 ){
				
				//State variables
				if ( 71 <= MY && MY <= 89 ){ DetailsActivated = 1; Changed = true;}
				
				//Methods
				else if ( 117 <= MY && MY <= 134 ){ DetailsActivated = 2; Changed = true; }
				
			}
			
		
		}
		
		
		
		//Mouse action: move around
		else if (Grabbed && Selection == 0){
			x += MX - OffsetX; y += MY - OffsetY;
		}
		
		if (!Changed) { DetailsActivated = 0; }

		
	}
	
	
	public void render(GameContainer gc, org.newdawn.slick.Graphics g, String ParentName){
		
		g.drawImage(img, x, y, null);
		//Render the box
		
		//Positions for checkboxes: Y: 7 ; X1: 11 ; X2: 78  ; X3: 125
		
		//Then determine the text to put on (and render checkboxes at the same time
		Text = "";
		if (Abstract){ Text += "abstract "; g.drawImage(Checkbox, x+11, y+7, null); }
		Text += "public ";
		if (Class){ Text += "class "; g.drawImage(Checkbox, x+78, y+7, null); }
		else if (Interface){ Text += "interface "; g.drawImage(Checkbox, x+125, y+7, null); }
		Text += Name; 
		if (ParentName != "") {Text += "\n" + ParentName;}
		Text += " {";
		
	
		//And render it
	    try {
	        font.loadGlyphs();
	        font.drawString( x+5, y+22, Text); //Draw the text
	        font.drawString( x+10, y+131, "}"); //Draw the end
	    } catch (SlickException e) {
	        e.printStackTrace();
	    }
	    
	    
	    
	    //If we want to see the variables or methods
	    if (DetailsActivated > 0){

	    	if (DetailsActivated == 1){ Minibox.draw( x+56, y + 71, Minibox.getWidth(), Minibox.getHeight() ); }
	    	else if (DetailsActivated == 2){ Minibox.draw( x+56, y + 117 , Minibox.getWidth(), Minibox.getHeight() ); }
	    	
	    }

        
		

	}
	
	
	
}
