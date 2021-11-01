package sg.edu.smu.app.datastructures;

/**
 * This file contains the LL + HM implementation used in the Dijkstra LL + HM experiment
 */

import java.util.*;

public class SortedLinkedList {
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
