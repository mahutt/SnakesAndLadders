// CURRENTLY OUT OF USE - TILE'S PLAYER ATTRIBUTE IS OF TYPE STRING

public class Player {
	
	private String name;
	private int position;
	
	
	public Player(String name) {
		this.setName(name);
		position = 0;
	}
	
	public Player() {
		name = null;
		position = 0;
	}
	
	
//	public int moveBy(int rollValue, int maxValue) {
//		this.position += rollValue;
//		if (this.position > maxValue) {
//			this.position = maxValue - (this.position - maxValue); // could be simplified further to 2*maxValue - position , but is that too unreadable?
//		}
//		return this.position;
//	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	
	
	public String getName() {
		return name;
	}
	
	public int getNameLength() {
		return name.length();
	}

	public void setName(String name) {
		if (name.length() > 6)
			name = name.substring(0, 6);
		this.name = name.toUpperCase();
	}
	
	public String toString() {
		return name;
	}
	
	

}
