package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

/**
 * Its execution rotates the robot This Instruction works if the robot writes TURN LEFT or RIGHT or GIRAR LEFT or RIGHT
 * @author Juan Manuel Carrera García
 */
public class TurnInstruction implements Instruction {
	private Rotation rotacion;
	private NavigationModule navig;
	private RobotEngine robot;
	
	/**
	 * Create Turn instruction with rotation
	 * @param rotate for the instruction
	 */
	public TurnInstruction(Rotation rotate) {
		this.rotacion = rotate;
	}
	
	/**
	 * Create Turn instruction
	 */
	public TurnInstruction() {
		this.rotacion = Rotation.UNKNOWN;
	}
	
	/**
	 * Set the execution context. The method receives the entire engine (engine, navigation and the robot container) even though the actual implementation of execute() may not require it.
	 * @param engine The robot engine
	 * @param navigation The information about the game, i.e., the places, current direction and current heading to navigate
	 * @param robotContainer The inventory of the robot
	 */
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer){
		this.navig = navigation;
		this.robot = engine;
	}
	
	/**
	 * Executes the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void execute() throws InstructionExecutionException{
		this.navig.rotate(this.rotacion);	
		this.robot.addFuel(-5);
	}
	
	@Override
	/**
	 * Undo the Instruction It must be implemented in every non abstract subclass.
	 * @throws InstructionExecutionException if there exist any execution error.
	 */
	public void executeUndo() throws InstructionExecutionException{
		this.navig.rotate(this.rotacion.rotacionOpuesta());	
		this.robot.addFuel(5);
	}
	
	/**
	 * Returns a description of the Instruction syntax. The string does not end with the line separator. It is up to the caller adding it before printing.
	 * @return The Instruction's syntax.
	 */
	public String getHelp(){
		return ("The instruction syntax TURN | GIRAR <LEFT|RIGHT>");
		
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
		
		if (words.length == 2 && (words [0].equalsIgnoreCase("TURN") || words [0].equalsIgnoreCase("GIRAR"))) {
			if (words [1].equalsIgnoreCase("RIGHT") || words [1].equalsIgnoreCase("LEFT")) {
				if(words [1].equalsIgnoreCase("LEFT"))
					aux = new TurnInstruction (Rotation.LEFT);
				else 	
					aux = new TurnInstruction (Rotation.RIGHT);
			}
			else throw new WrongInstructionFormatException(this.getHelp());
		}
		else throw new WrongInstructionFormatException(this.getHelp());
		
		return aux;
	}
}
