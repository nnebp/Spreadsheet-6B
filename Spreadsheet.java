/**
 * Class that has an underlying 2-d array of Cell objects.
 * 
 * @author Sandeep Heera
 *
 */

import java.util.LinkedList;
import java.util.Queue;

public class Spreadsheet {
	static final int MAX_ROWS = 4;
	static final int MAX_COLS = 4;
	
	private Cell[][] sheet;
	
	/**
	 * Default constructor. Sets the addresses of the cells.
	 */
	public Spreadsheet() {
		sheet = new Cell[MAX_ROWS][MAX_COLS];
		
		//set the cell token addresses
		for(int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLS; j++) {
				sheet[i][j] = new Cell(i, j);
			}
		}
	}
	
	/**
	 * TEMPORARY TEST FUNCTION. 
	 */
	public boolean updateCell(int row, int col, int value) {
		Cell toUpdate = sheet[row][col];
		toUpdate.resetCell(sheet);
		toUpdate.setValue(value);
		
		return this.isCyclic();
	}
	
	/**
	 * Performs a topological sort and returns whether a cycle 
	 * is found.
	 * 
	 * @return whether the graph is cyclic
	 */
	public boolean isCyclic() {
		//top sort algorithm
		int counter = 0, currentCounter = 0;
		int max = MAX_ROWS * MAX_COLS;
		Queue<Cell> q = new LinkedList<Cell>();
		
		while(counter != max) {
			//counters to check for cycles
			currentCounter = counter;
			for(int i = 0; i < MAX_ROWS; i++) {
				for(int j = 0; j < MAX_COLS; j++) {
					//if in-degree 0 is found, add to the queue
					if(sheet[i][j].getCurrentInDegree() == 0) {
						counter++;
						Cell toEval = sheet[i][j];
						q.add(toEval);
					}
				}
			}
			//check to see if nothing has been updated
			if(currentCounter == counter) {
				return true;
			}
			
			//iterate through the queue and update
			while(q.peek() != null) {
				Cell toEval = q.remove();
				toEval.updateDependents(sheet);
				System.out.println("Cell: " + toEval.getCellAddress().getRow() +
						", " + toEval.getCellAddress().getCol() + " is being evaluated.");
				System.out.println("Cell's value is " + toEval.getValue() + "\n");
			}
		}
		
		//restore the current in-degrees of the cells
		for(int i = 0; i < MAX_ROWS; i++) {
			for(int j = 0; j < MAX_COLS; j++) {
				sheet[i][j].setCurrentInDegree(sheet[i][j].getInDegree());
			}
		}
		
		return false;
	}
	
	//TEST
	public static void main(String[] args) {
		Spreadsheet sheet = new Spreadsheet();
		
		//update cell and print
		sheet.updateCell(1, 3, 4);		
		System.out.println(sheet.sheet[1][3]);
		
		//manually add dependency
		sheet.sheet[1][3].addDependency(sheet.sheet[0][1].getCellAddress());
		System.out.println(sheet.sheet[1][3]);
		sheet.sheet[1][3].addDependentCell(sheet.sheet, sheet.sheet[0][1].getCellAddress());
		System.out.println(sheet.sheet[0][1]);
		
		//do an update and see if cell (1, 3) updates last
		sheet.updateCell(0, 0, 0);
	}
}
