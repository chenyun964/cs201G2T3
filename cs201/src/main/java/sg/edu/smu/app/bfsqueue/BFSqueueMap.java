package sg.edu.smu.app.bfsqueue;

import java.util.LinkedList;
import java.util.Arrays;

import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.Vertex;

public class BFSqueueMap {
    private Graph<Integer, Integer> graph;
    private int numVertices;

    public BFSqueueMap(Graph<Integer, Integer> graph) {
        this.graph = graph;
        this.numVertices = graph.numVertices();
    }

    private boolean bfs(Vertex<Integer> src, Vertex<Integer> dest, int[] pred, int[] dist) {
        LinkedList<Vertex<Integer>> queue = new LinkedList<>();
        queue.add(src);

        boolean[] visited = new boolean[numVertices];
        visited[src.getElement()] = true;

        Arrays.fill(pred, -1);
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src.getElement()] = 0;

        while (!queue.isEmpty()) {
            Vertex<Integer> u = queue.remove();

            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<Integer> v = graph.opposite(u, e);
                Integer id = v.getElement();
                if (!visited[id]) {
                    visited[id] = true;
                    dist[id] = dist[u.getElement()] + 1;
                    pred[id] = u.getElement();
                    queue.add(v);
                    if (v == dest)
                        return true;
                }
            }
        }
        return false;
    }

    public void printShortestPath(Vertex<Integer> src, Vertex<Integer> dest) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int[] pred = new int[numVertices];
        int[] dist = new int[numVertices];

        if (!bfs(src, dest, pred, dist)) {
            System.out.println("Given source and destination are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = dest.getElement();
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.addFirst(pred[crawl]);
            crawl = pred[crawl];
        }

        // Print distance
        System.out.println("Shortest path length is: " + dist[dest.getElement()]);

        // Print path
        System.out.println("Path is :");
        for (int i = 0; i < path.size(); i++)
            System.out.print(path.get(i) + " ");
        System.out.println();
    }

    public void calShortestPath(Vertex<Integer> src, Vertex<Integer> dest) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int[] pred = new int[numVertices];
        int[] dist = new int[numVertices];

        if (!bfs(src, dest, pred, dist)) {
            System.out.println("Given source and destination are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<>();
        int crawl = dest.getElement();
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.addFirst(pred[crawl]);
            crawl = pred[crawl];
        }
        System.out.println("Shortest path length is: " + dist[dest.getElement()]);
    }
}
