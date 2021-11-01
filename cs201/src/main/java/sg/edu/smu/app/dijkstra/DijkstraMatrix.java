package sg.edu.smu.app.dijkstra;

import java.util.PriorityQueue;
import java.util.LinkedList;

import sg.edu.smu.app.datastructures.CustomNode;

public class DijkstraMatrix {
    boolean[][] graph;
    int numVertices;

    public DijkstraMatrix(boolean[][] graph) {
        this.graph = graph;
        this.numVertices = graph.length;
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
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && graph[current][i]) {
                    dist[i] = dist[current] + 1;
                    pred[i] = current;
                    visited[i] = true;
                    pq.add(new CustomNode(i, dist[i]));
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
        Integer c = dest;
        path.addFirst(c);
        while (pred[c] != -1) {
            path.addFirst(pred[c]);
            c = pred[c];
        }
        System.out.println("Shortest path length is: " + dist[dest]);
        System.out.println("Path is :");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) + " ");
        }
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
        Integer c = dest;
        path.addFirst(c);
        while (pred[c] != -1) {
            path.addFirst(pred[c]);
            c = pred[c];
        }
        System.out.println("Shortest path length is: " + dist[dest]);
    }
}
