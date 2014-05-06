package tp.pr5;

import java.util.Iterator;

import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.Item;

/**
 * 
 * This class is in charge of the robot navigation features. 
 * It contains the city where the robot looks for garbage, the current place where the robot is, and the current direction of the robot. 
 * It contains methods to handle the different robot movements and to pick and drop items at the current place.
 * @author Juan Manuel Carrera García
 */
public class NavigationModule extends tp.pr5.Observable<NavigationObserver> {
	private City cityMap;
	private Place currentPlace;
	private Direction currentDirection;
	
	// ----------------------------- CONSTRUCTORAS ----------------------------------------------
	
	/**
	 * Navigation module constructor. It needs the city map and the initial place
	 * @param aCity A city map
	 * @param initialPlace An initial place for the robot
	 */
	public NavigationModule(City aCity, Place initialPlace) {
		super();
		this.cityMap = aCity;
		this.currentPlace = initialPlace;
	}
	
	// -------------------- MÉTODO QUE DEVUELVE LA DIRECCIÓN DEL ROBOT ---------------------------
		
	/**
	 * Returns the robot heading
	 * @return The direction where the robot is facing to.
	 */
	public Direction getCurrentHeading() {
		return this.currentDirection;
	}
	
	// -------------------- MÉTODO QUE DEVUELVE EL LUGAR DEL ROBOT ---------------------------
		
	/**
	 * Returns the current place where the robot is (for testing purposes)
	 * @return The current place
	 */
	public Place getCurrentPlace() {
		return this.currentPlace;
	}
	
	// -------------------- MÉTODO QUE DEVUELVE LA CALLE DEL ROBOT ---------------------------
		
	/**
	 * Returns the street of the robot
	 * @return The street which the robot is facing, or null, if there is not any street in this direction
	 */
	public Street getHeadingStreet() {
		return this.cityMap.lookForStreet(this.currentPlace, this.currentDirection);
	} 
	/**
	 * Returns the street opposite the robot
	 * @return The street which the robot is facing, or null, if there is not any street in this direction
	 */
	public Street getHeadingStreetUndo() {
		return this.cityMap.lookForStreet(this.currentPlace, this.currentDirection.direccionOpuesta());
	} 
	
	// ------------------------------------------------- MÉTODOS -------------------------------------
	
	// -------------------- MÉTODO QUE INDICA SI UN LUGAR ES NAVE ESPACIAL ---------------------------
		
	/**
	 *  Is it a spaceship?
	 * @return true if the place represents a spaceship
	 */
	public boolean atSpaceship() {
		return this.currentPlace.isSpaceship();
	} 
	
	// -------------------- MÉTODO QUE INDICA SI UN LUGAR ES NAVE ESPACIAL ---------------------------
		
	/**
	 * Drop an item in the current place. It assumes that there is no other item with the same name/id there. Otherwise, 
	 * the behaviour is undefined.
	 * @param it The name of the item to be dropped.
	 */
	public void dropItemAtCurrentPlace(Item it) {
		this.currentPlace.dropItem(it);
		this.updateNavigationModule();
	}
	
	// -------------------- MÉTODO QUE QUE BUSCA UN ÍTEM A PARTIR DE UN STRING ---------------------------
		
	/**
	 * Checks if there is an item with a given id in this place
	 * @param id Identifier of the item we are looking for
	 * @return true if and only if an item with this id is in the current place
	 */
	public boolean findItemAtCurrentPlace(String id) {
		return (this.currentPlace.existItem(id));
	} 
		
	// -------------------- MÉTODO QUE INICIALIZA LA DIRECCIÓN DEL ROBOT ---------------------------
		
	/**
	 * Initializes the current heading according to the parameter
	 * @param heading New direction for the robot
	 */
	public void initHeading(Direction heading) {
		this.currentDirection = heading;
	}
	
	// -------------------- MÉTODO QUE INICIALIZA EL LUGAR EN EL OBSERVADOR ---------------------------
	
	/**
	 * Requests the navigation to inform that the simulation started.
	 * @param heading New direction for the robot
	 */
	public void initNavigationModule() {
		this.notifyObserversNavigationModule(TypeMessage.INIT_NAVIGATION_MODULE);
	}
	
	
	// -------------------- MÉTODO QUE MUEVE AL ROBOT A OTRO LUGAR SEGÚN LA DIRECCIÓN -----------------
		
