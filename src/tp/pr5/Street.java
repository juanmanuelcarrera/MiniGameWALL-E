package tp.pr5;

import tp.pr5.items.CodeCard;

/**
 * A street links two places A and B. All streets are two-way streets. If a street is defined as Street(A,NORTH,B) this means that Place B is at NORTH of Place A and Place A is at south of Place B
 * @author Juan Manuel Carrera García
 */
public class Street {
	private Place inicio;
	private Place destino;
	private Direction direccion;
	private boolean open;
	private String code;
	
	// --------------------------------------- CONSTRUCTORAS ----------------------------
		
	/**
	 * Street constructor
	 * @param source Source place (in the example A)
	 * @param direction Represents how is placed the target place with respect to the source place.
	 * @param target Target place (in the example B)
	 */
	public Street (Place source, Direction direction, Place target) {
		this.inicio = source;
		this.direccion = direction;
		this.destino = target;
		this.open = true;
		this.code = "";
	}
		
	/**
	 * Street constructor
	 * @param source Source place (in the example A)
	 * @param direction Represents how is placed the target place with respect to the source place.
	 * @param target Target place (in the example B)
	 * @param isOpen Determina es la calle que se abre o se cierra
	 * @param code El código que abre y cierra la calle
	 */
	public Street (Place source, Direction direction, Place target, boolean isOpen, String code) {
		this.inicio = source;
		this.direccion = direction;
		this.destino = target;
		this.open = isOpen;
		this.code = code;
	}
	
	// ---------------------------------------- MÉTODOS ---------------------------------------------
	
	// ----------------------------------------- GETTER -----------------------------------------
	
	/**
	 * Get the code associated with a street
	 * @return the code of the street
	 */
	public String getCodeStreet () {
		return this.code;
	}
	
		
	// ---------------------------- MÉTODO PARA VER SI UNA CALLE ESTA ABIERTA  --------------------
		
	/**
	 * Checks if the street is open or closed
	 * @return true, if the street is open, and false when the street is closed
	 */
	public boolean isOpen () {
		return this.open;
	}
	
	// ----------- MÉTODO QUE COMPRUEBA SI HAY UNA CALLE DESDE EL LUGAR DADO EN UNA DIRECCIÓN ------------
	
	/**
	 * Checks if the street comes out from a place in a given direction.
	 * @param place The place to check
	 * @param wichDirection Direction used
	 * @return Returns true if the street comes out from the input Place.
	 */
	public boolean comeOutFrom (Place place, Direction wichDirection) {
		return ((this.destino.equals(place) && this.direccion == wichDirection.direccionOpuesta())
				|| (this.inicio.equals(place) && this.direccion == wichDirection)) ;
	}
	
	// ------------------------- MÉTODO QUE DEVUELVE EL LUGAR OPUESTO DE LA CALLE --------------------------------
		
	/**
	 * Returns the place of the other side from the place whereAmI.
	 * @param whereAmI The place where i am
	 * @return It returns the Place at the other side of the street. Returns null if whereAmI does not belong to the street.
	 */
	public Place nextPlace (Place whereAmI) {
		Place aux = null;
		
		if (whereAmI.equals(this.inicio))
			aux = this.destino;
		else if (whereAmI.equals(this.destino))
			aux = this.inicio;
			
		return aux;
	}
	
	// --------------------- MÉTODO QUE COMPRUEBA SI SE PUEDE ABRIR UNA CALLE CON UNA TARJETA --------------------
	
	/**
	 * Tries to open a street using a code card. Codes must match in order to complete this action
	 * @param card A code card to open the street
	 * @return true if the action has been completed
	 */
	public boolean open (CodeCard card) {
		boolean ok = false;
		
		if (card.compareCodes(this.code)) {
			this.open = true;
			ok = true;
		}
		
		return ok;
	}
	
	// ------------------------- MÉTODO QUE COMPRUEBA SI SE PUEDE CERRAR UNA CALLE CON UNA TARJETA -----------------
	
	/**
	 * Tries to close a street using a code card. Codes must match in order to complete this action
	 * @param card A code card to close the street
	 * @return true if the action has been completed
	 */
	public boolean close (CodeCard card) {
		boolean ok = false;
		
		if (card.compareCodes(this.code)) {
			this.open = false;
			ok = true;
		}
				
		return ok;
	}
}
