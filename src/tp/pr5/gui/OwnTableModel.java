package tp.pr5.gui;
import javax.swing.table.DefaultTableModel;

/**
 * This class represents a own table model so that 
 * the cells are not altered .
 * @author Juan Manuel Carrera García
*/
public class OwnTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct of the table model
	 * @param data of the table model
	 * @param name colum of the table model
	 */
	public OwnTableModel(Object[][] data, Object[] name) {
		super(data, name);
	}
	
	/**
	 * Make de cells not editable
	 */
	public boolean isCellEditable (int row, int column)	   {
	   return false;
	}
}