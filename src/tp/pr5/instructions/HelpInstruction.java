package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;
/**
 * Shows the game help with all the instructions that the robot can execute. This instruction works if the user writes HELP or AYUDA
 * @author Juan Manuel Carrera García
 *
 */
public class HelpInstruction implements Instruction {
	private RobotEngine robot;
	
	/**
	 * Create Help instruction
	 */
	public HelpInstruction() {}
	
	/**
	 * Configuration of the context for this instruction
	 * @param engine - The robot engine
	 * @param navigation - The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer - The inventory of the robot

	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.robot = engine;
	}
	
	/**
	 * Prints the help string of every instruction. It delegates to the Interpreter class.
	 * @throws InstructionExecutionException - if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
		this.robot.requestHelp();
	}
	
	@Override
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException{}
	
	/**
	 * Help syntax
	 * @return the instruction syntax HELP
	 */
	public String getHelp(){
		return ("The instruction syntax HELP | AYUDA");
		
	}
	/**
	 * Parses the String returning a HelpInstruction instance or throwing a WrongInstructionFormatException()
	 * @param cad - Text String to parse
	 * @return Instruction Reference to an instance of HelpInstruction
	 * @throws WrongInstructionFormatException - When the String is not HELP
	 */
	public Instruction parse(String cad) throws WrongInstructionFormatException{
		String[] words = cad.split(" "); 
		Instruction aux;
		
		if (words.length == 1 && (words [0].equalsIgnoreCase("HELP") || words [0].equalsIgnoreCase("AYUDA")))
				aux = new HelpInstruction (); 			
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}
