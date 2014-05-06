package tp.pr5.cityLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tp.pr5.City;
import tp.pr5.Direction;
import tp.pr5.Place;
import tp.pr5.Street;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;
import tp.pr5.items.Item;

/**
 * City loader from a txt file The mandatory format must be:  
 BeginCity
 BeginPlaces
 place 0 Entrada Estamos_en_la_entrada._Comienza_la_aventura noSpaceShip
 place 1 Callao In_this_square... spaceShip
 ...
 EndPlaces
 BeginStreets
 street 0 place 0 south place 1 open
 street 1 place 1 east place 2 open
 street 2 place 2 north place 3 closed onetwothreefourfive
 ...
 EndStreets
 BeginItems
 fuel 0 Petrol from_olds_heatings 10 3 place 0
 fuel 1 Battery to_get_cracking -50 1 place 0
 codecard 2 Card The_key_is_too_easy onetwothreefourfive place 1
 garbage 3 Newspapers News_on_sport 30 place 2
 ...
 EndItems
 EndCity
  If you want to enhance your loader, it should also parse the descriptions using the following format:  place 0 Entrada "Estamos en la entrada. Comienza la aventura" noSpaceShip
 garbage Agua "Dicen que si los humanos no bebÃ­an de esto, terminaban muriendo." 10 3 place 0

 * @author Juan Manuel Carrera García
 *
 */
public class CityLoaderFromTxtFile {
	private List<Place> places;
	private Place initialPlace;
	private City map;
	
	// ------------------------------------------------- CONSTRUCTORAS -------------------------------
		
	/**
	 * Creates the different array lists
	 */
	public CityLoaderFromTxtFile() {
		this.places = new ArrayList<Place>();
		this.map = new City();
	}
	
	
	// --------------------------------------------- GETTER -----------------------------------------------
		
	/**
	 * Returns the place where the robot will start the simulation
	 * @return The initial place
	 */
	public Place getInitialPlace(){
		return this.initialPlace;
	}
	
	
	// --------------------------------------------- MÉTODOS -----------------------------------------------
	
	// ----------------------- MÉTODO QUE CARGA LA CIUDAD DE UN ARCHIVO DE TEXTO ---------------------------
	
	/**
	 * Thes method loadCity from a file
	 * @param file The input stream where the city is stored
	 * @return The city
	 * @throws IOException When there is some format error in the file (WrongCityFormatException) or some errors in IO operations
	 */
	public City loadCity(InputStream file) throws IOException {
		BufferedReader b = null;
		String line;

		try {
			b = new BufferedReader(new InputStreamReader(file));
		} catch (Exception e) {
			throw new WrongCityFormatException("The file doesn't exist");
		}

		line = b.readLine();

		if (line.equalsIgnoreCase("BeginCity")) {
			do {
				line = b.readLine();

				if (line == null)
					throw new WrongCityFormatException("Formato incorrecto");
				if (line.equalsIgnoreCase("BeginPlaces")) {
					while (!line.equalsIgnoreCase("EndPlaces")) {
						line = b.readLine();
						if (line == null)
							throw new WrongCityFormatException(
									"Incorrect Format");
						if (!line.equalsIgnoreCase("EndPlaces"))
							this.places.add(this.parsePlace(line));
					}
				} else if (line.equalsIgnoreCase("BeginStreets")) {
					while (!line.equalsIgnoreCase("EndStreets")) {
						line = b.readLine();
						if (line == null)
							throw new WrongCityFormatException(
									"Incorrect Format");
						if (!line.equals("EndStreets"))
							this.map.addStreet(this.parseStreet(line));
					}
				} else if (line.equalsIgnoreCase("BeginItems")) {
					int cont = 0;
					while (!line.equalsIgnoreCase("EndItems")) {
						line = b.readLine();
						if (line == null)
							throw new WrongCityFormatException(
									"Incorrect Format");
						if (!line.equals("EndItems")) {
							this.parseItem(line, cont);
							cont++;
						}
					}
				}
			} while (!line.equals("EndCity"));
		} else
			throw new WrongCityFormatException("Incorrect Format");

		this.initialPlace = this.places.get(0);

		return this.map;
	}
	
