package hw4;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;

public final class GraphTest {

    @Test
    public void is_equalTest() {
    	
        Graph<String> graphC = new Graph<String>();
        graphC.addNode("A");
        graphC.addNode("B");
        graphC.addEdge("A", "B", "7");
    	
        Graph<String> graphE = new Graph<String>();
        graphE.addNode("A");
        graphE.addNode("B");
        graphE.addEdge("A", "B", "7");

        ArrayList<String> output = graphC.findChildren("A");
        assertTrue(output.size() == 1);
        output = graphE.findChildren("A");
        assertTrue(output.size() == 1);
        
        //System.out.println(graphC.graph);
    }

    @Test
    public void addNodeTest(){
    	Graph<String> graphA = new Graph<String>();

        graphA.addNode("A");
        graphA.addNode("B");
        graphA.addNode("C");
        graphA.addNode("D");
        graphA.addEdge("A", "C", "45");
        graphA.addEdge("A", "B", "23");
        graphA.addEdge("B", "D", "56");
        graphA.addEdge("D", "A", "20");
        
        assertTrue(graphA.contains("A"));
    }
    

    @Test
    public void addEdge(){
    	Graph<String> graphA = new Graph<String>();

        graphA.addNode("A");
        graphA.addNode("B");
        graphA.addNode("C");
        graphA.addNode("D");
        graphA.addEdge("A", "C", "45");
        graphA.addEdge("A", "B", "23");
        graphA.addEdge("B", "D", "56");
        graphA.addEdge("D", "A", "20");
        
        assertTrue(graphA.getWeight("A","C") == "45");
    }

    @Test
    public void findParentTest(){
    	Graph<String> graphA = new Graph<String>();

        graphA.addNode("A");
        graphA.addNode("B");
        graphA.addNode("C");
        graphA.addNode("D");
        graphA.addEdge("A", "C", "45");
        graphA.addEdge("A", "B", "23");
        graphA.addEdge("B", "D", "56");
        graphA.addEdge("D", "A", "20");
        
        ArrayList<String> output = graphA.findParent("A");
        
        assertTrue(output.size() == 1);
        

        assertTrue(output.get(0).equals("D"));
    }
    
    @Test
    public void FindChildrenTest() {
    	Graph<String> graphC = new Graph<String>();
        graphC.addNode("A");
        graphC.addNode("B");
        graphC.addNode("C");
        graphC.addEdge("A", "B", "7");
        graphC.addEdge("A", "B", "8");
        graphC.addEdge("A", "C", "7");
        
        ArrayList<String> output = graphC.findChildren("A");
        
        assertTrue(output.size() == 3);
        assertTrue(graphC.connect("A","B"));
        assertTrue(graphC.count("A","B") == 2);
        assertTrue(graphC.size() == 3);
    }

}
