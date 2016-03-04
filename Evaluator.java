import java.util.Stack;

public class Evaluator {

 private String myFormula;
 private Cell[][] myCells;

 private static final int BadCell = -1;

 public Evaluator(String formula, Cell[][] cells) {
	 myFormula = formula;
	 myCells = cells;
	 calculate(myFormula);
 }
 
 //for now just turns a stack into a tree
 public void calculate(String formula) {
	 Stack tempStack = getFormula(formula);
	 
	 System.out.println("first item is poped after this");
	 

	 ExpressionTree tree = new ExpressionTree(tempStack);
	 
	 System.out.println( tree.evaluateTest(tree.getRoot()) );
	 //tree.printPostOrder(tree.getRoot());
	 //post oder traversal test 
	 
	 //print it out TODO make this its own method
	 while (!tempStack.isEmpty()) {
		 if (tempStack.peek() instanceof CellToken)
			 this.printCellToken((CellToken) tempStack.pop());
		 else if (tempStack.peek() instanceof OperatorToken)
			 System.out.println(((OperatorToken)tempStack.pop()).getOperatorToken());
		 else 
			 System.out.println(tempStack.pop());
			 
			
	 }
 }

  
    
    /**
 * getCellToken
 * 
 * Assuming that the next chars in a String (at the given startIndex)
 * is a cell reference, set cellToken's column and row to the
 * cell's column and row.
 * If the cell reference is invalid, the row and column of the return CellToken
 * are both set to BadCell (which should be a final int that equals -1).
 * Also, return the index of the position in the string after processing
 * the cell reference.
 * (Possible improvement: instead of returning a CellToken with row and
 * column equal to BadCell, throw an exception that indicates a parsing error.)
 * 
 * A cell reference is defined to be a sequence of CAPITAL letters,
 * followed by a sequence of digits (0-9).  The letters refer to
 * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
 * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
 * AAA = 702.  The digits represent the row number.
 *
 * @param inputString  the input string
 * @param startIndex  the index of the first char to process
 * @param cellToken  a cellToken (essentially a return value)
 * @return  index corresponding to the position in the string just after the cell reference
 */
public static int getCellToken (String inputString, int startIndex, CellToken cellToken) {
    char ch;
    int column = 0;
    int row = 0;
    int index = startIndex;

    // handle a bad startIndex
    if ((startIndex < 0) || (startIndex >= inputString.length() )) {
        cellToken.setColumn(inputString.charAt(index));
        cellToken.setRow(BadCell);
        return index;
        
    }

    // get rid of leading whitespace characters
    while (index < inputString.length() ) {
        ch = inputString.charAt(index);            
        if (!Character.isWhitespace(ch)) {
            break;
        }
        index++;
    }
    if (index == inputString.length()) {
        // reached the end of the string before finding a capital letter
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
        return index;
    }

    // ASSERT: index now points to the first non-whitespace character

    ch = inputString.charAt(index);            
    // process CAPITAL alphabetic characters to calculate the column
    if (!Character.isUpperCase(ch)) {
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
        return index;
    } else {
        column = ch - 'A';
        index++;
    }

    while (index < inputString.length() ) {
        ch = inputString.charAt(index);            
        if (Character.isUpperCase(ch)) {
            column = ((column + 1) * 26) + (ch - 'A');
            index++;
        } else {
            break;
        }
    }
    if (index == inputString.length() ) {
        // reached the end of the string before fully parsing the cell reference
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
        return index;
    }

    // ASSERT: We have processed leading whitespace and the
    // capital letters of the cell reference

    // read numeric characters to calculate the row
    if (Character.isDigit(ch)) {
        row = ch - '0';
        index++;
    } else {
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
        return index;
    }

    while (index < inputString.length() ) {
        ch = inputString.charAt(index);            
        if (Character.isDigit(ch)) {
            row = (row * 10) + (ch - '0');
            index++;
        } else {
            break;
        }
    }

    // successfully parsed a cell reference
    cellToken.setColumn(column);
    cellToken.setRow(row);
    return index;
}

public static void main(String [] args)
    {
		Evaluator test = new Evaluator("3 + (-200)/5*2 - (-7)", null);
		

       }

       
    /**
 * getFormula
 * 
 * Given a string that represents a formula that is an infix
 * expression, return a stack of Tokens so that the expression,
 * when read from the bottom of the stack to the top of the stack,
 * is a postfix expression.
 * 
 * A formula is defined as a sequence of tokens that represents
 * a legal infix expression.
 * 
 * A token can consist of a numeric literal, a cell reference, or an
 * operator (+, -, *, /).
 * 
 * Multiplication (*) and division (/) have higher precedence than
 * addition (+) and subtraction (-).  Among operations within the same
 * level of precedence, grouping is from left to right.
 * 
 * This algorithm follows the algorithm described in Weiss, pages 105-108.
 */
Stack getFormula(String formula) {
    Stack returnStack = new Stack();  // stack of Tokens (representing a postfix expression)
    boolean error = false;
    char ch = ' ';

    int literalValue = 0;

    CellToken cellToken;
    int column = 0;
    int row = 0;

    int index = 0;  // index into formula
    Stack operatorStack = new Stack();  // stack of operators

    while (index < formula.length() ) {
        // get rid of leading whitespace characters
        while (index < formula.length() ) {
            ch = formula.charAt(index);
            if (!Character.isWhitespace(ch)) {
                break;
            }
            index++;
        }

        if (index == formula.length() ) {
            error = true;
            break;
        }

        //TODO the negative number stuff
        if ( ch=='-' && Character.isDigit(formula.charAt(index + 1)) ) {
        	ch = formula.charAt(index + 1);
        	
        	System.out.println("-" + formula.charAt(index+1) + " is a negative number!!");
        	// We found a negative literal token
            literalValue = ch - '0';
            index++;
            index++;
            while (index < formula.length()) {
                ch = formula.charAt(index);
                if (Character.isDigit(ch)) {
                    literalValue = (literalValue * 10) + (ch - '0');
                    index++;
                } else {
                    break;
                }
            }
            // place the literal on the output stack
            System.out.println(literalValue*(-1));
            returnStack.push(new LiteralToken((-1)*literalValue));
        	
        }
        
        // ASSERT: ch now contains the first character of the next token.
        if (isOperator(ch)) {
            // We found an operator token
            switch (ch) {
                case OperatorToken.Plus:
                case OperatorToken.Minus:
                case OperatorToken.Mult:
                case OperatorToken.Div:
                case OperatorToken.LeftParen:
                    // push operatorTokens onto the output stack until
                    // we reach an operator on the operator stack that has
                    // lower priority than the current one.
                    OperatorToken stackOperator;
                    while (!operatorStack.isEmpty()) {
                        stackOperator = (OperatorToken) operatorStack.peek();
                        if ( (stackOperator.priority() >= operatorPriority(ch)) &&
                            (stackOperator.getOperatorToken() != OperatorToken.LeftParen) ) {

                            // output the operator to the return stack    
                            operatorStack.pop();
                            returnStack.push(stackOperator);
                        } else {
                            break;
                        }
                    }
                    break;

                default:
                    // This case should NEVER happen
                    System.out.println("Error in getFormula.");
                    System.exit(0);
                    break;
            }
            // push the operator on the operator stack
            operatorStack.push(new OperatorToken(ch));

            index++;

        } else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
            OperatorToken stackOperator;
            stackOperator = (OperatorToken) operatorStack.pop();
            // This code does not handle operatorStack underflow.
            while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
                // pop operators off the stack until a LeftParen appears and
                // place the operators on the output stack
                returnStack.push(stackOperator);
                stackOperator = (OperatorToken) operatorStack.pop();
            }

            index++;
        } else if (Character.isDigit(ch)) {
            // We found a literal token
            literalValue = ch - '0';
            index++;
            while (index < formula.length()) {
                ch = formula.charAt(index);
                if (Character.isDigit(ch)) {
                    literalValue = (literalValue * 10) + (ch - '0');
                    index++;
                } else {
                    break;
                }
            }
            // place the literal on the output stack
            returnStack.push(new LiteralToken(literalValue));

        } else if (Character.isUpperCase(ch)) {
            // We found a cell reference token
             cellToken = new CellToken();
            index = getCellToken(formula, index, cellToken);
            if (cellToken.getRow() == BadCell) {
                error = true;
                break;
            } else {
                // place the cell reference on the output stack
                returnStack.push(cellToken);
            }

        } else {
            error = true;
            break;
        }
    }

    // pop all remaining operators off the operator stack
    while (!operatorStack.isEmpty()) {
        returnStack.push(operatorStack.pop());
    }

    if (error) {
        // a parse error; return the empty stack
        while(!returnStack.isEmpty()){
            returnStack.pop();
        }
    }

    return returnStack;
}


