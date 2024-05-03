package hw4;


import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public final class GraphWrapperTest {    
    
	@Test
	public void defaultConstructorTest() {
		GraphWrapper graphA = new GraphWrapper();
		assertTrue(graphA != null);
	}
	
    @Test
    public void addNodeTest(){
        GraphWrapper graphA = new GraphWrapper();

        graphA.addNode("A");
        graphA.addNode("B");
        graphA.addNode("C");
        graphA.addNode("D");
        graphA.addEdge("A", "C", "45");
        graphA.addEdge("A", "B", "23");
        graphA.addEdge("B", "D", "56");
        graphA.addEdge("D", "A", "20");
        
        assertTrue(graphA.Contains("A"));
    }
    
    
    @Test
    public void addEdge(){
    	GraphWrapper graphA = new GraphWrapper();

        graphA.addNode("A");
        graphA.addNode("B");
        graphA.addNode("C");
        graphA.addNode("D");
        graphA.addEdge("A", "C", "45");
        graphA.addEdge("A", "B", "23");
        graphA.addEdge("B", "D", "56");
        graphA.addEdge("D", "A", "20");
        
        assertTrue(graphA.getLabel("A","C") == "45");
    }

    @Test
    public void listNodesTest(){
    	GraphWrapper graphA = new GraphWrapper();

        graphA.addNode("A");
        graphA.addNode("B");
        graphA.addNode("C");
        graphA.addNode("D");
        graphA.addEdge("A", "C", "45");
        graphA.addEdge("A", "B", "23");
        graphA.addEdge("B", "D", "56");
        graphA.addEdge("D", "A", "20");
        
        Iterator<String> output = graphA.listNodes();
        
        assertTrue(output.hasNext());
        assertEquals("A",output.next());
        assertEquals("B",output.next());
        assertEquals("C",output.next());
        assertEquals("D",output.next());
        assertFalse(output.hasNext());
    }
 
    @Test
    public void FindChildrenTest() {
    	GraphWrapper graphC = new GraphWrapper();
        graphC.addNode("A");
        graphC.addNode("B");
        graphC.addNode("C");
        graphC.addEdge("A", "B", "7");
        graphC.addEdge("A", "B", "8");
        graphC.addEdge("A", "C", "7");
        
        Iterator<String> output = graphC.listChildren("A");
        
        assertTrue(output.hasNext());
        assertEquals("B(7)",output.next());
        assertEquals("B(8)",output.next());
        assertEquals("C(7)",output.next());
        assertFalse(output.hasNext());
    }

}

