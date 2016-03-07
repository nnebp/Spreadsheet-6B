/**
 * Token class which represents an operator in an 
 * expression tree. 
 */

public class OperatorToken extends Token {
	public static final char Plus = '+';
	public static final char Minus = '-';
	public static final char Mult = '*';
	public static final char Div = '/';
	public static final char LeftParen = '(';
	private char operatorToken;
	
	/**
	 * Default constructor.
	 * 
	 * @param value the operator to be stored
	 */
	public OperatorToken(char value){
		operatorToken = value;
	}
	
	/**
	 * Returns the priority of the operator token. Parentheses have 
	 * the highest priority followed by multiplication/division and 
	 * finally addition/subtraction.
	 * 
	 * @return integer representation of the priority of this operator
	 */
	public int priority() {
		switch (this.operatorToken) {
		case Plus:
		    return 0;
		case Minus:
		    return 0;
		case Mult:
		    return 1;
		case Div:
		    return 1;
		case LeftParen:
		    return 2;
		
		default:
		    // This case should NEVER happen
		System.out.println("Error in priority.");
		System.exit(0);
		break;
		}
		return 0;
	}
	
	/**
	 * Returns a char containing the operator.
	 * 
	 * @return char containing the operator
	 */
	public char getOperatorToken(){
		return operatorToken;
	}
	
	/**
	 * Returns a string representation of the Operator token.
	 * 
	 * @return String containing the operator
	 */
	public String toString() {
		return operatorToken + "";
	}
}