	// ----------------------- MÉTODO QUE PARSEA UNA LÍNEA DE UN PLACE ---------------------------
		
	/**
	 * CParses the String returning an instance of Place or throwing a WrongCityFormatException()
	 * @param line a line of the file
	 * @return a place 
	 * @throws WrongCityFormatException
	 */
	private Place parsePlace(String line) throws WrongCityFormatException {
		Place aux;
		String[] words;

		if (line.indexOf('"') != -1)
			words = this.divideLinePlace(line);
		else
			words = line.split(" ");

		if (words.length != 5
				|| (!words[0].equalsIgnoreCase("Place"))
				|| !(words[4].equalsIgnoreCase("spaceShip") || words[4]
						.equalsIgnoreCase("noSpaceShip"))
				|| (places.size() != this.convertInt(words[1].toCharArray())))
			throw new WrongCityFormatException("Formato incorrecto");

		if (words[4].equalsIgnoreCase("spaceShip"))
			aux = new Place(words[2], true, words[3].replace('_', ' '));
		else
			aux = new Place(words[2], false, words[3].replace('_', ' '));

		return aux;
	}
	
	// ---------------- MÉTODO QUE DIVIDE UNA LÍNEA DE PLACE PARA EL FICHERO CON ESPACIOS -------------
	
	/**
	 * Divides a line from a place
	 * @param line to parse
	 * @return parsed line
	 * @throws WrongCityFormatException
	 */
	private String[] divideLinePlace(String line)
			throws WrongCityFormatException {
		
		int inicio = line.indexOf('"');
		int finDes = line.indexOf('"', inicio + 1);

		
		String principio = line.substring(0, inicio - 1);
		String descripcion = line.substring(inicio + 1, finDes);
		String fin = line.substring(finDes + 2);
		String[] aux1 = principio.split(" ");
		
		if (aux1.length != 3)
			throw new WrongCityFormatException();
		
		String[] words = new String[5];
		words[0] = aux1[0];
		words[1] = aux1[1];
		words[2] = aux1[2];
		words[3] = descripcion;
		words[4] = fin;
		return words;	
	}
	
	// ----------------------- MÉTODO QUE PARSEA UNA LÍNEA DE UNA STREET ---------------------------
	
	/**
	 * Parses the String returning an instance of Street or throwing a WrongCityFormatException()
	 * @param line a line of the file
	 * @return The City Map
	 * @throws WrongCityFormatException
	 */
	private Street parseStreet(String line) throws WrongCityFormatException {
		Street aux = null;
		String[] words = line.split(" ");
		Direction dir = Direction.parseDirection(words[4]);
		int placeI, placeF;

		placeI = this.convertInt(words[3].toCharArray());
		placeF = this.convertInt(words[6].toCharArray());
		
		if (placeI == -1 || placeF == -1)
			throw new WrongCityFormatException("Incorrect Format");
		
		if ((words.length != 8 && words.length != 9)
				|| (!words[0].equalsIgnoreCase("Street"))
				|| (placeI >= this.places.size() || placeF >= this.places
						.size())
				|| (dir == Direction.UNKNOWN)
				|| !((words[7].equalsIgnoreCase("open") && words.length == 8) || (words[7]
						.equalsIgnoreCase("closed")) && words.length == 9)
				|| (this.map.getNumStreet() != this.convertInt(words[1].toCharArray())))
			throw new WrongCityFormatException("Formato Incorrecto");

		if (words[7].equalsIgnoreCase("open"))
			aux = new Street(this.places.get(placeI), dir,
					this.places.get(placeF), true, "");
		else
			aux = new Street(this.places.get(placeI), dir,
					this.places.get(placeF), false, words[8]);

		return aux;
	}
	
	// ----------------------- MÉTODO QUE PARSEA UN ENTERO ---------------------------

