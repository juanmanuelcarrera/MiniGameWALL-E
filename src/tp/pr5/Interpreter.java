package tp.pr5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tp.pr5.instructions.DropInstruction;
import tp.pr5.instructions.HelpInstruction;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.QuitInstruction;
import tp.pr5.instructions.RadarInstruction;
import tp.pr5.instructions.ScanInstruction;
import tp.pr5.instructions.TurnInstruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * The interpreter is in charge of converting the user input in an instruction for the robot. Up to now, the valid instructions are:
 *	•MOVE
 *  •TURN { LEFT | RIGHT }
 *  •HELP
 *  •PICK <ITEM>
 *  •SCAN [ <ITEM> ]
 *  •OPERATE <ITEM>
 *  •QUIT
 *  @author Juan Manuel Carrera García 
 */
public class Interpreter {
	public static final java.lang.String LINE_SEPARATOR = System.getProperty("line.separator");

	
	// ----------------------------------------------------- MÉTODOS -----------------------------------------------------
	
	// ---------------------------------- MÉTODO QUE GENERA UNA NUEVA INSTRUCCIÓN ----------------------------------------
	
	/**
	 * Generates a new instruction according to the user input
	 * @param line A string to the user input
	 * @return The instruction read from the given line. If the instruction is not correct, then it returns a not valid instruction
	 */
	public static Instruction generateInstruction(String line)
			throws WrongInstructionFormatException {
		List<Instruction> ins = Interpreter.generateListInstrucctions();
		Iterator<Instruction> iter = ins.iterator();
		Instruction instruction = null;
		boolean correct = false;
			
		while (iter.hasNext() && !correct) {
			try {
				instruction = iter.next().parse(line);
				correct = true;
			} catch (WrongInstructionFormatException e) {
			}
		}
		
		if (!correct)
			throw new WrongInstructionFormatException("Invalid Instruction"
					+ Interpreter.LINE_SEPARATOR
					+ Interpreter.interpreterHelp());

		return instruction;
	}
		
	
	// ---------------------------------- MÉTODO QUE GENERA UN STRING CON LA AYUDA DEL JUEGO ----------------------------------------
		
	/**
	 * It returns information about all the instructions that the robot understands
	 * @return A string with the information about all the available instructions
	 */
	public static String interpreterHelp() {
		List<Instruction> ins = Interpreter.generateListInstrucctions();
		Iterator<Instruction> iter = ins.iterator();
		String aux = "";

		while (iter.hasNext()) 
			aux = aux + "	" + iter.next().getHelp() + LINE_SEPARATOR;
		
		return ("The valid instructions for WALL·E are:" + LINE_SEPARATOR + aux);
	}
	
	
	// ---------------------------------- MÉTODO QUE DEVUELVE UN ARRAY CON TODAS LAS INSTRUCCIONES ------------------------------------
	/**
	 * Generate a list of instructions
	 * @return A array with all information about all the available instructions
	 */
	private static List<Instruction> generateListInstrucctions() {
		List<Instruction> ins = new ArrayList<Instruction>();
		
		ins.add(new MoveInstruction());
		ins.add(new DropInstruction());
		ins.add(new HelpInstruction());
		ins.add(new QuitInstruction());
		ins.add(new ScanInstruction());
		ins.add(new RadarInstruction());
		ins.add(new TurnInstruction());
		ins.add(new OperateInstruction());
		ins.add(new PickInstruction());
		return ins;
	}
}