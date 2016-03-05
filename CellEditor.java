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
	
	public CellEditor(Spreadsheet aSpreadSheet, JFrame aFrame) {
		super(new JFormattedTextField());

		this.spreadSheet = aSpreadSheet;

		this.frame = aFrame;

		myComponent = (JFormattedTextField) getComponent();

		myComponent.setHorizontalAlignment(JTextField.LEFT);
        myComponent.setFocusLostBehavior(JFormattedTextField.PERSIST);
        cellRef = null;
	}

	// return the forumla for editing
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
    
    //THIS IS WHAT IS SET IN THE CELL
    public Object getCellEditorValue() {
		myComponent = (JFormattedTextField)getComponent();
		//remember the old formula for the case of a cycle
		String oldFormula = cellRef.getFormula();
		
    	cellRef.setFormula(myComponent.getText());
    	
    	this.spreadSheet.updateCell(cellRef.getCellAddress().getRow(),
									cellRef.getCellAddress().getCol());
    	if (spreadSheet.isCyclic()) {
    		System.out.println("THERE IS A CYCLE!!!");
    		
    		JOptionPane.showMessageDialog(frame,
    			    "There is a cycle in new formula!",
    			    "Error",
    			    JOptionPane.ERROR_MESSAGE);
    		
    		cellRef.resetCell(spreadSheet.getSheet());
    		cellRef.setFormula(oldFormula);
    		System.out.println(cellRef.debugString());
    		spreadSheet.isCyclic();
    		
    	}
    	
    	//update cell
    	//iscyclic
    	//TODO delete this after the top sort works
    	//cellRef.calculateValue();
		return cellRef;
    }
    
    
    public boolean stopCellEditing() {
		//myComponent = (JFormattedTextField)getComponent();
		
    	try {
			myComponent.commitEdit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return super.stopCellEditing();
    }
}
