package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sg.edu.smu.app.datastructures.AdjacencyMapGraph;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.GraphAlgorithms;
import sg.edu.smu.app.datastructures.Map;
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.datastructures.Edge;

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
        TestApplication app = new TestApplication();
        app.createUI();

        JSONParser parser = new JSONParser();
        JSONArray users = null;
        try (Reader reader = new FileReader("data.json")) {
            users = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Mapping Graph...");
        // Load user data to Adjacency Map
        long startTime = System.nanoTime();
        Graph<String, Integer> g = generateAdjacencyMapGraphFromData(users);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Time to Load Graph: " + totalTime / 1000000000.0 + "s");

        Vertex<String> v1 = findVertex(g, "zzrA6bRsAxj_qXui0SyBwQ");
        Vertex<String> v2 = findVertex(g, "PZW77I6qXeM0RQjo1kGBUg");
        
        System.out.println();
        System.out.println("Find Shortest Path...");
        System.out.println("Expected Steps: 13");
        // Find the shorters path between two users
        long startTime2 = System.nanoTime();
        Map<Vertex<String>, Integer> a = GraphAlgorithms.shortestPathLengths(g, v1);
        a.entrySet().forEach(element -> {
            if (element.getKey().getElement().equals(v2.getElement())){
                System.out.println("Actaul Steps: " + element.getValue());
                return;
            }
        });

        long endTime2 = System.nanoTime();
        long totalTime2 = endTime2 - startTime2;
        System.out.println("Time to Compute Path: " + totalTime2 / 1000000000.0 + "s");

        System.out.println();
        GraphAjdacencyMatrix graph = generateAdjacencyMatrixGraphFromData(users);
        // graph.printGraph();
    }

    public static Vertex<String> findVertex(Graph<String, Integer> g, String element) {
        Iterator<Vertex<String>> it = g.vertices().iterator();
        while (it.hasNext()) {
            Vertex<String> v = it.next();
            if (v.getElement().equals(element)) {
                return v;
            }
        }
        return null;
    }
    
    public static Graph<String, Integer> generateAdjacencyMapGraphFromData(JSONArray users) {
        Graph<String, Integer> g = new AdjacencyMapGraph<>(false);
        TreeSet<String> labels = new TreeSet<>();
        HashMap<String, Vertex<String>> verts = new HashMap<>();

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

        for (String label : labels) {
            verts.put(label, g.insertVertex(label));
        }
        
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

    public static GraphAjdacencyMatrix generateAdjacencyMatrixGraphFromData(JSONArray users) {

        TreeSet<String> labels = new TreeSet<>();
        HashMap<String, Integer> pairs = new HashMap<>();
        HashMap<String, Vertex<String>> verts = new HashMap<>();

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

        // Map user_id to a unique integer
        Integer index = 0;
        for (String label : labels) {
            pairs.put(label, index++);
        }

        // Initialise the matrix
        GraphAjdacencyMatrix g = new GraphAjdacencyMatrix(pairs.size());
        
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
        return null;
    }
}