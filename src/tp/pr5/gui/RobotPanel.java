package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

/**
 * RobotPanel displays information about the robot and its inventory. More specifically, it contains labels to show the robot fuel and the weight of recycled material and a table that represents the robot inventory. 
 * Each row displays information about an item contained in the inventory.
 * @author Juan Manuel Carrera García
 */

public class RobotPanel extends JPanel implements RobotEngineObserver, InventoryObserver{
	private static final long serialVersionUID = 1L;
	private static final String[] Titles = {"Id", "Description"};
	
	private OwnTableModel tableModel;
	private JTable table;
	private JLabel robotFuel;
	private JLabel robotRecycledMaterial;
		
	/**
	 * Initialize the Robot Panel. This method add to the panel WALL·E inventory and WALL·E state
	 */
	public RobotPanel(){
		super(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Robot Info"));
		
		this.tableModel = new OwnTableModel(null, RobotPanel.Titles);
		this.table = new JTable(this.tableModel);
		this.table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setPreferredScrollableViewportSize(new Dimension(600,70));
				
		this.robotFuel = new JLabel("Fuel: ");
		this.robotRecycledMaterial = new JLabel("Recycled Material: ");
		
		JPanel robotInfo = new JPanel(new FlowLayout());
		robotInfo.add(this.robotFuel);
		robotInfo.add(this.robotRecycledMaterial);
		
		this.add(robotInfo, BorderLayout.NORTH);
		this.add(new JScrollPane(this.table), BorderLayout.CENTER);
	}
	
	/**
	 * Updated the panel of the fuel 
	 * @param robotFuel Robot fuel to add to WALL·E
	 */
	public void updateRobotFuel(int robotFuel){
		this.robotFuel.setText("Fuel: " + robotFuel);
	}
	
	/**
	 * Updated the panel of the Recycled Material
	 * @param robotRecycledMaterial RecycledMateril to add to WWALL·E
	 */
	public void updateRobotRecycledMaterial(int robotRecycledMaterial){
		this.robotRecycledMaterial.setText("RecycledMaterial: " + robotRecycledMaterial);
	}
	
	/**
	 * This method return the string name of the selected item in the invetory or null otherwise
	 * @return the item which you have selected or null otherwise
	 */
	public String getSelectedItem() {
		String itemSelected = null;

		if (this.table.getSelectedRow() != -1) {
			itemSelected = this.table.getValueAt(this.table.getSelectedRow(), 0).toString();
		}
		
		return itemSelected;
	}
	

	/**
	 * Notifies that the container has changed
	 * @param inventory New Inventory
	 */
	@Override
	public void inventoryChange(List<Item> inventory) {
		String[][] infoUpdate = null;
			
		infoUpdate = new String[inventory.size()][2];
		for (int i = 0; i < inventory.size(); i++) {
			infoUpdate[i][0] = inventory.get(i).getId();
			infoUpdate[i][1] = inventory.get(i).getDescription();
		}			
		
		this.tableModel = new OwnTableModel(infoUpdate, RobotPanel.Titles);
		this.table.setModel(this.tableModel);
	}

	/**
	 * The robot engine informs that the fuel and/or the amount of recycled material has changed
	 * @param fuel Current amount of fuel
	 * @param recycledMaterial Current amount of recycled material
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.updateRobotFuel(fuel);
		this.updateRobotRecycledMaterial(recycledMaterial);
	}	
	
	/**
	 * The robot engine informs that the communication is over.
	 */
	@Override
	public void communicationCompleted() {}

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
	public void engineOff(boolean atShip) {}

	/**
	 * The robot engine informs that it has raised an error
	 * @param msg Error message
	 */
	@Override
	public void raiseError(String msg) {}

	/**
	 * The robot engine informs that the robot wants to say something
	 * @param message The robot message
	 */
	@Override
	public void robotSays(String message) {}
}
