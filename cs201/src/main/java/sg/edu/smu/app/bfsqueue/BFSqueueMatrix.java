package sg.edu.smu.app.bfsqueue;

import java.util.LinkedList;
import java.util.Arrays;

public class BFSqueueMatrix {
    private boolean[][] graph;
    private int v;

    public BFSqueueMatrix(boolean[][] graph, int v) {
        this.graph = graph;
        this.v = v;
    }

    private boolean bfs(int src, int dest, int[] pred, int[] dist) {
        // Create queue
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(src);

        boolean[] visited = new boolean[v];
        visited[src] = true;

        Arrays.fill(pred, -1);
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // BFS until queue is empty
        while (!queue.isEmpty()) {
            // Pop a node from the queue for search
            int current = queue.remove();
            // Loop through neighbors nodes to find the 'dest' node
            for (int i = 0; i < v; i++) {
                if (!visited[i] && graph[current][i]) {
                    visited[i] = true;
                    dist[i] = dist[current] + 1;
                    queue.add(i);
                    // update its preceding node
                    pred[i] = current;
                    // end BFS if the dest node is found
                    if (i == dest)
                        return true;
                }
            }
        }
        return false;
    }

    // Function to trace route using preceding nodes
    public void printShortestPath(int src, int dest) {
        int[] pred = new int[v];
        int[] dist = new int[v];
        if (!bfs(src, dest, pred, dist)) {
            System.out.println("Given source and destination are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int walk = dest;
        while (walk != -1) {
            path.addFirst(walk);
            walk = pred[walk];
        }

        // Print distance
        System.out.println("Shortest path length is: " + dist[dest]);

        // Print path
        System.out.println("Path is :");

        while (!path.isEmpty())
            System.out.print(path.remove() + " ");
        System.out.println();
    }

    // Function to trace route using preceding nodes
    public void calShortestPath(int src, int dest) {
        int[] pred = new int[v];
        int[] dist = new int[v];
        if (!bfs(src, dest, pred, dist)) {
            System.out.println("Given source and destination are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int walk = dest;
        while (walk != -1) {
            path.addFirst(walk);
            walk = pred[walk];
        }
        System.out.println("Shortest path length is: " + dist[dest]);
    }
}
