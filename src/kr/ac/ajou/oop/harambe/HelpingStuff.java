package kr.ac.ajou.oop.harambe;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

//Where we store constants that are independant from the game
public interface HelpingStuff {

	
	int[] ScreenSize = {1200, 630};
	
	int[] BoundariesX = {-370, 1064};
	
	int[] HPCoord = { 91 , 27 }; 
	
	int[] GoodHitY = {0, 40}; //1st one for UP (lower Y) ; 2nd for DOWN (higher Y)
	
	int[] DropOffset = {90, 150, 180}; //Xleft, Xright, Y
	//To know where to drop the drop
	
	int DropCoordOffset = 230;
	//To make it easier for the RenderOrder method
	
	int[] AmmoCountPos = { 125, 57 }; //Where to write the amount of bullets left
	
	int[] LaserCountPos = { 240, 57 }; //Where to write the amount of laser ammo left
	
	int[] DropRange = { -10, 70, -10, 80 }; //X1, X2, Y1, Y2
	
	int[] ScorePos = { 362, 62 };
	
	int[] HSizes = { 160, 230 }; //Normal Size, Blocking size
	
	int[] Damage = { 5, 3 }; //CQC damage, Range damage
	
	int VillainSpawnDelay = 30000;
	
	//Boundaries Y: 225 to 370
	
	

}
