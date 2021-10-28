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
import sg.edu.smu.app.datastructures.CustomNode;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.GraphAlgorithms;
import sg.edu.smu.app.datastructures.Vertex;
import sg.edu.smu.app.datastructures.Edge;
import sg.edu.smu.app.DijkstraAlgo.*;
import sg.edu.smu.app.DjikstraLinkedList.DijkstraLinkedList;

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
        try (Reader reader = new FileReader("100.json")) {
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
        labels = null;
        n = null;
        
        long startTime = System.nanoTime();
        g = generateAdjacencyMapFromData(users, g, verts);
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Time to Load Graph: " + totalTime / divider + "s");
        System.out.println();

        System.out.println(mapList.size());
        // Test input
        // From: "zzrA6bRsAxj_qXui0SyBwQ"
        // To: "PZW77I6qXeM0RQjo1kGBUg"
        Vertex<Integer> v1 = verts.get("bPEBX_5aRZA7StQ-WNPVDw");
        Vertex<Integer> v2 = verts.get("DZfhIL6tnEb7I42cHuuT6A");
        // Vertex<Integer> v1 = findVertex(g, new Random().nextInt(mapList.size()));
        // Vertex<Integer> v2 = findVertex(g, new Random().nextInt(mapList.size()));
        
        long startTime2 = System.nanoTime();
        System.out.println(v1 + " " + v2);

        sg.edu.smu.app.datastructures.Map<Vertex<Integer>, Integer> a = GraphAlgorithms.shortestPathLengths(g, v1, v2);
        
        System.out.printf("from: %s\nTo: %s\nSteps: %d\n", mapList.get(v1.getElement()), mapList.get(v2.getElement()),
                a.get(v2));
        long endTime2 = System.nanoTime();
        long totalTime2 = endTime2 - startTime2;
        System.out.println("Time to Compute Path: " + totalTime2 / divider + "s");


        g = null;
        v1 = null;
        v2 = null;
        mapList = null;
        verts = null;
        System.out.println("Generating adj map");
        JSONObject data = null;
        try (Reader reader = new FileReader("sample2.json")) {
            data = (JSONObject) parser.parse(reader);

            labels = new TreeSet<>();
            for (Object key: data.keySet()) {
                labels.add((String) key);
                JSONArray values = (JSONArray) data.get(key);
                for (Object s : values) {
                    labels.add((String) s);
                }
            }
            System.out.println("Found unique labels: " + labels.size());
            
            Map<String, Integer> uniqueList = new HashMap<>();
            n = 0; // convert all user id from 0 to n
            for (String label : labels) {
                uniqueList.put(label, n++);
            }

            Map<Integer, List<CustomNode>> adjMap = new HashMap();

            for (String label: labels) {
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
            int id1 = 18034;
            int id2 = 21217;
            int numVertices = adjMap.size();
            // DijkstraLinkedList dji = new DijkstraLinkedList(9);
            // int source = 1;
            // dji.adapter(DijkstraLinkedList.getAdjList(), source);
            // System.out.println("The shorted path from node :");
            
            // for (int i = 0; i < dji.distArr.length; i++)
            //   System.out.println(source + " to " + i + " is " + dji.distArr[i]);

            System.out.println("Loaded adjMap of size: " + adjMap.size());
            System.out.println("Actual data: ");

            startTime = System.nanoTime();
            DijkstraLinkedList dji = new DijkstraLinkedList(numVertices);
            dji.adapter(adjMap, id1);
            // for (int i = 0; i < dji.distArr.length; i++)
            System.out.println(id1 + " to " + id2 + " is " + dji.distArr[id2]);
            
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            System.out.println("Time to perform search: " + totalTime / divider + "s");
            System.out.println();

            // if (adjMap.get(18034).size() > 0) {
            //     List<CustomNode> l1 = adjMap.get(18034);
            //     for (CustomNode node: l1) {
            //         System.out.println(node.node);
            //     }
            // }

            // testing
            // for (int i = 0; i < 1000; i ++) {
            //     if (adjMap.get(i).size() > 0) {
            //         List<CustomNode> l1 = adjMap.get(i);
            //         for (CustomNode node: l1) {
            //             System.out.println(node.node);
            //         }
            //     }
            // }


        } catch (Exception e) {
            e.printStackTrace();
        }


        // GraphAjdacencyList adjList = generateAdjacencyListFromData(users, verts);

        // System.out.println();
        // long startTime3 = System.nanoTime();
        // BFSqueue<Integer> bfs = new BFSqueue<>();
        // bfs.printShortestDistance(g, v1, v2);
        // long endTime3 = System.nanoTime();
        // long totalTime3 = endTime3 - startTime3;
        // System.out.println();
        // System.out.println("Time to Compute Path: " + totalTime3 / divider + "s");
        
        // GraphAjdacencyMatrix graph = new GraphAjdacencyMatrix(mapList.size());
        // generateAdjacencyMatrixFromData(graph, users, verts);
        // graph.printGraph();
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
}