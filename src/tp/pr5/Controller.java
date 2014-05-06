package tp.pr5;

import tp.pr5.items.InventoryObserver;

/**
 * The common controller to both interfaces contains the engine and method to set observers and run the game.
 * @author Juan Manuel Carrera García
 *
 */
public abstract class Controller {
	protected RobotEngine robotEngine;
	
	/**
	 * Constructor of the controller. It receives the model main class.
	 * @param robot Engine that is being played
	 */
	public Controller(RobotEngine robot) {
		this.robotEngine = robot;
	}
	
	/**
	 * Initialize the game with swing interface, console interface or both
	 */
	public abstract void runGame();

	/**
	 * Set a observer of the robot on the robot engine.
	 * @param observer Observer of the robot engine.
	 */
	public void setRobotObserver(RobotEngineObserver observer) {
		this.robotEngine.addEngineObserver(observer);
	}

	/**
	 * Set a observer of the navigation on the navigation module.
	 * @param observer Observer of the navigation module.
	 */
	public void setNavigationObserver(NavigationObserver observer) {
		this.robotEngine.addNavigationObserver(observer);
	}
	
	/**
	 * Set a observer of the inventory on the item container.
	 * @param observer Observer of the inventory observer.
	 */
	public void setInventoryObserver(InventoryObserver observer) {
		this.robotEngine.addItemContainerObserver(observer);
	}
}
