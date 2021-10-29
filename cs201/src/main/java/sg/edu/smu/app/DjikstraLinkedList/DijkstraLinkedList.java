package sg.edu.smu.app.DjikstraLinkedList;

import java.util.*;
import sg.edu.smu.app.datastructures.CustomNode;
import sg.edu.smu.app.datastructures.MinNode;
import sg.edu.smu.app.datastructures.OrderedMinStack;
import sg.edu.smu.app.datastructures.SortedLinkedList;
import sg.edu.smu.app.datastructures.SortedLinkedListWithoutHashMap;
import sg.edu.smu.app.datastructures.MinHashMap;

public class DijkstraLinkedList {
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
  }

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

  public void dijkstra_LL_HM(Map<Integer, List<CustomNode>> adj, int source) {
    SortedLinkedList ll = new SortedLinkedList();

    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;

    ll.add(new CustomNode(source, 0));
    distArr[source] = 0;

    while (visited.size() != numVertices) {
      if (ll.isEmpty())
        return;

      int u = ll.removeMin().node;
      if (visited.contains(u))
        continue;
      visited.add(u);

      int edgeDistance = -1;
      int newDistance = -1;

      for (int i = 0; i < adj.get(u).size(); i++) { 
        CustomNode v = adj.get(u).get(i);

        if (!visited.contains(v.node)) {
          edgeDistance = v.cost;
          newDistance = distArr[u] + edgeDistance; 

          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;

          ll.add(new CustomNode(v.node, distArr[v.node]));
        }
      }
    }
  }

  public void dijkstra_LL(Map<Integer, List<CustomNode>> adj, int source) {
    SortedLinkedListWithoutHashMap ll = new SortedLinkedListWithoutHashMap();

    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;

    ll.add(new CustomNode(source, 0));
    distArr[source] = 0;

    while (visited.size() != numVertices) {
      if (ll.isEmpty())
        return;
      int u = ll.removeMin().node;
      if (visited.contains(u))
        continue;
      visited.add(u);

      int edgeDistance = -1;
      int newDistance = -1;

      for (int i = 0; i < adj.get(u).size(); i++) {
        CustomNode v = adj.get(u).get(i);

        if (!visited.contains(v.node)) {
          edgeDistance = v.cost;
          newDistance = distArr[u] + edgeDistance;

          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;
          ll.add(new CustomNode(v.node, distArr[v.node]));
        }
      }
    }
  }
  
  public void dijkstra_Stack(Map<Integer, List<CustomNode>> adj, int source) {
    OrderedMinStack ll = new OrderedMinStack();

    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;
    
    ll.insert(new CustomNode(source, 0));
    distArr[source] = 0;

    while (visited.size() != numVertices) {
      if (ll.currentIsEmpty())
        return;
      int u = ll.removeMin().node;
      if (visited.contains(u))
        continue;
      visited.add(u);

      int edgeDistance = -1;
      int newDistance = -1;

      for (int i = 0; i < adj.get(u).size(); i++) {
        CustomNode v = adj.get(u).get(i);

        if (!visited.contains(v.node)) {
          edgeDistance = v.cost;
          newDistance = distArr[u] + edgeDistance;

          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;
          ll.insert(new CustomNode(v.node, distArr[v.node]));
        }
      }
    }
  }

  public void dijkstra_HashMap_Que(Map<Integer, List<CustomNode>> adj, int source) {
    MinHashMap ll = new MinHashMap();

    for (int i = 0; i < numVertices; i++)
      distArr[i] = Integer.MAX_VALUE;
    
    ll.add(new CustomNode(source, 0));
    distArr[source] = 0;

    while (visited.size() != numVertices) {
      if (ll.isEmpty())
        return;
      int u = ll.removeMin().node;
      if (visited.contains(u))
        continue;
      visited.add(u);

      int edgeDistance = -1;
      int newDistance = -1;

      for (int i = 0; i < adj.get(u).size(); i++) {
        CustomNode v = adj.get(u).get(i);

        if (!visited.contains(v.node)) {
          edgeDistance = v.cost;
          newDistance = distArr[u] + edgeDistance;
          
          if (newDistance < distArr[v.node])
            distArr[v.node] = newDistance;
          ll.add(new CustomNode(v.node, distArr[v.node]));
        }
      }
    }
  }


}