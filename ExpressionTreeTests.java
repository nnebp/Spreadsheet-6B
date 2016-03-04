import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExpressionTreeTests {

	Cell[][] cells = {
		{ new Cell(0,0), new Cell(0,1), new Cell(0,2), new Cell(0,3) }
	};
	
	@Before
	public void setUp() throws Exception {
		cells[0][0].addReference(cells);
		cells[0][0].setFormula("A0 + 1");
//		cells[0][0].setFormula("B0 + (-5) * 100 + C0");
		
		cells[0][1].addReference(cells);
		cells[0][1].setFormula("33");
		
		cells[0][2].addReference(cells);
		cells[0][2].setFormula("5 * (-10)");
	}

	@Test
	public void testCellReferece() {
		cells[0][0].calculateValue();
		//assertEquals(cells[0][0].getValue(), 133);
		System.out.println(cells[0][0].getValue() + "!!!!");
	}

}
