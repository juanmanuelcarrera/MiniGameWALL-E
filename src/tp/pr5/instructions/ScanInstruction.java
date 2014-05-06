package tp.pr5.instructions;

import tp.pr5.Interpreter;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * The execution of this instruction shows the information of the inventory of the robot or the complete description about the item with identifier id contained in the inventory This Instruction works if the player writes SCAN or ESCANEAR (id is not mandatory)
 * @author Juan Manuel Carrera García
 */
public class ScanInstruction implements Instruction {
	private String id;
	private ItemContainer container;	
	private RobotEngine robot;
	
	/**
	 * Create Scan instruction
	 */
	public ScanInstruction() {}
	
	/**
	 * Set the execution context. The method receives the entire engine (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.container = robotContainer;
		this.robot = engine;
	}
	
	/**
	 * Executes the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
		String message = "";
		
		if (id != null) {
			Item aux = this.container.getItem(id);
			if(aux != null)
				message = "WALL·E says: " + aux.getId() + ": " + aux.toString();
			else throw new InstructionExecutionException("WALL·E says: I have not such object");
		}
		else {
			if(this.container.numberOfItems() > 0) 
				message = "WALL·E says: I am carrying the following items" + 
				             		Interpreter.LINE_SEPARATOR + this.container.toString();
			else throw new InstructionExecutionException("WALL·E says: My inventory is empty");			
		}
		this.robot.saySomething(message);
	}
	
	@Override
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException{}
	
	/**
	 * Returns a description of the Instruction syntax. The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return The Instruction's syntax.
	 */
	public String getHelp(){
		return ("The instruction syntax SCAN | ESCANEAR [id]");
		
	}
	
	/**
	 * Parses the String returning an instance its corresponding subclass if the string fits the instruction's syntax. Otherwise it throws an WrongInstructionFormatException. Each non abstract subclass must implement its corresponding parse.
	 * @param cad Text String
	 * @return Instruction Reference pointing to an instance of a Instruction subclass, if it is corresponding to the String cad
	 * @throws WrongInstructionFormatException When the String cad does not fit the Instruction syntax.
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" "); 
		Instruction aux;
		
		if (words [0].equalsIgnoreCase("SCAN") || words [0].equalsIgnoreCase("ESCANEAR")){
			if (words.length == 1)
				aux = new ScanInstruction ();	
			else if (words.length == 2) {
				aux = new ScanInstruction ();
				((ScanInstruction) aux).id = words[1];
			}
			else 
				throw new WrongInstructionFormatException(this.getHelp());
		}
		else throw new WrongInstructionFormatException(this.getHelp());
		
		
		return aux;
	}
}
