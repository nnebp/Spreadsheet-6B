/**
 * Represent vertices in the graph structure for the spreadsheet.
 * 
 * @author sheera
 */

import java.util.Iterator;
import java.util.LinkedList;

public class CellNode {
	private String cellAddress;
	private int inDegree = 0, currentDegree = 0;
	LinkedList<CellNode> depNodes;
	
	/**
	 * Constructor for CellNode.
	 * 
	 * @param cellAddress the address of the cell this node represents
	 * @param inDegree the in degree of the cell this node represents
	 */
	public CellNode(String cellAddress, int inDegree) {
		this.cellAddress = cellAddress;
		this.inDegree = inDegree;
		depNodes = new LinkedList<CellNode>();
		currentDegree = inDegree;
	}
	
	/**
	 * Returns an iterator pointing to the first element in the dependent nodes
	 * LinkedList.
	 * 
	 * @return an iterator pointing to depNodes
	 */
	public Iterator<CellNode> getDepNodesIterator() {
		return depNodes.iterator();
	}
	
	/**
	 * Returns the cell address of the CellNode in the spreadsheet.
	 * 
	 * @return cellAddress of the CellNode.
	 */
	public String getCellAddress() {
		return cellAddress;
	}
	
	/**
	 * Returns the in degree of this CellNode.
	 * 
	 * @return inDegree of this CellNode
	 */
	public int getInDegree() {
		return inDegree;
	}
	
	/**
	 * Returns the current degree of this CellNode.
	 * 
	 * @return currentDegree of this CellNode
	 */
	public int getCurrentDegree() {
		return currentDegree;
	}

	/**
	 * Sets the in degree of the CellNode to newInDegree.
	 * 
	 * @param newInDegree the new inDegree for this CellNode
	 */
	public void setInDegree(int newInDegree) {
		inDegree = newInDegree;
	}
	
	/**
	 * Sets the current degree of the CellNode to newCurrentDegree.
	 * 
	 * @param newCurrentDegree the new currentDegree for this CellNode
	 */
	public void setCurrentDegree(int newCurrentDegree) {
		currentDegree = newCurrentDegree;
	}
	
	/**
	 * Adds depNode to the depNodes LinkedList. This is an edge
	 * from this CellNode to depNode.
	 * 
	 * @param depNode the dependent node to be added
	 */
	public void addToDepList(CellNode depNode) {
		depNodes.add(depNode);
	}
	
	/**
	 * Removes depNode from the CellNodes depNode LinkedList. Removes
	 * an edge from this CellNode to depNode.
	 * 
	 * @param depNode the dependent node to be removed
	 */
	public void removeFromDepList(CellNode depNode) {
		depNodes.remove(depNode);
	}
	
	/**
	 * Returns true if the cell associated with this CellNode has
	 * any dependent nodes.
	 * 
	 * @return whether the CellNode has any dependent cells
	 */
	public boolean hasDepNodes() {
		return !depNodes.isEmpty();
	}
}
	
