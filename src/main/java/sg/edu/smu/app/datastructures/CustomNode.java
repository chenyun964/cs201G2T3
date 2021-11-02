package sg.edu.smu.app.datastructures;

/**
 * This file contains the Vertex used in Dijkstra LL + HM etc... experiments
 */

import java.util.Comparator;

public class CustomNode implements Comparator<CustomNode> {
  
  // Member variables of this class
  public int node, cost;
  public CustomNode prev, next;
  public CustomNode prevMin;

  public CustomNode() {
  }

  public CustomNode(int node, int cost) {
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

