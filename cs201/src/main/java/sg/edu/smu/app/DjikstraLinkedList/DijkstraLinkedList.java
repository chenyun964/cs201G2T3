package sg.edu.smu.app.DjikstraLinkedList;

import java.util.*;
import sg.edu.smu.app.datastructures.CustomNode;

// javac -d ./build DijkstraLinkedList.java 
// java -cp ./build DijkstraLinkedList

public class DijkstraLinkedList {

  // relaxtion can be up to n^2, as n * n-1

  // loop on each vertex
  // update the weight to each neighbour and total distance
  // non neighbours distance set to infinity

  // each step find shortest path to a neighbour
  // repeat

  /**
   * Set all to public cus no encapsulation required
   */
  public int numVertices;
  public Map<Integer, List<CustomNode>> adjList;
  public int distArr[];
  public Set<Integer> visited;
  public PriorityQueue<CustomNode> pq;

  public DijkstraLinkedList(int numVertices, Map<Integer, List<CustomNode>> adjMap) {
    this.numVertices = numVertices;
    this.distArr = new int[numVertices + 1];
    this.visited = new HashSet<Integer>();
    this.pq = new PriorityQueue<CustomNode>(numVertices, new CustomNode());
    this.adjList = adjMap;
  }
  
  public DijkstraLinkedList(int numVertices) {
    this.numVertices = numVertices;
    this.distArr = new int[numVertices + 1];
    this.visited = new HashSet<Integer>();
    this.pq = new PriorityQueue<CustomNode>(numVertices, new CustomNode());
  }

  public static void main(String[] args) {
    //
    DijkstraLinkedList dji = new DijkstraLinkedList(9);
    int source = 1;
    dji.adapter(getAdjList(), source, true);
    System.out.println("The shorted path from node :");

    for (int i = 0; i < dji.distArr.length; i++)
      System.out.println(source + " to " + i + " is " + dji.distArr[i]);

    // System.out.println("Loading");
    // JSONParser parser = new JSONParser();
    // JSONArray users = null;
    // try (Reader reader = new FileReader("100.json")) {
    //   users = (JSONArray) parser.parse(reader);
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }
    // System.out.println(users);
    

  }

  public void adapter(Object input, int source, boolean pq) {
    if (input instanceof Map<?, ?>) {
      adjList = (Map<Integer, List<CustomNode>>) input;
      if (pq) dijkstra_PQ(adjList, source);
      else dijkstra_LL(adjList, source);

    } else {
      System.out.println("");
    }
  }

  /**
   * LAZY COPY AND PASTE SAME CODE FOR PQ AND LL
   * Duplicated code
   * @param adj
   * @param source
   */
  public void dijkstra_PQ(Map<Integer, List<CustomNode>> adj, int source) {
    // init all nodes with distance of infinity first
    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;

    pq.add(new CustomNode(source, 0));
    distArr[source] = 0;

    // loop until all connected vertices are visited
    while (visited.size() != numVertices) {
      // or until no more neighbour
      if (pq.isEmpty())
        return;

      // get the current node which has the shortest cost
      int u = pq.remove().node;

      // if already visited next loop
      if (visited.contains(u))
        continue;
      visited.add(u);

      // process neighbours
      int edgeDistance = -1;
      int newDistance = -1;

      // get the neighbours of the current lowest cost node
      for (int i = 0; i < adj.get(u).size(); i++) { // loop through neighbours of u
        CustomNode v = adj.get(u).get(i); // get neighbour

        // If current node hasn't already been processed
        if (!visited.contains(v.node)) {
          edgeDistance = v.cost; // get distance from u to v
          newDistance = distArr[u] + edgeDistance; // distance from current to neighbour plus neighbour cost

          // If new distance is cheaper in cost
          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;

          // Add the current node to the queue
          pq.add(new CustomNode(v.node, distArr[v.node]));
        }
      }
    }
  }
  
  /**
   * LAZY SO COPY AND PASTE
   * @param adj
   * @param source
   */
  public void dijkstra_LL(Map<Integer, List<CustomNode>> adj, int source) {
    // init all nodes with distance of infinity first

    /**
     * Lazy init so put here
     */
    SortedLinkedList ll = new SortedLinkedList();
    
    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;

    ll.add(new CustomNode(source, 0));
    distArr[source] = 0;

    // loop until all connected vertices are visited
    while (visited.size() != numVertices) {
      // or until no more neighbour
      if (ll.isEmpty())
        return;

      // get the current node which has the shortest cost
      int u = ll.removeMin().node;
      // if already visited next loop
      if (visited.contains(u))
        continue;
      visited.add(u);

      // process neighbours
      int edgeDistance = -1;
      int newDistance = -1;

      // get the neighbours of the current lowest cost node
      for (int i = 0; i < adj.get(u).size(); i++) { // loop through neighbours of u
        CustomNode v = adj.get(u).get(i); // get neighbour

        // If current node hasn't already been processed
        if (!visited.contains(v.node)) {
          edgeDistance = v.cost; // get distance from u to v
          newDistance = distArr[u] + edgeDistance; // distance from current to neighbour plus neighbour cost

          // If new distance is cheaper in cost
          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;

          // Add the current node to the queue
          ll.add(new CustomNode(v.node, distArr[v.node]));
        }
      }
    }
  }

  static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  // Adjacency map
  public static Map<Integer, List<CustomNode>> getAdjList() {
    Map<Integer, List<CustomNode>> adjList = new HashMap<Integer, List<CustomNode>>();

    List<CustomNode> l1 = new ArrayList<CustomNode>();
    l1.add(new CustomNode(2, getRandomNumber(1, 1)));
    l1.add(new CustomNode(3, getRandomNumber(1, 1)));
    l1.add(new CustomNode(4, getRandomNumber(1, 1)));
    adjList.put(1, l1);

    List<CustomNode> l2 = new ArrayList<CustomNode>();
    l2.add(new CustomNode(5, getRandomNumber(1, 1)));
    l2.add(new CustomNode(6, getRandomNumber(1, 1)));
    l2.add(new CustomNode(7, getRandomNumber(1, 1)));
    adjList.put(2, l2);

    List<CustomNode> l3 = new ArrayList<CustomNode>();
    l3.add(new CustomNode(5, getRandomNumber(1, 1)));
    l3.add(new CustomNode(6, getRandomNumber(1, 1)));
    l3.add(new CustomNode(7, getRandomNumber(1, 1)));
    adjList.put(3, l3);

    List<CustomNode> l4 = new ArrayList<CustomNode>();
    l4.add(new CustomNode(5, getRandomNumber(1, 1)));
    l4.add(new CustomNode(6, getRandomNumber(1, 1)));
    l4.add(new CustomNode(7, getRandomNumber(1, 1)));
    adjList.put(4, l4);

    List<CustomNode> l5 = new ArrayList<CustomNode>();
    l5.add(new CustomNode(8, getRandomNumber(1, 1)));
    adjList.put(5, l5);

    List<CustomNode> l6 = new ArrayList<CustomNode>();
    l6.add(new CustomNode(8, getRandomNumber(1, 1)));
    adjList.put(6, l6);

    List<CustomNode> l7 = new ArrayList<CustomNode>();
    l7.add(new CustomNode(8, getRandomNumber(1, 1)));
    adjList.put(7, l7);

    List<CustomNode> l8 = new ArrayList<CustomNode>();
    adjList.put(8, l8);

    SortedLinkedList linkedList = new SortedLinkedList();
    linkedList.add(new CustomNode(1, 1));
    System.out.println(linkedList.toString());
    linkedList.add(new CustomNode(2, 1));
    System.out.println(linkedList.toString());
    linkedList.add(new CustomNode(3, 1));
    System.out.println(linkedList.toString());
    linkedList.add(new CustomNode(4, 2));
    System.out.println(linkedList.toString());
    linkedList.add(new CustomNode(5, 2));
    System.out.println(linkedList.toString());
    linkedList.add(new CustomNode(6, 2));
    System.out.println(linkedList.toString());
    linkedList.add(new CustomNode(7, 3));
    System.out.println(linkedList.toString());
    linkedList.removeMin();
    linkedList.removeMin();
    linkedList.removeMin();
    linkedList.removeMin();
    linkedList.removeMin();
    linkedList.removeMin();
    linkedList.removeMin();
    System.out.println(linkedList.toString());
    System.out.println(linkedList.size());

    return adjList;
  }
}

