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
    private static Runtime runtime;
    HashMap<Integer, String> mapList;
    HashMap<String, Vertex<Integer>> verts;

    public RunInput(HashMap<String, Vertex<Integer>> verts, HashMap<Integer, String> mapList) {
        this.verts = verts;
        this.mapList = mapList;
    }

    public void runMapDjikstra(Graph<Integer, Integer> g, Object[][] testSample, int times) {
        System.out.println("Adjacency Map + Djikstra PQ");
        double total = 0.0;
        double totalM = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(testSample[i][0]);
            Vertex<Integer> dest = verts.get(testSample[i][1]);

            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            DijkstraMap dijMap = new DijkstraMap(g);
            dijMap.calShortestDistance(src, dest);
            endTime = System.nanoTime();
            total += ((endTime - startTime) / divider);
            runtime.gc();
            totalM += runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, totalM / times, times);
    }

    public void runMapBFS(Graph<Integer, Integer> g, Object[][] testSample,  int times) {
        System.out.println("Adjacency Map + BFS Queue");
        double total = 0.0;
        double totalM = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(testSample[i][0]);
            Vertex<Integer> dest = verts.get(testSample[i][1]);

            runtime = Runtime.getRuntime();
            startTime = System.nanoTime();
            BFSqueueMap bfs = new BFSqueueMap(g);
            bfs.calShortestPath(src, dest);
            endTime = System.nanoTime();
            total += ((endTime - startTime) / divider);
            runtime.gc();
            totalM += runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, totalM / times, times);
    }

    public void runListDjikstra(List<List<Integer>> g, Object[][] testSample, int times) {
        System.out.println("Adjacency List + Dijkstra PQ");
        double total = 0.0;
        double totalM = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(testSample[i][0]);
            Vertex<Integer> dest = verts.get(testSample[i][1]);

            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            DijkstraList dijList = new DijkstraList(g);
            dijList.calShortestDistance(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            total += ((endTime - startTime) / divider);
            runtime.gc();
            totalM += runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, totalM / times, times);
    }

    public void runListBFS(List<List<Integer>> g, Object[][] testSample, int times) {
        System.out.println("Adjacency List + BFS Queue");
        double total = 0.0;
        double totalM = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(testSample[i][0]);
            Vertex<Integer> dest = verts.get(testSample[i][1]);

            runtime = Runtime.getRuntime();
            startTime = System.nanoTime();
            BFSqueueList bfsList = new BFSqueueList(g);
            bfsList.calShortestDistance(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            total += ((endTime - startTime) / divider);
            runtime.gc();
            totalM += runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, totalM / times, times);
    }

    public void runMatrixDjikstra(GraphAjdacencyMatrix g, Object[][] testSample, int times) {
        System.out.println("Adjacency Matrix + Dijkstra PQ");
        double total = 0.0;
        double totalM = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(testSample[i][0]);
            Vertex<Integer> dest = verts.get(testSample[i][1]);

            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            BFSqueueMatrix dijMatrix = new BFSqueueMatrix(g.matrix, g.vertex);
            dijMatrix.calShortestPath(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            total += ((endTime - startTime) / divider);
            runtime.gc();
            totalM += runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, totalM / times, times);
    }

    public void runMatrixBFS(GraphAjdacencyMatrix g, Object[][] testSample, int times) {
        System.out.println("Adjacency Matrix + BFS Queue");
        double total = 0.0;
        double totalM = 0.0;

        for (int i = 0; i < times; i++) {
            Vertex<Integer> src = verts.get(testSample[i][0]);
            Vertex<Integer> dest = verts.get(testSample[i][1]);

            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            BFSqueueMatrix bfsMatrix = new BFSqueueMatrix(g.matrix, g.vertex);
            bfsMatrix.calShortestPath(src.getElement(), dest.getElement());
            endTime = System.nanoTime();
            total += ((endTime - startTime) / divider);
            runtime.gc();
            totalM += runtime.totalMemory() - runtime.freeMemory();
        }

        printInfo(total / times, totalM / times, times);
    }

    public void runMapDjikstra(Graph<Integer, Integer> g, Vertex<Integer> src, Vertex<Integer> dest) {
        System.out.println("Form: " + src.getElement());
        System.out.println("To: " + dest.getElement());
        
        System.out.println("Adjacency Map + Djikstra PQ");
        double total = 0.0;
        double totalM = 0.0;

        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        DijkstraMap dijMap = new DijkstraMap(g);
        dijMap.printShortestDistance(src, dest);
        endTime = System.nanoTime();
        total += ((endTime - startTime) / divider);
        runtime.gc();
        totalM += runtime.totalMemory() - runtime.freeMemory();

        printInfo(total, totalM, 1);
    }

    public void runMapBFS(Graph<Integer, Integer> g, Vertex<Integer> src, Vertex<Integer> dest) {
        System.out.println("Form: " + src.getElement());
        System.out.println("To: " + dest.getElement());

        System.out.println("Adjacency Map + BFS Queue");
        double total = 0.0;
        double totalM = 0.0;

        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        BFSqueueMap bfs = new BFSqueueMap(g);
        bfs.printShortestPath(src, dest);
        endTime = System.nanoTime();
        total += ((endTime - startTime) / divider);
        runtime.gc();
        totalM += runtime.totalMemory() - runtime.freeMemory();

        printInfo(total, totalM, 1);
    }

    public void runListDjikstra(List<List<Integer>> g, Vertex<Integer> src, Vertex<Integer> dest) {
        System.out.println("Form: " + src.getElement());
        System.out.println("To: " + dest.getElement());

        System.out.println("Adjacency List + Dijkstra PQ");
        double total = 0.0;
        double totalM = 0.0;

        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        DijkstraList dijList = new DijkstraList(g);
        dijList.printShortestDistance(src.getElement(), dest.getElement());
        endTime = System.nanoTime();
        total += ((endTime - startTime) / divider);
        runtime.gc();
        totalM += runtime.totalMemory() - runtime.freeMemory();

        printInfo(total, totalM, 1);
    }

    public void runListBFS(List<List<Integer>> g, Vertex<Integer> src, Vertex<Integer> dest) {
        System.out.println("Form: " + src.getElement());
        System.out.println("To: " + dest.getElement());

        System.out.println("Adjacency List + BFS Queue");
        double total = 0.0;
        double totalM = 0.0;

        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        BFSqueueList bfsList = new BFSqueueList(g);
        bfsList.printShortestDistance(src.getElement(), dest.getElement());
        endTime = System.nanoTime();
        total += ((endTime - startTime) / divider);
        runtime.gc();
        totalM += runtime.totalMemory() - runtime.freeMemory();

        printInfo(total, totalM, 1);
    }

    public void runMatrixDjikstra(GraphAjdacencyMatrix g, Vertex<Integer> src, Vertex<Integer> dest) {
        System.out.println("Form: " + src.getElement());
        System.out.println("To: " + dest.getElement());

        System.out.println("Adjacency Matrix + Dijkstra PQ");
        double total = 0.0;
        double totalM = 0.0;

        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        BFSqueueMatrix dijMatrix = new BFSqueueMatrix(g.matrix, g.vertex);
        dijMatrix.printShortestPath(src.getElement(), dest.getElement());
        endTime = System.nanoTime();
        total += ((endTime - startTime) / divider);
        runtime.gc();
        totalM += runtime.totalMemory() - runtime.freeMemory();

        printInfo(total, totalM, 1);
    }

    public void runMatrixBFS(GraphAjdacencyMatrix g, Vertex<Integer> src, Vertex<Integer> dest) {
        System.out.println("Form: " + src.getElement());
        System.out.println("To: " + dest.getElement());

        System.out.println("Adjacency Matrix + BFS Queue");
        double total = 0.0;
        double totalM = 0.0;

        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        BFSqueueMatrix bfsMatrix = new BFSqueueMatrix(g.matrix, g.vertex);
        bfsMatrix.printShortestPath(src.getElement(), dest.getElement());
        endTime = System.nanoTime();
        total += ((endTime - startTime) / divider);
        runtime.gc();
        totalM += runtime.totalMemory() - runtime.freeMemory();

        printInfo(total, totalM, 1);
    }

    public static void printInfo(double average, double memory, int times) {
        System.out.println("Avg time for " + times + " runs: " + average + "s");
        System.out.println("Avg memory used: " + bytesToMegabytes(memory) + "MB");
        System.out.println("\n--------------------------------------------------\n");
    }

    public static double bytesToMegabytes(double bytes) {
        return bytes / MEGABYTE;
    }

}