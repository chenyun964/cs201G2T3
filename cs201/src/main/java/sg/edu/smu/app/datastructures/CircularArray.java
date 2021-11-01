package sg.edu.smu.app.datastructures;

/**
 * This file contains the Circular Array used in the Dijkstra HM + CA implementation
 */

public class CircularArray {

  public CustomNode[] array;
  public int front, back, maxSize, currentSize;

  public CircularArray() {
    front = back = 0;
    currentSize = 0;
    maxSize = 10;
    array = new CustomNode[maxSize];
  }

  public static void main(String[] args) {
    
  }

  public void add(CustomNode node) {
    if (currentSize == maxSize) {
      resize();
      back = currentSize;
    }
    array[back] = node;
    back = (back + 1)%maxSize;
    currentSize++;
  }

  public void resize() {
    // update maxSize
    maxSize = (int)(maxSize*1.75);
    CustomNode[] newArr = new CustomNode[maxSize];
    for (int i = 0; i < array.length; i++) {
      newArr[i] = array[i];
    }
    array = newArr;
    newArr = null;
  }

  public CustomNode remove() {
    CustomNode node = array[front];
    front = (front + 1)%maxSize;
    currentSize --;
    return node;
  }

  public boolean isEmpty() {
    return currentSize == 0;
  }

  public static void test() {
    CircularArray arr = new CircularArray();

    CustomNode CustomNode2 = new CustomNode(1, 2);
    arr.add(CustomNode2);
    CustomNode CustomNode5 = new CustomNode(2, 1);
    arr.add(CustomNode5);
    CustomNode CustomNode6 = new CustomNode(3, 5);
    arr.add(CustomNode6);
    CustomNode CustomNode7 = new CustomNode(4, 3);
    arr.add(CustomNode7);
    CustomNode CustomNode8 = new CustomNode(5, 4);
    arr.add(CustomNode8);
    CustomNode CustomNode9 = new CustomNode(6, 1);
    arr.add(CustomNode9);
    CustomNode CustomNode10 = new CustomNode(7, 5);
    arr.add(CustomNode10);
    CustomNode CustomNode15 = new CustomNode(8, 2);
    arr.add(CustomNode15);
    CustomNode CustomNode20 = new CustomNode(9, 0);
    arr.add(CustomNode20);

    // int count = 0;
    // while (count != 5) {
    //   CustomNode current = arr.remove();
    //   System.out.println(
    //       "Removed Node: " + current.node + " with cost: " + current.cost );
    //   System.out.println("Current size" + arr.currentSize);
    //   System.out.println("Current Front: " + arr.front);
    //   System.out.println("Current back: " + arr.back);
    //   count ++;
    // }


    CustomNode CustomNode19 = new CustomNode(10, 1);
    arr.add(CustomNode19);
    CustomNode CustomNode22 = new CustomNode(11, 0);
    arr.add(CustomNode22);
    CustomNode CustomNode23 = new CustomNode(12, 0);
    arr.add(CustomNode23);
    CustomNode CustomNode30 = new CustomNode(13, 5);
    arr.add(CustomNode30);
    CustomNode CustomNode31 = new CustomNode(14, 1);
    arr.add(CustomNode31);
    CustomNode CustomNode32 = new CustomNode(15, 9);
    arr.add(CustomNode32);
    CustomNode CustomNode33 = new CustomNode(16, 9);
    arr.add(CustomNode33);

    int count = 0;
    while (!arr.isEmpty()) {
      CustomNode current = arr.remove();
      System.out.println();
      System.out.println("Removed Node: " + current.node + " with cost: " + current.cost );
      System.out.println("Current size" + arr.currentSize);
      System.out.println("Current Front: " + arr.front);
      System.out.println("Current back: " + arr.back);
      System.out.println("Total length: " + arr.array.length);
      System.out.println("Current size: " + arr.currentSize);
      System.out.println();
      CustomNode n = new CustomNode(16, 9);
      arr.add(n);
      count ++;
      if (count == 101) break;
    }

    while (!arr.isEmpty()) {
      CustomNode current = arr.remove();
      System.out.println();
      System.out.println("Removed Node: " + current.node + " with cost: " + current.cost );
      System.out.println("Current size" + arr.currentSize);
      System.out.println("Current Front: " + arr.front);
      System.out.println("Current back: " + arr.back);
      System.out.println("Total length: " + arr.array.length);
      System.out.println("Current size: " + arr.currentSize);
      System.out.println();
    }
  }
}
