package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * Its execution request the robot to finish the simulation This Instruction works if the user writes QUIT or SALIR
 * @author Juan Manuel Carrera García
 */
public class QuitInstruction implements Instruction{
	private RobotEngine robot;
	
	/**
	 * Create Quit instruction
	 */
	public QuitInstruction() {}
	
	/**
	 * Set the execution context. The method receives the entire engine (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.robot = engine;
	}
	
	/**
	 * Executes the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
		this.robot.requestQuit();
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
		return ("The instruction syntax QUIT | SALIR");
		
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
		
		if (words.length == 1 && (words [0].equalsIgnoreCase("QUIT") || words [0].equalsIgnoreCase("SALIR"))) 
				aux = new QuitInstruction (); 			
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}

