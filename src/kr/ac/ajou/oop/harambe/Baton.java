package kr.ac.ajou.oop.harambe;
//OOP Project - Ilan Azoulay


import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Baton extends BadGuy {

	ArrayList<Image> SpriteContact = new ArrayList<Image>();
	
	
	public Baton(int x, int y) {
		super( x , y, 50, 120, 1); 
		//x, y, SizeX, SizeY, VillainType
		
		InitializeImages(); //Initialize all animations
		this.img = CurrentAnimation.get(0);
		
		WalkSpeed = 7;
	}


	@Override
	public void InitializeImages() {
		try {
			//Not moving
			SpriteIddle.add( new Image("res/Baton-Iddle0.png") );
			SpriteIddle.add( new Image("res/Baton-Iddle1.png") );
			SpriteIddle.add( new Image("res/Baton-Iddle2.png") );
			SpriteIddle.add( SpriteIddle.get(1) );
			//Taking Low Hit
			SpriteHit.add( new Image("res/Baton-Hit0.png") );
			SpriteHit.add( new Image("res/Baton-Hit1.png") );
			SpriteHit.add( SpriteHit.get(0) );
			//Attacking
			SpriteContact.add(new Image("res/Baton-Contact0.png"));
			SpriteContact.add(new Image("res/Baton-Contact1.png"));
			SpriteContact.add(new Image("res/Baton-Contact2.png"));
			//Death
			SpriteDeath.add(new Image("res/Baton-Death0.png"));
			SpriteDeath.add(new Image("res/Baton-Death1.png"));
			SpriteDeath.add(new Image("res/Baton-Death2.png"));
			//Walking
			SpriteWalking.add(new Image("res/Baton-Walk0.png"));
			SpriteWalking.add(new Image("res/Baton-Walk1.png"));
			SpriteWalking.add(new Image("res/Baton-Walk2.png"));
			SpriteWalking.add(new Image("res/Baton-Walk3.png"));
			SpriteWalking.add(new Image("res/Baton-Walk4.png"));
			SpriteWalking.add(new Image("res/Baton-Walk5.png"));
			SpriteWalking.add(new Image("res/Baton-Walk6.png"));
			SpriteWalking.add(new Image("res/Baton-Walk7.png"));
			SpriteWalking.add( SpriteIddle.get(0));
			
			
		} catch (SlickException e) { e.printStackTrace(); }
		
	}


	@Override
	public void DelayUpdater() {
		if (Iddling){Delay = 200;}
		else if (Walking || GoingUp || GoingDown){ Delay = 70;}
		else if (TakingHit){ Delay = 100;}
		else if (Attacking){ Delay = 100;}
		if (Alive == 1){Delay = 100;}
		if (Alive == 2){ Delay = 3000;}
		
	}

	@Override
	public void AnimationUpdater() {
		if (Iddling){ CurrentAnimation = SpriteIddle;}
		if (Walking || GoingUp || GoingDown){ CurrentAnimation = SpriteWalking;}
		else if (TakingHit){ CurrentAnimation = SpriteHit;}
		else if (Attacking){ CurrentAnimation = SpriteContact; }
		if (Alive == 1){ CurrentAnimation = SpriteDeath; }
		
		Counter = 0;
	}



	@Override
	public void ExceptionsSpecial() {
		
		//Contact
		if (Attacking){
			if (Counter == 1){ 
				if (FacingRight){AttackReach = 340; }
				else if (!FacingRight){AttackReach = 170; }
			}
			else if (Counter == 2){ AttackReach = 0; }
		}
		
	}





}
