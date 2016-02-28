public class OperatorToken extends Token {
 public static final char Plus = '+';
 public static final char Minus = '-';
 public static final char Mult = '*';
 public static final char Div = '/';
 public static final char LeftParen = '(';
 private char operatorToken;
 
 public OperatorToken(char value){
    
    operatorToken = value;
    }
 
  /* @return  the priority of operatorToken
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

public char getOperatorToken(){

    return operatorToken;
}
}