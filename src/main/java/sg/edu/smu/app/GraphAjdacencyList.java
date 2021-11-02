package sg.edu.smu.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GraphAjdacencyList {
    List<List<Integer>> adj;

    public GraphAjdacencyList(int n) {
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
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

    public boolean BFS(int s, int d, int[] pred, int[] dist) {
        boolean[] visited = new boolean[adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = Integer.MAX_VALUE;
        }
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[s] = true;
        queue.add(s);
        dist[s] = 0;
        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            Iterator<Integer> i = adj.get(s).listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                    dist[n] = dist[s] + 1;
                    pred[n] = s;
                }
                if (n == d) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printShortestDistance(int src, int dest) {
        int[] pred = new int[adj.size()];
        int[] dist = new int[adj.size()];
        if (!BFS(src, dest, pred, dist)) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        Integer c = dest;
        path.addFirst(c);
        while (pred[c] != Integer.MAX_VALUE) {
            path.addFirst(pred[c]);
            c = pred[c];
        }
        System.out.println("Shortest path length is: " + dist[dest]);
        System.out.println("Path is :");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + " ");
        }

    }

    public List<List<Integer>> getGraph() {
        return adj;
    }

}
