package kr.ac.ajou.oop.harambe;
// OOP Project - Ilan Azoulay

import java.awt.Graphics;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Harambe extends Entity {

	
	//VARIABLES ============================================================= 
	
	//Animations
	ArrayList<Image> SpriteJumping = new ArrayList<Image>(); 
	ArrayList<Image> SpriteHeavyPunching = new ArrayList<Image>(); Sound RobotPunchSound;
	ArrayList<Image> SpritePunching = new ArrayList<Image>(); Sound SlapSound;
	ArrayList<Image> SpriteShoot = new ArrayList<Image>(); Sound SoundShoot;
	ArrayList<Image> SpriteLaser = new ArrayList<Image>(); Sound SoundLaser;
	Sound Woosh, Jetpack; //For when you miss
	
	
	
	//    State variables
	boolean Jumping;
	int JumpRegulator; //To stay longer in the air
	int BackupY; //Keep posY in memory 
	boolean JumpingRight;
	boolean JumpingLeft;
	boolean HeavyPunching; boolean Punching;
	boolean ShootingBullets, ShootingLaser;
	boolean GoodHit; //Way to regulate the sound
	int AmmoSelection; //1: Bullets; 2: Ammo
	int Bullets, Lasers;
	
	
	// METHODS ==================================================
	
	//This is why there was a constructor in the superclass
	public Harambe(int x, int y) {
		super(x, y, 200, 160); //Super constructor 
		//Arguments: Position X, Position Y, SizeX, SizeY
		
		InitializeImages(); //Initialize all animations
		this.img = CurrentAnimation.get(0);
	}

	@Override
	public void update(GameContainer gc, int delta) {
		
		if (HP <= 0 && Alive == 0) { HP = 0; AllFalse(); Alive = 1; DelayUpdater(); AnimationUpdater(); }
		
		if (Alive < 2){
			//Animation
			if (Time > Delay){
				
				//Moving on the screen
				if (Walking){
					if (FacingRight) { x += 10; } //Moving the picture X coord to the right
					else if (!FacingRight) { x -= 10; } //Moving the picture X coord to the left
				}
				if (GoingUp && y-5 >= 225) { y -= 5; x += 5; } //Moving up, inside the boundaries 
				else if (GoingDown && y+5 <= 370 ) { y += 5; x -= 5; }  // Moving down, inside the boundaries
				
				//Making sure we didn't go too far
				BackToScreen();
				
				
				img = CurrentAnimation.get( Counter );
				//New image
	
				
				
				Exceptions();
				//Take care of special cases
				
				
				if (Counter == CurrentAnimation.size() - 1){Counter = 0; }
				else { Counter += 1; }
				Time = 0;
			}
		}
		
		
		//UPDATE TIME COUNTER
		Time += delta;
		
	}


	//Blit image on the screen
	public void render(GameContainer gc, org.newdawn.slick.Graphics g) {
		g.drawImage(img, x, y, null);
	}

	
	//To make Harambe go back in the screen when he goes too far left or right
	public void BackToScreen(){
	
		//Stay inside screen bounds
		if (x + (img.getWidth()/2) + (SizeX/2) < 0){ //Going too much to the left
			x = 1200 - (img.getWidth()/2) + (SizeX/2); 
		} 
		else if (x + (img.getWidth()/2) - (SizeX/2) > 1200){ //Going too much to the right
			x = 0 - (img.getWidth()/2) - (SizeX/2); 
		} 
		
	}
	
	public void InitializeImages(){
		try {
			//Filling the Iddle list of images of when Harambe is iddling
			SpriteIddle.add( new Image("res/HaRJ-Iddle0.png") );
			SpriteIddle.add( new Image("res/HaRJ-Iddle1.png") );
			SpriteIddle.add( new Image("res/HaRJ-Iddle2.png") );
			SpriteIddle.add( SpriteIddle.get(1) );
			//Filling the Animation list with images of Harambe walking, right order
			SpriteWalking.add( new Image("res/HaRJ-Walk1.png") );
			SpriteWalking.add( new Image("res/HaRJ-Walk2.png") );
			SpriteWalking.add( new Image("res/HaRJ-Walk3.png") );
			SpriteWalking.add( new Image("res/HaRJ-Walk4.png") );
			SpriteWalking.add( new Image("res/HaRJ-Walk5.png") );
			SpriteWalking.add( new Image("res/HaRJ-Walk6.png") );
			SpriteWalking.add( new Image("res/HaRJ-Walk7.png") );
			SpriteWalking.add( SpriteIddle.get(0) );
			//Jumping 
			SpriteJumping.add( new Image("res/HaRJ-Jump0.png") );
			SpriteJumping.add( new Image("res/HaRJ-Jump1.png") );
			SpriteJumping.add( new Image("res/HaRJ-Jump2.png") );
			SpriteJumping.add( SpriteJumping.get(1) );
			SpriteJumping.add( SpriteJumping.get(0) );
			SpriteJumping.add( SpriteIddle.get(0) );
			Jetpack = new Sound("res/resSounds/Jetpack.wav");
			//Heavy Punching
			SpriteHeavyPunching.add( new Image("res/HaRJ-HPunch0.png") );
			SpriteHeavyPunching.add( new Image("res/HaRJ-HPunch1.png") );
			SpriteHeavyPunching.add( new Image("res/HaRJ-HPunch2.png") );
			SpriteHeavyPunching.add( SpriteHeavyPunching.get(2) );
			SpriteHeavyPunching.add( SpriteHeavyPunching.get(1) );
			SpriteHeavyPunching.add( new Image("res/HaRJ-HPunch3.png") );
			RobotPunchSound = new Sound("res/resSounds/Robot Punch.wav");
			//Punching
			SpritePunching.add( new Image("res/HaRJ-Punch0.png") );
			SpritePunching.add( new Image("res/HaRJ-Punch1.png") );
			SpritePunching.add( new Image("res/HaRJ-Punch2.png") );
			SpritePunching.add( new Image("res/HaRJ-Punch3.png") );
			SlapSound = new Sound("res/resSounds/Slap.wav");
			//Shooting bullets
			SpriteShoot.add( new Image("res/HaRJ-Shoot0.png") );
			SpriteShoot.add( new Image("res/HaRJ-Shoot1.png") );
			SpriteShoot.add( new Image("res/HaRJ-Shoot2.png") );
			SpriteShoot.add( new Image("res/HaRJ-Shoot3.png") );
			SpriteShoot.add( new Image("res/HaRJ-Shoot4.png") );
			SpriteShoot.add( SpriteShoot.get(2) );
			SpriteShoot.add( SpriteShoot.get(1) );
			SpriteShoot.add( SpriteShoot.get(0) );
			SoundShoot = new Sound("res/resSounds/Rifle Fire.wav");
			//ShootingLaser
			SpriteLaser.add( new Image("res/HaRJ-Laser0.png") );
			SpriteLaser.add( new Image("res/HaRJ-Laser1.png") );
			SpriteLaser.add( new Image("res/HaRJ-Laser2.png") );
			SpriteLaser.add( new Image("res/HaRJ-Laser3.png") ); //Fire
			SpriteLaser.add( new Image("res/HaRJ-Laser4.png") ); //Recoil
			SpriteLaser.add( SpriteLaser.get(2) );
			SpriteLaser.add( SpriteLaser.get(1) );
			SpriteLaser.add( SpriteLaser.get(0) );
			SoundLaser = new Sound("res/resSounds/Blaster Sound.wav");
			//Blocking
			SpriteBlocking.add( new Image("res/HaRJ-Shield.png") );
			//Death
			SpriteDeath.add(new Image("res/HaRJ-Death0.png"));
			SpriteDeath.add(new Image("res/HaRJ-Death1.png")); //Pain
			SpriteDeath.add(new Image("res/HaRJ-Death2.png"));
			SpriteDeath.add(new Image("res/HaRJ-Death3.png"));
			SpriteDeath.add(new Image("res/HaRJ-Death4.png")); //Knees
			SpriteDeath.add(new Image("res/HaRJ-Death5.png"));
			SpriteDeath.add(new Image("res/HaRJ-Death6.png")); //Agonizing
			SpriteDeath.add(new Image("res/HaRJ-Death7.png"));
			SpriteDeath.add(new Image("res/HaRJ-Death8.png")); //Dead
			
			Woosh = new Sound("res/resSounds/Woosh.wav");
			
			
			
			
		} catch (SlickException e) { e.printStackTrace(); }
	}

	
	
	//Initializing all state variables at the beginning
	public void InitializeState(){
		Iddling = true;
		FacingRight = true;
		Walking = false;
		Jumping = false; JumpRegulator = 0; JumpingRight = false; JumpingLeft = false;
		GoingUp = false; GoingDown = false;
		Attacking = false; Attacking2 = false;
		Punching = false; HeavyPunching = false;
		ShootingBullets = false; ShootingLaser = false;
		Blocking = false;
		Time = 0;
		AmmoSelection = 1;
		Bullets = 0; Lasers = 0;
		DelayUpdater();
		HP = 100;
	}
	
	
	
	//Different actions have a different speed. For that reason, adjust the Delay according to the state
	public void DelayUpdater(){
		
		if (Iddling) { Delay = 200; }
		else if (Walking || GoingUp || GoingDown) { Delay = 70; }
		else if (Jumping) { Delay = 50;}
		else if (HeavyPunching) { Delay = 80; }
		else if (Punching) { Delay = 80; }
		else if (ShootingBullets || ShootingLaser){ Delay = 70;}
		
		if (Alive == 1){ Delay = 150; }
	}
	
	
	
	//Update animation according to state
	public void AnimationUpdater(){
		
		if (Iddling){ CurrentAnimation = SpriteIddle;}
		else if (Walking || GoingUp || GoingDown) { CurrentAnimation = SpriteWalking; }
		else if (Jumping) { CurrentAnimation = SpriteJumping; }
		else if (HeavyPunching) { CurrentAnimation = SpriteHeavyPunching; }
		else if (Punching) { CurrentAnimation = SpritePunching; }
		else if (ShootingBullets ){ CurrentAnimation = SpriteShoot;}
		else if (ShootingLaser ){ CurrentAnimation = SpriteLaser;}
		else if (Blocking) { CurrentAnimation = SpriteBlocking; }
		
		if (Alive >= 1){ CurrentAnimation = SpriteDeath; }
		
		
		Counter = 0; //Reinitialize
	}
	
	
	
	
	//There are always special occurences in an animation
	public void Exceptions(){
		
		//Flip image if Harambe is looking to left
		if (!FacingRight){
			img = img.getFlippedCopy(true, false);
		}
				
		//Case of Jumping
		if (Jumping){
			if (Counter == 0) {BackupY = y; Jetpack.play(); }
			if (Counter < 2 ) { y -= 20; }
			else if (Counter >= SpriteJumping.size() - 3) { y += 40; }
			if (Counter == SpriteJumping.size() - 1 ) { 
				Jumping = false; Iddling = true;
				DelayUpdater(); AnimationUpdater();
				y = BackupY; JumpRegulator = 0;
			}
			//To stay longer in the air
			if (Counter == 2 && JumpRegulator < 5){
				Counter -= 1; y -= 20;
				JumpRegulator += 1;
			}
			
			if (JumpingRight){x += 20;}
			else if (JumpingLeft){x -= 20;}

		}
		
		//So that the enemy can take the hit
		if (Punching && Counter == 1 ){
			if (FacingRight) { AttackReach = 360; } //Reaches up to PosX + this 
			else { AttackReach = 150;}
		} 
		else if (Punching && Counter == 2 ){ //Only works for one frame
			AttackReach = 0; 
			if (GoodHit){ SlapSound.play(); }//Play sound
			else if (!GoodHit) { Woosh.play(); } //Missed
			GoodHit = false;
		} 
		
		//So that the enemy can take the big hit
		else if (HeavyPunching && Counter == 2 ){
			if (FacingRight) { AttackReach = 370; } //Reaches up to PosX + this 
			else { AttackReach = 140;}
		} 
		else if (HeavyPunching && Counter == 3 ){ //Only works for one frame
			AttackReach = 0; 
			if (GoodHit){ RobotPunchSound.play(); } //Play sound
			else if (!GoodHit) { Woosh.play(); } //missed
			GoodHit = false;
		} 
		
		
		//All things for the shooting attack
		else if (ShootingBullets){
			//Play the gunshot sound
			if (Counter == 3 && !Walking){
				SoundShoot.play();
				Bullets -= 1;
				AttackReach = 1;
			}
			
			//See if we have to keep shooting
			if (Counter == 4){
				AttackReach = 0;
				if (Attacking){ Counter -= 3; }
			}
			
			//Can't shoot anymore if out of bullets
			if (Bullets <= 0){ Attacking = false; }
		}
		
		//All things for firing Lasers
		else if (ShootingLaser){
			//Play the gunshot sound
			if (Counter == 3 && !Walking){
				SoundLaser.play();
				Lasers -= 1;
				AttackReach = 1;
			}
			
			//See if we have to keep shooting
			if (Counter == 4){
				AttackReach = 0;
				if (Attacking){ Counter -= 3; }
			}
			
			//Can't shoot anymore if out of bullets
			if (Lasers <= 0){ Attacking = false; }
		}
		
		
		//To go back to iddling when an attack is finished
		if ( (Attacking || ShootingBullets || ShootingLaser ) && Counter == CurrentAnimation.size() - 1){ 
			Attacking = false;
			Punching = false; HeavyPunching = false;
			ShootingBullets = false; ShootingLaser = false;
			Iddling = true;
			AttackReach = 0;
			DelayUpdater(); AnimationUpdater();
		}
		
		
		
		//FINAL DEATH
		if (Alive == 1 && Counter == CurrentAnimation.size() - 1){
			Alive = 2;
		}
		
		
		
		
	}
	
	
}
