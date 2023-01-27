// CURRENTLY OUT OF USE - TILE'S PLAYER ATTRIBUTE IS OF TYPE STRING

public class Player {
	
	private String name;
	private int position;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	

}
