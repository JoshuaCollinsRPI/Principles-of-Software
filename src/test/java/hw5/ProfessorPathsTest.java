package hw5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public final class ProfessorPathsTest {
	
	@Test
	public void ReadFileTest() {
		String fileName = new String("data/test1.csv");
		ProfessorPaths test = new ProfessorPaths();
		test.createNewGraph(fileName);
		assertTrue(test!=null);
	}
	
	@Test
	public void createNewGraphTest() {
		String fileName = new String("data/test1.csv");
		ProfessorPaths test = new ProfessorPaths();
		test.createNewGraph(fileName);
		assertTrue(test.size() == 9);
		assertTrue(test.edgeSize() == 38);
		assertTrue(test.has("David Eric Goldschmidt"));
		assertTrue(test.connection("David Eric Goldschmidt", "Konstantin Kuzmin"));
		assertFalse(test.connection("David Eric Goldschmidt", "John Doe"));
	}
	
	@Test
	public void findPathTest() {
		String fileName1 = new String("data/courses.csv"); 
		String fileName2 = new String("data/test2.csv");
		
		ProfessorPaths test1 = new ProfessorPaths();
		ProfessorPaths test2= new ProfessorPaths();
		
		test1.createNewGraph(fileName1);
		test2.createNewGraph(fileName2);
		
		String std_out = test1.findPath("Mohammed J. Zaki", "Wilfredo Colon" );
		String std_correct = "path from Mohammed J. Zaki to Wilfredo Colon:\n"
				+ "Mohammed J. Zaki to David Eric Goldschmidt via CSCI-2300\n"
				+ "David Eric Goldschmidt to Michael Joseph Conroy via CSCI-4430\n"
				+ "Michael Joseph Conroy to Alan R Cutler via CHEM-1200\n"
				+ "Alan R Cutler to Wilfredo Colon via CHEM-1100\n";
		assertTrue(std_out.equals(std_correct));
		
		String abn_out = test2.findPath("John Doe","John Doe");
		String abn_out_correct = "path from John Doe to John Doe:\n";
		 
		assertTrue(abn_out.equals(abn_out_correct));
		
		abn_out = test2.findPath("Invalid Name 1","John Doe");
		abn_out_correct = "unknown professor Invalid Name 1\n";
		
		assertTrue(abn_out.equals(abn_out_correct));
		
		abn_out = test2.findPath("John Doe","Invalid Name 2");
		abn_out_correct = "unknown professor Invalid Name 2\n";
		
		assertTrue(abn_out.equals(abn_out_correct));
		
		abn_out = test2.findPath("Invalid Name 1","Invalid Name 2");
		abn_out_correct = "unknown professor Invalid Name 1\nunknown professor Invalid Name 2\n";
		
		assertTrue(abn_out.equals(abn_out_correct));
		
		abn_out = test2.findPath("John Doe","David Eric Goldschmidt");
		abn_out_correct = "path from John Doe to David Eric Goldschmidt:\nno path found\n";
		assertTrue(abn_out.equals(abn_out_correct));
		
		
	}
	
	
}