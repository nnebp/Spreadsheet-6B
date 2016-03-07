/**
 * OpenFrame is a JFrame which prompts the user for the size of a new spreadsheet
 * and creates a spreadsheet following the user's dimensions. Based on 
 * https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/FlowLayoutDemoProject/src/layout/FlowLayoutDemo.java
 * 
 * @author nnebp
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class OpenFrame extends JFrame{
	/** the field where the user enters the row number.*/
	JTextField rowField;
	
	
	/** the field where the user enters the column number.*/
	JTextField columnField;
	
	/** Row field label.*/
	JLabel rowLabel;
	
	/** Column field label. */
	JLabel columnLabel;
	
	/** Regex representing a valid integer.*/
	private static String isInteger = "^-?\\d+$";
	
	/** Frame layoug */
    FlowLayout layout = new FlowLayout();
    
    /** button to create the spreadsheet */
    JButton createButton = new JButton("Create Spreadsheet");
    
    public OpenFrame(String name) {
        super(name);
    }
    
    /**
     * Adds the necessary components to the pane.
     * @param pane container to add to.
     */
    public void addComponentsToPane(final Container pane) {
    	//setup the top panel
        final JPanel panel = new JPanel();
        panel.setLayout(layout);
        layout.setAlignment(FlowLayout.CENTER);
        //set up lower panel with button
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());
        
        this.rowLabel = new JLabel("Rows: ");
        this.columnLabel = new JLabel("Columns: ");
        this.rowField = new JTextField(5);
        this.columnField = new JTextField(5);
        
        //Add everything to the panel
        panel.add(rowLabel);
        panel.add(rowField);
        panel.add(columnLabel);
        panel.add(columnField);
        
        //Left to right component orientation is selected by default
        panel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        
        controls.add(createButton);
        
        //Make sure input is valid and create the spreadsheet
        createButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	
            	if(rowField.getText().matches(isInteger) && 
					columnField.getText().matches(isInteger) ) {
            		int rows = new Integer(rowField.getText());
            		int cols = new Integer(columnField.getText());

            		if ((rows > 1 && rows < 1001) &&
					   (cols > 1 && cols < 1001)) {
						//close the opening frame and show the spreadsheet
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
									public void run() {
										CellTable.createAndShowGUI(rows, cols);
									}
								});
						
						//destroy this frame
						OpenFrame.this.setVisible(false);
						OpenFrame.this.dispose();
            		} 
            		//user entered too small or too big of a table display error
            		else {
            			JOptionPane.showMessageDialog(OpenFrame.this,
                			    "Please enter an integer between 2 and 1000.",
                			    "Error",
                			    JOptionPane.ERROR_MESSAGE);
            		}
            		
            	}
            	//user did not enter an integer value
            	else {
            		JOptionPane.showMessageDialog(OpenFrame.this,
            			    "Please enter an integer value.",
            			    "Error",
            			    JOptionPane.ERROR_MESSAGE);
            	}
            	
            }
        });
        pane.add(panel, BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH); ;
    }
    
    /**
     * Create the GUI and show it.  
     */
    private static void createAndShowGUI() {
        //Create and set up the frame
        OpenFrame frame = new OpenFrame("SpreadSheet 6B");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane
        frame.addComponentsToPane(frame.getContentPane());
        //Display 
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Display the Opening frame. This is the only main function to 
     * be run in the entire program.
     * @param args
     */
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
