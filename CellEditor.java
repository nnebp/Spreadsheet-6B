/**
 * Cell editor for objects of type Cell. Allows the users to edit the
 * formula while the JTable displays a cells value. Based on examples from:
 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
 * 
 * @author nnebp
 */

import java.awt.Component;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CellEditor extends DefaultCellEditor{
	
	/**
	 * Generated UID. 
	 */
	private static final long serialVersionUID = -754912053220419892L;

	/** reference to editor component*/
	private JFormattedTextField myComponent;

	private Cell cellRef;
	
	/** reference to the cell spreadsheet */
	private Spreadsheet spreadSheet;
	
	private JFrame frame;
	
	/** 
	 * CellEditor constructor. 
	 * @param aSpreadSheet reference to spreadsheet object.
	 * @param aFrame reference to frame containing the jtable.
	 */
	public CellEditor(Spreadsheet aSpreadSheet, JFrame aFrame) {
		super(new JFormattedTextField());

		this.spreadSheet = aSpreadSheet;

		this.frame = aFrame;

		myComponent = (JFormattedTextField) getComponent();

		myComponent.setHorizontalAlignment(JTextField.LEFT);
        myComponent.setFocusLostBehavior(JFormattedTextField.PERSIST);
        cellRef = null;
	}

	/**
	 * Returns the component for editing and displays formula text for 
	 * editing.
	 */
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected,
            int row, int column) {

        myComponent = (JFormattedTextField)super.getTableCellEditorComponent(
                table, value, isSelected, row, column);
        this.cellRef = (Cell) value;
        
        myComponent.setValue(value);
		myComponent.setText(cellRef.getFormula() );

        return myComponent;
    }
    
    /**
     * Returns the Cell editor value.
     * @returns the edited Cell.
     */
    public Object getCellEditorValue() {
		myComponent = (JFormattedTextField)getComponent();
		//remember the old formula for the case of a cycle
		String oldFormula = cellRef.getFormula();
		
    	cellRef.setFormula(myComponent.getText());
    	
    	this.spreadSheet.updateCell(cellRef.getCellAddress().getRow(),
									cellRef.getCellAddress().getCol());
    	//check for cycles. isCyclic will perform the top sort if no
    	//cycles are present in the spreadsheet.
    	if (spreadSheet.isCyclic()) {
    		
    		JOptionPane.showMessageDialog(frame,
    			    "There is a cycle in new formula!",
    			    "Error",
    			    JOptionPane.ERROR_MESSAGE);
    		
    		//reset the cell to its old value and run isCyclic again to
    		//calculate the values of all the cells in the spreadsheet.
    		cellRef.resetCell(spreadSheet.getSheet());
    		cellRef.setFormula(oldFormula);
    		spreadSheet.isCyclic();
    		
    	}
    	
		return cellRef;
    }
    
    
    public boolean stopCellEditing() {
		
    	try {
			myComponent.commitEdit();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
        return super.stopCellEditing();
    }
}
