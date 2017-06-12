package kr.ac.ajou.oop.state;

public abstract class GameState {

	private int stateID = State.STATE_GAME_READY;

	public abstract void render();

	public abstract void update();

	public abstract void resetContent();

	public int getID() {
		return stateID;
	}

	public void setID(int stateID) {
		this.stateID = stateID;
	}
}
