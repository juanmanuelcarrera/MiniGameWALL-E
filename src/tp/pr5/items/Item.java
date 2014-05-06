package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * The superclass of every type of item. It contains the common information for all the items and it defines the interface that the items must match
 * @author Juan Manuel Carrera García
 */
 public abstract  class Item implements Comparable<Item> {
	protected String id;
	protected String description;
	
	// ------------------------------------------------- CONSTRUCTORAS --------------------------------------
	
	/**
	 * Builds an item from a given identifier and description
	 * @param id Item identifier
	 * @param description Item description
	 */
	public Item (String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	/**
	 * Builds an item from a given identifier for ItemGeneric
	 * @param id Item identifier
	 */
	public Item (String id) {
		this.id = id;
		this.description = "";
	}
	
	// ------------------------------------------ MÉTODO EQUALS -----------------------------------------------------
	
	/**
	* Compare two items
	* @param item An item
	* @return true if the items are equals
	*/
	public boolean equals (Object item) {
		boolean eq = false;
		if (item == null) 
			eq = false;
		else {
		    if (!(item instanceof Item)) 
				eq = false;
			else {
			    if (this.compareTo((Item) item) == 0) eq = true;
			    else eq = false;
			}
		}
		return eq;
	}
		
	// -------------------------------------------- MÉTODOS ---------------------------------------------------
	
	// -------------------------------------------- GETTER ---------------------------------------------------
	
	/**
	 * Return the item identifier
	 * @return The item identifier
	 */
	public String getId () {
		return this.id;
	}
	
	/**
	 * Generates a String with the Item description
	 * @return A string with description
	 */
	public String getDescription () {
		return this.description;
	}
	
	/**
	 * Generates a String with the Item description
	 * @return A string with description
	 */
	public String toString () {
		return this.description;
	}
	
	// ------------------------------------- MÉTODO PARA USAR UN ÍTEM ----------------------------------------
	
	/**
	 * Check if the item can be used
	 * @return true if the item can be used
	 */
	public abstract boolean canBeUsed ();
	
	/**
	 * Use the item
	 * @return true if the item are using
	*/
	public abstract boolean use (RobotEngine r, NavigationModule nav);
	
	// Comentar este método al pasar los test
	/**
	 * Undo the use of the item
	 * @param r - robotEngine
	 * @param nav - NavigationModule
	 * @param container - ItemContainer
	 * @return true if the item are using
	*/
	public abstract boolean useUndo (RobotEngine r, NavigationModule nav, ItemContainer cont);

	/**
	 * Compare two items
	 * @param it item to compare
	 */
	public int compareTo(Item it) {
		return this.id.compareToIgnoreCase(it.id);
	}
}
