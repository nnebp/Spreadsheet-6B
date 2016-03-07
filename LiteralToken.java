/**
 * Token class which represents a literal integer 
 * term in a postfix expression tree.
 */
 
public class LiteralToken extends Token {
	private int myLiteral;
	
	/**
	 * Default constructor. Takes the input and makes 
	 * it the int value of this Literal token.
	 * 
	 * @param value the value of the Literal token
	 */
	public LiteralToken(int value) {
		myLiteral = value;
    }
    
    /**
	 * Returns the value of this Literal token.
	 *
	 * @return value of this Literal token
	 */
    public int getLiteral() {
        return myLiteral;
    
    }
    
	/**
	 * Returns a string representation of the integer 
	 * value of this Literal token.
	 * @return String containing the integer value of this 
	 * Literal token
	 */
    public String toString() {
    	return new Integer(myLiteral).toString();
    }
}