package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * The garbage is a type of item that generates recycled material after using it. The garbage can be used only once. After using it, it must be removed from the robot inventory
 * @author Juan Manuel Carrera García
 */

public class Garbage extends Item {
	private int recycledMaterial;
	private int times;
	
	// --------------- CONSTRUCTORAS -------------------------
	
	/**
	 * Garbage constructor
	 * @param id Item id
	 * @param description Item description
	 * @param recycledMaterial The amount of recycled material that the item generates
	  */
	public Garbage (String id, String description, int recycledMaterial) {
		super (id, description);
		this.recycledMaterial = recycledMaterial;
		this.times = 1;
	}
	
	// ------------------------ GETTER --------------------------
	
	/**
	 * Get the recycled material
	 * @return A integer that represents the recycled material
	 */
	public int getRecycledMaterial(){
		return this.recycledMaterial;
	}
	
	
	/**
	 * Generates a String with the Item description
	 * @return A string with description
	 */
	public String toString () {
		return super.toString() + " // recycled material = " + this.recycledMaterial;		
	}
	
	// ------------------------- MÉTODOS PARA USAR LA BASURA ---------------------
	
	// --------------- MÉTODO QUE COMPRUEBA SI SE PUEDE USAR UN COMBUSTIBLE --------------
		
	@Override
	/**
	* Garbage can be used only once
	* @return true it the item still can be used
	*/	
	public boolean canBeUsed() {
		return (this.times != 0);
	}

	
	// ----------------------------- MÉTODO QUE USA UN COMBUSTIBLE -------------------------
	
	/**
	 * The garbage generates recycled material for the robot that uses it
	 * @return true if the garbage was transformed in recycled material
	 */
	@Override
	public boolean use(RobotEngine r, NavigationModule nav) {
		boolean ok = false;
		
		if (this.canBeUsed()) {
			ok = true;
			r.addRecycledMaterial(this.recycledMaterial);
			this.times--;
		} 
			
		return ok;
	}
	
	/**
	 * Undo the use of the item
	 * Add RecycledMaterial if you have used UNDO instruction
	 * @param r - robotEngine
	 * @param nav - NavigationModule
	 * @param container - ItemContainer
	 */
	public boolean useUndo(RobotEngine r, NavigationModule nav, ItemContainer container) {
		r.addRecycledMaterial(this.recycledMaterial * -1);
		this.times++;
		container.addItem(this);
		container.inventoryChange();
		
		return true;
	}
}