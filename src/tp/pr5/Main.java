package tp.pr5;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.cli.*; 

import tp.pr5.cityLoader.CityLoaderFromTxtFile;
import tp.pr5.cityLoader.cityLoaderExceptions.WrongCityFormatException;
import tp.pr5.console.ConsoleController;
import tp.pr5.gui.GUIController;
import tp.pr5.gui.MainWindow;

/**
 * Application entry-point. The application admits a parameter -m | --map with the name of the map file to be used and a parameter -i | --interface with the type of interface (console or swing)

   If no arg is specified (or more than one file is given), it prints an error message (in System.err) and the application finishes with an error code (-1).

   If the map file cannot be read (or it does not exist), the application ends with a different error code (-2).

   If the interface arg is not correct (console or swing) the application prints a message and the application finishes with an error code (-3). If the interface arg is not included it starts the application in console mode. Otherwise, the simulation starts and eventually the application will end normally (return code 0).
 
 * @author Juan Manuel Carrera García
 *
 */
public class Main {
	/**
	 * Print message of use of the parameters
	 */
	/*private static void printUsage(){
		System.out.println("Execute this assignment with these parameters: "+ Interpreter.LINE_SEPARATOR + 
							"usage: tp.pr5.Main [-h] [-i <type>] [-m <mapfile>]" + Interpreter.LINE_SEPARATOR +
							" -h,--help               Shows this help message" + Interpreter.LINE_SEPARATOR +
							" -i,--interface <type>   The type of interface: console or swing" + Interpreter.LINE_SEPARATOR +
							" -m,--map <mapfile>      File with the description of the city");
    }*/

	/**
	 * Create a parser and analyzes the input arguments
	 * @param args the input arguments
	 */
	private static void parser(String[] args) {
		CommandLineParser parser =  null;
		CommandLine cmdLine = null;
		String interfazOpt = null;
		String mapOpt = null;
	    		
		Options options = new Options();  
		options.addOption("i", "interface", true,  "The type of interface: console or swing");  
		options.addOption("m", "map", true, "File with the description of the city");   
		options.addOption("h", "help", false, "Shows this help message");
		
		try {
			parser = new BasicParser();
			cmdLine = parser.parse(options, args);
			
			if (cmdLine.hasOption("h")){    
				//Main.printUsage();
				System.out.println("Execute this assignment with these parameters: ");
				new HelpFormatter().printHelp(Main.class.getCanonicalName(), options ); 
				System.exit(0);  
            }  
			
			interfazOpt = (String)cmdLine.getOptionValue("i"); 
			mapOpt = (String)cmdLine.getOptionValue("m");
			
			if (mapOpt == null || cmdLine.getArgs().length > 0) { 
				System.err.println("Map file not specified");
				System.exit(1);
			}
			else {
				if (interfazOpt == null) {
					System.err.println("Interface not specified");
					System.exit(1);
				}
				if (!(interfazOpt.equalsIgnoreCase("console") || interfazOpt.equalsIgnoreCase("swing") || interfazOpt.equalsIgnoreCase("both"))) {
					System.err.println("Wrong type of interface");
					System.exit(3);
				}
				
				Main.initRobot(mapOpt, interfazOpt);
			}		 
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * This method init the robot engine and launches the game for the corresponding interface
	 * @param mapOpt The name of file with the map
	 * @param interfazOpt The name of the type of interface
	 */
	private static void initRobot(String mapOpt, String interfazOpt) {
		CityLoaderFromTxtFile loader = new CityLoaderFromTxtFile();
	
		try {
			City map = loader.loadCity(new FileInputStream(mapOpt));
			Place initPlace = loader.getInitialPlace();
			initPlace = loader.getInitialPlace();
			RobotEngine engine = new RobotEngine(map, initPlace, Direction.NORTH);
			
			if (interfazOpt.equalsIgnoreCase("swing")) 
				Main.initSwing(engine).runGame();
			if (interfazOpt.equalsIgnoreCase("console")) {
				Main.initConsole(engine).runGame();
				System.exit(0);
			}
			if (interfazOpt.equalsIgnoreCase("both")) {
				Main.initConsole(engine);
				Main.initSwing(engine).runGame();
			}
		} catch (WrongCityFormatException e) {
			System.err.println("Incorrect Format");
			System.exit(2);
		} catch (IOException e) {
				System.err.println("Error reading the map file: " + mapOpt
						+ " (No existe el fichero o el directorio)");
				System.exit(2);
			
		}		
	}
		
	/**
	 * This method initializes the swing interface
	 * @param engine Engine Robot that controls the interface
	 * @return the controller of the interface
	 */
	private static Controller initSwing(RobotEngine engine) {
		GUIController controller = new GUIController(engine);
		MainWindow window = new MainWindow(controller);
		window.createMainWindows();
		return controller;
	}
	
	/**
	 * This method initializes the console interface
	 * @param engine Engine Robot that controls the interface
	 * @return the controller of the interface
	 */
	private static Controller initConsole(RobotEngine engine) {
		ConsoleController controller = new ConsoleController(engine);
		return controller;
	}
	
	public static void main(String[] args) {
		Main.parser(args);
	}
}
