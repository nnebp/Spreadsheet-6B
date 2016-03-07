/**
 * Class that represents an individual cell in the spreadsheet. Stores the 
 * formula as a String and the value of the cell. Stores the address of the cell 
 * in a CellToken object. Also stores the in-degree and current in-degree 
 * (for the topological sort). The addresses of the nodes that must be 
 * evaluated before this node are stored in the dependencies linked list.
 * The addresses of the nodes that are dependent on this cell are also stored in
 * a linked list (dependentCells).
 * 
 * @author Sandeep Heera
 *
 */

import java.util.Iterator;
import java.util.LinkedList;

public class Cell {
	private String formula;
	private CellToken cellAddress;
	private Cell[][] cells;
	private int value, inDegree, currentInDegree;
	private LinkedList<CellToken> dependencies, dependentCells;
	private Evaluator evaluator;
	
	/**
	 * Default constructor.
	 * 
	 * @param row the row of this cell
	 * @param column the column of this cell
	 */
	public Cell(int row, int column) {
		cellAddress = new CellToken(row, column);
		formula = "";
		value = 0;
		inDegree = 0;
		currentInDegree = 0;
		dependencies = new LinkedList<CellToken>();
		dependentCells = new LinkedList<CellToken>();
		evaluator = new Evaluator(formula, null);
	}
	
	/**
	 * Adds a reference to the 2-d array of cells.
	 * 
	 * @param theCells 2-d array of cell objects containing every cell
	 */
	public void addReference(Cell[][] theCells) {
		this.cells = theCells;
	}

	/**
	 * Returns the formula of this cell.
	 * 
	 * @return formula of this cell
	 */
	public String getFormula() {
		return formula;
	}
	
	/**
	 * Returns the value of this cell.
	 * 
	 * @return value of this cell
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the in-degree of this cell.
	 * 
	 * @return the in-degree of this cell
	 */
	public int getInDegree() {
		return inDegree;
	}
	
	/**
	 * Returns the current in-degree of this cell. this is used
	 * for the topological sort algorithm in Spreadsheet.java.
	 * 
	 * @return current in-degree of this cell
	 */
	public int getCurrentInDegree() {
		return currentInDegree;
	}
	
	/**
	 * Returns the evaluator for this cell.
	 * 
	 * @return the evaluator for this cell
	 */
	public Evaluator getEvaluator() {
		return evaluator;
	}
	
	/**
	 * Return the cell address of this cell.
	 * 
	 * @return cell token of this cell
	 */
	public CellToken getCellAddress() {
		return cellAddress;
	}
	
	/**
	 * Sets the formula of this cell to the input.
	 * 
	 * @param formula the new formula of this cell
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	/**
	 * Sets the cells value based on its formula
	 */
	public void calculateValue() {
		evaluator.setFormula(formula);
		evaluator.setReference(cells);
		
		setValue(evaluator.calculate(formula));
	}

	/**
	 * Sets the value of this cell to the input.
	 * 
	 * @param newValue the new value for this cell
	 */
	public void setValue(int newValue) {
		value = newValue;
	}
	
	/**
	 * Sets the in-degree of this cell to the input.
	 * 
	 * @param newInDegree the new in-degree for this cell
	 */
	public void setInDegree(int newInDegree) {
		inDegree = newInDegree;
	}
	
	/**
	 * Sets the current in-degree of this cell to the input.
	 * 
	 * @param newCurrentInDegree the new current in-degree for 
	 * this cell
	 */
	public void setCurrentInDegree(int newCurrentInDegree) {
		currentInDegree = newCurrentInDegree;
	}
	
	/**
	 * Returns an iterator pointing to the first element in the dependencies
	 * linked list.
	 * 
	 * @return an iterator pointing to the first element in dependencies
	 */
	public Iterator<CellToken> getDependenciesIterator() {
		return dependencies.iterator();
	}
	
	/**
	 * Returns an iterator pointing to the first element in the dependent cells
	 * linked list.
	 * 
	 * @return an iterator pointing to the first element in dependent cells
	 */
	public Iterator<CellToken> getDependentCellsIterator() {
		return dependentCells.iterator();
	}
	
