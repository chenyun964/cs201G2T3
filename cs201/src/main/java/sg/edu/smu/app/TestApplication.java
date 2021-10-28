package sg.edu.smu.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sg.edu.smu.app.bfsqueue.BFSqueue;
import sg.edu.smu.app.datastructures.AdjacencyMapGraph;
import sg.edu.smu.app.datastructures.CustomNode;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.GraphAlgorithms;
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.DjikstraLinkedList.DijkstraLinkedList;

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

        System.out.println("Load Date...");
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

        // Test input
        // Vertex<Integer> v1 = findVertex(g, new Random().nextInt(mapList.size()));
        // Vertex<Integer> v2 = findVertex(g, new Random().nextInt(mapList.size()));
        Vertex<Integer> v1 = verts.get("bPEBX_5aRZA7StQ-WNPVDw");
        Vertex<Integer> v2 = verts.get("DZfhIL6tnEb7I42cHuuT6A");
        System.out.println("from: " + mapList.get(v1.getElement()));
        System.out.println("To: " + mapList.get(v2.getElement()));
        System.out.println("\n--------------------------------------------------\n");

        long startTime;
        long endTime;
        long totalTime;
        /**
         * Adjacency Map + Djikstra PQ
         */
        System.out.println("Adjacency Map + Djikstra PQ");
        Runtime runtime = Runtime.getRuntime();
        startTime = System.nanoTime();
        g = generateAdjacencyMapFromData(users, g, verts);
        sg.edu.smu.app.datastructures.Map<Vertex<Integer>, Integer> a = GraphAlgorithms.shortestPathLengths(g, v1, v2);
        System.out.println("Steps: " + a.get(v2));
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to Compute Path: " + totalTime / divider + "s");
        // Get the Java runtime
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory is bytes: " + memory);
        System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Adjacency List + Djikstra PQ
         */
        System.out.println("Adjacency List + Djikstra PQ");
        runtime = Runtime.getRuntime();
        startTime = System.nanoTime();
        GraphAjdacencyList adjList = generateAdjacencyListFromData(users, verts);
        adjList.printShortestDistance(v1.getElement(), v2.getElement());
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

        /**
         * Adjacency Map + BFS Queue
         */
        System.out.println("Adjacency Map + BFS Queue");
        startTime = System.nanoTime();
        runtime = Runtime.getRuntime();
        g = generateAdjacencyMapFromData(users, g, verts);
        BFSqueue<Integer> bfs = new BFSqueue<>();
        bfs.printShortestDistance(g, v1, v2);
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

        g = null;
        v1 = null;
        v2 = null;
        mapList = null;
        verts = null;

        System.out.println("Generating adj map for Djikstra Algo");
        JSONObject data = null;

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
        int id1 = 0;
        int id2 = 0;
        for (String label : labels) {
            if (label.equals("YiSFCdyb0dJQrSAGRzkzAw"))
                id1 = n;
            if (label.equals("dxqHh0JYQg9_X7whNAWWVA"))
                id2 = n;
            uniqueList.put(label, n++);
        }

        Map<Integer, List<CustomNode>> adjMap = new HashMap();

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

        System.out.println("From id " + id1 + " to " + id2);
        System.out.println("\n--------------------------------------------------\n");
        
        /**
         * Using PQ
         */
        System.out.println("Starting Experiments on Djikstra Algo: ");
        System.out.println("Actual data using PQ: ");

        startTime = System.nanoTime();
        DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
        dji.dijkstra_PQ(adjMap, id1);
        // for (int i = 0; i < dji.distArr.length; i++)
        System.out.println("Shortest path from " + id1 + " to " + id2 + " is " + dji.distArr[id2]);

        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to perform search: " + totalTime / divider + "s");
        System.out.println("\n--------------------------------------------------\n");

        /**
         * Using Linked List
         */
        System.out.println("Actual data using LL: ");
        startTime = System.nanoTime();
        dji = new DijkstraLinkedList(numVertices);
        dji.dijkstra_LL(adjMap, id1);
        System.out.println("Shortest path from " + id1 + " to " + id2 + " is " + dji.distArr[id2]);
        endTime = System.nanoTime();
        totalTime = endTime - startTime;
        System.out.println("Time to perform search: " + totalTime / divider + "s");

        // GraphAjdacencyMatrix graph = new GraphAjdacencyMatrix(mapList.size());
        // generateAdjacencyMatrixFromData(graph, users, verts);
        // graph.printGraph();

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