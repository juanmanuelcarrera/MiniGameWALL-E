package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * This instruction tries to pick an Item from the current place and puts it the robot inventory. This instruction works if the user writes PICK id or COGER id
 *  @author Juan Manuel Carrera García
 */
public class PickInstruction implements Instruction{
	private String id;
	private NavigationModule navig;
	private ItemContainer container;
	private RobotEngine robot;
	
	/**
	 * Create Pick instruction with id
	 * @param ide id for the instruction
	 */
	public PickInstruction(String ide) {
		this.id = ide;
	}
	
	/**
	 * Create Pick Instruction
	 */
	public PickInstruction() {
		this.id = "";
	}
	
	/**
	 * Set the execution context. The method receives the entire engine (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.navig = navigation;
		this.container = robotContainer;
		this.robot = engine;
	}
	
	
	/**
	 * Executes the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
		Item aux = this.navig.getCurrentPlace().pickItem(id);
		Item auxRobot = this.container.getItem(id);
		
		if (aux != null) {
			if (auxRobot == null) {
				this.container.addItem(aux);
				this.container.inventoryChange();
				this.navig.updateNavigationModule();
				this.robot.saySomething("WALL·E says: I am happy! Now I have " + id);
			}
			else {
				this.navig.getCurrentPlace().addItem(aux);
				throw new InstructionExecutionException ("WALL·E says: I am stupid! I already have the object " + id);
			}
		}
		else throw new InstructionExecutionException ("WALL·E says: Ooops, this place has not the object " + id);
	}
	
	@Override
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException{
		Instruction ins = new DropInstruction(this.id);
		ins.configureContext(robot, navig, container);
		ins.execute();
	}
	
	/**
	 * Returns a description of the Instruction syntax. The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return The Instruction's syntax.
	 */
	public String getHelp(){
		return ("The instruction syntax PICK | COGER <id>");
		
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
		
		if (words.length == 2 && (words [0].equalsIgnoreCase("PICK") || words [0].equalsIgnoreCase("COGER"))) 
				aux = new PickInstruction (words[1]);			
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}
