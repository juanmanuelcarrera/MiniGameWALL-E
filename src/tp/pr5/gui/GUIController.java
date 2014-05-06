package tp.pr5.gui;

import tp.pr5.Controller;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.Instruction;

/**
 * The controller employed when the application is configured as a swing application. It is responsible for requesting the robot engine start and it redirects the actions performed by the user on the window to the robot engine.
 * @author Juan Manuel Carrera García
 *
 */
public class GUIController extends Controller {
	
	/**
	 * Constructor of the controller. It receives the model main class.
	 * @param robot Engine that is being played
	 */
	public GUIController(RobotEngine robot) {
		super(robot);
	}

	/**
	 * Initialize the game with swing interface
	 */
	@Override
	public void runGame() {
		this.robotEngine.initNavigationModule();
		this.robotEngine.initRobotContainer();
		this.robotEngine.requestStart();
	}
	
	/**
	 * This method passes an instruction to the robot for execution
	 * @param ins Instruction to execute
	 */
	public void executeInstruction(Instruction ins) {
		this.robotEngine.comunicateRobot(ins);	
	}
	
	/**
	 * This method tells the robot to undo the last instruction executed
	 */
	public void executeInstructionUndo() {
		this.robotEngine.comunicateRobotUndo();
	}
}




