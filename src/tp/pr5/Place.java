package tp.pr5;

import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * It represents a place in the city. Places are connected by streets according to the 4 compass directions: North, East, South and West. Every place has a name and a textual description about itself. This description is displayed when the robot arrives at the place.
 * A place can represent the spaceship where the robot is safe. When the robot arrives at this place, the robot shuts down and the application will finish.
 * @author Juan Manuel Carrera García  
 */
public class Place implements PlaceInfo {
	private String nombre;
	private String descripcion;
	private boolean estaNaveEspacial;
	private ItemContainer container;
	
	// -------------------------------------------- CONSTRUCTORAS -------------------------------------
	
	/**
	 * Creates the place
	 * @param name Place name
	 * @param isSpaceShip Is it a spaceship?
	 * @param description Place description
	 */
	public Place (String name, boolean isSpaceShip, String description) {
		this.nombre = name;
		this.descripcion = description;
		this.estaNaveEspacial = isSpaceShip;
		this.container = new ItemContainer ();
	}
	
	// ---------------------------- GETTER Y SETTER -------------------------
	/**
	 * @return A string with the name of the place
	 */
	public String getName() {
		return this.nombre;
	}
	/**
	 * @return A string with the description
	 */
	public String getDescription() {
		return this.descripcion;
	}
	// ---------------------------- REIMPLEMENTACIÓN MÉTODOS EQUALS() Y TOSTRING() -------------------------
	
	public boolean equals(Place p) {
		return (this.nombre == p.nombre && this.descripcion == p.descripcion
				&& this.estaNaveEspacial == p.estaNaveEspacial && this.container == p.container);
	}

	/**
	 * Overrides toString method. Returns the place name and its description
	 */
	public String toString() {
		String aux = this.nombre + Interpreter.LINE_SEPARATOR
				+ this.descripcion + Interpreter.LINE_SEPARATOR;

		if (this.container.toString() == "")
			aux = aux + "The place is empty. There are no objects to pick";
		else
			aux = aux + "The place contains these objects:"
					+ Interpreter.LINE_SEPARATOR + this.container.toString();

		return aux;
	}
	
	// --------------------------------------------- MÉTODOS -------------------------------------------
		
	// ----------------------------------- MÉTODO PARA AÑADIR UN ÍTEM AL LUGAR -------------------------
	
	/**
	 * Tries to add an item to the place. The operation can fail (if the place already contains an item with the same name)
	 * @param item The item to be added
	 * @return true if and only if the item can be added to the place, i.e., the place does not contain an item with the same name
	 */
	public boolean addItem (Item item) {
		return this.container.addItem(item);
	}
	
	// --------------------------- MÉTODO PARA COGER Y ELIMINAR UN ÍTEM DEL LUGAR ------------------------
	
	/**
	 * Tries to pick an item characterized by a given identifier from the place. If the action was completed the item must be removed from the place
	 * @param id The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItem (String id) {
		return this.container.pickItem(id);
	}
	
	// ------------------------------ MÉTODO PARA BUSCAR UN ÍTEM EN UN LUGAR --------------------------------
	/**
	 * Returns the item from the container according to the item name
	 * @param id The identifier of the item
	 * @return Item with that name or null if the container does not store an item with that name
	 */
	public Item findItem(String id) {
		return this.container.getItem(id);
	}
	
	// ------------------------- MÉTODO PARA COMPROBAR SI UN LUGAR TIENE NAVE ESPACIAL ----------------------
		
	/**
	 * Is it a spaceship?
	 * @return true if the place represents a spaceship
	 */
	public boolean isSpaceship () {
		return this.estaNaveEspacial;
	}
	
	// ----------------------------- MÉTODO PARA VER SI EXISTE UN ÍTEM EN UN LUGAR ---------------------------
		
	/**
	 * 	Checks if an item is in this place
	 * @param id The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public boolean existItem(String id) {
		return this.container.containsItem(id);
	}
	
	// ---------------------------------- MÉTODO DEJAR UN ÍTEM EN UN LUGAR ------------------------------------
		
	/**
	 * Drop an item in this place. The operation can fail, returning false
	 * @param it The name of the item to be dropped.
	 * @return true if and only if the item is dropped in the place, i.e., an item with the same identifier does not exists in the place
	 */
	public boolean dropItem(Item it) {
		boolean ok = false;
		
		if (!this.existItem(it.getId())) {
			this.container.addItem(it);
			ok = true;
		} 
		
		return ok;
	}	
}

