/**
 * 
 * @author sheera
 *
 */

public class Spreadsheet {
	static final int MAX_ROWS = 4;
	static final int MAX_COLS = 4;
	
	private Cell[][] sheet;
	private Graph graph;
	
	//constructor
	public Spreadsheet() {
		sheet = new Cell[MAX_ROWS][MAX_COLS];
	}
	
	/**
	 * Performs a topological sort and returns whether a cycle 
	 * is found.
	 * 
	 * @return whether the graph is cyclic
	 */
	public boolean isCyclic() {
		return graph.topSort();
	}
}