	/**
	 * The method tries to move the robot following the current direction. 
	 * If the movement is not possible because there is no street, or there is a street which is closed, then it throws an exception. 
	 * Otherwise the current place is updated according to the movement
	 * @throws InstructionExecutionException An exception with a message about the encountered problem
	 */
	public void move() throws InstructionExecutionException {
		Street aux = this.getHeadingStreet();

		if (aux == null)
			throw new InstructionExecutionException(
					"WALL·E says: There is no street in direction "
							+ this.currentDirection);
		else {
			if (!aux.isOpen())
				throw new InstructionExecutionException(
						"WALL·E says: Arrggg, there is a door but it is closed!");
			else {
				this.currentPlace = aux.nextPlace(this.currentPlace);
				this.notifyObserversNavigationModule(TypeMessage.ROBOT_ARRIVE);
			}
		}
	}
	
	/**
	 * The method tries to move the robot following the current direction. 
	 * If the movement is not possible because there is no street, or there is a street which is closed, then it throws an exception. 
	 * Otherwise the current place is updated according to the movement
	 * @throws InstructionExecutionException An exception with a message about the encountered problem
	 */
	public void moveUndo() throws InstructionExecutionException {
		Street aux = this.getHeadingStreetUndo();

		if (aux == null)
			throw new InstructionExecutionException(
					"WALL·E says: There is no street in direction "
							+ this.currentDirection.direccionOpuesta());
		else {
			if (!aux.isOpen())
				throw new InstructionExecutionException(
						"WALL·E says: Arrggg, there is a door but it is closed!");
			else {
				this.currentPlace = aux.nextPlace(this.currentPlace);
				this.notifyObserversNavigationModule(TypeMessage.ROBOT_ARRIVE_UNDO);
			}
		}
	}
	
	// -------------------- MÉTODO QUE COGE UN ÍTEM DEL LUGAR ---------------------------
		
	/**
	 * Tries to pick an item characterized by a given identifier from the current place. If the action was completed the item is removed from the current place.
	 * @param id The identifier of the item
	 * @return The item of identifier id if it exists in the place. Otherwise the method returns null
	 */
	public Item pickItemFromCurrentPlace(String id) {
		return this.currentPlace.pickItem(id);
	}
	
	// -------------------- MÉTODO QUE GIRA A IZQUIERDA O DERECHA AL ROBOT ---------------------------
		
	/**
	 * Updates the current direction of the robot according to the rotation
	 * @param rotation left or right
	 */
	public void rotate(Rotation rotation) {
		if (rotation == Rotation.RIGHT) 
			this.currentDirection = this.currentDirection.rightRotation();
		else if (rotation == Rotation.LEFT) 
			this.currentDirection = this.currentDirection.leftRotation();
		this.notifyObserversNavigationModule(TypeMessage.HEADING_CHANGE);
	}
	
	// ---------------- MÉTODO QUE ESCANEA UN LUGAR SU DESCRIPCIÓN MÁS SUS ELEMENTOS ------------------
		
	/**
	 * Prints the information (description + inventory) of the current place
	 */
	public void scanCurrentPlace() {
		this.notifyObserversNavigationModule(TypeMessage.SCAN_PLACE);
	}
	
	
	// ------------------ MÉTODOS PARA ACTUALIZAR EL NAVIGATION MODULE ------------------------------------
		
	/**
	 * Updated the description of a place in the navigation module
	 */
	public void updateNavigationModule() {
		this.notifyObserversNavigationModule(TypeMessage.UPDATE_PLACE);
	}
	
	// ------------------ MÉTODO PARA AVISAR A LOS OBSERVADORES DEL CAMBIO QUE HA OCURRIDO ------------------
	
	
	/**
	 * Notifies observers with the changes that have occurred
	 * @param tipoMensaje Type of Message to to call the correct method
	 */
	private void notifyObserversNavigationModule (TypeMessage tipoMensaje) {
		Iterator<NavigationObserver> it = this.iterator();
		while (it.hasNext()) {
			switch (tipoMensaje) {
			case INIT_NAVIGATION_MODULE: it.next().initNavigationModule(this.currentPlace, this.currentDirection);
			break;
			case ROBOT_ARRIVE: it.next().robotArrivesAtPlace(this.currentDirection, this.currentPlace);
			break;
			case ROBOT_ARRIVE_UNDO: it.next().robotArrivesAtPlace(this.currentDirection.direccionOpuesta(), this.currentPlace);
			break;
			case HEADING_CHANGE: it.next().headingChanged(this.currentDirection);
			break;
			case SCAN_PLACE: it.next().placeScanned(this.currentPlace);
			break;
			case UPDATE_PLACE: it.next().placeHasChanged(this.currentPlace);
			default: break;
			}
		}
	}
}
