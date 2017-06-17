package kr.ac.ajou.oop.harambe;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

//OOP Projet - Ilan Azoulay


//Class that all bad guys must inherit from
public abstract class BadGuy extends Entity {
	
	int Type;
	//Type of villain. 1: Baton, 2: MG, 3: Ray, 4: Tank, 5: Heli
	
	int HposX, HmidX, HposY; //Harambe's position
	
	boolean Attacking1, Attacking2;
	int TimeAI, DelayAI;
	boolean RegulatorAI;
	boolean Entered; //Becomes true when bad guy enters the screen. Once he has entered, he cannot leave.
	
	int WalkSpeed;
	int Direction;
	
	
	
	//CONSTRUCTOR
	public BadGuy(int x, int y, int SizeX, int SizeY, int VillainType) {
		super(x, y, SizeX, SizeY);
		Type = VillainType;
	}
	
	
	//To show the entity's image on the screen
	@Override
	public void render(GameContainer gc, Graphics g) {
		g.drawImage(img, x, y, null);
	}
	
	
	//To update its state
	@Override
	public void update(GameContainer gc, int delta){
		
		//Direction
		FacingWhat();
		
		//if not dead yet, can still move
		if (Alive < 2){
		
			//Animation
			if (Time > Delay){
				
				//Controls
				AI();
				
				//Make sure he stays inside the screen
				InScreen();
	
				//Moving on the screen
				if (Walking){
					if (FacingRight) { x += WalkSpeed; } //Moving the picture X coord to the right
					else if (!FacingRight) { x -= WalkSpeed; } //Moving the picture X coord to the left
				}
				if (GoingUp && y-5 >= 225) { y -= 5; x += 0; } //Moving up, inside the boundaries 
				else if (GoingDown && y+5 <= 370 ) { y += 5; x -= 0; }  // Moving down, inside the boundaries
				
				
				img = CurrentAnimation.get( Counter );
				//New image
			
				
				Exceptions( );
				//Take care of special cases - common for all bad guys
				
				ExceptionsSpecial( );
				//Take care of special cases - specific to a type of bad guy
				
				
				if (Counter == CurrentAnimation.size() - 1){Counter = 0; }
				else { Counter += 1; }
				Time = 0;
			}
		
		}
		
		else if (Alive == 2 && Time > Delay){ Alive = 3; }
		//To make the body disappear after a while
		
		
		//UPDATE TIME COUNTER
		Time += delta;
		TimeAI += delta;
				
	}
	
	
	
	

	@Override
	public void InitializeState() {
		Alive = 0;
		Iddling = true;
		Walking = false;
		Attacking = false;
		GoingUp = false; GoingDown = false;
		Attacking1 = false; Attacking2 = false; AttackReach = 0;
		TakingHit = false; HP = 100;
		Blocking = false;
		Time = 0; TimeAI = 0; DelayAI = 0; RegulatorAI = false;
		DelayUpdater();
	}
	
	//To determine if the bad guy is looking left or right
	public void FacingWhat(){
		//Answer is, wherever Harambe is
		if ( x + img.getWidth()/2 < HmidX ){ FacingRight = true; }
		else if ( HposX <= x + img.getWidth()/2 ){ FacingRight = false; }
	}
	
	
	
	
	public void Exceptions(){
		
		//Flip image if looking to left
		if (!FacingRight){ img = img.getFlippedCopy(true, false); }
		
		//Move back if taking hit
		if (TakingHit){
			if (FacingRight){ x -= 5; }
			else if (!FacingRight){ x+=5;}
			//End of the pain
			if (Counter == CurrentAnimation.size() - 1){ AllFalse(); Iddling = true; DelayUpdater(); AnimationUpdater();}
		}
		
		
		//Ending the CQ attack
		if (Attacking && Counter == CurrentAnimation.size() - 1) { 
			AllFalse(); Iddling = true; DelayUpdater(); AnimationUpdater(); RegulatorAI = false; 
		}
		
		
		//DEATH
		if (Alive == 1 && Counter == CurrentAnimation.size() - 1){ 
			Alive = 2;
			Time = 0; Delay = 3000;
		}

	}
	
	
	public abstract void ExceptionsSpecial();
		
