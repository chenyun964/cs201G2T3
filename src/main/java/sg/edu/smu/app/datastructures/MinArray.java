package sg.edu.smu.app.datastructures;

/**
 * This file contains the Binary Searched Array
 */

import java.util.*;

public class MinArray {

  private ArrayList<CustomNode> list;

  public static void main(String[] args) {
    // test();
  }

  public MinArray() {
    list = new ArrayList<CustomNode>();
  }

  public void add(CustomNode node) {
    int pos = binarySearch(node.cost);
    list.add(pos, node);
  }

  public CustomNode removeMin() {
    CustomNode node = list.get(0);
    list.remove(node);
    return node;
  }

  public boolean isEmpty() {
    return list.size() == 0;
  }
  
  public int binarySearch(int x) {
    int left = 0, right = list.size() - 1;
    int mid = -1;
    while (left <= right) {
      mid = left + (right - left) / 2;
      
      // Check if x is present at mid
      if (list.get(mid).cost == x) {
        // probe until end of same value
        if (mid == list.size() - 1)
          return mid;
        mid++;
        while (list.get(mid).cost == x && mid != list.size() - 1)
          mid++;
        return mid;
      }

      // If x greater, ignore left half
      if (list.get(mid).cost < x)
        left = mid + 1;

      // If x is smaller, ignore right half
      else
        right = mid - 1;
    }

    // if we reach here, then element was
    // not present
    // System.out.println("inserting at: " + mid);
    if (mid < 0) return 0;
    if (mid + 1 == (list.size())) { // at edge
      if (x > list.get(mid).cost) return mid + 1;// larger than last value
      if (x < list.get(mid).cost) return mid;
    }
    // somewhere in middle
    if (x < list.get(mid).cost) return mid; // less than value at current spot
    if (x > list.get(mid).cost) return mid + 1;
    // at front
    return 0;
  }


  public static void test() {
    MinArray minArray = new MinArray();

    CustomNode CustomNode2 = new CustomNode(1, 0);
    minArray.add(CustomNode2);

    CustomNode CustomNode5 = new CustomNode(2, 1);
    minArray.add(CustomNode5);

    CustomNode CustomNode6 = new CustomNode(3, 7);
    minArray.add(CustomNode6);

    CustomNode CustomNode7 = new CustomNode(4, 8);
    minArray.add(CustomNode7);

    CustomNode CustomNode8 = new CustomNode(5, 6);
    minArray.add(CustomNode8);

    CustomNode CustomNode9 = new CustomNode(6, 5);
    minArray.add(CustomNode9);

    CustomNode CustomNode10 = new CustomNode(7, 10);
    minArray.add(CustomNode10);

    CustomNode CustomNode15 = new CustomNode(8, 9);
    minArray.add(CustomNode15);

    CustomNode CustomNode20 = new CustomNode(9, 12);
    minArray.add(CustomNode20);

    CustomNode CustomNode19 = new CustomNode(10, 13);
    minArray.add(CustomNode19);

    CustomNode CustomNode22 = new CustomNode(11, 11);
    minArray.add(CustomNode22);

    CustomNode CustomNode30 = new CustomNode(12, 15);
    minArray.add(CustomNode30);

    CustomNode CustomNode31 = new CustomNode(13, 14);
    minArray.add(CustomNode31);

    CustomNode CustomNode32 = new CustomNode(14, 16);
    minArray.add(CustomNode32);

    CustomNode CustomNode123 = new CustomNode(15, 15);
    minArray.add(CustomNode123);

    CustomNode CustomNode124 = new CustomNode(16, 5);
    minArray.add(CustomNode124);

    CustomNode CustomNode125 = new CustomNode(17, 2);
    minArray.add(CustomNode125);

    CustomNode CustomNode126 = new CustomNode(18, 9);
    minArray.add(CustomNode126);

    CustomNode CustomNode127 = new CustomNode(19, 0);
    minArray.add(CustomNode127);
    
    while (!minArray.isEmpty()) {
      CustomNode current = minArray.removeMin();
      System.out.println(
          "Removed Node: " + current.node + " with cost: " + current.cost);
    }
  }
}

