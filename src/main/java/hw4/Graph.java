package hw4;


import java.util.*;

public class Graph<W extends Comparable<W>> {
	public HashMap<String,HashMap<String,ArrayList<Edge<W>>>> graph;
	
	// Rep invariant:
	// 		Nodes cannot be null
	// Abstraction:
	// 		Every node object represents a node on a graph
	//		The nodes hold a list of connections (Directed Edges) leading to other nodes
	
	
	/*
	 * Base Constructor
	 * @modifies: initializes graph double hashmap holding an arraylist of edges
	 * @effects: initializes graph object
	 * */
	public Graph() {
		this.graph = new HashMap<>();
	}
	
	/*
	 * Copy Constructor
	 * @parameter: Graph b
	 * @requires: b is a graph object and b is not null
	 * @modifies: initializes node hashmap and copies from other graph
	 * @effects: copies node hashmap between graphs
	 * @effects: initializes graph object
	 * */
	public Graph(Graph<W> different) {
		this.graph = different.graph;
	}
	
	/*
	 * Method Adds a node to the hashmap
	 * @parameters: name
	 * @requires: name is not null string and not in the graph already 
	 * @modifies: Graph object and node hashmap
	 * @effects: adds a node to the graph
	 * @throws: RunTimeException if node already exists in graph
	 * */
	
	public void addNode(String name) {
		if (!graph.containsKey(name)) {
            graph.put(name, new HashMap<>());
        }
	}
	
	/*
	 * Method Adds a edge to the node object
	 * @parameters: name, name2 and weight
	 * @requires: name, name2 and weight are all strings that are not null. Start and end are in the graph
	 * @modifies: add edge to node object
	 * @effects: adds edge to graph
	 * @throws: RuntimeException if start or end is not in graph
	 * */
	public void addEdge(String name, String name2, W weight) { 
		if(graph.get(name).containsKey(name2)) {
			graph.get(name).get(name2).add(new Edge<W>(name2,weight));
			graph.get(name).get(name2).sort(Comparator.comparing(Edge::weight));
			//System.out.println(graph.get(name).get(name2));
		}else {
			graph.get(name).put(name2, new ArrayList<>());
			graph.get(name).get(name2).add(new Edge<W>(name2,weight));
			//System.out.println(graph.get(name).get(name2));
		}
	}
	
	/*
	 * Returns Label of an edge
	 * @parameters: start, stop
	 * @requires: start and stop to be strings and these are names of nodes in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: RuntimeException if nodes aren't in the graph
	 * @return: name of edge
	 * */
	public W getWeight(String start, String stop) {
		if (graph.get(start).get(stop).size() > 0) {
			//System.out.println(graph.get(start).get(stop).get(0).weight());
			return graph.get(start).get(stop).get(0).weight();
		}
		throw new RuntimeException("edge not found");
	}
	
	/*
	 * Returns if graph has this node
	 * @parameters: node
	 * @requires: node is a non null string
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @return: true if node is in graph and false otherwise
	 * */
	public Boolean contains(String node) {
		return graph.containsKey(node);
	}
	
	public ArrayList<String> kids(String node){
		ArrayList<String> out = new ArrayList<String>();
		
		Set<String> one = graph.get(node).keySet();
		for(String x : one) {
			ArrayList<Edge<W>> temp = graph.get(node).get(x);
			for(Edge<W> y : temp) {
				out.add(y.name());
			}
		}
		
		return out;
	}
	
	// --------------------------------------------------------------------------
	// hw 4 methods
	
	/*
	 * Returns a list of name of all the nodes
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: list of name of all the nodes
	 * */
	public ArrayList<String> allNodes() {
		ArrayList<String> out = new ArrayList<String>();
		for(String temp: this.graph.keySet()) {
			out.add(temp);
		}
		return out;
	}
	
	/*
	 * Returns a list of name to the children nodes
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: list of name to the children nodes
	 * */
	public ArrayList<String> allChildren(){
		ArrayList<String> out = new ArrayList<String>();
		for(String t: graph.keySet()) {
			for(String x: graph.get(t).keySet()) {
				for(Edge<W> z: graph.get(t).get(x)) {
					out.add(z.name());
				}
			}
		}
		return out;
	}
	
	public ArrayList<String> listChildren(String node){
		ArrayList<String> out = new ArrayList<String>();
		for(String t: graph.keySet()) {
			for(String x: graph.get(t).keySet()) {
				if(node.equals(t)) {
					for(Edge<W> z: graph.get(t).get(x)) {
						out.add(z.name()+"("+z.weight()+")");
					}
				}
			}
		}
		return out;
	}
	
	/*
	 * Returns a list of name to the nodes that connect to Node
	 * @parameters: Node
	 * @requires: Node is the name of a node in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: list of name to the nodes that connect to Node
	 * */
	public ArrayList<String> findParent(String Node){
		ArrayList<String> out = new ArrayList<String>();
		for(String t: graph.keySet()) {
			if (graph.get(t).containsKey(Node)) {
				out.add(t);
			}
		}
		return out;
	}
	
