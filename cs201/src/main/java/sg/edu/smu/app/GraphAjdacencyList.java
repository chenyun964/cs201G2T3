package sg.edu.smu.app;

import java.util.ArrayList;

public class GraphAjdacencyList {
    ArrayList<ArrayList<Integer>> adj;

    public GraphAjdacencyList(int n){
        adj = new ArrayList<ArrayList<Integer>>(n);
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<Integer>());
    }
    // A utility function to add an edge in an
    // undirected graph
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    // A utility function to print the adjacency list
    // representation of graph
    public void printGraph() {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("\nAdjacency list of vertex" + i);
            System.out.print("head");
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(" -> " + adj.get(i).get(j));
            }
            System.out.println();
        }
    }

    public ArrayList<ArrayList<Integer>> getGraph(){
        return adj;
    }
}
