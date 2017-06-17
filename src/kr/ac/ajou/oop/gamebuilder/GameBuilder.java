package kr.ac.ajou.oop.gamebuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class GameBuilder extends BasicGame {

	static AppGameContainer game;
	
	static Image Background, ProblemStopper, Clippy1, Clippy2, Clippy3, LaunchButton;
	Input key, MouseInput;
	boolean MouseClicked, ButtonClicked, RightClicked;
	int ObjectGrabbedC, ObjectGrabbedP;
	Sound Launch;
	
	
	int Selection;
	//0: Mouse, 1: IS-A, 2: HAS-A
	
	int SelectedLine;
	//-1: No selection; rest: index on the Lines list
	
	ArrayList<ClassBox> Classes = new ArrayList<ClassBox>(); //List of classes on the screen
	ArrayList<Palette> PaletteList = new ArrayList<Palette>(); //List of classes on the palette
	ArrayList<Button> Buttons = new ArrayList<Button>(); //List of buttons on the palette
	ArrayList<CustomLine> Lines = new ArrayList<CustomLine>(); //List of lines linking classes together on the screen
	RightClick RC;
	
	org.newdawn.slick.Graphics g2;
	
	//Constructor
	public GameBuilder() {
		super("Game Builder"); //Window name
	}
	
	
	// MAIN ==============================================================
	public static void main(String[] args) {
		
		//There's an error if we don't put try/catch, so...
		try { //ANYWAY, this is where the magic begins
			
			//AppGameContainer game = new AppGameContainer(new GameBuilder() );
			game = new AppGameContainer(new GameBuilder() );
			//Declare new window
			
			
			game.setTargetFrameRate(30); //30 FPS
			game.setMaximumLogicUpdateInterval(30); //Makes sense for both to be the same
			game.setVSync(true); //???? Apparently it's better to have this on
			game.setAlwaysRender(true); //So that it keeps going even if another window is on
			game.setShowFPS(false); //Slick2D, by default, shows the FPS number. It's useless, so I turned it off
			
			game.setDisplayMode( 1200 , 630 , false);
			//Dimension X, Dimension Y, Fullscreen
			
			
			
			game.start(); //Open window
			
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	

	//Initializing
	@Override
	public void init(GameContainer gc) throws SlickException {
		
		//Initialize background and foreground
		try { //Load Image
			Background = new Image("resB/Builder Background.jpg"); ProblemStopper = new Image("resB/Nothingness.png");
			Clippy1 = new Image("resB/Clippy1.png"); Clippy2 = new Image("resB/Clippy2.png"); Clippy3 = new Image("resB/Clippy3.png");
			LaunchButton = new Image("resB/Launch Button.png");
			Launch = new Sound("resB/FutureButtonClick.wav");
		} catch (SlickException e) { e.printStackTrace(); }
		
		RC = new RightClick();
		
		MouseClicked = false; ObjectGrabbedC = -1; ObjectGrabbedP = -1; Selection = 0; ButtonClicked = false; 
		SelectedLine = -1; RightClicked = false;
		
		//Add all characters to the palette
		PaletteList.add( new Palette( 940, 390, "Harambe") );
		PaletteList.add( new Palette( 1035, 395, "Baton") );
		PaletteList.add( new Palette( 975, 510, "Rifle") );
		PaletteList.add( new Palette( 1075, 510, "Blaster") );
		PaletteList.add( new Palette( 1080, 310, "Bad Guy") );
		PaletteList.add( new Palette( 1068, 347, "Character") );
		PaletteList.add( new Palette( 950, 330, "Engine") );
		
		//Add all buttons to the palette
		Buttons.add( new Button( 930, 235, 0) ); //Mouse
		Buttons.add( new Button( 1000, 235, 1) ); //IS-A
		Buttons.add( new Button( 1100, 235, 2) ); //HAS-A
				
		//TEST
		//Lines.add( new Line(100, 500)); Lines.get(0).x1 = 300; Lines.get(0).y1 = 300;
		g2 = new org.newdawn.slick.Graphics();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//======================================================================================
	//======== MAIN LOOP ==================================================================
	//======================================================================================
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		KeyboardEvents(gc);
		//What's going on on the keyboard
		
		MouseInput = gc.getInput();
		
		//LEFT CLICK
		if ( MouseInput.isMouseButtonDown( MouseInput.MOUSE_LEFT_BUTTON )){
			
			
			//Click a button
			if (!ButtonClicked && !MouseClicked){
				for (int i=0; i < Buttons.size(); i+=1){

					if (i != Selection){
						if ( Buttons.get(i).update( MouseInput.getMouseX(), MouseInput.getMouseY() ) ){
							Selection = i; 
							ButtonClicked = true;
							//Unselect all the others
							for (int j=0; j < Buttons.size(); j+=1){
								if (j != Selection){ Buttons.get(j).Selected = false; }
							}
						}
					}
				}
			}
			
			
			//Mouse actions
			if (Selection == 0){
				
				//Grabbing a class in the middle
				for (int i=0; i < Classes.size(); i+=1){
					if ((ObjectGrabbedC == -1 || ObjectGrabbedC == i) && ObjectGrabbedP == -1 ){
						
						Classes.get(i).update(gc, delta, MouseInput.getMouseX(), MouseInput.getMouseY(), Selection );
						//Update state
						
						if (ObjectGrabbedC == -1 && Classes.get(i).Grabbed){ ObjectGrabbedC = i;}
					}
				}
				
				//Grabbing a class on the palette
				for (int i=0; i < PaletteList.size(); i+=1){
					if ((ObjectGrabbedP == -1 || ObjectGrabbedP == i) && ObjectGrabbedC == -1){
						PaletteList.get(i).update(gc, delta, MouseInput.getMouseX(), MouseInput.getMouseY() );
						if (ObjectGrabbedP == -1 && PaletteList.get(i).Grabbed){ ObjectGrabbedP = i;}
					}
				}
			}
			
			

			else if ( (Selection == 1 || Selection == 2) && MouseInput.getMouseX() < 900){
				
				//New Click: Create new line
				if (!MouseClicked){
					Lines.add( new CustomLine( MouseInput.getMouseX(), MouseInput.getMouseY(), Selection ) );
				}
				
				//Else: draw it
				else if (MouseClicked && Lines.size() > 0){
					Lines.get( Lines.size()-1 ).update( MouseInput.getMouseX(), MouseInput.getMouseY(), 0, 0 );
				}
				
			}
			
			
			
			
			//Click Launch Button
			if ( 1020 < MouseInput.getMouseX() && MouseInput.getMouseX() < 1179 && 15 < MouseInput.getMouseY() && MouseInput.getMouseY() < 63 && !MouseClicked){
				Launch.play();
				Execute(); //Saving data
				LaunchGame(); //Launching the game
			}
			
			
			MouseClicked = true;
		}
		
		
		
		//Release Left Button
		else if ( !MouseInput.isMouseButtonDown( MouseInput.MOUSE_LEFT_BUTTON ) && MouseClicked ){ 
			MouseClicked = false; ObjectGrabbedP = -1; ObjectGrabbedC = -1; ButtonClicked = false;
			//Unselect whatever was selected
			for (int i=0; i < PaletteList.size(); i+=1){
				if (PaletteList.get(i).Grabbed){
					PaletteList.get(i).Grabbed = false;
					
					//Now let's see the effect
					//Transform it into an actual class if it's dropped in the center
					if (PaletteList.get(i).x < 900){
						Classes.add(new ClassBox( PaletteList.get(i).x, PaletteList.get(i).y, PaletteList.get(i).Name  ));
						PaletteList.remove(i);
					}
					//Do nothing otherwise
					else{
						PaletteList.get(i).update(gc, delta, -1, -1 );
					}
				}
			}
			
			//Unselect a class in the middle
			for (int i=0; i < Classes.size(); i+=1){
				Classes.get(i).Changed = false;
				if (Classes.get(i).Grabbed){
					Classes.get(i).Grabbed = false;
					//Classes.get(i).update(gc, delta, -1, -1, Selection );
				}
			}
			
			//Stop drawing a line
			if (Lines.size() > 0) {
				if ( !Lines.get( Lines.size()-1).Done ){ //Only if it's not done yet, of course
					Lines.get(Lines.size()-1).Done = true;
					LineChecker();
					LineUpdater();
				}
			}
			
			//Remove right click
			
			//See if we clicked on Delete
			if ( RC.LineSelected >= 0 ){ 
				if ( RC.update( MouseInput.getMouseX(), MouseInput.getMouseY(), true ) ){
					
					//Disconnect child class
					if ( Classes.get( Lines.get( RC.LineSelected ).Good0 ).Connected == 1 ){ Classes.get( Lines.get( RC.LineSelected ).Good0 ).Connected = 0; }
					else if ( Classes.get( Lines.get( RC.LineSelected ).Good0 ).Connected == 10 ){ Classes.get( Lines.get( RC.LineSelected ).Good0 ).Connected = -1; }
					Classes.get( Lines.get( RC.LineSelected ).Good0 ).ParentIndex = -1;
					
					//Lines.remove( RC.LineSelected ); //Delete Line, as asked
					
					Lines.get( RC.LineSelected ).Activated = false; //deactivate, not remove
					//Or else it causes disorder
					
					RC.LineSelected = -1; //And remove Right Click image
					
				}
			}
			
			//RC.LineSelected = -1; 
			
		}
		
		
		//RIGHT CLICK
		if ( MouseInput.isMouseButtonDown( MouseInput.MOUSE_RIGHT_BUTTON ) && !RightClicked){
			RightClicked = true;
		}
		
		//Release Right button
		else if ( !MouseInput.isMouseButtonDown( MouseInput.MOUSE_RIGHT_BUTTON ) && RightClicked ){ 
			
			RC.LineSelected = LineRightClick( MouseInput.getMouseX(), MouseInput.getMouseY() );
			
			if ( RC.LineSelected >= 0 ){ 
				RC.x = MouseInput.getMouseX(); RC.y = MouseInput.getMouseY();
			}
			
			RightClicked = false;
		}
		
		
		
		
		//Update Lines
		LineUpdater();
		
		//Update Right Click
		if ( RC.LineSelected >= 0 ){ RC.update( MouseInput.getMouseX(), MouseInput.getMouseY(), false ); }
		
		
	}
	
	
	
	/*
	 * ==========================================================================================================
	 *                 END OF UPDATE
	 *==========================================================================================================
	*/
	
	
	
	
	public void KeyboardEvents(GameContainer container){
		
		key = container.getInput();
		
		//Close the game
		if (key.isKeyPressed(Input.KEY_ESCAPE)){ System.exit(0); }
		
	}
	
	
		
	//Render all images on the screen
	@Override
	public void render(GameContainer gc, org.newdawn.slick.Graphics g) throws SlickException {
		
		//Render things in the back first
		g.drawImage(Background, 0, 0, null);
		
		
		//Clippy
		if (Selection == 0) { g.drawImage(Clippy1, 940, 80, null); }
		else if (Selection == 1) { g.drawImage(Clippy2, 940, 80, null); }
		else if (Selection == 2) { g.drawImage(Clippy3, 940, 80, null); }
		
		//Lines that connect classes
		for (int i=0; i < Lines.size(); i += 1){
			Lines.get(i).render(gc, g2);
		}
		if (Lines.size() > 0){ g.drawImage(ProblemStopper, 0, 0, null); }
		//Magical way of stopping a little black screen problem
		
		
		//Classes on the pool
		for (int i=0; i < Classes.size(); i += 1){
			Classes.get(i).render(gc, g, FindParentName(i) );
		}
		
		//Entities on the palette
		for (int i=0; i < PaletteList.size(); i += 1){
			PaletteList.get(i).render(gc, g);
		}
		
		//Buttons (Mouse, IS-A Line drawer, HAS-A line drawer)
		for (int i=0; i < Buttons.size(); i += 1){
			Buttons.get(i).render(gc, g);
		}
		
		//Right Click
		if ( RC.LineSelected >= 0 ){
			RC.render(gc, g);
		}
		
		//Launch Button
		g.drawImage(LaunchButton, 1020, 15, null);

		
	}
	
	
	
	
	
	
	//Method to check if a line connects 2 classes or is just drawn in the nothingness
	public void LineChecker(){
		
		int Good0 = -1, Good1 = -1;
		
		for (int i=0; i < Classes.size(); i += 1){
			
			if ( Classes.get(i).x <= Lines.get(Lines.size()-1).x0 && Lines.get(Lines.size()-1).x0 <= Classes.get(i).x + Classes.get(i).img.getWidth() ){                                              
				if ( Classes.get(i).y <= Lines.get(Lines.size()-1).y0 && Lines.get(Lines.size()-1).y0 <= Classes.get(i).y + Classes.get(i).img.getHeight() ){   
					Good0 = i; //Good0: child index
				}
			}
			
			else if ( Classes.get(i).x <= Lines.get(Lines.size()-1).x1 && Lines.get(Lines.size()-1).x1 <= Classes.get(i).x + Classes.get(i).img.getWidth() ){                                              
				if ( Classes.get(i).y <= Lines.get(Lines.size()-1).y1 && Lines.get(Lines.size()-1).y1 <= Classes.get(i).y + Classes.get(i).img.getHeight() ){   
					Good1 = i; //Good1: parent index
				}
			}
			
		}
		
		//Nope
		if (Good0 == -1 || Good1 == -1 || Good0 == Good1){
			Lines.remove( Lines.size()-1 );
		}
		
		//Good connection
		else{
			if ( Lines.get( Lines.size()-1 ).Type == 2 ){ //For HAS-A, child and parents are reverse
				int GoodInter = Good0;
				Good0 = Good1;
				Good1 = GoodInter;
			} 
			Lines.get( Lines.size()-1 ).Good0 = Good0;
			//Child
			Classes.get( Good0 ).Connected = 1; Classes.get( Good0 ).ParentIndex = Good1;
			Classes.get( Good0 ).LineIndex = Lines.size()-1;
			
			Lines.get( Lines.size()-1 ).Good1 = Good1;
			if (Classes.get( Good1 ).Connected == 1) { Classes.get( Good1 ).Connected = 10; }
			else { Classes.get( Good1 ).Connected = -1; Classes.get( Good1 ).LineIndex = -1; }
			
		}
		
	}
	
	
	//So that Lines may follow classes even if they move
	public void LineUpdater(){
		
		for (int i=0; i < Lines.size(); i += 1){
			if ( Lines.get(i).Activated && Lines.get(i).Done && Lines.get(i).Good0 != -1 && Lines.get(i).Good1 != -1 ){
				Lines.get(i).update( Classes.get(Lines.get(i).Good0).x + Classes.get(Lines.get(i).Good0).img.getWidth()/2 ,
						Classes.get(Lines.get(i).Good0).y + Classes.get(Lines.get(i).Good0).img.getHeight()/2 , 
						Classes.get(Lines.get(i).Good1).x + Classes.get(Lines.get(i).Good1).img.getWidth()/2 , 
						Classes.get(Lines.get(i).Good1).y + Classes.get(Lines.get(i).Good1).img.getHeight()/2 );
			}
		}
	}
	
	
	
	//To have the "extends/implements OtherClass" at the end of the name
	public String FindParentName( int ChildIndex ){
			
		if ( ( Classes.get( ChildIndex ).Connected == 1 || Classes.get( ChildIndex ).Connected == 10 ) 
				&& Classes.get( ChildIndex ).ParentIndex >= 0 && Classes.get( ChildIndex ).ParentIndex != ChildIndex 
				&& Classes.get(ChildIndex).LineIndex < Lines.size() && Classes.get(ChildIndex).LineIndex >= 0 ){                         
			
			//Only if it's a IS-A
			if ( Lines.get( Classes.get(ChildIndex).LineIndex ).Type == 1 ){  
			
				String ParentName = "";
				
				if ( Classes.get( Classes.get( ChildIndex ).ParentIndex ).Class ){ ParentName += "extends "; }
				else if ( Classes.get( Classes.get( ChildIndex ).ParentIndex ).Interface ){ ParentName += "implements "; }
				
				ParentName += Classes.get( Classes.get( ChildIndex ).ParentIndex ).Name;
				  
				return ParentName;
			
			}
		}
		
		return "";
	}
	
	
	
	//Check if a rightclick hits a line
	public int LineRightClick( int x, int y ){
		
		for (int i=0; i<Lines.size(); i+=1){
			
			if ( ((Lines.get(i).AreaCoveredX[0] <= x && x <= Lines.get(i).AreaCoveredX[2]) && (Lines.get(i).AreaCoveredX[1] <= y && y <= Lines.get(i).AreaCoveredX[3]) )
					|| ((Lines.get(i).AreaCoveredY[0] <= x && x <= Lines.get(i).AreaCoveredY[2]) && (Lines.get(i).AreaCoveredY[1] <= y && y <= Lines.get(i).AreaCoveredY[3]) ) ){
				return i;
			}
		}
		return -1;
	}
	

	
	//Save what was done in a txt file when "running" the diagram
	public void Execute(){
		
		
		//Write in txt file   
		try {
			PrintWriter writer = new PrintWriter( "resB/BuildData.txt" , "UTF-8");
			
			/* PLAN OF THE SAVE FILE
			 * ALL LINES PLAN: There?; Abstract?; Class?; Inheritence type; Inheritence from;
			 * Detail: Inheritence type (see if child)::   0: No inheritence; 1: child of IS-A; 2: child of HAS-A 
			 * Detail: Inheritence from::   0: Nothing; 1: Engine; 2: Character; etc (equals line number)
			 * Detail: Rest:: 0: false; 1: true
			 * 
			 * Line1: Engine
			 * Line2: Character
			 * Line3: Harambe
			 * Line4: Bad Guy
			 * Line5: Baton
			 * Line6: Rifle
			 * Line7: Blaster
			 * 
			*/
			
			String[] DataList = { "Engine", "Character", "Harambe", "Bad Guy", "Baton", "Rifle", "Blaster"  };
			
			String line = ""; int Index = -1;
			
			//System.out.println("here");
			
			for (int i=1; i <= 7; i += 1){ //All 7 lines
				
				line = ""; //Reinitialize new line
				
				Index = ThereChecker( DataList[ i-1 ] ); //Check if there or not (if yes, return index in Classes list
				
				if ( Index >= 0 ){ //There
					line += "1";
					if ( Classes.get(Index).Abstract ){ line += "1"; } else { line += "0"; } //Abstract
					if ( Classes.get(Index).Class ){ line += "1"; } else { line += "0"; } //Class
					
					//No inheritence
					if ( Classes.get(Index).Connected == 0 || Classes.get(Index).Connected == -1){ line += "00"; } 
					
					//Child, now let's see if IS-A or HAS-A
					else if ( Classes.get(Index).Connected == 1 || Classes.get(Index).Connected == 10 ){ 
						line += "" + Lines.get( Classes.get(Index).LineIndex ).Type; 
						
						//Next line, Parent, is done here too
						for (int j = 0; j < Classes.size(); j += 1){
							
							//System.out.println(j + ", " + Classes.get(j).Name + ", " + Classes.get( Classes.get(Index).ParentIndex ).Name );
							
							if ( DataList[j] == Classes.get( Classes.get(Index).ParentIndex ).Name ){
								line += "" + (j+1) ;
							}
						}

					}
					
					
					
				}
				else { line = "00000"; } //Not there
				
				writer.println( line );
				//System.out.println(line);
			
			}
			
		    writer.close();
		    
		    
		    
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (UnsupportedEncodingException e) {e.printStackTrace();}
	    
	}
	
	
	
	//See if a class has been put on the pool
	public int ThereChecker( String Name ){
		for (int i=0; i<Classes.size(); i+=1){
			if (Name == Classes.get(i).Name){ return i; }
		}
		return -1;
	}
	
	
	//Launch the Game
	public void LaunchGame(){
		System.exit(0);
		
		//Engine.main( new String[]{ } );
		//Engine HG = new Engine();

	}
	

}
