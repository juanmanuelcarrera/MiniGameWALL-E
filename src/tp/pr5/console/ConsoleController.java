package tp.pr5.console;

import java.util.Scanner;

import tp.pr5.Controller;
import tp.pr5.Interpreter;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;

/**
 * The controller employed when the application is configured as a console application. It contains the simulation loop that executes the instructions written by the user on the console.
 * @author Juan Manuel Carrera García 
 *
 */
 
public class ConsoleController extends Controller {
	/**
	 * Constructor of the controller. It receives the model main class.
	 * @param robot Engine that is being played
	 */
	public ConsoleController(RobotEngine robot) {
		super(robot);
		Console consola = new Console();
		this.setRobotObserver(consola);
		this.setNavigationObserver(consola);
		this.setInventoryObserver(consola);
	}

	/**
	 * Initialize the game with console interface
	 */
	@Override
	public void runGame() {
		Scanner sc = new Scanner(System.in);
		String line;
		Instruction ins = null;
		
		this.robotEngine.initNavigationModule();
		this.robotEngine.requestStart();
		do {
			System.out.print("WALL·E> ");
			line = sc.nextLine();
			try {
				ins = Interpreter.generateInstruction(line);
				this.robotEngine.comunicateRobot(ins);
			} catch (WrongInstructionFormatException e) {
				this.robotEngine.requestError(e.getMessage());
			}
		} while (!this.robotEngine.isOver());
		sc.close();
	}
}
