package sg.edu.smu.app.bfsqueue;

import java.util.LinkedList;
import java.util.Map;

import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.Vertex;

import java.util.HashMap;

public class BFSqueue<V> {
    public boolean BFS(Graph<V, Integer> graph, Vertex<V> src, Vertex<V> dest, 
            Map<Vertex<V>, Vertex<V>> pred,  Map<Vertex<V>, Integer> dist) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Vertex<V>> queue = new LinkedList<>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        Map<Vertex<V>, Boolean> visited = new HashMap<>();

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (Vertex<V> v : graph.vertices()) {
            visited.put(v, false);
            dist.put(v, Integer.MAX_VALUE);
            pred.put(v, null);
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited.put(src, true);
        dist.put(src, 0);
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            Vertex<V> u = queue.remove();

            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<V> vert = graph.opposite(u, e);
                if (Boolean.FALSE.equals(visited.get(vert))) {
                    visited.put(vert, true);
                    dist.put(vert, dist.get(u) + 1);
                    pred.put(vert, u);
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

    public void printShortestDistance(Graph<V, Integer> graph, Vertex<V> src, 
            Vertex<V> dest) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        Map<Vertex<V>, Vertex<V>> pred = new HashMap<>();
        Map<Vertex<V>, Integer> dist = new HashMap<>();

        if (!BFS(graph, src, dest, pred, dist)) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<V> path = new LinkedList<>();
        Vertex<V> crawl = dest;
        path.add(crawl.getElement());
        while (pred.get(crawl) != null) {
            path.add(pred.get(crawl).getElement());
            crawl = pred.get(crawl);
        }

        // Print distance
        System.out.println("Shortest path length is: " + dist.get(dest));

        // Print path
        System.out.println("Path is :");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
    }
}
