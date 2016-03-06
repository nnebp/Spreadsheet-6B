import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class CellTable extends JPanel{
	private static int CELL_WIDTH = 110;
	private static int CELL_HEIGHT= 20;
	private Cell[][] myData;
	private Spreadsheet mySpreadSheet; 
	private JFrame myFrame;
	
	
	public CellTable(JFrame theFrame) {
        super(new GridLayout(1,0));
        
        //add the frame ref
        myFrame = theFrame;
        
        //Create cell array
		myData = this.generateCells(10, 10);
		
		mySpreadSheet = new Spreadsheet(myData);
		
        JTable table = new JTable(new MyTableModel(myData));
        
        table.setPreferredScrollableViewportSize(new Dimension(CELL_WIDTH * myData[0].length, 
        										CELL_HEIGHT * myData.length));
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Display row numbers
        JTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
            rowTable.getTableHeader());
        
        //we need to have our special cell editor 
        table.setDefaultEditor(Cell.class,
                               new CellEditor(this.mySpreadSheet, this.myFrame));
        
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
	
	private Cell[][] generateCells(int columns, int rows) {
		List<List<Cell>> list = new ArrayList<List<Cell>>();
		Cell[][] result = new Cell[rows][columns];
		
		//fill the arrayLust
		for (int i = 0; i < rows; i++) {
			List<Cell> row = new ArrayList<Cell>();
			for (int j = 0; j < columns; j++) {
				row.add(new Cell(i, j)); //fill row with blank cells
				row.get(j).setFormula("");
				//TODO add reference to result (later on)
			}
			list.add(row);
		}
		
		//convert to an array
		int i = 0;
		for (List<Cell> tempList : list) {
			Cell[] tempArray = tempList.toArray(new Cell[tempList.size()]);
			result[i] = tempArray;
			i++;
		}
		
		// references to the table have to be added after the table is created
		for (int k = 0; k < result.length; k++) {
			for (int l = 0; l < result[0].length; l++) {
				result[k][l].addReference(result);
			}
		}
		return result;
	}
	
	class MyTableModel extends AbstractTableModel {
	    	
        private Object[][] data; 

        //all we do is set collumn names
        MyTableModel(Object[][] theData) {
        	super();
        	data = theData;
        }
		
        public int getColumnCount() {
        	return data[0].length;
        }
        
        public int getRowCount() {
            return data.length;
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /**
         * All Cells in this table are editable
         */
        public boolean isCellEditable(int row, int col) {
                return true;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");

            data[row][col] = value;
            // Normally, one should call fireTableCellUpdated() when 
            // a value is changed.  However, doing so in this demo
            // causes a problem with TableSorter.  The tableChanged()
            // call on TableSorter that results from calling
            // fireTableCellUpdated() causes the indices to be regenerated
            // when they shouldn't be.  Ideally, TableSorter should be
            // given a more intelligent tableChanged() implementation,
            // and then the following line can be uncommented.
            // fireTableCellUpdated(row, col);

        }

    }
	
	
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Cell Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CellTable newContentPane = new CellTable(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	  public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
}
