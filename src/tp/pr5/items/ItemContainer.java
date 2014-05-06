package tp.pr5.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import tp.pr5.Interpreter;

/**
 * A container of items. It can be employed by any class that stores items. A container cannot store two items with the same identifier
 *
 * It provides methods to add new items, access them and remove them from the container.
 * @author Juan Manuel Carrera García
 */
public class ItemContainer extends tp.pr5.Observable<InventoryObserver> {
	private List <Item> items;
	
	// ------------------------------------ CONSTRUCTORAS ------------------------------------
	
	/**
	 * Creates the empty container
	 */
	public ItemContainer () {
		super();
		this.items = new ArrayList<Item> ();
	}
	
	// ----------------------------------------- MÉTODOS --------------------------------------
	
	// ----------------------------------------- GETTER --------------------------------------
	
	/**
	 * Get the array size
	 * @return Array size
	 */
	public int getSize () {
		return this.items.size ();
	}
	
	
	// ---------------------------- MÉTODO TOSTRING() --------------------------
	
	/**
	 * Generates a String with information about the items contained in the container. Note that the items must appear sorted but the item name
	 * @return The identifiers of items in the array
	 */
	public String toString() {
		Iterator<Item> iter = this.items.iterator();
		String aux = "";
		
		while (iter.hasNext()) {
			aux = aux + "   " + iter.next().getId();
			if (iter.hasNext())
				aux = aux + Interpreter.LINE_SEPARATOR;
		}
		
		return aux;
	}
		
		
	// ---------------------------- MÉTODO PARA AÑADIR UN ÍTEM EN EL CONTENEDOR --------------------------
	
	/**
	 * Add an item to the container. The operation can fail, returning false
	 * @param item The name of the item to be added
	 * @return true if and only if the item is added, i.e., an item with the same identifier does not exists in the container
	 */
	public boolean addItem(Item item) {
		int i = 0;
		boolean ok = false;
		
		i = Collections.binarySearch(this.items, item);
		
		if (i < 0){
			i = (i * -1) - 1;
			this.items.add(i, item);
			ok = true;
		}
	
		return ok;
	}

	
	// ---------------------------- MÉTODO PARA SABER SI HAY UN ÍTEM EN EL CONTENEDOR --------------------------
	
	/**
	 * Returns the number of items contained
	 * @return the number of items in the container
	 */
	public int numberOfItems () {
		return this.items.size();
	}
	
	// ---------------------------- MÉTODO PARA CONSEGUIR UN ÍTEM DEL CONTENEDOR --------------------------
	
	/**
	 * Returns the item from the container according to the item name
	 * @param id Item name
	 * @return Item with that name or null if the container does not store an item with that name
	 */
	public Item getItem(String id) {
		Item itAux = new ItemGeneric(id);
		int i = Collections.binarySearch(this.items, itAux);
		Item it = null;
		
		if (i >= 0)
			it = this.items.get(i);

		return it;
	}
	
	// ---------------------------- MÉTODO PARA CONSEGUIR Y BORRAR UN ÍTEM DEL CONTENEDOR --------------------------
	
	/**
	 * Returns and deletes an item from the inventory. This operation can fail, returning null
	 * @param id Name of the item
	 * @return An item if and only if the item identified by id exists in the inventory. Otherwise it returns null
	 */
	public Item pickItem(String id) {
		Item itAux = new ItemGeneric(id);
		int i = Collections.binarySearch(this.items, itAux);
		Item it = null;
		
		if (i >= 0) {
			it = this.items.get(i);
			this.items.remove(i);
		}
	
		return it;
	}
	
	// ---------------------------- MÉTODO PARA VER SI HAY UN ÍTEM EN EL CONTENEDOR --------------------------
		
	/**
	 * Checks if the Item with this id exists in the container.
	 * @param id Name of the item.
	 * @return true if the container as an item with that name.
	 */
	public boolean containsItem(String id){
		Item itAux = new ItemGeneric(id);
		return (Collections.binarySearch(this.items, itAux) >= 0);
	}
	
	// ---------------------------- MÉTODO PARA USAR UN ÍTEM DEL CONTENEDOR --------------------------
	
	/**
	 * Use an item, removing the item from the inventory if the item can not be used any more
	 * @param item to be used
	 */
	public void useItem(Item item) {
		if (!item.canBeUsed()) {
			this.items.remove(item);
			this.inventoryChange();
		}
	}
	
	/**
	 * Get an item in the i position
	 * @param i position of the item
	 * @return the item in the position
	 */
	public Item getItem(int i) {
		return this.items.get(i);
	} 
	
	/**
	 * Update the inventory of the robot in the GUI
	 */
	public void inventoryChange() {
		Iterator<InventoryObserver> it = this.iterator();
		while (it.hasNext()) 
			it.next().inventoryChange(items);
	}
}
