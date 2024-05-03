package hw6;


import static org.junit.Assert.assertTrue;

import org.junit.*;

public final class LegoPathsTest {
	
	@Test
	public void findPathTest1() throws Exception {
		LegoPaths test = new LegoPaths();
		test.createNewGraph("data/test1.csv");
		String output = test.findPath("Isom Herron", "Konstantin Kuzmin");
		
		assertTrue(output.equals("path from Isom Herron to Konstantin Kuzmin:\n"
				+ "Isom Herron to Alexander Simms Pincus with weight 1.000\n"
				+ "Alexander Simms Pincus to Alan R Cutler with weight 1.000\n"
				+ "Alan R Cutler to David Eric Goldschmidt with weight 1.000\n"
				+ "David Eric Goldschmidt to Konstantin Kuzmin with weight 1.000\n"
				+ "total cost: 4.000\n"));
		
	}
	
	@Test
	public void findPathTest2() throws Exception {
		LegoPaths test = new LegoPaths();
		test.createNewGraph("data/test2.csv");
		String output = test.findPath("Isom Herron", "Konstantin Kuzmin");
		
		assertTrue(output.equals("path from Isom Herron to Konstantin Kuzmin:\n"
				+ "Isom Herron to Alexander Simms Pincus with weight 1.000\n"
				+ "Alexander Simms Pincus to Alan R Cutler with weight 1.000\n"
				+ "Alan R Cutler to David Eric Goldschmidt with weight 1.000\n"
				+ "David Eric Goldschmidt to Konstantin Kuzmin with weight 1.000\n"
				+ "total cost: 4.000\n"));
		
	}
	
	@Test
	public void findPathTest3() throws Exception {
		LegoPaths test2= new LegoPaths();
		test2.createNewGraph("data/test3.csv");
		String output = (test2.findPath("Konstantin Kuzmin", "Bob Marley"));
		
		assertTrue(output.equals("path from Konstantin Kuzmin to Bob Marley:\n"
				+ "Konstantin Kuzmin to Taylor Swift with weight 0.500\n"
				+ "Taylor Swift to Steven Walker with weight 0.333\n"
				+ "Steven Walker to Bob Marley with weight 0.500\n"
				+ "total cost: 1.333\n"));
		
	}
}