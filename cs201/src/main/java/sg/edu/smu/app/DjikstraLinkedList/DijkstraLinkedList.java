package sg.edu.smu.app.DjikstraLinkedList;

import java.util.*;

public class DijkstraLinkedList {

  // relaxtion can be up to n^2, as n * n-1

  // loop on each vertex
  // update the weight to each neighbour and total distance
  // non neighbours distance set to infinity

  // each step find shortest path to a neighbour
  // repeat
  
  private int numVertices;
  private Map<Integer, List<Node>> adjList;
  private int distArr[];
  private Set<Integer> visited;
  private PriorityQueue<Node> pq;

  public DijkstraLinkedList(int numVertices) {
    this.numVertices = numVertices;
    this.distArr = new int[numVertices + 1];
    this.visited = new HashSet<Integer>();
    this.pq = new PriorityQueue<Node>(numVertices, new Node());
  }
  
  public static void main(String[] args) {
    //
    DijkstraLinkedList dji = new DijkstraLinkedList(9);
    int source = 1;
    dji.adapter(getAdjList(), source);
    System.out.println("The shorted path from node :");

    for (int i = 0; i < dji.distArr.length; i++)
      System.out.println(source + " to " + i + " is " + dji.distArr[i]);
  }

  public void adapter(Object input, int source) {
    if (input instanceof Map<?, ?>) {
      adjList = (Map<Integer, List<Node>>) input;
      dijkstra(adjList, source);

    } else {
      System.out.println("");
    }
  }

  public void dijkstra(Map<Integer, List<Node>> adj, int source) {
    // init all nodes with distance of infinity first
    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;

    pq.add(new Node(source, 0));
    distArr[source] = 0;

    // loop until all connected vertices are visited
    while (visited.size() != numVertices) {
      // or until no more neighbour
      if (pq.isEmpty())
        return;

      int u = pq.remove().node;

      if (visited.contains(u))
        continue;
      visited.add(u);

      // process neighbours
      int edgeDistance = -1;
      int newDistance = -1;

      for (int i = 0; i < adj.get(u).size(); i++) { // loop through neighbours of u
        Node v = adj.get(u).get(i); // get neighbour

        // If current node hasn't already been processed
        if (!visited.contains(v.node)) {
          edgeDistance = v.cost; // get distance from u to v
          newDistance = distArr[u] + edgeDistance;

          // If new distance is cheaper in cost
          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;

          // Add the current node to the queue
          pq.add(new Node(v.node, distArr[v.node]));
        }
      }

    }

  }

  static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  // Adjacency map
  static Map<Integer, List<Node>> getAdjList() {
    Map<Integer, List<Node>> adjList = new HashMap<Integer, List<Node>>();
    Random rand = new Random();

    List<Node> l1 = new ArrayList<Node>();
    l1.add(new Node(2, getRandomNumber(1,1)));
    l1.add(new Node(3, getRandomNumber(1,1)));
    l1.add(new Node(4, getRandomNumber(1,1)));
    adjList.put(1, l1);

    List<Node> l2 = new ArrayList<Node>();
    l2.add(new Node(5, getRandomNumber(1,1)));
    l2.add(new Node(6, getRandomNumber(1,1)));
    l2.add(new Node(7, getRandomNumber(1,1)));
    adjList.put(2, l2);

    List<Node> l3 = new ArrayList<Node>();
    l3.add(new Node(5, getRandomNumber(1,1)));
    l3.add(new Node(6, getRandomNumber(1,1)));
    l3.add(new Node(7, getRandomNumber(1,1)));
    adjList.put(3, l3);

    List<Node> l4 = new ArrayList<Node>();
    l4.add(new Node(5, getRandomNumber(1,1)));
    l4.add(new Node(6, getRandomNumber(1,1)));
    l4.add(new Node(7, getRandomNumber(1,1)));
    adjList.put(4, l4);

    List<Node> l5 = new ArrayList<Node>();
    l5.add(new Node(8, getRandomNumber(1,1)));
    adjList.put(5, l5);

    List<Node> l6 = new ArrayList<Node>();
    l6.add(new Node(8, getRandomNumber(1,1)));
    adjList.put(6, l6);

    List<Node> l7 = new ArrayList<Node>();
    l7.add(new Node(8, getRandomNumber(1,1)));
    adjList.put(7, l7);

    List<Node> l8 = new ArrayList<Node>();
    adjList.put(8, l8);


    for (Map.Entry<Integer, List<Node>> entry : adjList.entrySet()) {
      System.out.println("Key: " + entry.getKey());
      List<Node> li = entry.getValue();
      for (Node node : li) {
        System.out.print(node.node + " : " + node.cost + " | ");
      }
      System.out.println("");
    }

    return adjList;
  }
}

class Node implements Comparator<Node> {

  // Member variables of this class
  public int node;
  public int cost;

  public Node() {
  }

  public Node(int node, int cost) {
    this.node = node;
    this.cost = cost;
  }

  @Override
  public int compare(Node node1, Node node2) {
    if (node1.cost < node2.cost)
      return -1;

    if (node1.cost > node2.cost)
      return 1;

    return 0;
  }
}
