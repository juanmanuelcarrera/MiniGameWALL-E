package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;

/**
 * @author Juan Manuel Carrera García
 */
 
public class ItemGeneric extends Item {
	/**
	 * Create a item generic with a id
	 * @param id id of the item
	 */
	public ItemGeneric(String id) {
		super(id);
	}

	/**
	 * Check if the item can be used
	 * @return false
	 */
	@Override
	public boolean canBeUsed() {
		return false;
	}

	/**
	 * Use the item
	 * @return false
	*/
	@Override
	public boolean use(RobotEngine r, NavigationModule nav) {
		return false;
	}

	/**
	 * Undo the use of the item
	 * @param r - robotEngine
	 * @param nav - NavigationModule
	 * @param container - ItemContainer
	 * @return true if the item are using
	*/
	public boolean useUndo(RobotEngine r, NavigationModule nav, ItemContainer cont) {
		return false;
	}
}
