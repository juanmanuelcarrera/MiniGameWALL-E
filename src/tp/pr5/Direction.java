package tp.pr5;
/**
 * An enumerated type that represents the compass directions (north, east, south and west) plus a value that represents an unknown direction
 * @author Juan Manuel Carrera García
*/
public enum Direction {
	EAST, NORTH, SOUTH, UNKNOWN, WEST;
	
	// -------------------------------------------- MÉTODOS ----------------------------------------
	
	
	// --------------------------- MÉTODO PARA CALCULAR LA DIRECCIÓN OPUESTA ----------------------
	
	/**
	 * This method return the opposite direction from a given direction.
	* @return Returns the opposite direction from a given direction.
	*/	
	public Direction direccionOpuesta() {
		Direction aux = Direction.UNKNOWN;

		if (this == Direction.EAST)
			aux = Direction.WEST;
		else if (this == Direction.WEST)
			aux = Direction.EAST;
		else if (this == Direction.NORTH)
			aux = Direction.SOUTH;
		else if (this == Direction.SOUTH)
			aux = Direction.NORTH;

		return aux;
	}
	
	
	// ------------------------- MÉTODO PARA CALCULAR LA DIRECCIÓN DE UN GIRO A LA DERECHA -------------
		
	/**
	 * Process the instruction TURN RIGHT
	 * @return Returns the right direction from a given direction.
	 */
	public Direction rightRotation() {
		Direction aux = Direction.UNKNOWN;

		if (this == Direction.NORTH)
			aux = Direction.EAST;
		else if (this == Direction.EAST)
			aux = Direction.SOUTH;
		else if (this == Direction.SOUTH)
			aux = Direction.WEST;
		else if (this == Direction.WEST)
			aux = Direction.NORTH;

		return aux;
	}
	
	
	// ---------------------- MÉTODO PARA CALCULAR LA DIRECCIÓN DE UN GIRO A LA IZQUIERDA -----------
		
	/**
	 * Process the instruction TURN LEFT
	 * @return Returns the left direction from a given direction.
	 */
	public Direction leftRotation() {
		Direction aux = Direction.UNKNOWN;

		if (this == Direction.NORTH)
			aux = Direction.WEST;
		else if (this == Direction.EAST)
			aux = Direction.NORTH;
		else if (this == Direction.SOUTH)
			aux = Direction.EAST;
		else if (this == Direction.WEST)
			aux = Direction.SOUTH;

		return aux;
	}
	
	
	// ---------------------- MÉTODO PARA PARSEAR UN LINEA Y DEVOLVER SU DIRECCIÓN -----------
		
	/**
	 * checks if the Direction is correct
	 * @param line 
	 * @return the direction, if the Direction is incorrect returns Direction Unknown
	 */
	public static Direction parseDirection(String line) {
		Direction aux = Direction.UNKNOWN;

		if (line.equalsIgnoreCase("WEST"))
			aux = Direction.WEST;
		else if (line.equalsIgnoreCase("EAST"))
			aux = Direction.EAST;
		else if (line.equalsIgnoreCase("SOUTH"))
			aux = Direction.SOUTH;
		else if (line.equalsIgnoreCase("NORTH"))
			aux = Direction.NORTH;

		return aux;
	}
}	


