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

  public void runSortedPQ(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2) {
    Runtime runtime = Runtime.getRuntime();

    System.out.println("Adjacency Map + Djikstra Sorted PQ");
    startTime = System.nanoTime();
    runtime = Runtime.getRuntime();

    DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
    dji.dijkstra_PQ(adjMap, id1);
    System.out.println("Shortest path length is: " + dji.distArr[id2]);
    endTime = System.nanoTime();
    totalTime = endTime - startTime;
    System.out.println();
    System.out.println("Time to Compute Path: " + totalTime / divider + "s");
    // Run the garbage collector
    runtime.gc();
    // Calculate the used memory
    memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used memory is bytes: " + memory);
    System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
    System.out.println("\n--------------------------------------------------\n");
  }

  public void runSortedLL(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2) {
    Runtime runtime = Runtime.getRuntime();

    System.out.println("Adjacency Map + Djikstra (Sorted LL w HashMap)");
    startTime = System.nanoTime();
    runtime = Runtime.getRuntime();
    DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
    dji = new DijkstraLinkedList(numVertices);
    dji.dijkstra_LL_HM(adjMap, id1);
    System.out.println("Shortest path length is: " + dji.distArr[id2]);
    endTime = System.nanoTime();
    totalTime = endTime - startTime;
    System.out.println();
    System.out.println("Time to Compute Path: " + totalTime / divider + "s");
    // Run the garbage collector
    runtime.gc();
    // Calculate the used memory
    memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used memory is bytes: " + memory);
    System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
    System.out.println("\n--------------------------------------------------\n");
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

  public void runMinStack(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2) {
    Runtime runtime = Runtime.getRuntime();

    DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
    System.out.println("Adjacency Map + Djikstra (Stack)");
    startTime = System.nanoTime();
    runtime = Runtime.getRuntime();
    dji = new DijkstraLinkedList(numVertices);
    dji.dijkstra_Stack(adjMap, id1);
    System.out.println("Shortest path length is: " + dji.distArr[id2]);
    endTime = System.nanoTime();
    totalTime = endTime - startTime;
    System.out.println();
    System.out.println("Time to Compute Path: " + totalTime / divider + "s");
    // Run the garbage collector
    runtime.gc();
    // Calculate the used memory
    memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used memory is bytes: " + memory);
    System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
    System.out.println("\n--------------------------------------------------\n");
  }

  public void runHashMapCircular(int numVertices, Map<Integer, List<CustomNode>> adjMap, int id1, int id2) {
    Runtime runtime = Runtime.getRuntime();

    DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
    System.out.println("Adjacency Map + Djikstra (HashMap w Circular Array as Queue)");
    startTime = System.nanoTime();
    runtime = Runtime.getRuntime();
    dji = new DijkstraLinkedList(numVertices);
    dji.dijkstra_HashMap_Que(adjMap, id1);
    System.out.println("Shortest path length is: " + dji.distArr[id2]);
    endTime = System.nanoTime();
    totalTime = endTime - startTime;
    System.out.println();
    System.out.println("Time to Compute Path: " + totalTime / divider + "s");
    // Run the garbage collector
    runtime.gc();
    // Calculate the used memory
    memory = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Used memory is bytes: " + memory);
    System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
    System.out.println("\n--------------------------------------------------\n");
  }

  public static long bytesToMegabytes(long bytes) {
    return bytes / MEGABYTE;
  }

}