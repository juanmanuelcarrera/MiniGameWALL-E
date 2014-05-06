package tp.pr5.console;

import java.util.Iterator;
import java.util.List;

import tp.pr5.Direction;
import tp.pr5.Interpreter;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * @author Juan Manuel Carrera García 
 */
 
public class Console implements NavigationObserver, RobotEngineObserver, InventoryObserver {
	/**
	 * Notifies that the container has changed
	 * @param inventory New Inventory
	 */	
	@Override
	public void inventoryChange(List<Item> inventory) {
		String aux = "";
		
		if (inventory.isEmpty())
			aux = "The inventory is empty";
		else {
			aux = "WALL·E says: I am carrying the following items" + Interpreter.LINE_SEPARATOR;
			Iterator<Item> iter = inventory.iterator();
			
			while (iter.hasNext()) {
				aux = aux + "   " + iter.next().getId();
				if (iter.hasNext())
					aux = aux + Interpreter.LINE_SEPARATOR;
			}
		}
		System.out.println(aux);
	}

	/**
	 * The robot engine informs that the communication is over.
	 */
	@Override
	public void communicationCompleted() {
		System.out.println("WALL·E says: I have communications problems. Bye bye");
	}

	/**
	 * The robot engine informs that the help has been requested
	 * @param help A string with information help
	 */
	@Override
	public void communicationHelp(String help) {
		System.out.println(help);
	}

	/**
	 * The robot engine informs that the robot has shut down (because it has arrived at the spaceship or it has run out of fuel)
	 * @param atShip true if the robot shuts down because it has arrived at the spaceship or false if it has run out of fuel
	 */
	@Override
	public void engineOff(boolean atShip) {
		if (atShip)
			System.out.println("WALL·E says: I am at my spaceship. Bye bye");
		else 
			System.out.println("WALL·E says: I run out of fuel. I cannot move. Shutting down...");
	}

	/**
	 * The robot engine informs that it has raised an error
	 * @param msg Error message
	 */
	@Override
	public void raiseError(String msg) {
		System.out.println(msg);
	}

	/**
	 * The robot engine informs that the robot wants to say something
	 * @param message The robot message
	 */
	@Override
	public void robotSays(String msg) {
		System.out.println(msg);
	}
	
	/**
	 * The robot engine informs that the fuel and/or the amount of recycled material has changed
	 * @param fuel Current amount of fuel
	 * @param recycledMaterial Current amount of recycled material
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		System.out.println ("      * My power is " + fuel + 
				Interpreter.LINE_SEPARATOR +
			    "      * My reclycled material is " + recycledMaterial);
	}

	/**
	 * Notifies that the robot heading has changed
	 * @param newHeading New robot heading
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		System.out.println("WALL·E is looking at direction " + newHeading);
	}

	/**
	 * Notifies that the navigation module has been initialized
	 * @param initialPlace The place where the robot starts the simulation
	 * @param heading The initial robot heading
	 */
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		System.out.println(initialPlace.toString());
		System.out.println("WALL·E is looking at direction "
					+ heading);		
	}

	/**
	 * Notifies that the place where the robot stays has changed (because the robot picked or dropped an item)
	 * @param placeDescription Information with the current place
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		System.out.println("Places has changed" + Interpreter.LINE_SEPARATOR + placeDescription.toString());
	}

	/**
	 * Notifies that the user requested a RADAR instruction
	 * @param placeDescription Information with the current place
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		System.out.println(placeDescription.toString());
	}

	/**
	 * Notifies that the robot has arrived at a place
	 * @param heading The robot movement direction
	 * @param place The place where the robot arrives
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		System.out.println("WALL·E says: Moving in direction "
				+ heading);
		System.out.println(place.toString());
	}	
}
