package sg.edu.smu.app;

import javax.print.attribute.HashAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sg.edu.smu.app.bfsqueue.BFSqueue;
import sg.edu.smu.app.datastructures.AdjacencyMapGraph;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.GraphAlgorithms;
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.DijkstraAlgo.*;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.List;

public class TestApplication {

    String fromId;
    String toId;
    JTextField fromIdInput;
    JTextField toIdInput;

    public void createUI() {
        // Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setSize(800, 400);

        GraphDraw graph = new GraphDraw();
        graph.addNode("a", 50, 50);
        graph.addNode("b", 100, 100);
        graph.addNode("c", 200, 200);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        // Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label1 = new JLabel("Enter user id 1");
        fromIdInput = new JTextField(20); // accepts upto 20 characters
        JLabel label2 = new JLabel("Enter user id 2");
        toIdInput = new JTextField(20); // accepts upto 20 characters
        JButton sendBtn = new JButton("Connect");

        Action action = new AbstractAction("1") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromId = fromIdInput.getText();
                toId = toIdInput.getText();
                System.out.println("From:" + fromId + " To: " + toId);
            }
        };

        sendBtn.getActionMap().put("Enter", action);
        sendBtn.addActionListener(action);

        panel.add(label1); // Components Added using Flow Layout
        panel.add(fromIdInput);
        panel.add(label2); // Components Added using Flow Layout
        panel.add(toIdInput);
        panel.add(sendBtn);

        frame.add(panel);
        frame.add(graph);

        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // TestApplication app = new TestApplication();
        // app.createUI();
        Double divider = 1000000000.0;
        System.out.println("Load Date...");
        JSONParser parser = new JSONParser();
        JSONArray users = null;
        try (Reader reader = new FileReader("data.json")) {
            users = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Key: unique integer
        // Value: user_id
        HashMap<Integer, String> mapList = new HashMap<>();
        // Key: user_id
        // Value: unique integer
        HashMap<String, Vertex<Integer>> verts = new HashMap<>();

        Graph<Integer, Integer> g = new AdjacencyMapGraph<>(false);

        TreeSet<String> labels = getLabels(users);
        Integer n = 0;
        for (String label : labels) {
            mapList.put(n, label);
            verts.put(label, g.insertVertex(n++));
        }

        long startTime = System.nanoTime();
        g = generateAdjacencyMapFromData(users, g, verts);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Time to Load Graph: " + totalTime / divider + "s");
        System.out.println();

        // Test input
        // From: "zzrA6bRsAxj_qXui0SyBwQ"
        // To: "PZW77I6qXeM0RQjo1kGBUg"
//        Vertex<Integer> v1 = findVertex(g, 889231);
//        Vertex<Integer> v2 = findVertex(g, 369441);
        Vertex<Integer> v1 = findVertex(g, new Random().nextInt(mapList.size()));
        Vertex<Integer> v2 = findVertex(g, new Random().nextInt(mapList.size()));
        long startTime2 = System.nanoTime();
        sg.edu.smu.app.datastructures.Map<Vertex<Integer>, Integer> a = GraphAlgorithms.shortestPathLengths(g, v1, v2);

        System.out.printf("from %s\nTo %s\nSteps: %d\n", mapList.get(v1.getElement()),
                        mapList.get(v2.getElement()), a.get(v2));
        long endTime2 = System.nanoTime();
        long totalTime2 = endTime2 - startTime2;
        System.out.println("Time to Compute Path: " + totalTime2 / divider + "s");

        // GraphAjdacencyMatrix graph = generateAdjacencyMatrixFromData(users, verts);
        // graph.printGraph();

        GraphAjdacencyList adjList = generateAdjacencyListFromData(users, verts);

        System.out.println();
        long startTime3 = System.nanoTime();
        BFSqueue<Integer> bfs = new BFSqueue<>();
        bfs.printShortestDistance(g, v1, v2);
        long endTime3 = System.nanoTime();
        long totalTime3 = endTime3 - startTime3;
        System.out.println();
        System.out.println("Time to Compute Path: " + totalTime3 / divider + "s");
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

        // ajdList.printGraph();

        return ajdList;
    }

    public static GraphAjdacencyMatrix generateAdjacencyMatrixFromData(JSONArray users,
            HashMap<String, Vertex<Integer>> userToInt) {

        // Initialise the matrix
        GraphAjdacencyMatrix g = new GraphAjdacencyMatrix(userToInt.size());

        for (Object u : users) {
            JSONObject user = (JSONObject) u;
            Integer id = userToInt.get(user.get("user_id")).getElement();
            String friendString = (String) user.get("friends");
            String[] friends = friendString.replace(" ", "").split(",");
            for (String s : friends) {
                g.addEdge(id, userToInt.get(s).getElement());
            }
        }
        return g;
    }
}