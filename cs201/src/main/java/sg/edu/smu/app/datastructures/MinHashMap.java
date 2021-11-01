package sg.edu.smu.app.datastructures;

/**
 * This file contains the HM + CA data structure used in Dijkstra HM + CA implementation
 */

import java.util.*;

public class MinHashMap {
  
  public HashMap<Integer, CircularArray> minMap = new HashMap<Integer, CircularArray>();
  public int currentMin;
  
  public MinHashMap() {
    this.currentMin = Integer.MAX_VALUE;
  }

  public static void main(String[] args) {
    
  }
  
  public void add(CustomNode node) {
    currentMin = Math.min(node.cost, currentMin);
    if (!this.minMap.containsKey(node.cost)) {
      CircularArray queue = new CircularArray();
      queue.add(node);
      this.minMap.put(node.cost, queue);
    }
    else {
      this.minMap.get(node.cost).add(node);
    }    
  }

  public CustomNode removeMin() {
    CircularArray queue = minMap.get(currentMin);
    CustomNode node = queue.remove();

    if (queue.isEmpty()) {
      minMap.remove(currentMin); // current smallest key is gone
      currentMin = Integer.MAX_VALUE;
      for (Integer key: minMap.keySet()) { // update current Min
        currentMin = Math.min(currentMin, key);
      }
    }
    return node;
  }

  public boolean isEmpty() {
    return minMap.size() == 0;
  }

  public static void test() {
    MinHashMap minHashMap = new MinHashMap();

    CustomNode CustomNode2 = new CustomNode(1, 2);
    minHashMap.add(CustomNode2);

    CustomNode CustomNode5 = new CustomNode(2, 1);
    minHashMap.add(CustomNode5);

    CustomNode CustomNode6 = new CustomNode(3, 5);
    minHashMap.add(CustomNode6);

    CustomNode CustomNode7 = new CustomNode(4, 3);
    minHashMap.add(CustomNode7);

    CustomNode CustomNode8 = new CustomNode(5, 4);
    minHashMap.add(CustomNode8);

    CustomNode CustomNode9 = new CustomNode(6, 1);
    minHashMap.add(CustomNode9);

    CustomNode CustomNode10 = new CustomNode(7, 5);
    minHashMap.add(CustomNode10);

    CustomNode CustomNode15 = new CustomNode(8, 2);
    minHashMap.add(CustomNode15);

    CustomNode CustomNode20 = new CustomNode(9, 0);
    minHashMap.add(CustomNode20);

    CustomNode CustomNode19 = new CustomNode(10, 1);
    minHashMap.add(CustomNode19);

    CustomNode CustomNode22 = new CustomNode(11, 0);
    minHashMap.add(CustomNode22);

    CustomNode CustomNode30 = new CustomNode(12, 5);
    minHashMap.add(CustomNode30);

    CustomNode CustomNode31 = new CustomNode(13, 1);
    minHashMap.add(CustomNode31);

    CustomNode CustomNode32 = new CustomNode(14, 9);
    minHashMap.add(CustomNode32);

    CustomNode CustomNode123 = new CustomNode(15, 1);
    minHashMap.add(CustomNode123);

    CustomNode CustomNode124 = new CustomNode(16, 5);
    minHashMap.add(CustomNode124);

    CustomNode CustomNode125 = new CustomNode(17, 2);
    minHashMap.add(CustomNode125);

    CustomNode CustomNode126 = new CustomNode(18, 9);
    minHashMap.add(CustomNode126);

    CustomNode CustomNode127 = new CustomNode(19, 0);
    minHashMap.add(CustomNode127);
    
    while (!minHashMap.isEmpty()) {
      CustomNode current = minHashMap.removeMin();
      System.out.println(
          "Removed Node: " + current.node + " with cost: " + current.cost);
    }
  }
}