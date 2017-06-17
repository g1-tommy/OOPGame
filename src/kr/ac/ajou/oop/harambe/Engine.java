package kr.ac.ajou.oop.harambe;
// OOP Project - Ilan Azoulay

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;

import static java.lang.Math.*;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



//   ==============
//==== MAIN CLASS ======================================
//   ==============

public class Engine extends BasicGame implements HelpingStuff {
		
	
	//Everything from the Game Builder
	boolean EngineThere, CharacterThere, HarambeThere, BadGuyThere, BatonThere, RifleThere, BlasterThere;
	
	
	static Image Background;
	static Image Foreground;
	ArrayList<Image> SelectedWeapon = new ArrayList<Image>();
	
	Input key;
	
	Harambe H; //Main character
	ArrayList<BadGuy> Villains = new ArrayList<BadGuy>(); //List of bad guys
	ArrayList<Drop> DropList = new ArrayList<Drop>(); //List of drops on the screen
	ArrayList<Projectile> Projectiles = new ArrayList<Projectile>(); //List of projectiles on the screen
	
	double Time = 0;
	
	//Keyboard variables
	boolean Right; boolean Left;
	boolean Up; boolean Down;
	boolean Space;
	boolean D, S, A, X, R;
	
	boolean SelectionChanged;
	//Regulator
	
	int Score;
	//speaks for itself
	
	Font font;
	TrueTypeFont ttf;
	//For the font
	
	RenderListing RenderList[] = new RenderListing[1];
	//It's important to know in which order to render all entities
	//Those on the foreground (higher Y) first, those on the background first
	//This exists to place Harambe in the list of entities
	
	
	//HUD
	Image Hud, Healthbar, KeyBindings;
	
	//Sounds
	Sound PickupDrop, HealthPickup, AmmoPickup, LaserPickup;
	
	
	
	//To spawn new villains
	double VillainTimer;
	boolean VillainSpawnRegulator;
	
	  
	
	
	//==== METHODS ==========================================================

	public Engine() {
		super("OOP Project S2D"); //Window title	
	}

	
	
