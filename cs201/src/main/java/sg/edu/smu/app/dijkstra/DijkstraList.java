package sg.edu.smu.app.dijkstra;

import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;

import sg.edu.smu.app.datastructures.CustomNode;

public class DijkstraList {
    List<List<Integer>> graph;
    int v;

    public DijkstraList(List<List<Integer>> graph, int v) {
        this.graph = graph;
        this.v = v;
    }

    // Function implementing Dijkstra's Algorithm
    public boolean dijkstra(int src, int dest, int[] dist, int[] pred) {
        boolean[] visited = new boolean[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        dist[src] = 0;

        PriorityQueue<CustomNode> pq = new PriorityQueue<>(v, new CustomNode());
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
        int[] pred = new int[graph.size()];
        int[] dist = new int[graph.size()];
        if (!dijkstra(src, dest, dist, pred)) {
            System.out.println("Given source and destination" + "are not connected");
            return;
        }
        LinkedList<Integer> path = new LinkedList<>();
        Integer c = dest;
        path.addFirst(c);
        while (c != -1) {
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
}
