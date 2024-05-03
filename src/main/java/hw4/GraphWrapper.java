package hw4;

import java.util.*;

public class GraphWrapper {
	
	private Graph<String> graph;
	
	/*
	 * Constructor
	 * @parameters:none
	 * @requires: none
	 * @modifies: graph object is initialized
	 * @effects: none
	 * @throws: none
	 * @returns: graph object is initialized
	 * */
	public GraphWrapper() {
		graph = new Graph<String>();
	}
	
	/*
	 * Method Adds a node to the graph
	 * @parameters: nodeData
	 * @requires: nodeData is not null string and not in the graph already 
	 * @modifies: Graph object and graph
	 * @effects: adds a node to the graph
	 * @throws: RunTimeException if node already exists in graph
	 * */
	public void addNode(String nodeData) {
		graph.addNode(nodeData);
	}
	
	/*
	 * Method Adds a edge to the graph
	 * @parameters: parentNode, childNode, EdgeLabel
	 * @requires: parentNode, childNode, EdgeLabel are all strings that are not null. parentNode & childNode are in the graph
	 * @modifies: add edge to graph
	 * @effects: adds edge to graph
	 * @throws: RuntimeException if start or end is not in graph
	 * */
	public void addEdge(String parentNode, String childNode, String EdgeLabel) {
		if (graph.contains(parentNode)&&graph.contains(childNode)) {
			graph.addEdge(parentNode, childNode, EdgeLabel);
		}else {
			throw new RuntimeException("Both nodes must be in the graph");
		}
	}
	
	/*
	 * Return iterator to a list of all nodes
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: iterator to a list of all node
	 * */
	public Iterator<String> listNodes(){
		ArrayList<String> temp = graph.allNodes();
		Collections.sort(temp);
		Iterator<String> output = temp.iterator();
		return output;
	}
	
	/*
	 * Return iterator to a list of all nodes connected to parentNode
	 * @parameters: parentNode
	 * @requires: parentNode is in graph
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: iterator to a list of all nodes connected to parentNode
	 * */
	public Iterator<String> listChildren(String parentNode){
		ArrayList<String> out_list = graph.listChildren(parentNode);
		
		Collections.sort(out_list); 
		
		Iterator<String> output = out_list.iterator();
		return output;
	}
	
	/*
	 * Returns if graph has this node
	 * @parameters: A_node
	 * @requires: A_node is a non null string
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: true if node is in graph and false otherwise
	 * */
	public Boolean Contains(String node) {
		return this.graph.contains(node);
	}
	
	/*
	 * Returns Label of an edge
	 * @parameters: start, To
	 * @requires: start and To to be strings and these are names of nodes in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: RuntimeException if nodes aren't in the graph
	 * @return: name of edge
	 * */
	public String getLabel(String From, String To) {
		return this.graph.getWeight(From,To);
	}
	
}