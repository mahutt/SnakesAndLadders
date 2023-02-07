// -----------------------------------------------------
// Assignment 1
// Written by: Thomas Mahut
// -----------------------------------------------------

/**
 * Thomas Mahut (40249811)
 * COMP249
 * Assignment 1
 * February 3rd, 2023
 */

/**
 * An instance of the {@code Player} class exhibits the behavior of an entity playing a
 * board game based purely on chance. That is, a {@code Player} can be identified by its
 * name attribute, and can be situated on a tiled board by its position attribute.
 *
 * @author Thomas Mahut
 *
 */
public class Player {
	
	/**
	 * The name of the {@code Player}, uniqueness of which isn't enforced by the
	 * {@code Player} class itself.
	 */
	private String name;
	
	/**
	 * The position of the {@code Player}, uniqueness of which also ins't enforced by the
	 * {@code Player} class itself.
	 */
	private int position;

	/**
	 * Constructs a new {@code Player} whose name and position are specified by the 
	 * arguments of the same name. 
	 * @param name the name of the {@code Player}
	 * @param position the position of the {@code Player}
	 */
	public Player(String name, int position) {
		this.setName(name);
		this.setPosition(position);
	}
	
	/**
	 * Constructs a new {@code Player} whose name is specified by the arguments
	 * of the same name, and whose position is set to 0.
	 * @param name the name of the {@code Player}
	 */
	public Player(String name) {
		this(name, 0);
	}
	
	/**
	 * Constructs a new {@code Player} whose name is null, and whose position is
	 * set to 0.
	 */
	public Player() {
		this(null, 0);
	}
	
	/**
	 * Constructs a new {@code Player} whose name and position is equal to that
	 * of the specified player.
	 * @param player the {@code Player} object to copy attribute values from
	 */
	public Player(Player player) {
		this(player.getName(), player.getPosition());
	}
	
	/**
	 * Returns the name of the {@code Player} as a {@code String}.
	 * @return the name of the {@code Player}.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of this {@code Player} to the specified name, modified such that
	 * it is no more than 6 characters long, and such that it is in upper case.
	 * @param name the name of the {@code Player}
	 */
	public void setName(String name) {
		if (name != null) {
			if (name.length() > 6)
				name = name.substring(0, 6);
			this.name = name.toUpperCase();
		}
	}
	
	/**
	 * Returns the position of the {@code Player} in {@code int} precision.
	 * @return the position of the {@code Player}.
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the position of this {@code Player} to the specified position.
	 * @param position the position of the {@code Player}
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Returns a {@code String} representing this {@code Player} by only its name.
	 * @return a {@code String} representing this {@code Player} object's name.
	 */
	public String toString() {
		return name;
	}

}