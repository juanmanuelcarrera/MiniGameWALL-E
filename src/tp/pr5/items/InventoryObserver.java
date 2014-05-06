package tp.pr5.items;

import java.util.List;

/**
 * Interface of the observers that want to be notified about the events ocurred in the robot inventory. The container will notify its observer every change in the container (when the robot picks or drops items) and when an item is removed from the container beacuse it is empty. The container will also notify when the user requests to scan an item or the whole container
 * @author Juan Manuel Carrera García
 *
 */
public interface InventoryObserver {
	/**
	 * Notifies that the container has changed
	 * @param inventory New Inventory
	 */
	public void inventoryChange(List<Item> inventory);
}
