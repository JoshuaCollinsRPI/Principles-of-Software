package hw6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hw4.Graph;
import hw4.Edge;
import hw4.Trail;
import hw5.ProfessorParser;

public class LegoPaths {
	public Graph<Double> graph; 
	public HashMap<String,HashMap<String,Integer>> table;
	
	public LegoPaths(){
		this.graph = new Graph<Double>();
	}
	
	public void createNewGraph(String filename) {
	    Map<String, Set<String>> LegoSets = new HashMap<>();
	    Set<String> Parts = new HashSet<>();
	    this.graph = new Graph<Double>();
	    this.table = new HashMap<>();
	    
	    try {
	        ProfessorParser.readData(filename, LegoSets, Parts);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Add all parts as nodes
	    for (String part : Parts) {
	        graph.addNode(part);
	        table.put(part,new HashMap<>());
	    }
	    
	    // Add edges and calculate occurrences
	    for (String set : LegoSets.keySet()) {
	        for (String part1 : LegoSets.get(set)) {
	            for (String part2 : LegoSets.get(set)) {
                    HashMap<String, Integer> m = table.get(part1);
                    Integer n = m.get(part2);

                    if (part1.equals(part2)) {
                    	continue;
                    }else {
                        if (n == null) {
                            m.put(part2, 1);
                        }
                        else {
                            m.put(part2, n + 1);
                        }
                    }

                }

            }

        }
        for (String part1 : table.keySet()) {
            for (String part2 : table.keySet()) {
            	if(table.get(part1).get(part2) != null) {
            		Double Weight = 1.0 / table.get(part1).get(part2);
                	graph.addEdge(part1, part2, Weight);
			/*
			Got a 60 on this hw because we forgot the following line

    			graph.addEdge(part2, part1, Weight);

       			Each node that connects to another can go A->B and B->A

			*/
            	}
            }
        }
	}

	
	public String findPath(String start, String stop) throws Exception {
		// cover edge case where some nodes aren't in the graph
		if(!(graph.contains(start)&&graph.contains(stop))) {
			String out = "";
			if (!(graph.contains(start))) {
				out += "unknown part " + start + "\n";
			}
			if (!(graph.contains(start))) {
				out += "unknown part " + stop + "\n";
			}
			return out;
		}
		String out = "path from " + start + " to " + stop + ":\n";
		Trail path = graph.dijkstra(start,stop);

		if(start.equals(stop)) {
			
			out += "total cost: 0.000\n";
			
		} else if(path == null || path.trail.size() == 0) {
			out += "no path found\n";
		}else{
			ArrayList<Edge<Double>> test = path.trail();
			Edge<Double> spot = test.get(0);
			out += (start + " to " + spot.name() 
						+ String.format(" with weight %.3f\n", graph.getWeight(start, spot.name())));
			for(int x = 0; x < test.size()-1; x++) {
				out += (test.get(x).name() + " to " 
						+ test.get(x+1).name()
						+ String.format(" with weight %.3f\n", graph.getWeight(test.get(x).name(), test.get(x+1).name())));
			}
			out += String.format("total cost: %.3f\n", path.price);
		}
		return out;
	}
	
//	public static void main(String[] args) throws Exception {
//		String file = "data/test1.csv";
//		//String file = "data/lego1980.csv";
//		long startTime = System.currentTimeMillis();
//		LegoPaths test = new LegoPaths();
//		test.createNewGraph(file);
//		long endTime1 = System.currentTimeMillis();
//		System.out.println("Execution time: " + (endTime1 - startTime)/1000.0 + " seconds");
////		String output = test.findPath("31367 Green Duplo Egg Base", "98138pr0080 Pearl Gold Tile Round 1 x 1 with Blue, Yellow and Black"
////						+ "Minecraft Print");
//		String output = test.findPath("Bob Marley", "Konstantin Kuzmin");
//		
//		System.out.println(output);
//		long endTime2 = System.currentTimeMillis();
//		long duration = endTime2 - startTime;
//		System.out.println("Execution time: " + duration/1000.0 + " seconds");
//	}

	
}
