package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * An item that represents fuel. This item can be used at least once and it provides power energy to the robot. When the item is used the configured number of times, then it must be removed from the robot inventory
 * @author Juan Manuel Carrera García
*/

public class Fuel extends Item {
	private int power;
	private int times;
	
	// --------------- CONSTRUCTORAS -------------------------
	
	/**
	 * Fuel constructor
	 * @param id Item id
	 * @param description Item description
	 * @param power The amount of power energy that this item provides the robot
	 * @param times Number of times the robot can use the item
	 */
	public Fuel (String id, String description, int power, int times) {
		super (id, description);
		this.power = power;
		this.times = times;	
	}
	
	// ------------------------ GETTER --------------------------
	
	/**
	 * Get the fuel
	 * @return A integer that represents the fuel
	 */
	public int getFuel(){
		return this.power;
	}
	
	/**
	 * Get the times use
	 * @return A integer that represents the times
	 */
	public int getTimes(){
		return this.times;
	}
	
	/**
	 * Generates a String with the Item description
	 * @return A string with description
	 */
	public String toString () {
		return super.toString() + " // power = " + this.power + ", times = " + this.times;			
	}
	
	// ------------------------- MÉTODOS PARA USAR LOS COMBUSTIBLES ---------------------
	
	// --------------- MÉTODO QUE COMPRUEBA SI SE PUEDE USAR UN COMBUSTIBLE --------------
	
	@Override
	/**
	 * Fuel can be used as many times as it was configured
	 * @return true it the item still can be used
	 */
	public boolean canBeUsed() {
		return (this.times != 0);		
	}
	
	// ----------------------------- MÉTODO QUE USA UN COMBUSTIBLE -------------------------
	
	/**
	 * Using the fuel provides energy to the robot (if it can be used)
	 * @return true if the fuel has been used
	 */
	@Override
	public boolean use (RobotEngine r, NavigationModule nav) {
		boolean ok = false;
		
		if (this.canBeUsed()) {
			ok = true;
			r.addFuel(this.power);
			this.times--;
		}
		
		return ok;
	}	
	
	/**
	 * Undo the use of the item
	 * Add fuel if you have used UNDO instruccion
	 * @param r - robotEngine
	 * @param nav - NavigationModule
	 * @param container - ItemContainer
	 * @return true if the item are using
	*/
	public boolean useUndo (RobotEngine r, NavigationModule nav, ItemContainer container) {
		r.addFuel(this.power * -1);
		
		if (container.getItem(this.id) != null)
			((Fuel)container.getItem(this.id)).times++;
		else {
			this.times++;
			container.addItem(this);
			container.inventoryChange();
		}
		
		return true;
	}	
}