/**
 * A sorted linkedlist of Nodes
 */
class SortedLinkedList {
  private CustomNode header, trailer;
  private int size = 0;
  private HashMap<Integer, CustomNode> map = new HashMap<Integer, CustomNode>();

  public SortedLinkedList() {
    header = new CustomNode(Integer.MAX_VALUE - 1, 0);
    trailer = new CustomNode(Integer.MAX_VALUE, Integer.MAX_VALUE);
    trailer.setPrev(header);
    header.setNext(trailer);
  }

  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Insert node into LL and sort on insert
   * 
   * We can speed up by tracking largest elem and a pointer to the last smallest
   * elem as well as every step, the last elem of that step
   * 
   * @param node
   */
  public void add(CustomNode node) {
    if (map.containsKey(node.cost)) { // worst case o(n)
      CustomNode current = map.get(node.cost);
      node.setPrev(current);
      node.setNext(current.getNext());
      current.getNext().setPrev(node);
      current.setNext(node);
    } else { // potentially o(n)
      CustomNode current = header.getNext();
      while (node.cost >= current.cost)
        current = current.getNext();
      current.getPrev().setNext(node);
      node.setPrev(current.getPrev());
      current.setPrev(node);
      node.setNext(current);
    }
    this.size++;
    map.put(node.cost, node);
  }

  /**
   * Get size of linkedlist
   * 
   * @return Count
   */
  public int size() {
    return this.size;
  }

  public CustomNode min() {
    if (isEmpty())
      return null;
    return header.getNext();
  }

  public CustomNode removeMin() {
    if (isEmpty())
      return null;
      CustomNode current = header.getNext();
    header.setNext(current.getNext());
    current.getNext().setPrev(header);
    current.setNext(null);
    current.setPrev(null);
    this.size--;
    return current;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    CustomNode current = header;
    while (current != trailer) {
      sb.append(current.node);
      sb.append(" ");
      current = current.getNext();
    }
    return sb.toString();
  }

  public String toString2() {
    StringBuilder sb = new StringBuilder();
    CustomNode current = trailer;
    while (current != header) {
      sb.append(current.node);
      sb.append(" ");
      current = current.getPrev();
    }
    return sb.toString();
  }
}
