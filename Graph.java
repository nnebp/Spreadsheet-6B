/**
 * Graph structure of CellNodes represented as an adjacency list.
 * 
 * @author sheera
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
	ArrayList<CellNode> adjList;
	
	public static void main(String[] args) {
		System.out.println("Graph looks like this: B3->A1->A2\n" + "and C5->D1->A2");
		//quick tests
		CellNode a = new CellNode("A1", 1);
		CellNode b = new CellNode("B3", 0);
		CellNode c = new CellNode("C5", 0);
		CellNode d = new CellNode("A2", 2);
		CellNode e = new CellNode("D1", 1);
		
		//add the dependent nodes to their respective positions
		a.addToDepList(d);
		b.addToDepList(a);
		c.addToDepList(e);
		e.addToDepList(d);
		
		//create graph with new vertices
		Graph graph = new Graph();
		graph.addToList(a);
		graph.addToList(b);
		graph.addToList(c);
		graph.addToList(d);
		graph.addToList(e);
		
		//print dependent nodes
		CellNode toPrint = a;
		if(toPrint.hasDepNodes()) {
			CellNode adjNode;
			Iterator<CellNode> it = toPrint.getDepNodesIterator();
			
			while(it.hasNext()) {
				adjNode = it.next();
				System.out.println(toPrint.getCellAddress() + "->" + adjNode.getCellAddress());
			}
		}
		
		toPrint = b;
		if(toPrint.hasDepNodes()) {
			CellNode adjNode;
			Iterator<CellNode> it = toPrint.getDepNodesIterator();
			
			while(it.hasNext()) {
				adjNode = it.next();
				System.out.println(toPrint.getCellAddress() + "->" + adjNode.getCellAddress());
			}
		}
		
		toPrint = c;
		if(toPrint.hasDepNodes()) {
			CellNode adjNode;
			Iterator<CellNode> it = toPrint.getDepNodesIterator();
			
			while(it.hasNext()) {
				adjNode = it.next();
				System.out.println(toPrint.getCellAddress() + "->" + adjNode.getCellAddress());
			}
		}
		
		toPrint = d;
		if(toPrint.hasDepNodes()) {
			CellNode adjNode;
			Iterator<CellNode> it = toPrint.getDepNodesIterator();
			
			while(it.hasNext()) {
				adjNode = it.next();
				System.out.println(toPrint.getCellAddress() + "->" + adjNode.getCellAddress());
			}
		}
		
		toPrint = e;
		if(toPrint.hasDepNodes()) {
			CellNode adjNode;
			Iterator<CellNode> it = toPrint.getDepNodesIterator();
			
			while(it.hasNext()) {
				adjNode = it.next();
				System.out.println(toPrint.getCellAddress() + "->" + adjNode.getCellAddress());
			}
		}
		
		//perform a topsort
		graph.topSort();
	}
	
	//constructor
	public Graph() {
		adjList = new ArrayList<CellNode>();
	}
	
	/**
	 * Adds newNode to the adjacency list.
	 * 
	 * @param newNode the CellNode to be added to the adjacency list
	 */
	public void addToList(CellNode newNode) {
		adjList.add(newNode);
	}
	
	/**
	 * Removes the node with cellAddress from the adjacency list and trims
	 * the ArrayList. Also decrements all CellNodes in the adjacency list 
	 * with the node in their dependency lists and removes them.
	 * 
	 * @param cellAddress the cell address of the node to be removed
	 */
	public void removeFromList(String cellAddress) {
		int i = 0;
		CellNode temp = adjList.get(i);
		
		//iterate through the list to find the node
		while(temp.getCellAddress() != cellAddress) {
			temp = adjList.get(++i);
		}
		
		//remove from the list
		adjList.remove(i);
		adjList.trimToSize();
		
		//iterate through the list and find any nodes the cell was dependent on
		for(int j = 0; j < adjList.size(); j++) {
			CellNode checkNode = adjList.get(j);
			if(checkNode.hasDepNodes()) {
				Iterator<CellNode> it = checkNode.getDepNodesIterator();
				while(it.hasNext()) {
					CellNode test = it.next();
					
					//if the cell address matches, remove it
					if(test.getCellAddress().equals(cellAddress)) {
						it.remove();
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Performs a topological sort and returns false if a cycle is found.
	 * 
	 * @return whether the graph is cyclic
	 */
	public boolean topSort() {
		int tracker = adjList.size();
		Queue<CellNode> q1 = new LinkedList<CellNode>();
		Queue<CellNode> q2 = new LinkedList<CellNode>();
		
		//top sort algorithm
		while(q2.size() != tracker) {
			for(int i = 0; i < adjList.size(); i++) {
				CellNode temp = adjList.get(i);
				
				//if the in-degree is currently zero throw into the queue
				if(temp.getCurrentDegree() == 0) {
					temp.setCurrentDegree(temp.getCurrentDegree() - 1);
					q1.add(temp);
					q2.add(temp);
					
					//remove from the list and adjust i
					adjList.remove(i);
					i--;
				}
				adjList.trimToSize();
			}
			
			
			while(q1.peek() != null) {
				//this is where the call to evaluate the cells should be
				//for now just printing out the address
				CellNode toPrint = q1.remove();
				
				//reduce the adjacent nodes current degrees by one
				if(toPrint.hasDepNodes()) {
					CellNode adjNode;
					Iterator<CellNode> it = toPrint.getDepNodesIterator();
					
					while(it.hasNext()) {
						adjNode = it.next();
						adjNode.setCurrentDegree(adjNode.getCurrentDegree() - 1);
					}
				}
				System.out.println("Address evaluated: " + toPrint.getCellAddress());
			}
		}
			//place all of the elements back into the adjacency list
			while(q2.peek() != null) {
				CellNode newNode = q2.remove();
				newNode.setCurrentDegree(newNode.getInDegree());
				
				adjList.add(newNode);
			}
		//need to finish case where a cycle is found
		return true;
	}
}