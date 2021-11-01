package sg.edu.smu.app.dijkstra;

import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;

import sg.edu.smu.app.datastructures.CustomNode;

public class DijkstraList {
    List<List<Integer>> graph;
    int numVertices;

    public DijkstraList(List<List<Integer>> graph) {
        this.graph = graph;
        this.numVertices = graph.size();
    }

    // Function implementing Dijkstra's Algorithm
    public boolean dijkstra(int src, int dest, int[] dist, int[] pred) {
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        dist[src] = 0;

        PriorityQueue<CustomNode> pq = new PriorityQueue<>(numVertices, new CustomNode());
        // add src vertex to the queue
        pq.add(new CustomNode(src, 0));

        // run until queue is not empty
        while (!pq.isEmpty()) {
            Integer current = pq.remove().node;
            if (current == dest)
                return true;
            for (Integer v : graph.get(current)) {
                if (!visited[v] && dist[current] + 1 < dist[v]) {
                    dist[v] = dist[current] + 1;
                    pred[v] = current;
                    visited[v] = true;
                    pq.add(new CustomNode(v, dist[v]));
                }
            }
        }
        return false;
    }

    public void printShortestDistance(int src, int dest) {
        int[] pred = new int[numVertices];
        int[] dist = new int[numVertices];
        if (!dijkstra(src, dest, dist, pred)) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        int c = dest;
        path.addFirst(c);
        while (pred[c] != -1) {
            path.addFirst(pred[c]);
            c = pred[c];
        }

        System.out.println("Shortest path length is: " + dist[dest]);
        System.out.println("Path is :");
        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i) + " ");
        System.out.println();
    }

    public void calShortestDistance(int src, int dest) {
        int[] pred = new int[numVertices];
        int[] dist = new int[numVertices];
        if (!dijkstra(src, dest, dist, pred)) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        int c = dest;
        path.addFirst(c);
        while (pred[c] != -1) {
            path.addFirst(pred[c]);
            c = pred[c];
        }
        System.out.println("Shortest path length is: " + dist[dest]);
    }
}