/**
 * Return true if the char ch is an operator of a formula.
 * Current operators are: +, -, *, /, (.
 * @param ch  a char
 * @return  whether ch is an operator
 */
public boolean isOperator (char ch) {
    return ((ch == '+') ||
            (ch == '-') ||
            (ch == '*') ||
            (ch == '/') ||
            (ch == '(') );
}



/**
 * Given an operator, return its priority.
 *
 * priorities:
 *   +, - : 0
 *   *, / : 1
 *   (    : 2
 *
 * @param ch  a char
 * @return  the priority of the operator
 */
public int operatorPriority (char ch) {
    if (!isOperator(ch)) {
        // This case should NEVER happen
        System.out.println("Error in operatorPriority.");
        System.exit(0);
    }
    switch (ch) {
        case '+':
            return 0;
        case '-':
            return 0;
        case '*':
            return 1;
        case '/':
            return 1;
        case '(':
            return 2;

        default:
            // This case should NEVER happen
            System.out.println("Error in operatorPriority.");
            System.exit(0);
            break;
    }
    return -1;
}



/**
 *  Given a CellToken, print it out as it appears on the
 *  spreadsheet (e.g., "A3")
 *  @param cellToken  a CellToken
 *  @return  the cellToken's coordinates
 */
public String printCellToken (CellToken cellToken) {
    char ch;
    String returnString = "";
    int col;
    int largest = 26;  // minimum col number with number_of_digits digits
    int number_of_digits = 2;

    col = cellToken.getCol();

    // compute the biggest power of 26 that is less than or equal to col
    // We don't check for overflow of largest here.
    while (largest <= col) {
        largest = largest * 26;
        number_of_digits++;
    }
    largest = largest / 26;
    number_of_digits--;

    // append the column label, one character at a time
    while (number_of_digits > 1) {
        ch = (char) (((col / largest) - 1) + 'A');
        returnString += ch;
        col = col % largest;
        largest = largest  / 26;
        number_of_digits--;
    }

    // handle last digit
    ch = (char)(col + 'A');
    returnString += ch;

    // append the row as an integer
    returnString += cellToken.getRow();

    return returnString;
}


}