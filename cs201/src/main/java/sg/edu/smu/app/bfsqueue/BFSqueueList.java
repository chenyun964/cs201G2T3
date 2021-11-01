package sg.edu.smu.app.bfsqueue;

import java.util.LinkedList;
import java.util.List;

public class BFSqueueList {

    List<List<Integer>> graph;

    public BFSqueueList(List<List<Integer>> graph) {
        this.graph = graph;
    }

    public boolean bfs(int s, int d, int[] pred, int[] dist) {
        boolean[] visited = new boolean[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        visited[s] = true;
        queue.add(s);
        dist[s] = 0;
        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            s = queue.poll();
            // Get all graphacent vertices of the dequeued vertex s
            // If a graphacent has not been visited, then mark it
            // visited and enqueue it
            for (Integer v : graph.get(s)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue.add(v);
                    dist[v] = dist[s] + 1;
                    pred[v] = s;
                }
                if (v == d) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printShortestDistance(int src, int dest) {
        int[] pred = new int[graph.size()];
        int[] dist = new int[graph.size()];
        if (!bfs(src, dest, pred, dist)) {
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
        int[] pred = new int[graph.size()];
        int[] dist = new int[graph.size()];
        if (!bfs(src, dest, pred, dist)) {
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
