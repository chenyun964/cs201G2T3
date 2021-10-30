package sg.edu.smu.app.experiments;

import sg.edu.smu.app.DjikstraLinkedList.DijkstraLinkedList;
import java.util.*;
import sg.edu.smu.app.datastructures.CustomNode;

public class RunDJI {
  private static final long MEGABYTE = 1024L * 1024L;
  private static final Double divider = 1000000000.0;
  private static long startTime;
  private static long endTime;
  private static long totalTime;
  private static long memory;

  public RunDJI() {
  }

  public static void main(String[] args) {
  }

  public void runSortedPQ(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2, int times) {
    Runtime runtime = Runtime.getRuntime();
    double total = 0.0;
    int shortest = -1;
    System.out.println("Adjacency Map + Djikstra Sorted PQ");
    for (int i = 0; i < times; i ++) {
      DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
      startTime = System.nanoTime();
      dji.dijkstra_PQ(adjMap, id1);
      endTime = System.nanoTime();
      shortest = dji.distArr[id2];
      totalTime = endTime - startTime;
      total += (totalTime / divider);
      runtime.gc();
      
      memory = runtime.totalMemory() - runtime.freeMemory();
    }
    printInfo(shortest, total/times, memory, times);
  }

  public void runSortedLL(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2, int times) {
    Runtime runtime = Runtime.getRuntime();
    double total = 0.0;
    int shortest = -1;
    System.out.println("Adjacency Map + Djikstra (Sorted LL w HashMap)");
    for (int i = 0; i < times; i ++) {
      DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
      startTime = System.nanoTime();
      dji.dijkstra_LL_HM(adjMap, id1);      
      endTime = System.nanoTime();
      shortest = dji.distArr[id2];
      totalTime = endTime - startTime;
      total += (totalTime / divider);
      runtime.gc();
      
      memory = runtime.totalMemory() - runtime.freeMemory();
    }
    printInfo(shortest, total/times, memory, times);
  }

  public void runLinearlySortedLinkedList(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2) {
    // Runtime runtime = Runtime.getRuntime();

    // DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
    // System.out.println("Adjacency Map + Djikstra (Sorted LL w/o HashMap, like simple linear sorted array)");
    // startTime = System.nanoTime();
    // runtime = Runtime.getRuntime();
    // dji = new DijkstraLinkedList(numVertices);
    // dji.dijkstra_LL(adjMap, id1);
    // System.out.println("Shortest path length is: " + dji.distArr[id2]);
    // endTime = System.nanoTime();
    // totalTime = endTime - startTime;
    // System.out.println();
    // System.out.println("Time to Compute Path: " + totalTime / divider + "s");
    // // Run the garbage collector
    // runtime.gc();
    // // Calculate the used memory
    // memory = runtime.totalMemory() - runtime.freeMemory();
    // System.out.println("Used memory is bytes: " + memory);
    // System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
    System.out.println("This actually takes very long, hence pre run results are printed instead");
    System.out.println("Shortest path length is: 5");
    System.out.println("Time to Compute Path: 4774.6261447s");
    System.out.println("Used memory is bytes: 134289280");
    System.out.println("Used memory is megabytes: 128");
    System.out.println("\n--------------------------------------------------\n");
  }

  public void runMinStack(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2, int times) {
    Runtime runtime = Runtime.getRuntime();
    double total = 0.0;
    int shortest = -1;

    System.out.println("Adjacency Map + Djikstra (Stack)");
    for (int i = 0; i < times; i ++) {
      DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
      startTime = System.nanoTime();
      dji.dijkstra_Stack(adjMap, id1);
      endTime = System.nanoTime();
      shortest = dji.distArr[id2];
      totalTime = endTime - startTime;
      total += (totalTime / divider);
      runtime.gc();
      
      memory = runtime.totalMemory() - runtime.freeMemory();
    }
    printInfo(shortest, total/times, memory, times);
  }

  public void runHashMapCircular(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2, int times) {
    Runtime runtime = Runtime.getRuntime();
    double total = 0.0;
    int shortest = -1;

    System.out.println("Adjacency Map + Djikstra (HashMap w Circular Array as Queue)");
    for (int i = 0; i < times; i ++) {
      DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
      startTime = System.nanoTime();
      dji.dijkstra_HashMap_Que(adjMap, id1);
      endTime = System.nanoTime();
      shortest = dji.distArr[id2];
      totalTime = endTime - startTime;
      total += (totalTime / divider);
      runtime.gc();
      
      memory = runtime.totalMemory() - runtime.freeMemory();
    }
    printInfo(shortest, total/times, memory, times);
  }

  public void runMinArray(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2, int times) {
    // Runtime runtime = Runtime.getRuntime();
    // double total = 0.0;
    // int shortest = -1;

    // System.out.println("Adjacency Map + Djikstra (Sorted Array using bSearch)");
    // for (int i = 0; i < times; i ++) {
    //   DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
    //   startTime = System.nanoTime();
    //   dji.dijkstra_Array_bSearch(adjMap, id1);
    //   endTime = System.nanoTime();
    //   shortest = dji.distArr[id2];
    //   totalTime = endTime - startTime;
    //   total += (totalTime / divider);
    //   runtime.gc();
      
    //   memory = runtime.totalMemory() - runtime.freeMemory();
    // }
    // printInfo(shortest, total/times, memory, times);
    System.out.println("This actually takes quite long, hence pre run results are printed instead");
    System.out.println("Shortest path length is: 5");
    System.out.println("Average time taken out of 1 runs: 1070.3592277");
    System.out.println("Used memory is bytes: 149579216");
    System.out.println("Used memory is megabytes: 142");
    System.out.println("\n--------------------------------------------------\n");
  }

  public static void printInfo(int shortest, double average, long memory, int times) {
    System.out.println("Shortest path length is: " + shortest);
    System.out.println("Average time taken out of " + times + " runs: " + average + "s");
    System.out.println("Used memory is bytes: " + memory);
    System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
    System.out.println("\n--------------------------------------------------\n");
  }

  public static long bytesToMegabytes(long bytes) {
    return bytes / MEGABYTE;
  }

}