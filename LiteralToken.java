public class LiteralToken extends Token {
 private int myLiteral;
 
 public LiteralToken(int value){
    
    myLiteral = value;
    }
    
    
    public int getLiteral(){
        return myLiteral;
    
    }
}