package tp.pr5.items;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Street;

/**
 * An item that represents a code card. This item contains a code that can be employed to open and close streets. It always remains in the robot inventory after using it.
 * @author Juan Manuel Carrera García
 */

public class CodeCard extends Item {
	private String code;
	
	// --------------- CONSTRUCTORAS -------------------------
	/**
	 * Code card constructor
	 * @param id Code card name
	 * @param description Code card description
	 * @param code Secret code stored in the code card
	 */
	public CodeCard (String id, String description, String code) {
		super (id, description);
		this.code = code;		
	}
	
	
	// ------------------------ GETTER --------------------------
	/**
	 * Get the card code
	 * @return A String that represents the code
	 */
	public String getCode () {
		return this.code;
	}
	
	
	// -------------------------- MÉTODO QUE COMPARA CÓDIGOS -----------------------
	
	/**
	 *  This methods compare the string of two codes.
	 * @param cod The code to compare
	 * @return true if the code are equals
	 */
	public boolean compareCodes(String cod) {
		return (this.code.equalsIgnoreCase(cod));
	}
	
	
	// ------------------------- MÉTODOS PARA USAR LAS TARJETAS ---------------------
	
	@Override
	/**
	 * A code card always can be used. Since the robot has the code card it never loses it
	 * @return true because it always can be used
	 */
	public boolean canBeUsed () {
		return true;
	}
	
	// ----------------------- MÉTODO PARA ABRIR O CERRAR UNA CALLE ---------------------
	
	
	/**
	 * The method to use a code card. 
	 * If the robot is in a place which contains a street in the direction he is looking at, 
	 * then the street is opened or closed if the street code and the card code match
	 * @return  If the codecard can complete the action of opening or closing a street. Otherwise it returns false
	 */
	@Override
	public boolean use(RobotEngine r, NavigationModule nav) {
		boolean ok = false;
		Street calle = nav.getHeadingStreet();

		if (calle != null) {
			if (calle.isOpen())
				ok = calle.close(this);
			else
				ok = calle.open(this);
		} 

		return ok;
	}
	
	/**
	 * Undo the use of the item
	 * @param r - robotEngine
	 * @param nav - NavigationModule
	 * @param container - ItemContainer
	 * @return true if the item are using
	*/
	public boolean useUndo(RobotEngine r, NavigationModule nav, ItemContainer container) {
		return this.use(r, nav);
	}
}
