package tp.pr5;
/**
 * An enum class that represents in which direction the robot rotates (left or right) plus a value that represents an unknown direction.
 * @author Juan Manuel Carrera García 
 */
public enum Rotation {
	LEFT, RIGHT, UNKNOWN;
	
	/**Return the opposite rotation
	 * @return a rotation 
	 */
	public Rotation rotacionOpuesta() {
			Rotation aux = Rotation.UNKNOWN;

			if (this == Rotation.LEFT)
				aux = Rotation.RIGHT;
			else if (this == Rotation.RIGHT)
				aux = Rotation.LEFT;
			
			return aux;
		
	}
}
