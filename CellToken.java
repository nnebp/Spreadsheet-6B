/**
 * CellToken class which extends Token. Stores the row and column number
 * of the cell as integers.
 * 
 * @author 
 */

public class CellToken extends Token {
	private int column; // column A = 0, B = 1, etc.
	private int row;
	 
	 /**
	  * Default constructor.
	  * 
	  * @param row row of the cell
	  * @param column column of the cell
	  */
	 public CellToken(int row, int column) {
		 this.row = row;
		 this.column = column;
	 }
	 
	 /**
	  * Returns true of this cell token is equal to the input
	  * cell token.
	  * 
	  * @param token the cell token to be compared to
	  * @return true if the cells are equal and false otherwise
	  */
	 public boolean isEqual(CellToken token) {
		 if(this.row == token.row && this.column == token.column) {
			return true;
		 }
		else {
			return false;
		}
	 }
	 
	 /**
	  * Returns the row of this cell token.
	  * 
	  * @return row of this cell token
	  */
	 public int getRow() {
		 return row;
	 }
	 
	 /**
	  * Returns the column of this cell token.
	  * 
	  * @return column of this cell token
	  */
	 public int getCol() {
		 return column;
	 }
}