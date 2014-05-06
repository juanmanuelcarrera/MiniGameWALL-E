package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;
/**
 * This instruction drops an Item from the current place and puts it the robot inventory. This instruction works if the user writes DROP or SOLTAR
 * @author Juan Manuel Carrera García
 */
public class DropInstruction implements Instruction {
	private String id;
	private NavigationModule navig;
	private ItemContainer container;
	private RobotEngine robot;
	
	/**
	 * Create Drop instruction with id
	 * @param ide id for the instruction
	 */
	public DropInstruction(String ide) {
		this.id = ide;
	}
	
	/**
	 * Create Drop instruction
	 */
	public DropInstruction(){
		this.id = "";
	}
		
	/**
	 * Configuration of the context for this instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.navig = navigation;
		this.container = robotContainer;
		this.robot = engine;
	}
	
	/**
	 * The robot drops an Item from its inventory in the current place, if the item exists
	 * @throws InstructionExecutionException - if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
			if (!this.container.containsItem(this.id)) 
				throw new InstructionExecutionException ("You do not have any " + this.id + ".");
		
			if (this.navig.findItemAtCurrentPlace(this.id)) 
				throw new InstructionExecutionException ("The item already exists in this place");
			
			Item aux = this.container.pickItem(this.id);
			this.navig.dropItemAtCurrentPlace(aux);
			this.container.inventoryChange();
			this.robot.saySomething("Great! I have dropped " + aux.getId());
	}
	
	@Override
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException {
		Instruction ins = new PickInstruction(this.id);
		ins.configureContext(robot, navig, container);
		ins.execute();
	}
	
	/**
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return the instruction syntax DROP | SOLTAR <id>
	 */
	public String getHelp(){
		return ("The instruction syntax DROP | SOLTAR <id>");
		
	}
	
	/**
	 * Parses the String returning a MoveInstruction instance or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of DropInstruction
	 * @throws WrongInstructionFormatException When the String is not Drop
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" "); 
		Instruction aux;
		
		if (words.length == 2 && (words [0].equalsIgnoreCase("DROP") || words [0].equalsIgnoreCase("SOLTAR"))) 
				aux = new DropInstruction (words [1]); 			
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}