	/**
	 * Converts a char to an integer or -1 if the char is not a integer
	 * @param char to parse
	 * @return parsed number or -1 if the char is not a integer
	 */
	private int parseInt(char num) {
		int n;
		switch (num) {
		case '0': n = 0; break;
		case '1': n = 1; break;
		case '2': n = 2; break;
		case '3': n = 3; break;
		case '4': n = 4; break;
		case '5': n = 5; break;
		case '6': n = 6; break;
		case '7': n = 7; break;
		case '8': n = 8; break;
		case '9': n = 9; break;
		default: n = -1;
		}
		
		return n;
	} 
	
	/**
	 * Converts a array of char to an integer
	 * @param char array of numbers
	 * @return parsed number or -1 if the number is not correct
	 */
	private int convertInt(char [] n) {
		int i = n.length - 1;
		boolean ok = true;
		int resultado = 0;
		int potencia = 1;
		while (i >= 0 && ok) {
			int aux = parseInt(n[i]);
			if (aux != -1) {
				resultado = resultado + aux * potencia;
				potencia *= 10;
				i--;				
			}
			else 
				ok = false;
		}	
		
		if (!ok)
			resultado = -1;
		
		return resultado;
	}
	
	// ----------------------- MÉTODO QUE PARSEA UNA LÍNEA DE UN ÍTEM ---------------------------
		
	/**
	 * Parses the String returning an instance of Item or throwing a WrongCityFormatException()
	 * @param line a line of the file
	 * @param contador number of elements
	 * @throws WrongCityFormatException
	 */
	private void parseItem(String line, int contador)
			throws WrongCityFormatException {
		int place;
		String[] words;

		if (line.indexOf('"') != -1)
			words = this.divideLineItem(line);
		else
			words = line.split(" ");

		if (words[0].equalsIgnoreCase("Fuel"))
			place = this.convertInt(words[7].toCharArray());
		else if (words[0].equalsIgnoreCase("Garbage")
				|| words[0].equalsIgnoreCase("CodeCard"))
			place = this.convertInt(words[6].toCharArray());
		else
			throw new WrongCityFormatException("Incorrect Format");

		if (!((words[0].equalsIgnoreCase("Fuel") && words.length == 8) || ((words[0]
				.equalsIgnoreCase("Garbage") || words[0]
				.equalsIgnoreCase("CodeCard")) && words.length == 7))
				|| (place >= this.places.size())
				|| (this.convertInt(words[1].toCharArray()) != contador))
			throw new WrongCityFormatException("Formato Incorrecto");

		Item aux;

		if (words[0].equalsIgnoreCase("Fuel"))
			aux = new Fuel(words[2], words[3].replace('_', ' '),
					this.convertInt(words[4].toCharArray()), this.convertInt(words[5].toCharArray()));
		else if (words[0].equalsIgnoreCase("Garbage"))
			aux = new Garbage(words[2], words[3].replace('_', ' '),
					this.convertInt(words[4].toCharArray()));
		else
			aux = new CodeCard(words[2], words[3].replace('_', ' '), words[4]);

		this.places.get(place).addItem(aux);
	}
		
	// ---------------- MÉTODO QUE DIVIDE UNA LÍNEA DE PLACE PARA EL FICHERO CON ESPACIOS -------------
		
	/**
	 * Divides a line from a item
	 * @param line to parse
	 * @return parsed line
	 * @throws WrongCityFormatException
	 */
	private String[] divideLineItem(String line)
			throws WrongCityFormatException {

		String[] words = null;
		int inicio = line.indexOf('"');
		int finDes = line.indexOf('"', inicio + 1);

		String principio = line.substring(0, inicio - 1);
		String descripcion = line.substring(inicio + 1, finDes);
		String fin = line.substring(finDes + 2);
		String[] iniAux = principio.split(" ");
		String[] finAux = fin.split(" ");

		if (iniAux.length != 3)
			throw new WrongCityFormatException();
		if (finAux.length != 3 && finAux.length != 4)
			throw new WrongCityFormatException();
		
		
		if (finAux.length == 4) {
			words = new String[8];
			words[7] = finAux[3];
		} else
			words = new String[7];

		words[0] = iniAux[0];
		words[1] = iniAux[1];
		words[2] = iniAux[2];
		words[3] = descripcion;
		words[4] = finAux[0];
		words[5] = finAux[1];
		words[6] = finAux[2];

		return words;
	}
}
