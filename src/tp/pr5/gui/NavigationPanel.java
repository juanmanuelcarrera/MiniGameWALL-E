package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;

/**
 * This class is in charge of the panel that displays the information about the robot heading and the city that is traversing. It contains the grid that represents the city in the Swing interface, a text area to show the place descriptions, and a label with an icon which represents the robot heading 

   The 11x11 grid contains PlaceCell objects and the first place starts at (5,5). This panel will update the visited places when the robot moves from one place to another. Additionally it will show the place description on a text area if the user clicks on a visited place.
 
 * @author Juan Manuel Carrera García
 *
 */
public class NavigationPanel extends JPanel implements NavigationObserver {
	private static final long serialVersionUID = 1L;
	private static final int SIZE = 11;
	
	private PlaceCell[][] cells;
	private int row;
	private int col;
	private JTextArea descripArea;
	private JLabel imageWall;
	
	private ImageIcon walleNorth;
	private ImageIcon walleEast;
	private ImageIcon walleWest;
	private ImageIcon walleSouth;
	
	/**
	 * Initialize the navigationPanel with the cells and images
	 */
	public NavigationPanel(){
		super(new BorderLayout());
		this.cells = new PlaceCell[SIZE][SIZE];
		this.row = 5;
		this.col = 5;
		
		this.walleNorth = new ImageIcon("src/tp/pr5/gui/images/walleNorth.png");
		this.walleEast = new ImageIcon("src/tp/pr5/gui/images/walleEast.png");
		this.walleWest = new ImageIcon("src/tp/pr5/gui/images/walleWest.png");
		this.walleSouth = new ImageIcon("src/tp/pr5/gui/images/walleSouth.png");
	
		this.initPanel();		
	}
	
	/**
	 * Initialize the Panel with buttons that represent the places where WALL·E will move
	 */
	private void initPanel() {
		JPanel cityMapPanel = new JPanel(new GridLayout(NavigationPanel.SIZE, NavigationPanel.SIZE));
		cityMapPanel.setBorder(BorderFactory.createTitledBorder("City Map"));
		
		JPanel descripPanel = new JPanel(new BorderLayout());
		descripPanel.setBorder(BorderFactory.createTitledBorder("Log"));
		
		this.descripArea = new JTextArea(7,0);
		this.descripArea.setEnabled(false);		
		this.descripArea.setDisabledTextColor(Color.BLACK);
		descripPanel.add(new JScrollPane(this.descripArea), BorderLayout.CENTER);
		
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				final int x = i,  y = j;
				cityMapPanel.add(this.cells[i][j] = new PlaceCell());
				this.cells[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						setDescripArea(cells[x][y].getDescription());
					}
				});
			}
		}
				
		this.imageWall = new JLabel(this.walleNorth);
	
		this.add(this.imageWall, BorderLayout.WEST);
		this.add(cityMapPanel, BorderLayout.CENTER);
		this.add(descripPanel,BorderLayout.SOUTH);	
	}
	
	/**
	 * This method modify the description of the text area
	 * @param description description to be placed in the text area
	 */
	public void setDescripArea(String description){
		this.descripArea.setText(description);
	}
	
	/**
	 * This method to find out which cell these
	 * @return the current cell where WALL·E stay
	 */
	public PlaceCell getCell(){
		return this.cells[row][col];
	}
	
	/**
	 * This method to set the cell text with the name of the place
	 * @param name Name of the place
	 */
	public void setTextCell(String name){
		this.cells[row][col].setText(name);
	}
	
	/**
	 * method to set the cell description with the description of the place
	 * @param description Description of the place
	 */
	public void setDescriptionCell(String description){
		this.cells[row][col].setDescription(description);
	}
	
	/**
	 * method that given a direction we take the image with that direction
	 * @param dir
	 */
	public void setImageWalle(Direction dir){
		switch (dir) {
		case NORTH:
			this.imageWall.setIcon(this.walleNorth);
			break;
		case SOUTH:
			this.imageWall.setIcon(this.walleSouth);
			break;
		case WEST:
			this.imageWall.setIcon(this.walleWest);
			break;
		case EAST:
			this.imageWall.setIcon(this.walleEast);
			break;
		default:
			break;
		}
	}
	
	/**
	 * This method to move the cell and mark it as green and the previous cell is marked in gray
	 * @param dir Direction direction on the WALL·E moves
	 * @param place Place to WALL·E arrive
	 */
	public void changeCell(Direction dir, PlaceInfo place){
		this.cells[row][col].changeColor(Color.GRAY);
		
		switch (dir) {
		case NORTH:
			this.row--;
			break;
		case SOUTH:
			this.row++;
			break;
		case WEST:
			this.col--;
			break;
		case EAST:
			this.col++;
			break;
		default:
			break;
		}

		this.cells[row][col].changeColor(Color.GREEN);
		this.cells[row][col].setText(place.getName());
		this.cells[row][col].setDescription(place.toString());
		this.placeHasChanged(place);
	}

	/**
	 * Notifies that the robot heading has changed
	 * @param newHeading New robot heading
	 */
	@Override
	public void headingChanged(Direction newHeading) {
		this.setImageWalle(newHeading);
	}

	/**
	 * Notifies that the navigation module has been initialized
	 * @param initialPlace The place where the robot starts the simulation
	 * @param heading The initial robot heading
	 */
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		this.setTextCell(initialPlace.getName());
		this.setDescriptionCell(initialPlace.toString());
		this.setDescripArea(initialPlace.toString());
		this.getCell().changeColor(Color.GREEN);	
	}

	/**
	 * Notifies that the place where the robot stays has changed (because the robot picked or dropped an item)
	 * @param placeDescription Information with the current place
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {	
		this.setDescripArea(placeDescription.toString());
	}

	/**
	 * Notifies that the user requested a RADAR instruction
	 * @param placeDescription Information with the current place
	 */
	@Override
	public void placeScanned(PlaceInfo placeDescription) {}

	/**
	 * Notifies that the robot has arrived at a place
	 * @param heading The robot movement direction
	 * @param place The place where the robot arrives
	 */
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		this.changeCell(heading, place);
	}	
}