	/**
	 * Checks to see if the cell address is present in the dependencies
	 * linked list.
	 * 
	 * @param cellAddress the cell address to search for
	 * @return true if the cell address is in dependencies and false otherwise
	 */
	public boolean dependenciesAddressExists(CellToken cellAddress) {
		if(dependencies.isEmpty()) {
			return false;
		}
		else{
			Iterator<CellToken> it = this.getDependenciesIterator();
			
			//iterate through the list to see if the CellToken exists
			while(it.hasNext()) {
				CellToken check = it.next();
				if(check.isEqual(cellAddress)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Checks to see if the cell address is present in the dependent cells
	 * linked list.
	 * 
	 * @param cellAddress the cell address to search for
	 * @return true if the cell address is in dependent cells and false otherwise
	 */
	public boolean dependentAddressExists(CellToken cellAddress) {
		if(dependentCells.isEmpty()) {
			return false;
		}
		else{
			Iterator<CellToken> it = this.getDependentCellsIterator();
			
			//iterate through the list to see if the CellToken exists
			while(it.hasNext()) {
				CellToken check = it.next();
				if(check.isEqual(cellAddress)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Adds the cell token to the list of dependency cells. Increases 
	 * the in-degree of this cell by one.
	 * 
	 * @param cellAddress cell token of the dependency cell
	 */
	public void addDependency(CellToken cellAddress) {
		//check to see if the cell address isn't already there
		if(!dependenciesAddressExists(cellAddress)) {
			dependencies.add(cellAddress);
			inDegree++;
			currentInDegree++;
		}
	}
	
	/**
	 * Adds this cell token to the list of dependent cells of the input
	 * address of the 2-d array.
	 * 
	 * @param sheet 2-d array containing the cell
	 * @param cellAddress cell token of the dependent cell
	 */
	public void addDependentCell(Cell[][] sheet, CellToken cellAddress) {
		//index into the cell to update
		Cell toUpdate = sheet[cellAddress.getRow()][cellAddress.getCol()];
		
		//check to see if the cell address isn't already there
		if(!toUpdate.dependentAddressExists(this.cellAddress)) {
			toUpdate.dependentCells.add(this.cellAddress);
		}
	}
	
	/**
	 * Removes all dependencies from the list of dependency cells. Updates the
	 * dependent cell lists of the dependency cells.
	 * 
	 * @param sheet 2-d cell array which contains all cells to be updated
	 */
	public void removeAllDependencies(Cell[][] sheet) {
		//iterate through the dependencies list and update the corresponding cells in the 2-d cell array
		Iterator<CellToken> it = this.getDependenciesIterator();
		
		while(it.hasNext()) {
			CellToken cellAddress = it.next();
			Cell toUpdate = sheet[cellAddress.getRow()][cellAddress.getCol()];
			
			//iterate through dependent cells list and remove this cell reference
			Iterator<CellToken> updateIt = toUpdate.getDependentCellsIterator();
			
			while(updateIt.hasNext()) {
				CellToken cellAddress1 = updateIt.next();
				
				if(cellAddress1.isEqual(this.cellAddress)) {
					updateIt.remove();
					break;
				}
			}
		}
		
		//clear the dependencies linked list
		dependencies.clear();
	}
	
	/**
	 * Updates dependent cells by decreasing their respective current
	 * in-degrees by one. Also decreases this cell's current in-degree
	 * by one. Used during the topological sort in Spreadsheet.java in 
	 * the toCyclic() method.
	 * 
	 * @param the 2-d cell array with the dependent cells
	 */
	public void updateDependents(Cell[][] sheet) {
		//decrease the current in-degree
		this.setCurrentInDegree(this.getCurrentInDegree() - 1);
		
		if(!this.dependentCells.isEmpty()) {
			Iterator<CellToken> it = this.getDependentCellsIterator();
			
			//iterate through the list and decrease the current in-degrees of the cells
			while(it.hasNext()) {
				CellToken cellAddress = it.next();
				Cell toDecrease = sheet[cellAddress.getRow()][cellAddress.getCol()];
				
				toDecrease.setCurrentInDegree(toDecrease.getCurrentInDegree() - 1);
			}
		}
	}
	
	/**
	 * Resets this cell by reducing the in-degree, current in-degree and value to 0. Also,
	 * clears the dependency list and updates all associated cells to match the change.
	 * 
	 * @param sheet 2-d cell array containing all cells to be updated
	 */
	public void resetCell(Cell[][] sheet) {
		//update in-degrees and value
		this.setCurrentInDegree(0);
		this.setInDegree(0);
		this.setValue(0);
		
		//go through the dependency list and update those cells to reflect the change
		if(!dependencies.isEmpty()) {
			this.removeAllDependencies(sheet);
		}
	}
	
	/**
	 * Returns a string representation of the cell object including all of the fields.
	 * 
	 * @return string representation of the cell
	 */
	public String debugString() {
		String toPrint = ("Row: " + this.getCellAddress().getRow() + "\nColumn: " + this.getCellAddress().getCol() 
				+ "\nFormula: " + this.getFormula() + "\nValue: " + this.getValue() + "\nIn-degree, Current In-Degree: " +
				this.getInDegree() + ", " + this.getCurrentInDegree() + "\n");
		
		//check to see if we need to print dependencies
		if(!dependencies.isEmpty()) {
			toPrint = toPrint.concat("Dependencies: ");
			//iterate through the dependencies list and print all of the cells
			Iterator<CellToken> it = this.getDependenciesIterator();
			
			while(it.hasNext()) {
				CellToken address = it.next();
				toPrint = toPrint.concat("(" + address.getRow() + ", " + address.getCol() + ") ");
				toPrint = toPrint.concat("\n");
			}
		}
		
		//check to see if we need to print dependent cells
		if(!dependentCells.isEmpty()) {
			toPrint = toPrint.concat("Dependent Cells: ");
			
			//iterate through the dependencies list and print all of the cells
			Iterator<CellToken> it = this.getDependentCellsIterator();
			
			while(it.hasNext()) {
				CellToken address = it.next();
				toPrint = toPrint.concat("(" + address.getRow() + ", " + address.getCol() + ") ");
			}
			toPrint = toPrint.concat("\n");
		}
		return toPrint;
	}
	
	/**
	 * Returns a string representation of the value of the cell.
	 * 
	 * @return string containing value of the cell
	 */
	 public String toString() {
		if (this.formula.equals(""))
			return "";
		
	 	return Integer.toString(this.getValue());
	 }
}
