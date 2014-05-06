package tp.pr5.gui;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * Panel at the bottom of the window that displays messages about the events that occur during the simulation. This panel implements all the observer interfaces in order to be notified about all event ocurred
 * @author Juan Manuel Carrera García
 *
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel implements RobotEngineObserver, NavigationObserver, InventoryObserver {
	private JLabel information;
	
	/**
	 * Constructor of the InfoPanel in the MainWindow to show the informaiton of the game
	 */
	public InfoPanel() {
		super();
		this.add(this.information = new JLabel());
	}
	
	/**
	 * Notifies that the container has changed
	 * @param inventory New Inventory
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {
		this.information.setText("WALL·E says: The inventory change");
	}

	/**
	 * Notifies that the robot heading has changed
	 * @param newHeading New robot heading
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		this.information.setText("WALL·E is looking at direction " + newHeading);
	}

	/**
	 * Notifies that the navigation module has been initialized
	 * @param initialPlace The place where the robot starts the simulation
	 * @param heading The initial robot heading
	 */
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {}

	/**
	 * Notifies that the place where the robot stays has changed (because the robot picked or dropped an item)
	 * @param placeDescription Information with the current place
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		this.information.setText("WALL·E says: The place " + placeDescription.getName() + " has changed");
	}

	/**
	 * Notifies that the user requested a RADAR instruction
	 * @param placeDescription Information with the current place
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {}

	/**
	 * Notifies that the robot has arrived at a place
	 * @param heading The robot movement direction
	 * @param place The place where the robot arrives
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		this.information.setText("WALL·E says: Moving in direction " + heading);
	}

	/**
	 * The robot engine informs that the communication is over.
	 */
	@Override
	public void communicationCompleted() {
		this.information.setText("WALL·E says: I have communications problems. Bye bye");		
	}

	/**
	 * The robot engine informs that the help has been requested
	 * @param help A string with information help
	 */
	@Override
	public void communicationHelp(String help) {}

	/**
	 * The robot engine informs that the robot has shut down (because it has arrived at the spaceship or it has run out of fuel)
	 * @param atShip true if the robot shuts down because it has arrived at the spaceship or false if it has run out of fuel
	 */
	@Override
	public void engineOff(boolean atShip) {
		if (atShip)
			this.information.setText("WALL·E says: I am at my spaceship. Bye bye");
		else 
			this.information.setText("WALL·E says: I run out of fuel. I cannot move. Shutting down...");
	}

	/**
	 * The robot engine informs that it has raised an error
	 * @param msg Error message
	 */
	@Override
	public void raiseError(String msg) {
		this.information.setText(msg);
	}

	/**
	 * The robot engine informs that the robot wants to say something
	 * @param message The robot message
	 */
	@Override
	public void robotSays(String message) {
		this.information.setText(message);
	}

	/**
	 * The robot engine informs that the fuel and/or the amount of recycled material has changed
	 * @param fuel Current amount of fuel
	 * @param recycledMaterial Current amount of recycled material
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.information.setText("Robot atributes has been updated: (" + 
						fuel + ", " + recycledMaterial + ")");
	}
}
