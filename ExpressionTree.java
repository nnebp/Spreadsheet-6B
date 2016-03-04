import java.util.Stack;

public class ExpressionTree {
	
	private ExpressionTreeNode root;
	//public void makeEmpty(); 
	//public void printTree(); 
	
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
	
	public ExpressionTreeNode getRoot() {
		return root;
	}
	
	/**
	 * Constructor for the Expression tree
	 * @param postFix the stack from which to build the tree
	 */
	ExpressionTree(Stack postFix) {
		this.BuildExpressionTree(postFix);
	}
	
	// Build an expression tree from a stack of ExpressionTreeTokens 
	void BuildExpressionTree (Stack s) { 
		root = GetExpressionTree(s); 
		
		if (!s.isEmpty()) {
			System.out.println("Error in BuildExpressionTree.");
		}
		
	}
	
	//TODO this is a test method we delete it
	void printPostOrder(ExpressionTreeNode root) {
		if (root != null) {
			printPostOrder(root.left);
			printPostOrder(root.right);
			
			System.out.print(root.token + ", ");
		}
	}
	
	int evaluateTest(ExpressionTreeNode root) {
		if (root != null) {
			
			// if its an operator
			if (root.token instanceof OperatorToken) {
				switch (((OperatorToken) root.token).getOperatorToken()) {
					case '+':
						return evaluateTest(root.left) + evaluateTest(root.right);
					case '*':
						return evaluateTest(root.left) * evaluateTest(root.right);
					case '-':
						return evaluateTest(root.left) - evaluateTest(root.right);
					case '/':
						return evaluateTest(root.left) / evaluateTest(root.right);
				}
			}
			
			// not an operator but is a literal
			//TODO allow cell values
			if (root.token instanceof LiteralToken) {
				
				return ( (LiteralToken) root.token).getLiteral();
			}
			
//			evaluateTest(root.left);
//			evaluateTest(root.right);
//			System.out.print(root.token + ", ");
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
