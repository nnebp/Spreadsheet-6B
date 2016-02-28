public class Cell {
 private String formula;
 private int value;
 // the expression tree below represents the formula
// private ExpressionTree expressionTree;
 //public void Evaluate (Spreadsheet spreadsheet){
    
    
 //   };
 
 
 

 
 public static int ExcelColumnNameToNumber(String columnName)
{
    if (columnName== null) {
        throw new NullPointerException("String is empty");
    };

    columnName = columnName.toUpperCase();

    int sum = 0;

    for (int i = 0; i < columnName.length(); i++)
    {
        sum *= 26;
        sum += (columnName.charAt(i) - 'A' + 1);
    }

    return sum;
}


 public static String getExcelColumnName(int number) {
        final StringBuilder sb = new StringBuilder();

        int num = number - 1;
        while (num >=  0) {
            int numChar = (num % 26)  + 65;
            sb.append((char)numChar);
            num = (num  / 26) - 1;
        }
        return sb.reverse().toString();
    }


}