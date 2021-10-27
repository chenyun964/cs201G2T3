package sg.edu.smu.app.bfsqueue;

import java.util.LinkedList;

import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.Vertex;

import java.util.ArrayList;
import java.util.Iterator;

public class BFSqueue {
    public boolean BFS(Graph<Integer, Integer> graph, Vertex<Integer> src, Vertex<Integer> dest, 
            int v, int pred[], int dist[]) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Vertex<Integer>> queue = new LinkedList<>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean[] visited = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src.getElement()] = true;
        dist[src.getElement()] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            Vertex<Integer> u = queue.remove();
            
            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<Integer> vert = graph.opposite(u, e);
                if (visited[vert.getElement()] == false) {
                    visited[vert.getElement()] = true;
                    dist[vert.getElement()] = dist[u.getElement()] + 1;
                    pred[vert.getElement()] = u.getElement();
                    queue.add(vert);

                    // stopping condition (when we find
                    // our destination)
                    if (vert == dest)
                        return true;
                }
            }
        }
        return false;
    }

    public void printShortestDistance(ArrayList<ArrayList<Integer>> adj, int s, int dest, int v) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        // Print distance
        System.out.println("Shortest path length is: " + dist[dest]);

        // Print path
        System.out.println("Path is :");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
    }
}
