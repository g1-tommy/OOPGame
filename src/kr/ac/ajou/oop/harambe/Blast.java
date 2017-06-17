package kr.ac.ajou.oop.harambe;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

//The bad guy with the laser gun
public class Blast extends BadGuy {

	ArrayList<Image> SpriteContact = new ArrayList<Image>();
	ArrayList<Image> SpriteShoot = new ArrayList<Image>(); Sound SoundShoot;
	
	
	public Blast(int x, int y) {
		super(x, y, 40, 120, 3); 
		//x, y, SizeX, SizeY, VillainType
		
		InitializeImages(); //Initialize all animations
		this.img = CurrentAnimation.get(0);
		
		WalkSpeed = 6;
	}


	@Override
	public void InitializeImages() {
		try {
			//Not moving
			SpriteIddle.add( new Image("res/Blast-Iddle0.png") );
			SpriteIddle.add( new Image("res/Blast-Iddle1.png") );
			SpriteIddle.add( new Image("res/Blast-Iddle2.png") );
			SpriteIddle.add( SpriteIddle.get(1) );
			//Taking Hit
			SpriteHit.add( new Image("res/Blast-Hit0.png") );
			SpriteHit.add( new Image("res/Blast-Hit1.png") );
			SpriteHit.add( SpriteHit.get(0) );
			//Attacking - Close Range
			SpriteContact.add(new Image("res/Blast-Contact0.png"));
			SpriteContact.add(new Image("res/Blast-Contact1.png"));
			SpriteContact.add(new Image("res/Blast-Contact2.png"));
			//Shooting - Long Range
			SpriteShoot.add(new Image("res/Blast-Shoot0.png"));
			SpriteShoot.add(new Image("res/Blast-Shoot1.png"));
			SpriteShoot.add(new Image("res/Blast-Shoot2.png"));
			SpriteShoot.add(new Image("res/Blast-Shoot3.png")); //The shot
			SpriteShoot.add(new Image("res/Blast-Shoot4.png")); //The recoil
			SpriteShoot.add( SpriteShoot.get(2) );
			SpriteShoot.add( SpriteShoot.get(1) );
			SpriteShoot.add( SpriteShoot.get(0) );
			SoundShoot = new Sound("res/resSounds/Blaster Sound.wav");
			//Walking
			SpriteWalking.add(new Image("res/Blast-Walk0.png"));
			SpriteWalking.add(new Image("res/Blast-Walk1.png"));
			SpriteWalking.add(new Image("res/Blast-Walk2.png"));
			SpriteWalking.add(new Image("res/Blast-Walk3.png"));
			SpriteWalking.add(new Image("res/Blast-Walk4.png"));
			SpriteWalking.add(new Image("res/Blast-Walk5.png"));
			SpriteWalking.add(new Image("res/Blast-Walk6.png"));
			SpriteWalking.add( SpriteIddle.get(0));
			//Death
			SpriteDeath.add(new Image("res/Blast-Death0.png"));
			SpriteDeath.add(new Image("res/Blast-Death1.png"));
			SpriteDeath.add(new Image("res/Blast-Death2.png"));
			
			
		} catch (SlickException e) { e.printStackTrace(); }
		
	}


	@Override
	public void DelayUpdater() {
		if (Iddling){Delay = 200;}
		else if (Walking || GoingUp || GoingDown){ Delay = 80;}
		else if (TakingHit){ Delay = 100;}
		else if (Attacking){ Delay = 100;}
		else if (Attacking2){ Delay = 100;}
		if (Alive == 1){Delay = 100;}
		if (Alive == 2){ Delay = 3000;}
	}

	@Override
	public void AnimationUpdater() {
		if (Iddling){ CurrentAnimation = SpriteIddle;}
		else if (Walking || GoingUp || GoingDown){ CurrentAnimation = SpriteWalking;}
		else if (TakingHit){ CurrentAnimation = SpriteHit;}
		else if (Attacking){ CurrentAnimation = SpriteContact; }
		else if (Attacking2){ CurrentAnimation = SpriteShoot; }
		if (Alive == 1){ CurrentAnimation = SpriteDeath; }
		
		Counter = 0;
	}
	
	
	@Override
	public void ExceptionsSpecial(){
		
		//Contact
		if (Attacking){
			Attacking2 = false;
			if (Counter == 1){ 
				if (FacingRight){AttackReach = 320; }
				else if (!FacingRight){AttackReach = 190; }
			}
			else if (Counter == 2){ AttackReach = 0; }
		}
		
		//All things for the shooting attack
		if (Attacking2){
			//Play the shooting sound
			if (Counter == 3 && !(Walking||GoingUp||GoingDown) ){
				SoundShoot.play();
				AttackReach = 1; 
			}
			
			//To decide if we keep shooting (only if still in range
			if (Counter == 4){
				AttackReach = 0;
				if ( Direction == 2){ if ( 0 + (int)(Math.random() * 2) == 0 ){ Counter -= 3; } }
			}
			
			//End of shooting
			else if (Counter == CurrentAnimation.size() - 1 ){
				AllFalse();
				Iddling = true; AttackReach = 0;
				DelayUpdater(); AnimationUpdater();
			}
		}
		
		
		
	}




}