		//Baton attack OR Rifle CQ attack
		//if ( (Type == 1 || Type == 2) && Attacking && Counter == 2){ Attacking = false; Iddling = true; DelayUpdater(); AnimationUpdater(); RegulatorAI = false; }
	
	
	
	
	
	
	//ARTIFICIAL INTELLIGENCE ================================================================
	public void AI(){
		
		Direction = IsHarambeClose();
		//0: Far; 1: Close in X but not Y; 2: Close in Y but not X; 10: Truly Close
		
		
		//AI
		if (Type == 1 || Type == 2 || Type == 3){

			//Walking until in range of Harambe
			if (Iddling && Direction != 10 ){

				AllFalse(); Iddling = true;
				
				//Needs to move on X
				if (Direction == 2) {
					
					//For Rifle and Blast, he can either shoot or walk, but only inside the screen 
					if ( (Type == 2 || Type == 3) && 0 < x + img.getWidth()/2 && x + img.getWidth()/2 < 1200  ){ 
						if ( 0 + (int)(Math.random() * 2) == 0 ){  //Chose Walking
							AllFalse(); Walking = true; 
						}
						else { AllFalse(); Attacking2 = true; }
						TimeAI = 0; DelayAI = 1000;
					}
					
					//Otherwise, just walk
					else {AllFalse(); Walking = true;}
					
				}
				
				//Needs to move on Y
				else if (Direction == 1){
					AttackReach = 0; Attacking2 = false; //Safety, desperately trying to bring an end to a bug
					if ( (y + img.getHeight()/2 + SizeY/2 ) > HposY - 10 ){ //Harambe above (lower Y)
						AllFalse(); GoingUp = true;
					}
					else if ( HposY - 30  > (y + img.getHeight()/2 + SizeY/2) ){ //Harambe below (higher Y)
						AllFalse(); GoingDown = true;
					}
				}
				
				else if ( Direction == 0 ){ //This is where it gets complicated
					TimeAI = 0; DelayAI = 1000;
					//Chose randomly between X and Y
					if ( 0 + (int)(Math.random() * 2) == 0 ){  //Choice X
						AllFalse(); Walking = true; 
					}
					else if (!Walking){ //Choice Y
						if ( HarambeDirection() /10 == 1){ AllFalse(); GoingDown = true;}
						else { AllFalse(); GoingUp = true; }
					}
				
				}
				
				DelayUpdater(); AnimationUpdater();
			}
			
			
			//Emergency stop
			if  ( (GoingUp || GoingDown) && ( Direction == 2 || (Direction == 0 && TimeAI > DelayAI) ) )  { 
				AllFalse(); Iddling = true; DelayUpdater(); AnimationUpdater(); TimeAI = 0;
			}
			//Similar thing
			if ( Walking && ( Direction == 1 || (Direction == 0 && TimeAI > DelayAI) ) ){ 
				AllFalse(); Iddling = true; DelayUpdater(); AnimationUpdater(); TimeAI = 0; 
			}
			
			//For Rifle and Blast to switch between Walking and shooting
			if ( (Type == 2 || Type == 3) && Direction == 2 && TimeAI > DelayAI){
				AllFalse(); Iddling = true; DelayUpdater(); AnimationUpdater(); TimeAI = 0; 
			}
			
			//Final security
			if (Direction == 10 && (Walking || GoingDown || GoingUp)){ AllFalse(); Iddling = true; DelayUpdater(); AnimationUpdater();}

		}
		
		
		
		
		if ( (Iddling || Walking || GoingUp || GoingDown ) && Direction == 10 && !RegulatorAI){
			AllFalse();
			Iddling = true;
			TimeAI = 0;
			DelayAI = 50 + (int)(Math.random() * 300); //REPLACE with random number
			RegulatorAI = true;
		}
		else if ( RegulatorAI && TimeAI > DelayAI && Iddling ){
			AllFalse();
			Attacking = true;
			DelayUpdater(); AnimationUpdater();
		}
		
	}
	//===== END OF AI ================================================================================
	
	
	
	
	
	
	
	//0: Not close; 1: Close in X; 2: Close in Y;  10: Super close
	public int IsHarambeClose(){
		
		//First check if close enough on the Y scale
		if ( HposY - 30  < (y + img.getHeight()/2 + SizeY/2 ) && (y + img.getHeight()/2 + SizeY/2 ) < HposY - 10 ){
			//Then the X scale
			if ( (FacingRight && ( x + img.getWidth()/2 + SizeX/2 >= HposX ))  ||  ((!FacingRight) && ( x + img.getWidth()/2 - SizeX/2 <= HposX )) ){                 
				return 10; 
			}
			return 2;
		}
		
		if ( (FacingRight && ( x + img.getWidth()/2 + SizeX/2 >= HposX ))  ||  ((!FacingRight) && ( x + img.getWidth()/2 - SizeX/2 <= HposX )) ){
			return 1;
		}
		
		return 0;
	}
	
	
	//0 if nothing; +1: Harambe to the side; +10: Harambe below; +20: Harambe above;
	public int HarambeDirection(){

		int ReturnValue = 0;
		
		if (( FacingRight && (x + img.getWidth()/2 + SizeX/2 ) < HposX ) || ( !FacingRight && (x + img.getWidth()/2 - SizeX/2 ) > HposX ) ) { 
			ReturnValue += 1; }
		if ( (y + img.getHeight()/2 + SizeY/2 ) > HposY - 10 ){ //Harambe above (lower Y)
			ReturnValue += 20; }
		else if ( HposY - 30  > (y + img.getHeight()/2 + SizeY/2) ){ //Harambe below (higher Y)
			ReturnValue += 10; }
		

		return ReturnValue;
	}
	
	
	//To prevent any superposition with Harambe
	public void NoCollusionWithHarambe(){
		//This one is pretty weird because we have to return false if it worked and true if it did not
		
		//If Harambe is close
		if (IsHarambeClose() == 10){
			//We check if not TOO close
			if ( FacingRight && (x+(img.getWidth()/2)+SizeX/2) > HposX + 35 ){
				x = HposX + 35 - ((img.getWidth()/2)+SizeX/2) ; 
			}
			else if (!FacingRight && (x+(img.getWidth()/2)-SizeX/2) < HposX - 35 ){
				x = HposX - 35 - ((img.getWidth()/2)-SizeX/2) ; 
			}
		}
		
		//Then make sure we stay in the screen
		InScreen();
	}
	
	
	//Check when he enters the screen, and then make sure he stays there
	public void InScreen(){
		
		//Hasn't entered yet
		if (!Entered){
			if ((FacingRight && x + img.getWidth()/2 - SizeX/2 > 0) || ( !FacingRight && x + img.getWidth()/2 + SizeX/2 < 1200 ) ){ 
				Entered = true; 
			}
		}
		
		else if (Entered){
			//NEED TO FINISH THIS
			if (FacingRight && x + img.getWidth()/2 - SizeX/2 < 0){
				x = 0 - img.getWidth()/2 + SizeX/2;
			}
			else if ( !FacingRight && x + img.getWidth()/2 + SizeX/2 > 1200 ){
				x = 1200 - img.getWidth()/2 - SizeX/2;
			}
		}
		
	}
	
}
