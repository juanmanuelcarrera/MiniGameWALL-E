package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import tp.pr5.RobotEngineObserver;
import tp.pr5.Rotation;
import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.TurnInstruction;

/**
 * This class creates the window for the Swing interface. The MainWindow contains the following components: •A menu with the QUIT action
	•An Action panel with several buttons to perform MOVE, TURN, OPERATE, PICK, and DROP actions. Additionally it has a combo box of turn rotations and a text field to write the name of the item that the robot wants to pick from the current place
	•A RobotPanel that displays the robot information (fuel and recycled material) and the robot inventory, which shows a table with item names and descriptions that the robot carries. The user can select the items contained in the inventory in order to DROP or OPERATE an item
	•A CityPanel that represents the city. It shows the places that the robot has visited and an icon that represents the robot heading. The robot heading is updated when the user performs a TURN action. The visible places are updated when the robot performs a MOVE action. Once a place is visited, the user can click on it in order to display the place description (similar to the RADAR command). 
 * @author Juan Manuel Carrera García
 */
public class MainWindow extends JFrame implements RobotEngineObserver {
	private static final long serialVersionUID = 1L;
	
	private GUIController controller;
	private GUIListener listener;
	
	private NavigationPanel navPanel;
	private RobotPanel robotPanel;
	private InfoPanel infoPanel;
	
	private JSplitPane panelSuperior;
	private JPanel buttonPanel;
	private JButton move, operate, pick, drop, quit, turn, undo;
	private JTextField items;
	private JComboBox<Rotation> comboRotation;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem quitMenu;
	
	// -------------------- CONSTRUCTORA -------------------
	
	/**
	 * Creates the window and the panels using Swing Components.
	 *  It stores a reference to the RobotEngine object and provides the panels to the robot engine
	 *   in order to communicate the simulation events.
	 * @param robot - The RobotEngine that receives the instructions performed by the action panel
	 */
	public MainWindow(GUIController control){
		super("WALL·E The garbage collector");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

        this.navPanel = new NavigationPanel();
        this.robotPanel = new RobotPanel();
        this.infoPanel = new InfoPanel();
        
        this.controller = control;
        this.controller.setRobotObserver(this);
        this.controller.setRobotObserver(this.robotPanel);
        this.controller.setRobotObserver(this.infoPanel);
        this.controller.setNavigationObserver(this.navPanel);
        this.controller.setNavigationObserver(this.infoPanel);
        this.controller.setInventoryObserver(this.robotPanel);
        this.controller.setInventoryObserver(this.infoPanel);      
        
        this.listener = new GUIListener();
	}
       
	// -------------------- CREADORA DE LA VENTANA -------------------
	
	/**
	 * Prepare all actionListener of the instruction buttons and create the Main Window
	 */
	public void createMainWindows(){
        this.initMenuBar();	
		this.initButtonPanel();
		this.initPanelSuperior();
		
		this.add(this.panelSuperior, BorderLayout.NORTH);
		this.add(this.navPanel, BorderLayout.CENTER);
		this.add(this.infoPanel, BorderLayout.SOUTH);
		
		this.setSize(900, 650);
		this.setVisible(true);		
	}
	
	/**
	 * Initialize the MenuBar to the MainWindow
	 */
	private void initMenuBar() {
		this.menuBar= new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.quitMenu = new JMenuItem("Quit");
		
		this.quitMenu.addActionListener(this.listener);
		this.fileMenu.add(quitMenu);
	    this.menuBar.add(this.fileMenu);
	    this.setJMenuBar(this.menuBar);		
	}
	
	/**
	 * Initialize the Button Panel. This method placed the buttons on panel
	 */
	private void initButtonPanel() {
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout(5,2,2,2));
		this.buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		
		this.initButtons();
		
