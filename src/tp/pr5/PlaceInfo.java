package tp.pr5;

/**
 * PlaceInfo defines a non-modifiable interface over a Place. It is employed by the classes that need to access the information contained in the place but that cannot modify the place itself.
 * @author Juan Manuel Carrera García
 *
 */
public interface PlaceInfo {
	
	/**
	 * Return the place name
	 * @return The place name
	 */
	public String getDescription();
	
	/**
	 * Return the place description
	 * @return The place description
	 */
	public String getName();
	
	/**
	 * Is this place the space ship?
	 * @return true if the place represents a space ship
	 */
	public boolean isSpaceship();	
}
