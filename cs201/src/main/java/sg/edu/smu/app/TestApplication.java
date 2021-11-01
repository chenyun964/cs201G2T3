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
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.dijkstra.DijkstraList;
import sg.edu.smu.app.dijkstra.DijkstraMap;

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
        try (Reader reader = new FileReader("data/100.json")) {
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

        System.out.println("Data size: " + mapList.size());

        // Test input
        List<Integer> keyLists = new ArrayList<>(mapList.keySet());
        Integer s1 = keyLists.get(new Random().nextInt(keyLists.size()));
        Integer s2 = keyLists.get(new Random().nextInt(keyLists.size()));
        Vertex<Integer> v1 = verts.get(mapList.get(s1));
        Vertex<Integer> v2 = verts.get(mapList.get(s2));

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
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory) + "MB");
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
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory) + "MB");
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
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory) + "MB");
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
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory) + "MB");
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency Matrix + Dijkstra PQ
         */
        // Generate Adjacency Matrix
        GraphAjdacencyMatrix graph = new GraphAjdacencyMatrix(mapList.size());
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
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory) + "MB");
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency Matrix + BFS Queue
         */
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
        System.out.println("Used memory in megabytes: " + bytesToMegabytes(memory) + "MB");
        System.out.println("\n--------------------------------------------------\n");
        System.out.println("\n------------------ End Test ----------------------\n");
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