	// MAIN ==============================================================
	public static void main(String[] args  ) {
		


		//There's an error if we don't put try/catch, so...
		try { //ANYWAY, this is where the magic begins
			
			AppGameContainer game = new AppGameContainer(new Engine() );
			//Declare new window
			
			
			game.setTargetFrameRate(30); //30 FPS
			game.setMaximumLogicUpdateInterval(30); //Makes sense for both to be the same
			game.setVSync(true); //???? Apparently it's better to have this on
			game.setAlwaysRender(true); //So that it keeps going even if another window is on
			game.setShowFPS(false); //Slick2D, by default, shows the FPS number. It's useless, so I turned it off
			
			
			
			game.setDisplayMode( ScreenSize[0] , ScreenSize[1] , false);
			//Dimension X, Dimension Y, Fullscreen
			
			System.out.println( game.getScreenHeight() );
			
			game.start(); //Open window
			
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		

	}
	
	
//==================================================================================================
	
	
	//Trucs en plus ===================================================
	@Override
	public void render(GameContainer gc, org.newdawn.slick.Graphics g) throws SlickException {
		
		//Render things in the back first
		g.drawImage(Background, 0, 0, null);
		
		//Then, find the order in which all entities must be render (those in the back first)
		RenderOrder();
		
		//Then render them all
		for (int i=0; i < RenderList.length; i += 1){
			//If it's Harambe
			if (RenderList[i].Type == 0){ H.render(gc, g); }
			
			//If it's a bad guy
			else if (RenderList[i].Type == 1){ Villains.get( RenderList[i].MiniIndex ).render(gc, g); }
			
			//If it's a drop
			else if (RenderList[i].Type == 2){ DropList.get( RenderList[i].MiniIndex ).render(gc, g); }
			
			//If it's a projectile
			else if (RenderList[i].Type == 3){ Projectiles.get( RenderList[i].MiniIndex ).render(gc, g); }
		}
		
		//render Hud
		Healthbar.draw( HPCoord[0], HPCoord[1], (int)(4.20*H.HP), 32); Hud.draw(0,0); KeyBindings.draw(0,0); 
		
		
		//Render Ammo counts
		ttf.drawString( AmmoCountPos[0], AmmoCountPos[1], "" + H.Bullets);
		ttf.drawString( LaserCountPos[0], LaserCountPos[1], "" + H.Lasers);
		//Render score
		ttf.drawString( ScorePos[0], ScorePos[1], "SCORE:    " + Score);
		
		//Other HUD stuff
		g.drawImage(SelectedWeapon.get( H.AmmoSelection-1 ), 0, 0, null);
		
		//And render the foreground
		g.drawImage(Foreground, 0, 0, null);
		
		
		
	}

	
//==================================================================================================

	
	@Override //Initialize entities
	public void init(GameContainer gc) throws SlickException {
		
		BuildData( false ); //CHANGE TO TRUE FOR FULL GAME WITHOUT PROBLEM
		
		if (!HarambeThere || !EngineThere || !CharacterThere){ System.out.println("You built the diagram wrong ! TRY AGAIN !"); System.exit(0); }
		
		
		//Initialize background and foreground
		try { //Load Image
			Background = new Image("res/OOP Background2.jpg");
			Foreground = new Image("res/OOP Foreground.png");
			SelectedWeapon.add( new Image("res/SelectedRifle.png") ); SelectedWeapon.add( new Image("res/SelectedBlaster.png") );
		} catch (SlickException e) { e.printStackTrace(); }
		
		
		//Initialize Harambe
		H = new Harambe(0,270); //PosX, PosY as arguments
		
		//Initialize first villain, a bad guy with a baton
		if (BatonThere) { Villains.add( new Baton( FirstCoord(0), FirstCoord(1) ) ); }//PosX, PosY as arguments
		
		//Add a bad guy with a rifle
		//Villains.add( new Rifle( FirstCoord(0),  FirstCoord(1) ) ); //PosX, PosY as arguments
		
		//Add a bad guy with a freaking laser gun
		//Villains.add( new Blast( FirstCoord(0),  FirstCoord(1) ) ); //PosX, PosY as arguments
		
		
		//Initialize keys
		Right = false; Left = false;
		Up = false; Down = false;
		Space = false;
		D = false; S = false; A = false; X = false; R = false;
		SelectionChanged = false;
		
		Score = 0;
		
		//Initialize Hud
		InitializeHud();
		
		//Initialize font
		font = new Font("Verdana", Font.BOLD, 20);
	    ttf = new TrueTypeFont(font, true);
	    
	    //Initialize sounds
	    PickupDrop = new Sound("res/resSounds/Pickup Dropped.wav");
	    HealthPickup = new Sound("res/resSounds/Health Pickup.wav");
	    AmmoPickup = new Sound("res/resSounds/Ammo Pickup.wav");
	    LaserPickup = new Sound("res/resSounds/Blaster Charge.wav");
		
		//And finally, play the background music
		Music BackgroundMusic = new Music("res/resSounds/DixOut4Harambe.ogg");
		BackgroundMusic.loop();
		
		
		//Initialize Villain Spawn variables
		VillainTimer = 0;
		VillainSpawnRegulator = false;
		   
	}
	
	
	
	
	//============================================================================================================
	//====== UPDATE - MAIN LOOP =====================================================================================
	//============================================================================================================
	@Override 
	public void update(GameContainer gc, int delta) throws SlickException {
		
		KeyboardEvents(gc);
		//Check what the user does on the keyboard
		
		
		H.update(gc, delta);
		//Then update Harambe accordingly
		
		
		//Check if he's attacking anyone, in which case we have to know if an enemy is being hit
		if (H.AttackReach > 0) { 
			//If not shooting, just check if bad guy is in range
			if ( !(H.ShootingBullets || H.ShootingLaser) ){EntityHit(true, -1); H.AttackReach = 0;} 
			
			//If shooting, create projectile
			else if (H.ShootingBullets){ 
				Projectiles.add( new Projectile( H.x, H.y, H.FacingRight, true, true ) );  H.AttackReach = 0;
			} //x, y, FacingRight, Bullet, Friendly
			else if (H.ShootingLaser){ 
				Projectiles.add( new Projectile( H.x, H.y, H.FacingRight, false, true ) );  H.AttackReach = 0;
			} //x, y, FacingRight, Bullet, Friendly
		}
		
		
		//Update bad guys
		for (int i=0; i < Villains.size(); i += 1){ 
			
			//Send them Harambe's coordonates first
			//X Coordonates
			Villains.get(i).HmidX = H.x + H.img.getWidth()/2;
			if (Villains.get(i).FacingRight){ Villains.get(i).HposX = H.x + (H.img.getWidth()/2) - (H.SizeX /2); } 
			else { Villains.get(i).HposX = H.x + (H.img.getWidth()/2) + (H.SizeX /2); }
			//Y Coordonates 
			if (!H.Jumping){ Villains.get(i).HposY = H.y + (H.img.getHeight()/2) + (H.SizeY /2) ; }
			else if (H.Jumping){ Villains.get(i).HposY = H.BackupY + (H.img.getHeight()/2) + (H.SizeY /2) ; }
			
			
			//Then update
			Villains.get(i).update(gc, delta);
			
			
			//Check how their attacks are going
			if ( Villains.get(i).AttackReach > 0 ){//&& !Villains.get(i).AttackRegulator){
				
				//If not shooting, just check if Harambe is in range
				if ( !Villains.get(i).Attacking2 ){EntityHit(false, i); Villains.get(i).AttackReach = 0;} 
				
				//Shooting
				else if (Villains.get(i).Attacking2){ 
					if (Villains.get(i).Type == 2){ Projectiles.add( new Projectile( Villains.get(i).x, Villains.get(i).y, Villains.get(i).FacingRight, true, false ) ); }
					else if (Villains.get(i).Type == 3){ Projectiles.add( new Projectile( Villains.get(i).x, Villains.get(i).y, Villains.get(i).FacingRight, false, false ) ); }
					//x, y, Bullet, Friendly
				}
				Villains.get(i).AttackReach = 0;
			}
			
			CollusionPreventer(i); //Then make sure there's no collusion problem
			
			//Remove dead people
			if (Villains.get(i).Alive == 3){ 
				DropStuff(i); 
				//First, replace with another
				//VillainReplacement();
				//Then remove it
				//Villains.remove(i); 
				
				Villains.get(i).AllFalse();
				Villains.get(i).x = FirstCoord(0);
				Villains.get(i).y = FirstCoord(1);
				Villains.get(i).Iddling = true;
				Villains.get(i).Alive = 0;
				Villains.get(i).HP = 100;
				Villains.get(i).Entered = false;
				
				Score += 1;
			}
		}
		
		//Check if Harambe is picking up any Drop
		if (DropList.size() > 0) { PickDrop(); }
				
		//Also update Drops
		for (int i = 0; i < DropList.size(); i += 1){
			DropList.get(i).update(gc, delta);
			if (!DropList.get(i).StillThere){ DropList.remove(i); }
		}
		
		//And update Projectiles
		for (int i = 0; i < Projectiles.size(); i += 1){
			Projectiles.get(i).update(gc, delta);
			
			//Check if they hit anyone
			ProjectileHit(i);
			
			//And remove when need be
			if (!Projectiles.get(i).StillThere){ Projectiles.remove(i); }
			
		}
		
		
		//Maybe spawn new bad guys
		if (!VillainSpawnRegulator){
			
			VillainReplacement();
			
			VillainTimer = 0 + (Math.random() * VillainSpawnDelay );
			Time = 0;
			VillainSpawnRegulator = true;
		}
		else if (VillainSpawnRegulator && Time > VillainTimer){
			VillainSpawnRegulator = false;
		}
		
		
		
		Time += delta;
		//Update time
		
	}
	//============================================================================================================
	//===== END OF MAIN UPDATE =======================================================================================================
	//============================================================================================================
	
	
	
	
	
	
	
	//  =============================
	//===== TAKING KEYBOARD INPUTS =====================================
	// ===============================
	public void KeyboardEvents(GameContainer container){
		
		key = container.getInput();
		
		
		// == KEYS PRESSED AND RELEASED ======
		//Right Arrow
		if (key.isKeyPressed( Input.KEY_RIGHT ) ){ Right = true; }
		if (!key.isKeyDown( Input.KEY_RIGHT ) ){ Right = false; }
		//Left Arrow
		if (key.isKeyPressed( Input.KEY_LEFT ) ){ Left = true; }
		if (!key.isKeyDown( Input.KEY_LEFT ) ){ Left = false; }
		//Up Arrow
		if (key.isKeyPressed( Input.KEY_UP ) ){ Up = true; }
		if (!key.isKeyDown( Input.KEY_UP ) ){ Up = false; }
		//Down Arrow
		if (key.isKeyPressed( Input.KEY_DOWN ) ){ Down = true; }
		if (!key.isKeyDown( Input.KEY_DOWN ) ){ Down = false; }
		//Spacebar
		if (key.isKeyPressed( Input.KEY_SPACE ) ){ Space = true; }
		if (!key.isKeyDown( Input.KEY_SPACE ) ){ Space = false; }
		//A/Q - both so there's no problem between AZERTY and QWERTY
		if (key.isKeyPressed( Input.KEY_A ) || key.isKeyPressed( Input.KEY_Q )){ A = true; }
		if (! ( key.isKeyDown( Input.KEY_A ) || key.isKeyDown( Input.KEY_Q ) )){ A = false; }
		//S
		if (key.isKeyPressed( Input.KEY_S ) ){ S = true; }
		if (!key.isKeyDown( Input.KEY_S ) ){ S = false; }
		//D
		if (key.isKeyPressed( Input.KEY_D ) ){ D = true; }
		if (!key.isKeyDown( Input.KEY_D ) ){ D = false; }
		//R
		if (key.isKeyPressed( Input.KEY_R ) ){ R = true; }
		if (!key.isKeyDown( Input.KEY_R ) ){ R = false; SelectionChanged = false;}
		//X
		if (key.isKeyPressed( Input.KEY_X ) ){ X = true; }
		if (!key.isKeyDown( Input.KEY_X ) ){ X = false; }
		
		
		
		
		
		// == WHAT WE DO WITH THE KEYS ==============
		
		if (H.Alive == 0){ //Only works if Harambe is alive

			//Moving Right
			if ( Right ){ 
				if (H.Iddling || ( H.Walking && !H.FacingRight) || (!H.Walking && (H.GoingUp || H.GoingDown) ) ){
					H.Walking = true; H.FacingRight = true;
					H.Iddling = false; H.Jumping = false;
					H.DelayUpdater(); H.AnimationUpdater();
				}
				else if (H.Jumping){H.JumpingRight = true; H.JumpingLeft = false; H.FacingRight = true;}
				else if ((H.ShootingBullets || H.ShootingLaser) && !H.FacingRight ){ H.FacingRight = true; }
			}
			
			//Moving Left
			else if ( Left ){ 
				if (H.Iddling || ( H.Walking && H.FacingRight) || (!H.Walking && (H.GoingUp || H.GoingDown) ) ){
					H.Walking = true; H.FacingRight = false;
					H.Iddling = false; H.Jumping = false;
					H.DelayUpdater(); H.AnimationUpdater();
				}
				else if (H.Jumping){H.JumpingLeft = true; H.JumpingRight = false; H.FacingRight = false;}
				else if ((H.ShootingBullets || H.ShootingLaser) && H.FacingRight ){ H.FacingRight = false; }
			}
			
			//Jumping
			if ( Space && !H.Jumping ){ 
				H.Jumping = true;
				H.Walking = false; H.Iddling = false;
				H.GoingUp = false; H.GoingDown = false;
				H.DelayUpdater(); H.AnimationUpdater();
				if (Right){H.JumpingRight = true; H.JumpingLeft = false;}
				else if (Left){H.JumpingLeft = true; H.JumpingRight = false;}
			}
			
			
			//Moving up
			if (Up && ( H.Walking || H.Iddling ) ){
				H.GoingUp = true; H.GoingDown = false;
				if (!H.Walking) { H.Iddling = false; H.DelayUpdater(); H.AnimationUpdater(); }
			}
			if (H.GoingUp && !Up){ H.GoingUp = false; 
				if (!(Right || Left)){ H.Walking = false; H.Iddling = true; H.DelayUpdater(); H.AnimationUpdater(); } 
			}
			
			//Moving down
			if (Down && ( H.Walking || H.Iddling ) ){
				H.GoingDown = true; H.GoingUp = false;
				if (!H.Walking) { H.Iddling = false; H.DelayUpdater(); H.AnimationUpdater(); }
			}
			if (H.GoingDown && !Down){ H.GoingDown = false; 
				if (!(Right || Left)){ H.Walking = false; H.Iddling = true; H.DelayUpdater(); H.AnimationUpdater(); } 
			}
			
			//Punch
			if ( D && ( H.Walking || H.Iddling ) ){
				H.AllFalse();
				H.Punching = true; H.Attacking = true;
				H.DelayUpdater(); H.AnimationUpdater();
			}
			
			//Heavy Punch
			if ( A && ( H.Walking || H.Iddling ) ){
				H.AllFalse();
				H.HeavyPunching = true; H.Attacking = true;
				H.DelayUpdater(); H.AnimationUpdater();
			}
			
			//Blocking
			if ( X && ( H.Walking || H.Iddling ) ){
				H.AllFalse();
				H.Blocking = true;
				H.SizeX = HSizes[1]; //Bigger size
				H.AnimationUpdater();
			} if (!X && H.Blocking){H.Blocking = false; H.SizeX = HSizes[0]; H.Iddling = true; H.AnimationUpdater();}
			
			//Changing Ammo Type
			if (R && !SelectionChanged){
				if (H.AmmoSelection == 1){H.AmmoSelection = 2;}
				else if (H.AmmoSelection == 2){H.AmmoSelection = 1;}
				SelectionChanged = true;
			}
			
			//Shooting
			if (S && ( H.Walking || H.Iddling ) ){
				if (H.AmmoSelection == 1 && H.Bullets > 0){ //Shooting bullets
					H.AllFalse(); H.ShootingBullets = true; H.Attacking = true;
					H.DelayUpdater(); H.AnimationUpdater();
				}
				else if ( H.AmmoSelection == 2 && H.Lasers > 0) { //Shooting laser gun
					H.AllFalse(); H.ShootingLaser = true; H.Attacking = true;
					H.DelayUpdater(); H.AnimationUpdater();
				}
			}
			//Stop shooting
			if ((H.ShootingBullets || H.ShootingLaser) && !S ){H.Attacking = false;}
			
		
			//No known key down
			else if (!( Right || Left || Up || Down || Space || H.Iddling || H.Attacking || D || S || A || X) ){ 
				if ( !(H.Jumping ) ){ //If no action: Going to iddle
					H.AllFalse();
					H.Iddling = true; 
					H.Jumping = false; H.JumpingRight = false; H.JumpingLeft = false;
					H.Punching = false; H.HeavyPunching = false;
					H.ShootingBullets = false; H.ShootingLaser = false;
					H.DelayUpdater(); H.AnimationUpdater();
				}
				//Case of Jumping
				else if (H.Jumping){H.JumpingRight = false; H.JumpingLeft = false;}
			}
			
		}
		
		
		
		
		//Close the game
		if (key.isKeyPressed(Input.KEY_ESCAPE)){ System.exit(0); }
		
		
		//Useful as a mean of debugging
		if (key.isKeyPressed(Input.KEY_ENTER)){ 
			
		}
		//Boundaries X: -370; 1064
		
	}
	
	
//==================================================================================================
	
	//To check if enemies are getting hit / if Harambe is getting hit
	public void EntityHit( boolean HarambeHitting, int Index ){
		
		//To check if Harambe is hitting any enemy
		if (HarambeHitting){
			for (int i = 0; i < Villains.size(); i += 1){
				//in range and condition for the attack to be good
				if ( Villains.get(i).Alive == 0 && (!Villains.get(i).TakingHit) && GoodHit(H, Villains.get(i), HarambeHitting ) ){
					Villains.get(i).AllFalse();
					Villains.get(i).HP -= 50;
					if ( Villains.get(i).HP > 0) { Villains.get(i).TakingHit = true; }
					else if (Villains.get(i).HP <= 0) { Villains.get(i).Alive = 1; }
					Villains.get(i).DelayUpdater(); Villains.get(i).AnimationUpdater();
					H.GoodHit = true;
				}
			}
			
		}
		
		//To check if an enemy is hitting Harambe
		else if (!HarambeHitting){
			//Cannot hit Harambe if he's jumping or blocking in your direction
			if (!( H.Jumping  || ( H.Blocking && ( H.FacingRight != Villains.get(Index).FacingRight ) ) ) ){
				//if it's a good hit
				if ( GoodHit(Villains.get(Index), H, HarambeHitting) && H.Alive == 0){
					H.HP -= Damage[0]; 
					//Now see if he's dead
					if (H.HP <= 0 && H.Alive == 0){H.Alive = 1; H.DelayUpdater(); H.AnimationUpdater();} 
				}
			}
		}
	}

//==================================================================================================
	
	//Check coordonates to see if an attack is a hit or a miss
	public boolean GoodHit(Entity Attacker, Entity Victim, boolean HarambeHitting){
		
		//1: Determine the attacker's position
		int AX[] = new int[2]; //X position of the attack
		AX[0] = Attacker.x + min(Attacker.AttackReach, Attacker.img.getWidth()/2); //Left end
		AX[1] = Attacker.x + max(Attacker.AttackReach, Attacker.img.getWidth()/2); //Right end
		int AY = Attacker.y + (Attacker.img.getHeight()/2) + (Attacker.SizeY/2); //Lower end
		
		//2: Determine the victim's position
		int VX, VX2 = 0;
		if (Victim.FacingRight){ VX = Victim.x + (Victim.img.getWidth()/2) + Victim.SizeX/2;  }
		else { VX = Victim.x + (Victim.img.getWidth()/2) - Victim.SizeX/2; }
		int VY = Victim.y + (Victim.img.getHeight()/2) + (Victim.SizeY/2); //Lower end
		
		if (!HarambeHitting){
			if (!Attacker.FacingRight){ VX = Victim.x + (Victim.img.getWidth()/2) + Victim.SizeX/2;  }
			else { VX = Victim.x + (Victim.img.getWidth()/2) - Victim.SizeX/2; }
			VX2 = Victim.x + (Victim.img.getWidth()/2);
		}
		
		
		

		//System.out.println(AX[0] + " " + AX[1] + " " + AY + " " + VX + " " + VY);
		
		//Good hit
		if (HarambeHitting){ //For when Harambe is the attacker
			if (( AX[0] <= VX && VX <= AX[1] ) && ( VY + GoodHitY[0] < AY && AY < VY + GoodHitY[1] ) ){ 
				return true;  
			}
		}
		else if (!HarambeHitting){ //For when a bad guy is the attacker
			if (( AX[0] <= VX && VX <= AX[1] || ( min(VX,VX2) <= (AX[0] + AX[1])/2 && (AX[0] + AX[1])/2 <= max(VX,VX2 )) ) && ( VY - GoodHitY[1] < AY && AY < VY + GoodHitY[0] ) ){ 
				return true;  
			}
		}
		
		//If we reach here, then it's false
		return false;
	}
	
//==================================================================================================
	
	//Import Hud images
	public void InitializeHud(){
		try {
			Hud = new Image("res/Harambe HUD4.png");
			Healthbar = new Image("res/Healthbar.png");
			KeyBindings = new Image("res/Key Bindings.png");
		} catch (SlickException e) { e.printStackTrace(); }
	}
	
	
//==================================================================================================	
	
	//To make sure a character did not move into another
	public void CollusionPreventer(int Index){
		
		if (!H.Jumping && Villains.get(Index).Alive == 0){
			Villains.get(Index).NoCollusionWithHarambe();
		}	
		
	}
	
//==================================================================================================	
	
	//Randomly chose where bad guy appears
	public int FirstCoord(int Axis){
		if (Axis == 0){ //X, either left or right

			if ( (int)(Math.random() * 2) == 0 ){return -370; }
			else { return 1064;}
		}
		else if (Axis == 1){ //Y, any coordonate from up to down
			return ( 225 + (int)(Math.random() * (370-225)) ) ;
		}
		return 0; //It's pretty much impossible to reach there anyway, just leaving it here so Eclipse stops annoying me
	}
	
	
//==================================================================================================	
	
	//Make bad guys drop stuff when they die
	public void DropStuff( int Index ){
		int Odds;
		
		//Baton can only drop meds
		if ( Villains.get(Index).Type == 1 ){ 
			Odds = 0 + (int)(Math.random() * (100));
			if (Odds < 50){ 
				DropList.add( new Drop( Villains.get(Index).x + Villains.get(Index).img.getWidth()/2, Villains.get(Index).y + DropOffset[2], 0, !(Villains.get(Index).FacingRight) ) ); 
				PickupDrop.play();
			}
		}
		//Rifle can drop meds and ammo
		else if ( Villains.get(Index).Type == 2 ){ 
			for (int i=0; i < 2; i += 1){
				Odds = 0 + (int)(Math.random() * (100));
				if (Odds < 40){ //Ammo
					DropList.add( new Drop( Villains.get(Index).x + Villains.get(Index).img.getWidth()/2, Villains.get(Index).y + DropOffset[2], 1, (i==0) ) ); 
					PickupDrop.play();
				}
				else if (40 <= Odds && Odds < 60){ //Meds
					DropList.add( new Drop( Villains.get(Index).x + Villains.get(Index).img.getWidth()/2, Villains.get(Index).y + DropOffset[2], 0, (i==0) ) ); 
					PickupDrop.play();
				}
			}
		}
		//Blast can drop meds and Laser Ammo
		else if ( Villains.get(Index).Type == 3 ){ 
			for (int i=0; i < 2; i += 1){
				Odds = 0 + (int)(Math.random() * (100));
				if (Odds < 40){ //Ammo
					DropList.add( new Drop( Villains.get(Index).x + Villains.get(Index).img.getWidth()/2, Villains.get(Index).y + DropOffset[2], 2, (i==0) ) ); 
					//x, y, Type, Left
					PickupDrop.play();
				}
				else if (40 <= Odds && Odds < 60){ //Meds
					DropList.add( new Drop( Villains.get(Index).x + Villains.get(Index).img.getWidth()/2, Villains.get(Index).y + DropOffset[2], 0, (i==0) ) ); 
					PickupDrop.play();
				}
			}
		}
			
	}
	
	
//==================================================================================================	
	
	//So that entities can be rendered in the right order
	public void RenderOrder(){
	
		RenderListing Inter; //Only used for swapping
		boolean Done = false;
		
		int HposY = H.y; //Harambe's position
		if (H.Jumping){HposY = H.BackupY;}
		
		
		//Resize amount of entities
		if (Villains.size() + DropList.size() + Projectiles.size() + 1 != RenderList.length){ RenderList = Arrays.copyOf(RenderList, Villains.size() + DropList.size() + Projectiles.size() + 1); }                                 
		
		
		//Place Harambe in it
		RenderList[0] = new RenderListing(0, HposY, 0); //Type, y, MiniIndex
		
		//Place all bad guys in it
		for ( int i=0; i < Villains.size(); i+=1 ){
			RenderList[i+1] = new RenderListing(1, Villains.get(i).y, i);
		} //Type, posY, MiniIndex
		
		//Place all Drops in it
		for ( int i=0; i < DropList.size(); i+=1 ){
			RenderList[i + Villains.size() + 1] = new RenderListing(2, DropList.get(i).y - DropOffset[2] + 15, i);
		} //Type, posY, MiniIndex
		
		//Place all projectiles in it
		for ( int i=0; i < Projectiles.size(); i+=1 ){
			RenderList[i + Villains.size() + DropList.size() + 1] = new RenderListing(3, Projectiles.get(i).y - Projectiles.get(i).OffsetY, i);
		} //Type, posY, MiniIndex
		
		
		//And finally, order everything
		while (!Done){
			Done = true;
			for (int i=0; i < RenderList.length - 1; i += 1 ){
				//Lower Y must be first
				if ( RenderList[i].y > RenderList[i+1].y ){
					Done = false;
					Inter = RenderList[i];
					RenderList[i] = RenderList[i+1];
					RenderList[i+1] = Inter;
				}
			}
		}
	}
					
//==================================================================================================
		
	//See if Harambe is close to a drop
	public void PickDrop(){
		
		int HposX = H.x + H.img.getWidth()/2;
		int HposY = H.y + H.img.getHeight()/2 + H.SizeY/2;
		
		for (int i=0; i < DropList.size(); i += 1){
			//Check X
			if ( DropList.get(i).x + DropRange[0] <= HposX && HposX <=  DropList.get(i).x + DropRange[1] ){
				//Check Y
				if ( DropList.get(i).y + DropRange[2] <= HposY && HposY <=  DropList.get(i).y + DropRange[3] ){
					
					//Then pick it up
					if ( DropList.get(i).Type == 0 && DropList.get(i).StillThere ){ H.HP += 10; if (H.HP > 100){ H.HP = 100; HealthPickup.play(); } } //Meds
					else if ( DropList.get(i).Type == 1 && DropList.get(i).StillThere ){ H.Bullets += 10; AmmoPickup.play(); } //Bullets
					else if ( DropList.get(i).Type == 2 && DropList.get(i).StillThere ){ H.Lasers += 10; LaserPickup.play(); } //Laser ammo
					
					DropList.get(i).StillThere = false; //Also now it must disappear
				}
			}
		}
		
	}

	
//==================================================================================================

	//To add a new villain of random type after one died
	public void VillainReplacement(){
		int Rand = (int)(Math.random() * 300 );
		if (Rand <= 100 && BatonThere){ Villains.add( new Baton( FirstCoord(0), FirstCoord(1) )); }
		else if ( 100 < Rand && Rand <= 200 && RifleThere){ Villains.add( new Rifle( FirstCoord(0), FirstCoord(1) )); }
		else if (BlasterThere){ Villains.add( new Blast( FirstCoord(0), FirstCoord(1) )); }
	}
	
	
	
	
	
	
	//To check if a projectile is hitting anyone
	public void ProjectileHit(int Index){
		
		
		
		//If Harambe is shooting
		if (Projectiles.get(Index).Friendly){
			for (int i = 0; i < Villains.size(); i += 1){
				
				if ( (min(Projectiles.get(Index).LastX, Projectiles.get(Index).x) < Villains.get(i).x + Villains.get(i).img.getWidth()/2 
						&& Villains.get(i).x + Villains.get(i).img.getWidth()/2 <= max(Projectiles.get(Index).LastX, Projectiles.get(Index).x))                                      
							&& Projectiles.get(Index).y - Projectiles.get(Index).OffsetY - GoodHitY[1]/2 <= Villains.get(i).y
										&& Villains.get(i).y <= Projectiles.get(Index).y - Projectiles.get(Index).OffsetY + GoodHitY[1]
											&& Villains.get(i).Alive == 0 && Villains.get(i).HP > 0 ){                           
					
						Villains.get(i).HP -= 30;
						Villains.get(i).AllFalse();
						Villains.get(i).TakingHit = true;
						if (Villains.get(i).HP <= 0){Villains.get(i).Alive = 1; Villains.get(i).TakingHit = false;}
						Villains.get(i).DelayUpdater(); Villains.get(i).AnimationUpdater();
		
				}
				
			}
		}
		
		//if a bad guy is shooting
		else if (!Projectiles.get(Index).Friendly){
			
			if ( !(( H.FacingRight != Projectiles.get(Index).FacingRight && H.Blocking) || H.Jumping )){
				if ( (min(Projectiles.get(Index).LastX, Projectiles.get(Index).x) < H.x + H.img.getWidth()/2 
						&& H.x + H.img.getWidth()/2 <= max(Projectiles.get(Index).LastX, Projectiles.get(Index).x))                                      
							&& Projectiles.get(Index).y - Projectiles.get(Index).OffsetY - GoodHitY[1] <= H.y
										&& H.y <= Projectiles.get(Index).y - Projectiles.get(Index).OffsetY + GoodHitY[1] + GoodHitY[1]/2
											&& H.Alive == 0 && H.HP > 0 ){  
					
					H.HP -= Damage[1];
					//Now see if he's dead
					if (H.HP <= 0){H.Alive = 1; H.DelayUpdater(); H.AnimationUpdater();}
				}
			}
			
		}
		
	}
	
	
	
	
	//Read the data from the Game Builder, see what is right and wrong
	public void BuildData( boolean Cheat ){
		
		if (!Cheat){
			try {
				
				FileReader Text;
				Text = new FileReader("resB/BuildData.txt");
				
				BufferedReader br = new BufferedReader(Text);
		
				String line; int LineNumber = 0;
				
				//Initialize all at false until proven there
				EngineThere = false; CharacterThere = false; HarambeThere = false; BadGuyThere = false;
				BatonThere = false; RifleThere = false; BlasterThere = false;
				
				//And heeeere we go
				try {
					while ((line = br.readLine()) != null) {
					    LineNumber += 1;
					    
					    if ( LineNumber == 1 && StringComparison(line, "10100") ){ EngineThere = true; }
					    if ( LineNumber == 2 && StringComparison(line, "10121") ){ CharacterThere = true; }
					    if ( LineNumber == 3 && StringComparison(line, "10112") ){ HarambeThere = true; }
					    if ( LineNumber == 4 && ( StringComparison(line, "10112") || StringComparison(line, "11112") ) ){ BadGuyThere = true; }
					    if ( LineNumber == 5 && StringComparison(line, "10114") ){ BatonThere = true; }
					    if ( LineNumber == 6 && StringComparison(line, "10114") ){ RifleThere = true; }
					    if ( LineNumber == 7 && StringComparison(line, "10114") ){ BlasterThere = true; }
					    
					}
				} catch (IOException e) {e.printStackTrace();}
			    try {
					Text.close();
				} catch (IOException e) {e.printStackTrace();}
		    
			} catch (FileNotFoundException e) {e.printStackTrace();}
		
		}
		
		else if (Cheat){
			EngineThere = true; CharacterThere = true; HarambeThere = true; BadGuyThere = true;
			BatonThere = true; RifleThere = true; BlasterThere = true;
		}
	    
	}
	
	
	public boolean StringComparison(String Input0, String Good0){
		
		char[] Input = Input0.toCharArray(), Good = Good0.toCharArray();
		
		for (int i=0; i<5; i+=1){
			if (Input[i] != Good[i]){ return false;}
		}
		
		return true;
	}
	
	
			
}









