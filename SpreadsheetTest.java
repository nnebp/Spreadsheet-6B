import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SpreadsheetTest {
	
	Cell[][] cells = {
			{ new Cell(0,0), new Cell(0,1), new Cell(0,2), new Cell(0,3), new Cell(0,4) },
			{ new Cell(1,0), new Cell(1,1), new Cell(1,2), new Cell(1,3), new Cell(1,4) },
			{ new Cell(2,0), new Cell(2,1), new Cell(2,2), new Cell(2,3), new Cell(2,4) },
			{ new Cell(3,0), new Cell(3,1), new Cell(3,2), new Cell(3,3), new Cell(3,4) },
			{ new Cell(4,0), new Cell(4,1), new Cell(4,2), new Cell(4,3), new Cell(4,4) }
		};

	Spreadsheet test = new Spreadsheet(cells);
	@Before
	public void setUp() throws Exception {
		
		
		//C0
		cells[0][2].addReference(cells);
		cells[0][2].setFormula("23");
		//A1
		cells[1][0].addReference(cells);
		cells[1][0].setFormula("4");
		//D2
		cells[2][3].addReference(cells);
		cells[2][3].setFormula("2*5");
		//D4
		cells[4][3].addReference(cells);
		cells[4][3].setFormula("(-5)");
		//E0
		cells[0][4].addReference(cells);
		cells[0][4].setFormula("B2+C2");
		test.updateCell(0, 4);
		
		//D1
		cells[1][3].addReference(cells);
		cells[1][3].setFormula("B3+2");
		test.updateCell(1, 3);
		//B3
		cells[3][1].addReference(cells);
		cells[3][1].setFormula("A1+B1");
		test.updateCell(3, 1);
		//C3
		cells[3][2].addReference(cells);
		cells[3][2].setFormula("B3+A4");
		test.updateCell(3, 2);
		
		//E3
		cells[3][4].addReference(cells);
		cells[3][4].setFormula("3+C0");
		test.updateCell(3, 4);
		//A4
		cells[4][0].addReference(cells);
		cells[4][0].setFormula("A1-D4");
		test.updateCell(4, 0);
		
		
		System.out.println(test.isCyclic());
	}

	@Test
	public void test() {
		assertEquals(cells[1][0].getValue(), 4);
		assertEquals(cells[4][3].getValue(), -5);
		assertEquals(cells[4][0].getValue(), 9);
		assertEquals(cells[2][3].getValue(), 10);
		assertEquals(cells[3][2].getValue(), 13);
		assertEquals(cells[1][3].getValue(), 6);
		assertEquals(cells[0][4].getValue(), 0);
		assertEquals(cells[3][1].getValue(), 4);
		assertEquals(cells[3][4].getValue(), 26);
	}
	
	@Test
	public void testCyclic(){
		Cell[][] thecells = {
				{ new Cell(0,0), new Cell(0,1), new Cell(0,2), new Cell(0,3), new Cell(0,4) },
				{ new Cell(1,0), new Cell(1,1), new Cell(1,2), new Cell(1,3), new Cell(1,4) },
				{ new Cell(2,0), new Cell(2,1), new Cell(2,2), new Cell(2,3), new Cell(2,4) },
				{ new Cell(3,0), new Cell(3,1), new Cell(3,2), new Cell(3,3), new Cell(3,4) },
				{ new Cell(4,0), new Cell(4,1), new Cell(4,2), new Cell(4,3), new Cell(4,4) }};
		
		Spreadsheet test1 = new Spreadsheet(thecells);
		thecells[0][0].addReference(thecells);
		thecells[0][0].setFormula("B1");
		test1.updateCell(0, 0);
		
		thecells[1][1].addReference(thecells);
		thecells[1][1].setFormula("A0");
		test1.updateCell(1, 1);
		System.out.println(test1.isCyclic());
		assertTrue(test1.isCyclic());
		
		
	}

}
