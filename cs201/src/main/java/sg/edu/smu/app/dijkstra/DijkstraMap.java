package sg.edu.smu.app.dijkstra;

import java.util.LinkedList;

import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.datastructures.AdaptablePriorityQueue;
import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.HeapAdaptablePriorityQueue;
import sg.edu.smu.app.datastructures.Map;
import sg.edu.smu.app.datastructures.ProbeHashMap;
import sg.edu.smu.app.datastructures.Entry;

public class DijkstraMap {
    private Graph<Integer, Integer> graph;
    private int numVertices;

    public DijkstraMap(Graph<Integer, Integer> graph) {
        this.graph = graph;
        this.numVertices = graph.numVertices();
    }

    // Function implementing Dijkstra's Algorithm
    public boolean dijkstra(Vertex<Integer> src, Vertex<Integer> dest, int[] dist, int[] pred) {
        // map reachable v to its d value
        boolean[] visited = new boolean[numVertices];
        // pq will have vertices as elements, with d.get(v) as key
        AdaptablePriorityQueue<Integer, Vertex<Integer>> pq = new HeapAdaptablePriorityQueue<>();
        // maps from vertex to its pq locator
        Map<Vertex<Integer>, Entry<Integer, Vertex<Integer>>> pqTokens = new ProbeHashMap<>();

        // for each vertex v of the graph, add an entry to the priority queue, with
        // the source having distance 0 and all others having infinite distance
        for (Vertex<Integer> v : graph.vertices()) {
            pred[v.getElement()] = -1;
            if (v == src)
                dist[v.getElement()] = 0;
            else
                dist[v.getElement()] = Integer.MAX_VALUE;
            pqTokens.put(v, pq.insert(dist[v.getElement()], v));
        }

        while (!pq.isEmpty()) {
            Entry<Integer, Vertex<Integer>> entry = pq.removeMin();
            Vertex<Integer> u = entry.getValue();
            pqTokens.remove(u);
            if (u == dest)
                return true;
            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<Integer> v = graph.opposite(u, e);
                int id= v.getElement();
                if (!visited[id] && dist[u.getElement()] + 1 < dist[id]) {
                    dist[id] = dist[u.getElement()] + 1;
                    pred[id] = u.getElement();
                    visited[id] = true;
                    pq.replaceKey(pqTokens.get(v), dist[id]);
                }
            }
        }
        return false;
    }

    public void printShortestDistance(Vertex<Integer> src, Vertex<Integer> dest) {
        int[] pred = new int[numVertices];
        int[] dist = new int[numVertices];
        if (!dijkstra(src, dest, dist, pred)) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        int c = dest.getElement();
        path.addFirst(c);
        while (pred[c] != -1) {
            path.addFirst(pred[c]);
            c = pred[c];
        }
        System.out.println("Shortest path length is: " + dist[dest.getElement()]);
        System.out.println("Path is :");
        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i) + " ");
        System.out.println();
    }
}
