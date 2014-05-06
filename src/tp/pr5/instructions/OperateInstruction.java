package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * The Instruction for using an item. This Instruction works if the user writes OPERATE id or OPERAR id
 *  @author Juan Manuel Carrera García
 */
public class OperateInstruction implements Instruction{
	private String id;
	private Item item;
	private RobotEngine robot;
	private NavigationModule navig;
	private ItemContainer container;
	
	/**
	 * Create Operate instruction with id
	 * @param ide id for the instruction
	 */
	public OperateInstruction(String ide) {
		this.id = ide;
	}
	
	/**
	 * Create Operate instruction
	 */
	public OperateInstruction() {
		this.id = "";
	}
	
	/**
	 * Set the execution context. The method receives the entire engine (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
   	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
		this.navig = navigation;
		this.container = robotContainer;
	}
	
   	/**
	 * Executes the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
		this.item = this.container.getItem(id);
		
		if(this.item != null){
			if(!this.item.use(this.robot, this.navig))
				throw new InstructionExecutionException("WALL·E says: I have problems using the object " +  id);
			if (!this.item.canBeUsed()) 
				this.robot.saySomething("WALL·E says: What a pity! I have no more " + id + " in my inventory");
			this.container.useItem(this.item);
		}
		else throw new InstructionExecutionException("WALL·E says: I am stupid. I have not " + id);
	}
	
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException {
		Item item = this.item;
		
		if(!item.useUndo(this.robot, this.navig, this.container))
			throw new InstructionExecutionException("WALL·E says: I have problems using the object " +  item.getId());
	}
		
	/**
	 * Returns a description of the Instruction syntax. The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return The Instruction's syntax.
	 */
	public String getHelp(){
		return ("The instruction syntax OPERATE | OPERAR <ID>");
		
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
		
		if (words.length == 2 && (words [0].equalsIgnoreCase("OPERATE") || words [0].equalsIgnoreCase("OPERAR")))  
				aux = new OperateInstruction (words [1]);			
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}	
	
