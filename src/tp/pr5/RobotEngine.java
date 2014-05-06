package tp.pr5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.ItemContainer;

/**
 * This class represents the robot engine. It controls robot movements by processing the instructions introduced with the keyboard. The engine stops when the robot arrives at the spaceship or receives a quit instruction,
 * @author Juan Manuel Carrera García
 */
public class RobotEngine extends tp.pr5.Observable<RobotEngineObserver>{
	private ItemContainer container;
	private int fuel;
	private int weight;
	private NavigationModule nav;
	private boolean endExecution;
	private List<Instruction> instrucUndo;
	

	// -------------------------------------- CONSTRUCTORA -------------------------------
		
	/**
	 * Create robot engine
	 * @param initialPlace Initial place of the robot
	 * @param direction Initial direction of the robot
	 * @param cityMap City map
	 */
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		super();
		this.nav = new NavigationModule(cityMap, initialPlace);
		this.nav.initHeading(direction);
		this.container = new ItemContainer();
		this.instrucUndo = new ArrayList<Instruction>();
	}
	
	
	// ---------------------------- SETTER Y GETTER ------------------------------------
	
	/**
	 * @return A integer with the fuel
	 */
	public int getFuel() {
		return this.fuel;
	}
	
	/**
	 * Configure the fuel
	 * @param fuel
	 */
	public void setFuel(int fuel) {
		this.fuel = fuel;
		this.printRobotState();
	}
	
	/**
	 * @return A integer with the the Recycled Material
	 */
	public int getRecycledMaterial() {
		return this.weight;
	}
	
	/**
	 * Configure the RecycledMateriar
	 * @param recycledMat
	 */
	public void setRecycledMaterial(int recycledMat) {
		this.weight = recycledMat;
		this.printRobotState();
	}
	
	// ---------------------------------- MÉTODOS ----------------------------------------------
	
	// ---------------------------- MÉTODOS PARA AÑADIR FUEL Y MATERIAL RECICLADO --------------
	
	/**
	 * Adds an amount of fuel to the robot (it can be negative)
	 * @param fuel Amount of fuel added to the robot
	 */
	public void addFuel (int fuel) {
		this.fuel = this.fuel + fuel;
		this.printRobotState();
		if (this.fuel <= 0) 
			this.fuel = 0;
	}
		
	/**
	 * Increases the amount of recycled material of the robot
	 * @param weight Amount of recycled material
	 */
	public void addRecycledMaterial (int weight) {
		this.weight = this.weight + weight;
		this.printRobotState();
	}
	
	// ---------------------------- MÉTODOS PARA AÑADIR OBSERVADORES --------------
	
	/**
	 * Registers an EngineObserver to the model
	 * @param observer The observer that wants to be registered
	 */
	public void addEngineObserver(RobotEngineObserver observer) {
		addObserver(observer);
	}
	
	/**
	 * Registers an ItemContainerObserver to the model
	 * @param observer The observer that wants to be registered
	 */
	public void addItemContainerObserver(InventoryObserver observer) {
		this.container.addObserver(observer);
	}
	
	/**
	 * Register a NavigationObserver to the model
	 * @param robotObserver The observer that wants to be registered
	 */
	public void addNavigationObserver(NavigationObserver observer) {
		this.nav.addObserver(observer);
	}
	
	// -------------- MÉTODO PARA MOSTRAR LAS CARACTERÍSTICAS DEL ROBOT --------------

	/**
	 * Show the features of WALL·E
	 */
	public void printRobotState() {
		this.notifyObserversRobotEngine(TypeMessage.UPDATE_ROBOT, null);
	}
	
	// -------------------- MÉTODO PARA QUE EL ROBOT EJECUTE UNA INSTRUCCIÓN -------------------
		
	/**
	 * It executes an instruction. The instruction must be configured with the context before executing it. 
	 * It controls the end of the simulation. If the execution of the instruction throws an exception, 
	 * then the corresponding message is printed
	 * @param c The instruction to be executed
	 */
	public void comunicateRobot(Instruction c){
		c.configureContext(this, this.nav, this.container);
		try {
			c.execute();	
			this.instrucUndo.add(c);
			if (this.isOver())
				this.endExecute();
		} catch (InstructionExecutionException e) {
			this.requestError(e.getMessage());
		}
	}
	
	/**
	 * It executes an undo instruction. The undo instruction must be configured with the context before executing it. 
	 * It controls the end of the simulation. If the execution of the instruction throws an exception, 
	 * then the corresponding message is printed
	 * @param c The instruction to be executed
	 */
	public void comunicateRobotUndo(){
		Instruction ins = this.getIntructionForUndo();
		
		try {
			if (ins == null) 
				throw new InstructionExecutionException("No more instruction for Undo");
	
			ins.executeUndo();			
		} catch (InstructionExecutionException e) {
			this.requestError(e.getMessage());
		}
	}
	
	// -------------------- MÉTODO PARA ACTUALIZAR EL ROBOT Y COMPROBAR SI HEMOS ACABADO -------------------
	
	// -------------------- MÉTODO PARA RESPONDER A LA INSTRUCCIÓN DE HELP -------------------
		
	/**
	 * Prints the information about all possible instructions
	 */
	public void requestHelp() {
		this.notifyObserversRobotEngine(TypeMessage.REQUEST_HELP, null);
	}
	
	// -------------------- MÉTODO PARA RESPONDER A LA INSTRUCCIÓN DE QUIT -------------------
	
	/**
	 * Requests the quit instruction
	 */
	public void requestQuit() {
		this.endExecution = true;
	}
	
	// -------------------- MÉTODO PARA INDICAR EL MENSAJE DE FIN -------------------
	
	/**
	 * Requests the end of the simulation
	 */
	public void endExecute() {
		if (this.isSpaceShip()) 
			this.notifyObserversRobotEngine(TypeMessage.ENGINE_OFF_SPACESHIP, null);
		else if (!this.fuelAfterInstruction()) 
			this.notifyObserversRobotEngine(TypeMessage.ENGINE_OFF_NO_FUEL, null);
		else 
			this.notifyObserversRobotEngine(TypeMessage.COMMUNICATION_COMPLETED, null);
		
	}
		
	// -------------------- MÉTODO PARA INDICAR A LOS OBSERVADORES -------------------

	/**
	 * Request the engine to say something
	 * @param msg The message to say
	 */
	public void saySomething(String msg) {
		this.notifyObserversRobotEngine(TypeMessage.SAY_SOMETHING, msg);
	}
	
	// -------------------- MÉTODO PARA RESPONDER A UN ERROR -------------------
	
	/**
	 * Requests the engine to inform that an error has been raised
	 */
	public void requestError(String msg) {
		this.notifyObserversRobotEngine(TypeMessage.REQUEST_ERROR, msg);
	}
	
	// -------------------- MÉTODO PARA INDICAR A LOS OBSERVADORES -------------------
	
	/**
	 * Requests the engine to inform the observers that the simulation starts
	 */
	public void requestStart() {
		this.fuel = 100;
		this.weight = 0;
		this.endExecution = false;
		this.printRobotState();
	}
		
	// ------------ MÉTODO QUE COMPRUEBA EL FUEL DE DESPUÉS DE EJECUTAR UNA INSTRUCCIÓN ------

	/**
	 * Check the fuel of WALL·E
	 * @return true if WALL·E has not fuel
	 */
	private boolean fuelAfterInstruction () {
		return this.fuel > 0;
	}
	
	// ------------ MÉTODO QUE COMPRUEBA SI LA EJECUCIÓN FINALIZA POR LLEGAR A LA NAVE ESPACIAL ------
	/**
	 * Check if the place contains the spaceShip
	 * @return true if the place contains the spaceShip
	 */
	private boolean isSpaceShip() {
		return this.nav.atSpaceship();
	}
	
	// ------------ MÉTODO QUE COMPRUEBA SI LA EJECUCIÓN FINALIZA POR ALGUNA RAZÓN ------
	
	/**
	 * Check if the simulation is finished
	 * @return true if the game has finished
	 */
	public boolean isOver() {
		return (!this.fuelAfterInstruction() || this.isSpaceShip() || this.endExecution); 
	}
	
	// -------------------- MÉTODOS PARA INICIALIZAR -------------------

	/**
	 * Initialize the Navigation Module
	 */
	public void initNavigationModule() {
		this.nav.initNavigationModule();
	}
	
	/**
	 * Initialize the container of the robot
	 */
	public void initRobotContainer() {
		this.container.inventoryChange();
		this.notifyObserversRobotEngine(TypeMessage.UPDATE_ROBOT, null);
	}

	// -------------------- DA UNA INSTRUCTCIÓN AL MAINWINDOW PARA EJECUTAR UN UNDO -------------------
	/**
	 * Gives an instruction to the MainWindow for the instruction Undo
	 */
	public Instruction getIntructionForUndo() {
		Instruction ins = null;
		if (!instrucUndo.isEmpty()) {
			ins = instrucUndo.get(instrucUndo.size() - 1);
			instrucUndo.remove(instrucUndo.size() - 1);
		}
		return ins;
	}
	
	/**
	 * Notifies observers with the changes that have occurred
	 * @param tipoMensaje Type of Message to to call the correct method
	 * @param message Message to show of the interface
	 */
	private void notifyObserversRobotEngine (TypeMessage tipoMensaje, String message) {
		Iterator<RobotEngineObserver> it = this.iterator();
		while (it.hasNext()) {
			switch (tipoMensaje) {
			case REQUEST_HELP: it.next().communicationHelp(Interpreter.interpreterHelp());
			break;
			case ENGINE_OFF_SPACESHIP: it.next().engineOff(true);
			break;
			case ENGINE_OFF_NO_FUEL: it.next().engineOff(false);
			break;
			case COMMUNICATION_COMPLETED: it.next().communicationCompleted();
			break;
			case SAY_SOMETHING: it.next().robotSays(message);
			break;
			case REQUEST_ERROR: it.next().raiseError(message);
			break;
			case UPDATE_ROBOT: it.next().robotUpdate(this.fuel, this.weight);
			break;
			default: break;
			}
		}
	}
}
		

