package tp.pr5;

/*import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;*/


/**
 * This class represents the city where the robot is wandering. It contains information about the streets and the places in the city
 * @author Juan Manuel Carrera García 
 */

public class City {
	
	// ------------- CÓDIGO CON LISTAS (FUNCIONA PERFECTAMENTE EJECUTANDOLO PERO LOS TEST NO FUNCIONAN) !!!!!!!!!!!!!!
	
	/*private List<Street> cityMap;
	
	// ------------------------------------------------- CONSTRUCTORAS -------------------------------
	
	
	public City() {
		this.cityMap = new ArrayList<Street>();
	}
	
	
	public City(Street[] city) {
		for (int i = 0; i < city.length; i++)
			this.cityMap.add(city [i]);
	}
	
	
	// --------------------------------------------- GETTER -----------------------------------------------
	
	
	public int getNumStreet() {
		return this.cityMap.size();
	}
	
	
	// --------------------------------------------- MÉTODOS -----------------------------------------------
	 
	// ------------- MÉTODO QUE BUSCA Y DEVUELVE UNA CALLE EN UNA DIRECCIÓN DADA ---------------------------
	
	
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		Iterator<Street> iter = this.cityMap.iterator();
		Street aux = null;
		boolean encontrado = false;
			
		while (iter.hasNext() && !encontrado) {
			aux = iter.next();
			if (aux.comeOutFrom(currentPlace, currentHeading))
				encontrado = true;
		}
		
		if (!encontrado)
			aux = null;
	
		return aux;
	}
	
	
	// ------------- MÉTODO QUE AÑADE UNA CALLE AL MAPA DE LA CIUDAD SI ES NECESARIO SE REDIMENSIONA --------------

	
	public void addStreet(Street street) {
		this.cityMap.add(street);
	}
	*/
	

	// ------------- CÓDIGO CON ARRAY NORMAL --------------
	
	private Street[] cityMap;
	private int numStreet;
	private int tam;
	
	// ------------------------------------------------- CONSTRUCTORAS -------------------------------
	
	/**
	 * Default constructor
	 */
	public City() {
		this.cityMap = new Street[0];
		this.numStreet = 0;
		this.tam = 0;
	}
	
	/**
	 * Create a city using an array of streets
	 * @param city Map of city
	 */
	public City(Street[] city) {
		this.cityMap = city;
		this.numStreet = city.length;
		this.tam = city.length;
	}
	
	
	// --------------------------------------------- GETTER -----------------------------------------------
	
	/**
	 * Number of streets
	 * @return The number of streets
	 */
	public int getNumStreet() {
		return this.numStreet;
	}
	
	
	// --------------------------------------------- MÉTODOS -----------------------------------------------
	 
	// ------------- MÉTODO QUE BUSCA Y DEVUELVE UNA CALLE EN UNA DIRECCIÓN DADA ---------------------------
	
	/**
	 * Looks for the street that starts from the given place in the given direction
	 * @param currentPlace The place where to look for the street
	 * @param currentHeading The direction to look for the street
	 * @return The street that stars from the given place in the given direction. 
	 * 			It returns null if there is not any street in this direction from the given place
	 */
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		int i = 0;
		boolean encontrado = false;
		Street aux;

		while (i < this.numStreet && !encontrado) {
			aux = this.cityMap[i];
			if (aux.comeOutFrom(currentPlace, currentHeading))
				encontrado = true;
			else
				i++;
		}

		if (encontrado)
			aux = this.cityMap[i];
		else
			aux = null;

		return aux;
	}
	
	
	// ------------- MÉTODO QUE AÑADE UNA CALLE AL MAPA DE LA CIUDAD SI ES NECESARIO SE REDIMENSIONA --------------

	/**
	 * Adds an street to the city
	 * @param street The street to be added
	 */
	public void addStreet(Street street) {
		if (this.numStreet == this.tam)
			this.cityMap = this.reSize();
		this.cityMap[this.numStreet] = street;
		this.numStreet++;
	}

	/**
	 * Resize an array of Street
	 * @return the new size of the array of street
	 */
	private Street[] reSize() {
		Street[] aux = new Street[2 * (this.tam + 1)];
		this.tam = 2 * (this.tam + 1);
		for (int i = 0; i < this.numStreet; i++)
			aux[i] = this.cityMap[i];

		return aux;
	}
	
}
