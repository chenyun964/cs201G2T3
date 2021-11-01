package sg.edu.smu.app.experiments;

import java.util.*;
import sg.edu.smu.app.dijkstra.DijkstraMap;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.bfsqueue.BFSqueueList;
import sg.edu.smu.app.bfsqueue.BFSqueueMap;
import sg.edu.smu.app.bfsqueue.BFSqueueMatrix;
import sg.edu.smu.app.dijkstra.DijkstraList;
import sg.edu.smu.app.GraphAjdacencyMatrix;

public class RunInput {
    private static final long MEGABYTE = 1024L * 1024L;
    private static final Double divider = 1000000000.0;
    private static long startTime;
    private static long endTime;
    private static long totalTime;
    private static long memory;
    private static Runtime runtime;
    HashMap<Integer, String> mapList;
    HashMap<String, Vertex<Integer>> verts;

    public RunInput(HashMap<String, Vertex<Integer>> verts, HashMap<Integer, String> mapList) {
        this.verts = verts;
        this.mapList = mapList;
    }

    public void runMapDjikstra(Graph<Integer, Integer> g, int times) {
        runtime = Runtime.getRuntime();
        System.out.println("Adjacency Map + Djikstra PQ");
        double total = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            Vertex<Integer> dest = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            System.out.println();
            System.out.println("Form：" + src.getElement());
            System.out.println("To: " + dest.getElement());

            runtime = Runtime.getRuntime();
            startTime = System.nanoTime();
            DijkstraMap dijMap = new DijkstraMap(g);
            dijMap.printShortestDistance(src, dest);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            total += (totalTime / divider);
            runtime.gc();
            memory = runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, memory, times);
    }

    public void runMapBFS(Graph<Integer, Integer> g, int times) {
        runtime = Runtime.getRuntime();
        System.out.println("Adjacency Map + BFS Queue");
        double total = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            Vertex<Integer> dest = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            System.out.println();
            System.out.println("Form：" + src.getElement());
            System.out.println("To: " + dest.getElement());

            startTime = System.nanoTime();
            BFSqueueMap bfs = new BFSqueueMap(g);
            bfs.printShortestPath(src, dest);
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            total += (totalTime / divider);
            runtime.gc();
            memory = runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, memory, times);
    }

    public void runListDjikstra(List<List<Integer>> g, int times) {
        runtime = Runtime.getRuntime();
        System.out.println("Adjacency List + Dijkstra PQ");
        double total = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            Vertex<Integer> dest = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            System.out.println();
            System.out.println("Form：" + src.getElement());
            System.out.println("To: " + dest.getElement());

            startTime = System.nanoTime();
            DijkstraList dijList = new DijkstraList(g);
            dijList.printShortestDistance(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            total += (totalTime / divider);
            runtime.gc();
            memory = runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, memory, times);
    }

    public void runListBFS(List<List<Integer>> g, int times) {
        runtime = Runtime.getRuntime();
        System.out.println("Adjacency List + BFS Queue");
        double total = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            Vertex<Integer> dest = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            System.out.println();
            System.out.println("Form：" + src.getElement());
            System.out.println("To: " + dest.getElement());

            runtime = Runtime.getRuntime();
            startTime = System.nanoTime();
            BFSqueueList bfsList = new BFSqueueList(g);
            bfsList.printShortestDistance(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            total += (totalTime / divider);
            runtime.gc();
            memory = runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, memory, times);
    }

    public void runMatrixDjikstra(GraphAjdacencyMatrix g, int times) {
        runtime = Runtime.getRuntime();
        System.out.println("Adjacency Matrix + Dijkstra PQ");
        double total = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            Vertex<Integer> dest = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            System.out.println();
            System.out.println("Form：" + src.getElement());
            System.out.println("To: " + dest.getElement());

            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            BFSqueueMatrix dijMatrix = new BFSqueueMatrix(g.matrix, g.vertex);
            dijMatrix.printShortestPath(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            total += (totalTime / divider);
            runtime.gc();
            memory = runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, memory, times);
    }

    public void runMatrixBFS(GraphAjdacencyMatrix g, int times) {
        runtime = Runtime.getRuntime();
        System.out.println("Adjacency Matrix + BFS Queue");
        double total = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            Vertex<Integer> dest = verts.get(mapList.get(new Random().nextInt(mapList.size())));
            System.out.println();
            System.out.println("Form：" + src.getElement());
            System.out.println("To: " + dest.getElement());

            BFSqueueMatrix bfsMatrix = new BFSqueueMatrix(g.matrix, g.vertex);
            startTime = System.nanoTime();
            bfsMatrix.printShortestPath(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            total += (totalTime / divider);
            runtime.gc();
            memory = runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, memory, times);
    }

    public static void printInfo(double average, long memory, int times) {
        System.out.println("Average time taken out of " + times + " runs: " + average + "s");
        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory) + "MB");
        System.out.println("\n--------------------------------------------------\n");
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

}