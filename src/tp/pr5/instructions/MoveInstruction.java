package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 *Its execution moves the robot from one place to the next one in the current direction. This instruction works if the user writes MOVE or MOVER
 * @author Juan Manuel Carrera García
 */
public class MoveInstruction implements Instruction{
	private NavigationModule navig;
	private RobotEngine robot;	
	
	/**
	 * Create Move instruction
	 */
	public MoveInstruction() {}
	
	/**
	 * Set the execution context. The method receives the entire engine (engine, navigation and the robot container)
	 * even though the actual implementation of execute() may not require it.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.robot = engine;
		this.navig = navigation;		
	}
	
	/**
	 * Moves from the current place to the next place on the current Direction. 
	 * An opened street must exist between both places to be moved
	 * @throws InstructionExecutionException When the robot cannot go to other place (there is a wall, a closed street...)
	  */
	public void execute() throws InstructionExecutionException{
		this.navig.move();
		this.robot.addFuel(-5);
	}
	
	@Override
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException{
		this.navig.moveUndo();
		this.robot.addFuel(5);
	}
	
	/**
	 * Returns a description of the Instruction syntax. 
	 * The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return the command syntax MOVE|MOVER
	 */
	public String getHelp(){
		return ("The instruction syntax MOVE | MOVER");
		
	}
	/**
	 * Parses the String returning a MoveInstruction instance or throwing a WrongInstructionFormatException()
	 * @param cad text String to parse
	 * @return Instruction Reference to an instance of MoveInstruction
	 * @throws WrongInstructionFormatException When the String is not MOVE
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" "); 
		Instruction aux;
		
		if (words.length == 1 && (words [0].equalsIgnoreCase("MOVE") || words [0].equalsIgnoreCase("MOVER"))) 
			aux = new MoveInstruction ();
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}
