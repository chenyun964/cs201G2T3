package sg.edu.smu.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sg.edu.smu.app.bfsqueue.BFSqueueList;
import sg.edu.smu.app.bfsqueue.BFSqueueMap;
import sg.edu.smu.app.bfsqueue.BFSqueueMatrix;
import sg.edu.smu.app.datastructures.AdjacencyMapGraph;
import sg.edu.smu.app.datastructures.CustomNode;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.GraphAlgorithms;
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.dijkstra.DijkstraList;
import sg.edu.smu.app.dijkstra.DijkstraMap;
import sg.edu.smu.app.DjikstraLinkedList.DijkstraLinkedList;
import sg.edu.smu.app.experiments.RunDJI;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.List;

public class TestApplication {
    private static final long MEGABYTE = 1024L * 1024L;
    private static final Double divider = 1000000000.0;

    public static void main(String[] args) {
        UserInterface app = new UserInterface();
        app.createUI();

        System.out.println("Load Data...");
        JSONParser parser = new JSONParser();
        JSONArray users = null;
        try (Reader reader = new FileReader("data/1k.json")) {
            users = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Map unique integer to user_id
        HashMap<Integer, String> mapList = new HashMap<>();
        // Map user_id to unique integer
        HashMap<String, Vertex<Integer>> verts = new HashMap<>();
        Graph<Integer, Integer> g = new AdjacencyMapGraph<>(false);

        // Find the unique user_ids
        TreeSet<String> labels = getLabels(users);
        Integer n = 0;
        for (String label : labels) {
            mapList.put(n, label);
            verts.put(label, g.insertVertex(n++));
        }

        labels = null;
        n = null;

        // Test input
        // Vertex<Integer> v1 = findVertex(g, new Random().nextInt(mapList.size()));
        // Vertex<Integer> v2 = findVertex(g, new Random().nextInt(mapList.size()));
        //        HashMap<Integer, String> mapList = new HashMap<>();

        List<Integer> keyLists = new ArrayList<>(mapList.keySet());
        Integer s1 = keyLists.get(new Random().nextInt(keyLists.size()));
        Integer s2 = keyLists.get(new Random().nextInt(keyLists.size()));
        Vertex<Integer> v1 = verts.get(mapList.get(s1));
        Vertex<Integer> v2 = verts.get(mapList.get(s2));
//        Vertex<Integer> v1 = verts.get("WT6yryyLUiDLLcvNfwOH3A");
//        Vertex<Integer> v2 = verts.get("Rjemuu7YgvuAsk3M39a7bw");
        //WT6yryyLUiDLLcvNfwOH3A
        //Rjemuu7YgvuAsk3M39a7bw
        System.out.println("From: " + v1.getElement() + " " + mapList.get(v1.getElement()));
        System.out.println("  To: " + v2.getElement() + " " + mapList.get(v2.getElement()));
        System.out.println("\n--------------------------------------------------\n");

        long startTime;
        long endTime;
        long totalTime;
        /**
         * Adjacency Map + Djikstra PQ
         */
        // Generate Adjacency Map
        g = generateAdjacencyMapFromData(users, g, verts);
        System.out.println("Adjacency Map + Djikstra PQ");
        Runtime runtime = Runtime.getRuntime();
        startTime = System.nanoTime();
        DijkstraMap dijMap = new DijkstraMap(g);
        dijMap.printShortestDistance(v1, v2);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to Compute Path: " + totalTime / divider + "s");
        // Get the Java runtime
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
       long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency Map + BFS Queue
         */
        System.out.println("Adjacency Map + BFS Queue");
        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        BFSqueueMap bfs = new BFSqueueMap(g);
        bfs.printShortestPath(v1, v2);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to Compute Path: " + totalTime / divider + "s");
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency List + Dijkstra PQ
         */
        // Generate Adjacency List
        List<List<Integer>> adjList = generateAdjacencyListFromData(users, verts).getGraph();
        System.out.println("Adjacency List + Dijkstra PQ");
        runtime = Runtime.getRuntime();
        startTime = System.nanoTime();
        // Find shortest path
        DijkstraList dijList = new DijkstraList(adjList);
        dijList.printShortestDistance(v1.getElement(), v2.getElement());
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to Compute Path: " + totalTime / divider + "s");
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency List + BFS Queue
         */
        // Generate Adjacency List
        System.out.println("Adjacency List + BFS Queue");
        runtime = Runtime.getRuntime();
        startTime = System.nanoTime();
        // Find shortest path
        BFSqueueList bfsList = new BFSqueueList(adjList);
        bfsList.printShortestDistance(v1.getElement(), v2.getElement());
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to Compute Path: " + totalTime / divider + "s");
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory in bytes: " + memory);
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
        System.out.println("\n--------------------------------------------------\n");

        GraphAjdacencyMatrix graph = null;

        /**
         * Adjacency Matrix + Dijkstra PQ
         */
        // Generate Adjacency Matrix
        try{
            graph = new GraphAjdacencyMatrix(mapList.size());
            generateAdjacencyMatrixFromData(graph, users, verts);
            System.out.println("Adjacency Matrix + Dijkstra PQ");
            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            BFSqueueMatrix dijMatrix = new BFSqueueMatrix(graph.matrix, graph.vertex);
            dijMatrix.printShortestPath(v1.getElement(), v2.getElement());
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("Time to Compute Path: " + totalTime / divider + "s");
            // Run the garbage collector
            runtime.gc();
            // Calculate the used memory
            memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory in bytes: " + memory);
            System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
            System.out.println("\n--------------------------------------------------\n");

            /**
             * Adjacency Matrix + BFS Queue
             */
            generateAdjacencyMatrixFromData(graph, users, verts);
            System.out.println("Adjacency Matrix + BFS Queue");
            startTime = System.nanoTime();
            runtime = Runtime.getRuntime();
            BFSqueueMatrix bfsMatrix = new BFSqueueMatrix(graph.matrix, graph.vertex);
            bfsMatrix.printShortestPath(v1.getElement(), v2.getElement());
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("Time to Compute Path: " + totalTime / divider + "s");
            // Run the garbage collector
            runtime.gc();
            // Calculate the used memory
            memory = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Used memory in bytes: " + memory);
            System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory));
        }catch (OutOfMemoryError ot){
            System.out.println("Memory out of heap");
        }

