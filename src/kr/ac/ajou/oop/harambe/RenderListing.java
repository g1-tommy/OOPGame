package kr.ac.ajou.oop.harambe;

//We need to create our own type of object to know in which order we have to render all objects
public class RenderListing {

	int Type;
	//0: Harambe; 1: Bad Guy; 2: Drop; 3: Projectile
	
	int MiniIndex;
	//Index of the list in which the Entity is
	
	int y;
	
	public RenderListing(int Type, int y, int MiniIndex){
		this.Type = Type;
		this.y = y;
		this.MiniIndex = MiniIndex;
	}
	
	
}
