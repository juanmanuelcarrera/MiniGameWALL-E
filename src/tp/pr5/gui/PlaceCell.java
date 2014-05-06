package tp.pr5.gui;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Represents a Place in the city on the Swing interface. It is a button, which name is the place name. 

A PlaceCell needs to store a reference to the place that it represents. However, this place should not be modified by the PlaceCell 

When the user clicks on a PlaceCell the CityPanel will show the place description if the Place was previously visited.

 * @author Juan Manuel Carrera García
 */
public class PlaceCell extends JButton {
	private static final long serialVersionUID = 1L;
	private String description;
	
	/**
	 * Initialize the Place Cell
	 */
	public PlaceCell() {
		super();
		this.setVisible(true);
		this.description = "";
	}
	
	/**
	 * Initialize the Place Cell with a description
	 * @param descrip description of a place
	 */
	public PlaceCell(String descrip) {
		super();
		this.setVisible(true);
		this.description = descrip;
	}
	
	/**
	 * Get the description of the button
	 * @return the description of the button
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of the button
	 */
	public void setDescription(String descrip) {
		this.description = descrip;
	}	
	
	/**
	 * Change the color of the button
	 */
	public void changeColor(Color c) {
		this.setBackground(c);
	}	
}