        System.out.println("\n--------------------------------------------------\n");
        System.out.println("\n------------------ End Test ----------------------\n");

        g = null;
        v1 = null;
        v2 = null;
        // mapList = null;
        verts = null;

        System.out.println("Generating adj map for Djikstra Algo");
        JSONObject data = null;
        // data/sample3.json
        // data/100.json
        try (Reader reader = new FileReader("data/sample3.json")) {
            data = (JSONObject) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        labels = new TreeSet<>();
        for (Object key : data.keySet()) {
            labels.add((String) key);
            JSONArray values = (JSONArray) data.get(key);
            for (Object s : values) {
                labels.add((String) s);
            }
        }

        Map<String, Integer> uniqueList = new HashMap<>();
        n = 0; // convert all user id from 0 to n

        for (String label : labels) {
            uniqueList.put(label, n++);
        }

        int id1 = uniqueList.get("YiSFCdyb0dJQrSAGRzkzAw");
        int id2 = uniqueList.get("dxqHh0JYQg9_X7whNAWWVA");

        Map<Integer, List<CustomNode>> adjMap = new HashMap<>();

        for (String label : labels) {
            try {
                JSONArray values = (JSONArray) data.get(label);
                List<CustomNode> l1 = new ArrayList<CustomNode>(); // list of node
                for (Object s : values) { // loop thru all values
                    l1.add(new CustomNode(uniqueList.get((String) s), 1));
                }
                // l1.add(new Node(1, 1));
                adjMap.put(uniqueList.get(label), l1);

            } catch (Exception e) {
                adjMap.put(uniqueList.get(label), new ArrayList<CustomNode>());
            }
        }

        // memory clearing
        data = null;
        labels = null;
        uniqueList = null;
        int numVertices = adjMap.size();

        System.out.println("In this experiment, we are fixing the input Data Structure (Adj Map) and Algo (Djikstra) \n"
                + "and will be varying the data structure used for sorting");
        System.out.println("From id " + id1 + " to " + id2);
        System.out.println(numVertices);
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency Map + Djikstra PQ
         */
        int times = 10;
        RunDJI djiExperiments = new RunDJI();
        djiExperiments.runSortedPQ(numVertices, adjMap, id1, id2, times);

        // /**
        // * Adjacency Map + Djikstra LL w Hash Map
        // */
        djiExperiments.runSortedLL(numVertices, adjMap, id1, id2, times);

        // /**
        // * Adjacency Map + Djikstra LL w/o hashmap
        // */
        System.out.println("Adjacency Map + Djikstra (Sorted LL w/o HashMap, like simple linear sorted array)");
        // THIS ACTUALLY TAKES VERY LONG HENCE COMMENTED OUT
        djiExperiments.runLinearlySortedLinkedList(numVertices, adjMap, id1, id2);

        // /**
        // * Adjacency Map + Djikstra Dumb Stack
        // */
        djiExperiments.runMinStack(numVertices, adjMap, id1, id2, times);

        /**
         * Adjacency Map + Djikstra HashMap w Que
         */
        djiExperiments.runHashMapCircular(numVertices, adjMap, id1, id2, times);

        /**
         * Adjacency Map + Djikstra Sorted Array via bSearch
         */
        System.out.println("Adjacency Map + Djikstra (Sorted Array using Binary Search)");
        djiExperiments.runMinArray(numVertices, adjMap, id1, id2, 1);
    }

