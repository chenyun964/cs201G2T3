package sg.edu.smu.app.dijkstra;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.datastructures.Graph;

public class DijkstraMap {
    private Graph<Integer, Integer> graph;
    private int numVertices;

    public DijkstraMap(Graph<Integer, Integer> graph) {
        this.graph = graph;
        this.numVertices = graph.numVertices();
    }

    class CustomNode implements Comparator<CustomNode> {
        // Member variables of this class
        public Vertex<Integer> node;
        public int cost;
        public CustomNode prev, next;
        public CustomNode prevMin;

        public CustomNode() {
        }

        public CustomNode(Vertex<Integer> node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        public CustomNode getPrevMin() {
            return prevMin;
        }

        public void setPrevMin(CustomNode node) {
            this.prevMin = node;
        }

        @Override
        public int compare(CustomNode node1, CustomNode node2) {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }

        public CustomNode getNext() {
            return next;
        }

        public CustomNode getPrev() {
            return prev;
        }

        public void setNext(CustomNode node) {
            this.next = node;
        }

        public void setPrev(CustomNode node) {
            this.prev = node;
        }
    }

    // Function implementing Dijkstra's Algorithm
    public boolean dijkstra(Vertex<Integer> src, Vertex<Integer> dest, int[] dist, int[] pred) {
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }
        dist[src.getElement()] = 0;

        PriorityQueue<CustomNode> pq = new PriorityQueue<>(numVertices, new CustomNode());
        // add src vertex to the queue
        pq.add(new CustomNode(src, 0));

        while (!pq.isEmpty()) {
            Vertex<Integer> u = pq.remove().node;
            if (u == dest)
                return true;
            for (Edge<Integer> e : graph.outgoingEdges(u)) {
                Vertex<Integer> v = graph.opposite(u, e);
                int id = v.getElement();
                if (!visited[id] && dist[u.getElement()] + 1 < dist[id]) {
                    dist[id] = dist[u.getElement()] + 1;
                    pred[id] = u.getElement();
                    visited[id] = true;
                    pq.add(new CustomNode(v, dist[id]));
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

    public void calShortestDistance(Vertex<Integer> src, Vertex<Integer> dest) {
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
    }
}
