import java.util.Stack;

public class ExpressionTree {
	
	/** reference to the table */
	private Cell cells[][];
	
	/** The root of the tree. */
	private ExpressionTreeNode root;
	
	/**
	 * Class representing a Expression tree node
	 * @author chinn
	 */
	private class ExpressionTreeNode { 
		private Token token;
		ExpressionTreeNode left; 
		ExpressionTreeNode right; 
		
		ExpressionTreeNode(Token theToken,
						ExpressionTreeNode leftSubtree, 
						ExpressionTreeNode rightSubtree) {
			this.token = theToken;
			this.left = leftSubtree;
			this.right = rightSubtree;
		}
	}
	
	/**
	 * Returns the root node of the ExpressionTree.
	 * @return
	 */
	public ExpressionTreeNode getRoot() {
		return root;
	}
	
	/**
	 * Constructor for the Expression tree
	 * @param postFix the stack from which to build the tree
	 * @param theCells reference to Cells (which we may get values from)
	 */
	ExpressionTree(Stack postFix, Cell[][] theCells) {
		this.BuildExpressionTree(postFix);
		this.cells = theCells;
	}
	
	// Build an expression tree from a stack of ExpressionTreeTokens 
	void BuildExpressionTree (Stack s) { 
		root = GetExpressionTree(s); 
		
		if (!s.isEmpty()) {
			System.out.println("Error in BuildExpressionTree.");
		}
		
	}

	/**
	 * Prints the values in a tree in postodrder recursively 
	 * @param root the root of the tree
	 */
	private void postOrder(ExpressionTreeNode root) {
		if (root != null) {
			postOrder(root.left);
			postOrder(root.right);
			
			System.out.print(root.token + ", ");
		}
	}
	
	
	public int evaluate() {
		return this.recursiveEvaluate(root);
	}
	
	private int recursiveEvaluate(ExpressionTreeNode root) {
		if (root != null) {
			
			// if node is operator
			if (root.token instanceof OperatorToken) {
				switch (((OperatorToken) root.token).getOperatorToken()) {
					case '+':
						return recursiveEvaluate(root.left) + recursiveEvaluate(root.right);
					case '*':
						return recursiveEvaluate(root.left) * recursiveEvaluate(root.right);
					case '/':
						return recursiveEvaluate(root.left) / recursiveEvaluate(root.right);
					case '-':
						return recursiveEvaluate(root.left) - recursiveEvaluate(root.right);
				}
			}
			
			// node is a literal
			else if (root.token instanceof LiteralToken) {
				return ( (LiteralToken) root.token).getLiteral();
			}
			
			//node is a cell
			else if (root.token instanceof CellToken) {
				//get the location of the cell and evaluate its value
				CellToken cellToken = ( (CellToken) root.token);
				int row = cellToken.getRow();
				int column = cellToken.getCol();
				
				return (this.cells[row][column].getValue());
			}
			
		}
		//java made me TODO deal with this we got a null node
		return 0;
	}
	
	
	ExpressionTreeNode GetExpressionTree(Stack s) {
		ExpressionTreeNode returnTree; 
		Token token; 
		
		if (s.isEmpty()) 
			return null; 
		
		token = (Token) s.pop();
		
		// need to handle stack underflow 
		if ((token instanceof LiteralToken) || (token instanceof CellToken) ) { 
			// Literals and Cells are leaves in the expression tree
			returnTree = new ExpressionTreeNode(token, null, null); 
			return returnTree; 
		} else if (token instanceof OperatorToken) {
				// Continue finding tokens that will form the 
				// right subtree and left subtree. 
				ExpressionTreeNode rightSubtree = GetExpressionTree (s); 
				ExpressionTreeNode leftSubtree = GetExpressionTree (s); 
				returnTree = new ExpressionTreeNode(token, leftSubtree, rightSubtree); 
				return returnTree; 
			}
		//java made me do it
		return null;
	}
	

}