    public static TreeSet<String> getLabels(JSONArray users) {
        TreeSet<String> labels = new TreeSet<>();
        for (Object u : users) {
            JSONObject user = (JSONObject) u;
            String user_id = (String) user.get("user_id");
            labels.add(user_id);
            String friendString = (String) user.get("friends");
            String[] friends = friendString.replace(" ", "").split(",");
            for (String s : friends) {
                labels.add(s);
            }
        }
        return labels;
    }

    public static Graph<Integer, Integer> generateAdjacencyMapFromData(JSONArray users, Graph<Integer, Integer> g,
                                                                       HashMap<String, Vertex<Integer>> verts) {

        for (Object u : users) {
            JSONObject user = (JSONObject) u;
            String user_id = (String) user.get("user_id");
            String friendString = (String) user.get("friends");
            String[] friends = friendString.replace(" ", "").split(",");
            for (String s : friends) {
                if (g.getEdge(verts.get(user_id), verts.get(s)) == null) {
                    g.insertEdge(verts.get(user_id), verts.get(s), 1);
                }
            }
        }
        return g;
    }

    public static GraphAjdacencyList generateAdjacencyListFromData(JSONArray users,
                                                                   HashMap<String, Vertex<Integer>> userToInt) {

        GraphAjdacencyList ajdList = new GraphAjdacencyList(userToInt.size());

        for (Object u : users) {
            JSONObject user = (JSONObject) u;
            Integer id = userToInt.get(user.get("user_id")).getElement();
            String friendString = (String) user.get("friends");
            String[] friends = friendString.replace(" ", "").split(",");
            for (String s : friends) {
                ajdList.addEdge(id, userToInt.get(s).getElement());
            }
        }

        return ajdList;
    }

    public static void generateAdjacencyMatrixFromData(GraphAjdacencyMatrix g, JSONArray users,
                                                       HashMap<String, Vertex<Integer>> userToInt) {

        for (Object u : users) {
            JSONObject user = (JSONObject) u;
            Integer id = userToInt.get(user.get("user_id")).getElement();
            String friendString = (String) user.get("friends");
            String[] friends = friendString.replace(" ", "").split(",");
            for (String s : friends) {
                g.addEdge(id, userToInt.get(s).getElement());
            }
        }
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
}