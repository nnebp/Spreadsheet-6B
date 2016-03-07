/** 
 * CellTable is a frame containg a JTable representing the editable spreadsheet.
 * It is the main GUI of the program. Based on example code from :
 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
 * 
 * @author nnebp
 */

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
	/** Width of a cell in pixels. */
	private static int CELL_WIDTH = 110;
	
	/** Height of a cell in pixels. */
	private static int CELL_HEIGHT= 20;
	
	/** Max table height. */
	private static int MAX_HEIGHT = CELL_HEIGHT * 30;
	
	/** Max table width. */
	private static int MAX_WIDTH = CELL_WIDTH * 10;
	
	/** the data for the table. */
	private Cell[][] myData;
	
	/** Spreadsheet object for the table. */
	private Spreadsheet mySpreadSheet; 
	
	/** frame containg the table. */
	private JFrame myFrame;
	
	
	/**
	 * CellTable Constructor. 
	 * @param theFrame reference to the tables parent frame.
	 * @param rows number of rows for the table.
	 * @param cols number of cols for the table.
	 */
	public CellTable(JFrame theFrame, int rows, int cols) {
        super(new GridLayout(1,0));
        int width = rows * CELL_WIDTH;
        int height = cols * CELL_HEIGHT;
        
        //add the frame ref
        myFrame = theFrame;
        
		 //Create cell array
		mySpreadSheet = new Spreadsheet(rows, cols);
		myData = mySpreadSheet.getSheet();
		
        JTable table = new JTable(new MyTableModel(myData));
        
        //work with max table size
        if (width > MAX_WIDTH) {
        	width = MAX_WIDTH;
        }
        if (height > MAX_HEIGHT) {
        	height = MAX_HEIGHT;
        }
        
        table.setPreferredScrollableViewportSize(new Dimension(width, height));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Display row numbers
        JTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
            rowTable.getTableHeader());
        
        //we need to use our special cell editor 
        table.setDefaultEditor(Cell.class,
                               new CellEditor(this.mySpreadSheet, this.myFrame));
        
        //Add the scroll pane to this panel.
        add(scrollPane);
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
         * editor for each cell.  
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

        /**
         * Set the value of a cell of data.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
        }

    }
	
	
	/**
	 * Sets up and shows the frame containg the table.
	 * @param rows table row number.
	 * @param cols table column number.
	 */
    public static void createAndShowGUI(int rows, int cols) {
        //Create and set up the window.
        JFrame frame = new JFrame("SpreadSheet 6B");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        CellTable newContentPane = new CellTable(frame, rows, cols);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
}
