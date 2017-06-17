package kr.ac.ajou.oop.gamebuilder;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Line;

public class CustomLine {
	
	Image img, img2;
	int x0, y0, x1, y1, rotation, Good0, Good1;
	boolean Done, Xfirst;
	boolean Activated;
	
	
	int Type; //1: IS-A ; 2: HAS-A
	
	
	int[] AreaCoveredX = {0, 0, 0, 0}; //x0, y0, x1, y1
	int[] AreaCoveredY = {0, 0, 0, 0};
	
	public CustomLine(int x, int y, int Type){
		this.x0 = x;
		this.y0 = y;
		this.Type = Type;
		
		x1 = x0; y1 = y0; Done = false; rotation = 0; Good0 = -1; Good1 = -1; Xfirst = (Math.random() * 100 <= 50);
		Activated = true;
		
		try {
			if (Type == 1){ img = new Image("resB/Line.png"); }
			else if (Type == 2){ img = new Image("resB/LineR.png"); }
		} catch (SlickException e) {e.printStackTrace();}
	}
	
	public void update(int MX, int MY, int X, int Y){
		
		if (!Done){
			x1 = MX; y1 = MY; 
		} //The line, as it is drawn, goes from (x,y) to (x1,y1) 
		
		//In the event that we make an actual connection
		else if (Done){
			//In this case, MX,MY becomes the origin, and X,Y the end
			x0 = MX; y0 = MY;
			x1 = X; y1 = Y;
			
			//Area covered
			if (Xfirst){
				AreaCoveredX[0] = Math.min(x0, x1); AreaCoveredX[1] = y0; 
				AreaCoveredX[2] = Math.max(x0, x1); AreaCoveredX[3] = y0+3;
				
				AreaCoveredY[0] = x1; AreaCoveredY[1] = Math.min(y0, y1); 
				AreaCoveredY[2] = x1+3; AreaCoveredY[3] = Math.max(y0, y1);
			}
			else if (!Xfirst){
				img.draw( Math.min(x0, x1), y1, Math.abs(x0-x1), 3); // -
				AreaCoveredY[0] = x0; AreaCoveredY[1] = Math.min(y0, y1); 
				AreaCoveredY[2] = x0 + 3; AreaCoveredY[3] = Math.max(y0, y1);
				
				AreaCoveredX[0] = Math.min(x0, x1); AreaCoveredX[1] = y1;
				AreaCoveredX[2] = Math.max(x0, x1); AreaCoveredX[3] = y1 +3;
			}
			
			
		}
		
	}
	
	//Now this is gonna involve some geometry...
	public void render(GameContainer gc, org.newdawn.slick.Graphics g2){
		
		if (Activated ){
			
			if (!Done){
				Line Line2 = new Line(x0, y0, x1, y1);
				if (Type == 1){ g2.setColor(Color.black); }
				if (Type == 2){ g2.setColor(Color.red); }
				g2.draw(Line2);
				g2.setColor(null);
			}
			
			else if (Done){
				
				//Randomly chose between x first and y first
				if ( Xfirst){ //X first
					img.draw( Math.min(x0, x1), y0, Math.abs(x0-x1), 3); // -
					img.draw( x1, Math.min(y0, y1), 3, Math.abs(y0-y1) ); // |
				}
				else if (!Xfirst) { //Y first
					img.draw( x0, Math.min(y0, y1), 3, Math.abs(y0-y1) ); // |
					img.draw( Math.min(x0, x1), y1, Math.abs(x0-x1), 3); // -
				}
				
				
			}
			
		}
		
	}
}











