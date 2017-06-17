package kr.ac.ajou.oop.harambe;
// OOP Project - Ilan Azoulay

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;


//abstract class that ALL CHARACTERS on the screen must inherit from
public abstract class Entity {

	
	// == COMMON VARIABLES ==================
	
	protected int x, y; //PosX and PosY
	//protected: only classes that inherit this can use those variables
	
	public int SizeX, SizeY, HP;
	
	Image img; //Current Image
	ArrayList<Image> CurrentAnimation = new ArrayList<Image>(); //Current Animation
	
	//Animations
	ArrayList<Image> SpriteIddle = new ArrayList<Image>();
	ArrayList<Image> SpriteWalking = new ArrayList<Image>();
	ArrayList<Image> SpriteHit = new ArrayList<Image>();
	ArrayList<Image> SpriteBlocking = new ArrayList<Image>();
	ArrayList<Image> SpriteDeath = new ArrayList<Image>();
	
	
	//State variables
	boolean Iddling;  //Not moving
	boolean FacingRight;	
	boolean Walking;
	boolean GoingUp;
	boolean GoingDown;
	boolean Attacking, Attacking2;
	int AttackReach; //Range of the current attack. 0 if not attacking.
	boolean TakingHit; 
	boolean Blocking;
	
	int Alive;
	//0: Alive; 1: Dying; 2: Dead; 3: Must disappear
	
	double Time; 
	double Delay;
	int Counter;
	
	
	
	

	
	// == METHODS =============================
	
	//Constructor - no void
	public Entity( int x, int y, int SizeX, int SizeY){
		this.x = x;
		this.y = y;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		HP = 100;
		
		InitializeState(); //Initialize all state variables
		
		CurrentAnimation = SpriteIddle;
		
		Counter = 0;
	}
	
	
	
	//Reinitialize all state variables to false
	public void AllFalse(){
		Iddling = false;  	
		Walking = false;
		GoingUp = false;
		GoingDown = false;
		Attacking = false; Attacking2 = false;
		AttackReach = 0; 
		TakingHit = false;
		Blocking = false;
	}
	
	
	
	
	
	public abstract void update(GameContainer gc, int delta);
	
	public abstract void render(GameContainer gc, org.newdawn.slick.Graphics g);
	
	
	public abstract void InitializeImages();
	
	public abstract void InitializeState();
	
	public abstract void DelayUpdater();
	
	public abstract void AnimationUpdater();
	
	public abstract void Exceptions();
	
	
	
	//Getters and Setters
	//public int getX() {return x;}
	//public void setX(int x) {this.x = x;}
	//public int getY() {return y;}
	//public void setY(int y) {this.y = y;}
	
	
}











