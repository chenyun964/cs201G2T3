package sg.edu.smu.app.datastructures;

/**
 * This file contains the data structure used for the LL only experiment
 */

public class SortedLinkedListWithoutHashMap {
  private CustomNode header, trailer;
  private int size = 0;

  public SortedLinkedListWithoutHashMap() {
    header = new CustomNode(Integer.MAX_VALUE - 1, 0);
    trailer = new CustomNode(Integer.MAX_VALUE, Integer.MAX_VALUE);
    trailer.setPrev(header);
    header.setNext(trailer);
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public void add(CustomNode node) {
    CustomNode current = header.getNext();
    while (node.cost >= current.cost)
      current = current.getNext();
    current.getPrev().setNext(node);
    node.setPrev(current.getPrev());
    current.setPrev(node);
    node.setNext(current);
    this.size++;
  }

  /**
   * Get size of linkedlist
   * 
   * @return Count
   */
  public int size() {
    return this.size;
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
}