	/*
	 * Return list of name to the nodes that are connected to from Node
	 * @parameters: Node
	 * @requires: Node is the name of a node in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: list of name to the nodes that are connected to from Node
	 * */
	public ArrayList<String> findChildren(String Node){
		ArrayList<String> out = new ArrayList<String>();
		for(String t: graph.keySet()) {
			if(t.equals(Node)) {
				for(String x: graph.get(t).keySet()) {
					for(Edge<W> z: graph.get(t).get(x)) {
						out.add(z.name());
					}
				}
			}
			
		}
		return out;
	}
	
	/*
	 * Return true if the two node connect via an edge, false otherwise
	 * @parameters: A and B
	 * @requires: both nodes are in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: Return true if the two node connect via an edge, false otherwise
	 * */
	public boolean connect(String A, String B) {
		if(graph.get(A).keySet().contains(B)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Return number of edges between 2 nodes
	 * @parameters: A and B
	 * @requires: both nodes are in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: number of edges between 2 nodes
	 * */
	public int count(String A, String B) {
		return graph.get(A).get(B).size();
	}
	
	/* 
	 * Return number of nodes
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: number of nodes
	 * */
	public int size() {
		return graph.keySet().size();
	}
	
	/*
	 * Return true if two graphs are the same
	 * @parameters: A and B
	 * @requires: both nodes are in the graph
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: number of edges between 2 nodes
	 * */
	public boolean equals(Graph<W> B) {
		return graph.equals(B.graph);
	}

	
	// --------------------------------------------------------------------------
	// hw 5 methods
	
	/*
	 * Return number of edges in the whole graph
	 * @parameters: none
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: number of edges in the whole graph
	 * */
	public int edgeSize() {
		int x = 0;
		for (String a: graph.keySet()) {
			for (String b: graph.get(a).keySet()) {
				x += graph.get(a).get(b).size();
			}
		}
		return x;
	}
	
	/*
	 * Returns arraylist of optimal path via breadth first search
	 * @parameters: start and stop
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: optimal path from start to stop via breadth first search
	 * */
	public ArrayList<String> BreadthFirstSearch(String start, String stop) {
	    Queue<String> queue = new LinkedList<>();
	    Map<String, ArrayList<String>> paths = new HashMap<>();
	    Set<String> visited = new HashSet<>();

	    queue.add(start);
	    paths.put(start, new ArrayList<>());
	    visited.add(start);


	    while (!queue.isEmpty()) {
	        String current = queue.poll();

	        if (current.equals(stop)) {
	            return paths.get(current);
	        }

	        ArrayList<String> currentPath = paths.get(current);
	        ArrayList<String> children = this.kids(current);
	        Collections.sort(children);

	        for (String child : children) {
	            if (!visited.contains(child)) {
	                ArrayList<String> newPath = new ArrayList<>(currentPath);
	                newPath.add(child);
	                paths.put(child, newPath);
	                queue.add(child);
	                visited.add(child);
	            }
	        }
	    }

	    // destination was not found
	    return null;
	}
	
	// --------------------------------------------------------------------------
	// hw 6
	
	/*
	 * Returns arraylist of edges and their weights for dijkstra;s algorithm
	 * @parameters: start
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: arraylist of edges and their weights for dijkstra;s algorithm
	 * */
	public ArrayList<Edge<Double>> subnodes(String stops) throws Exception {
		ArrayList<Edge<Double>> output = new ArrayList<>();
	    
	    for(String x: graph.get(stops).keySet()) {
	    	for (Edge<W> y: graph.get(stops).get(x)) {
	    		output.add(new Edge<Double>(x,(Double)y.weight()));
	    	}
	    }
	    
	    return output;
	}

	/*
	 * Returns trail object that conatins the cost and most optimal path for dijkstra's algorithm
	 * @parameters: start, stop
	 * @requires: none
	 * @modifies: none
	 * @effects: none
	 * @throws: none
	 * @returns: atrail object that conatins the cost and most optimal path for dijkstra's algorithm
	 * */
	public Trail dijkstra(String start, String stop) throws Exception {
        PriorityQueue<Trail> queue = new PriorityQueue<>();
        Trail i = new Trail(start, start);
        queue.add(i);
        HashSet<String> visited = new HashSet<>();
        
        while (!queue.isEmpty()) {
            Trail optimal = queue.poll();
            String pause = optimal.stops;
            
            if (pause.equals(stop)) {
                return optimal;
            } else if (visited.contains(pause)) {
                continue;
            }
            
            visited.add(pause);
            
            for (Edge<Double> x : subnodes(pause)) {
            	Edge<Double> temp = new Edge<Double>( x.name(), x.weight());
                if (!visited.contains(x.name())) {
					Trail newTrail = optimal.add(temp);
                    queue.add(newTrail);
                } 
            }

        }
        
        return null;
    }


}