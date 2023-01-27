
public class Tile {
	
	private String player; // maybe set this to string and just remove player class altogether
	private String type;
	private Tile sendTo;
	private int[] position;
	
	// could a private int value; attribute be merged with type? or its use be completely handled within driver?
	
	// Default
	public Tile() {
		this.player = null;
		this.type = null; // or set to string that reads "empty"?
		sendTo = null;
		position = null;
	}
	
	public Tile(int x, int y) {
		this.player = null;
		this.type = null;
		this.sendTo = null;
		position = new int[2]; // use a constant for 2?
		position[0] = x;
		position[1] = y;
	}
	
	public Tile getSendTo() {
		return this.sendTo;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
	
	// meant to return single int rather int[]
	public int getPosition() {
		int tileNumber;
		if (this.position[1] == 9) {
			tileNumber = (this.position[0]+1)*10;
		}
		else
			tileNumber = this.position[0]*10 + (this.position[1] + 1);
		return tileNumber;
	}
	
	public void setSnake(Tile tile) {
		this.setType("SH"); // maybe change this
		this.sendTo = tile;
	}
	
	public void setLadder(Tile tile) {
		this.setType("LB");
		this.sendTo = tile; // use setter?
	}
	
	
	
	
	

}
