public class CellToken extends Token {
 private int myColumn; // column A = 0, B = 1, etc.
 private int myRow;
 
 
 //Constructor 
 
 public CellToken(){
    int myColumn = 0;
    int myRow = 0;
    
    }
    
 //Mutattor method for changing CellToken Values;

 public void setColumn(int col){
    
    myColumn= col;
    }
    
 public void setRow(int row){
    
    myRow = row;
    
    }
    
    public int getRow(){
    return myRow;
    }
    
      public int getCol(){
          return myColumn;
    
    }
}