		this.buttonPanel.add(this.move);
		this.buttonPanel.add(this.quit);
		this.buttonPanel.add(this.turn);
		this.comboRotation.removeItem(Rotation.UNKNOWN);
		this.buttonPanel.add(this.comboRotation);
		this.buttonPanel.add(this.pick);
		this.buttonPanel.add(this.items);
		this.buttonPanel.add(this.drop);
		this.buttonPanel.add(this.operate);
		this.buttonPanel.add(this.undo);
	}
	
	/**
	 * Initialize the Buttons to the Buttons Panel
	 */
	private void initButtons() {
		this.move = new JButton("MOVE");
        this.pick = new JButton("PICK");
        this.drop = new JButton("DROP");
        this.operate = new JButton("OPERATE");
        this.items = new JTextField(15);
        this.quit = new JButton("QUIT");
        this.turn = new JButton("TURN");
        this.comboRotation = new JComboBox<Rotation>(Rotation.values());
        this.undo = new JButton("UNDO");
		
        this.move.addActionListener(this.listener);
		this.operate.addActionListener(this.listener);
		this.drop.addActionListener(this.listener);
		this.pick.addActionListener(this.listener);
		this.quit.addActionListener(this.listener);
		this.turn.addActionListener(this.listener);
		this.undo.addActionListener(this.listener);
	}
	
	/**
	 * Initialize the top panel. This method places the panel buttons and the inventory of the robot in the Main Window
	 */
	private void initPanelSuperior(){
		this.panelSuperior = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.panelSuperior.add(this.buttonPanel);
		this.panelSuperior.add(this.robotPanel);
	}
	
	// -------------------- CUADROS DE DIALOGO PARA MOSTRARA MENSAJES -------------------
		
	/**
	 * This method that prints out a message of confirmation to know if you want to exit the application
	 */
	public void quit() {
		if(JOptionPane.showConfirmDialog(this, "Do you really want to leave?", "Confirm exit", JOptionPane.YES_NO_OPTION)
				== JOptionPane.YES_OPTION)  
			System.exit(0);
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
	public void engineOff(boolean atShip) {
		if (atShip)
			JOptionPane.showMessageDialog(this, "WALL·E says: I am at my spaceship. Bye bye", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
		else 
			JOptionPane.showMessageDialog(this, "WALL·E says: I run out of fuel. I cannot move. Shutting down...", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	/**
	 * The robot engine informs that it has raised an error
	 * @param msg Error message
	 */
	@Override
	public void raiseError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * The robot engine informs that the robot wants to say something
	 * @param message The robot message
	 */
	@Override
	public void robotSays(String message) {}

	/**
	 * The robot engine informs that the fuel and/or the amount of recycled material has changed
	 * @param fuel Current amount of fuel
	 * @param recycledMaterial Current amount of recycled material
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {}
		
	
	/**
	 * This class is responsible for capturing and managing events produced in the main window when the user 
	 * presses any button of the panel buttons
	 * @author Juan Manuel Carrera García - Sergio Gil Jiménez
	 */	
	private class GUIListener implements ActionListener {
		/**
		 * Invoked when an action occurs.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (arg0.getActionCommand()) {
			case "MOVE":
				controller.executeInstruction(new MoveInstruction());
				break;
			case "PICK":
				controller.executeInstruction(new PickInstruction(items.getText()));
				items.setText("");
				break;
			case "TURN":
				controller.executeInstruction(new TurnInstruction((Rotation) comboRotation.getSelectedItem()));
				break;
			case "QUIT":
			case "Quit":	
				quit();
				break;
			case "DROP":
				if(robotPanel.getSelectedItem() == null)
					raiseError("You must select a item in the list");
				else 
					controller.executeInstruction(new DropInstruction(robotPanel.getSelectedItem()));
				break;
			case "UNDO":
				controller.executeInstructionUndo();
				break;
			case "OPERATE":
				if(robotPanel.getSelectedItem() == null)
					raiseError("You must select a item in the list");
				else 
					controller.executeInstruction(new OperateInstruction(robotPanel.getSelectedItem()));
				break;
			}
		}
	}
}
