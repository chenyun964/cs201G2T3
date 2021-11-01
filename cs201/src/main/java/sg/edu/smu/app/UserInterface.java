package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sg.edu.smu.app.experiments.RunDJI;

import sg.edu.smu.app.datastructures.CustomNode;
import sg.edu.smu.app.datastructures.AdjacencyMapGraph;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.Vertex;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.List;

import java.io.PrintStream;

public class UserInterface {
    String fromId;
    String toId;
    String ds;
    String algo;
    JTextField fromIdInput;
    JTextField toIdInput;
    JComboBox<String> dsBox;
    JComboBox<String> algoBox;

    JTextArea resultArea;

    public void createUI() {
        // Creating the Frame
        JFrame frame = new JFrame("CS201G1T2");
        frame.setSize(900, 600);

        // GraphDraw graph = new GraphDraw();
        // graph.addNode("a", 50, 50);
        // graph.addNode("b", 100, 100);
        // graph.addNode("c", 200, 200);
        // graph.addEdge(0, 1);
        // graph.addEdge(0, 2);

        // Creating the panel at bottom and adding components
        JPanel formPanel = new JPanel(); // the panel is not visible in output

        JLabel label1 = new JLabel("Enter user id 1");
        fromIdInput = new JTextField(20); // accepts upto 20 characters

        JLabel label2 = new JLabel("Enter user id 2");
        toIdInput = new JTextField(20); // accepts upto 20 characters

        JLabel dsLabel = new JLabel("Data Structure");
        String[] dsChoices = { "Ajdacency Map", "Ajdacency List", "Ajdacency Matrix" };
        dsBox = new JComboBox<String>(dsChoices);

        JLabel algoLabel = new JLabel("Algorithm");
        String[] algoChoices = { "BFS", "Djikstra Algo" };
        algoBox = new JComboBox<String>(algoChoices);

        resultArea = new JTextArea();

        JButton sendBtn = new JButton("Connect");
        Action singleConnection = new AbstractAction("Single Connection") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromId = fromIdInput.getText();
                toId = toIdInput.getText();
                ds = dsBox.getSelectedItem().toString();
                algo = algoBox.getSelectedItem().toString();
                System.out.println("From:" + fromId);
                System.out.println("To: " + toId);
                System.out.println("Data Structure: " + ds);
                System.out.println("Algo: " + algo);
            }
        };

        sendBtn.getActionMap().put("Enter", singleConnection);
        sendBtn.addActionListener(singleConnection);

        JButton inputTestBtn = new JButton("Input Test");
        Action inputTest = new AbstractAction("Input Test") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromId = fromIdInput.getText();
                toId = toIdInput.getText();
                ds = dsBox.getSelectedItem().toString();
                algo = algoBox.getSelectedItem().toString();
                System.out.println("From:" + fromId);
                System.out.println("To: " + toId);
                System.out.println("Data Structure: " + ds);
                System.out.println("Algo: " + algo);
            }
        };

        inputTestBtn.getActionMap().put("Enter", inputTest);
        inputTestBtn.addActionListener(inputTest);

        JButton inAlgoTestBtn = new JButton("Data Structure Test");
        Action inAlgoTest = new AbstractAction("In Algo Test") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONParser parser = new JSONParser();
                JSONObject data = null;
                TreeSet<String> labels;

                try (Reader reader = new FileReader("data/sample3.json")) {
                    data = (JSONObject) parser.parse(reader);
                } catch (Exception exc) {
                    exc.printStackTrace();
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
                Integer n = 0; // convert all user id from 0 to n
                for (String label : labels) {
                    uniqueList.put(label, n++);
                }

                int id1 = uniqueList.get("YiSFCdyb0dJQrSAGRzkzAw");
                int id2 = uniqueList.get("dxqHh0JYQg9_X7whNAWWVA");
                // List<Integer> keyLists = new ArrayList<>(uniqueList.values());
                // int id1 = keyLists.get(new Random().nextInt(keyLists.size()));
                // int id2 = keyLists.get(new Random().nextInt(keyLists.size()));

                System.out.println(id1);
                System.out.println(id2);

                Map<Integer, List<CustomNode>> adjMap = new HashMap<>();

                for (String label : labels) {
                    try {
                        JSONArray values = (JSONArray) data.get(label);
                        List<CustomNode> l1 = new ArrayList<CustomNode>(); // list of node
                        for (Object s : values) { // loop thru all values
                            l1.add(new CustomNode(uniqueList.get((String) s), 1));
                        }
                        adjMap.put(uniqueList.get(label), l1);
                    } catch (Exception exc) {
                        adjMap.put(uniqueList.get(label), new ArrayList<CustomNode>());
                    }
                }

                // memory clearing
                int numVertices = adjMap.size();
                int times = 10;
                RunDJI djiExperiments = new RunDJI();
                resultArea.setText("");
                JTextAreaOutputStream out = new JTextAreaOutputStream (resultArea);
                System.setOut(new PrintStream (out));
                /**
                 * Adjacency Map + Djikstra PQ
                 */
                djiExperiments.runSortedPQ(numVertices, adjMap, id1, id2, times);

                /**
                 * Adjacency Map + Djikstra LL w Hash Map
                 */
                djiExperiments.runSortedLL(numVertices, adjMap, id1, id2, times);

                /**
                 * Adjacency Map + Djikstra LL w/o hashmap
                 */
                // THIS ACTUALLY TAKES VERY LONG HENCE COMMENTED OUT
                djiExperiments.runLinearlySortedLinkedList(numVertices, adjMap, id1, id2);

                /**
                 * Adjacency Map + Djikstra Dumb Stack
                 */
                djiExperiments.runMinStack(numVertices, adjMap, id1, id2, times);

                /**
                 * Adjacency Map + Djikstra HashMap w Que
                 */
                djiExperiments.runHashMapCircular(numVertices, adjMap, id1, id2, times);

                /**
                 * Adjacency Map + Djikstra Sorted Array via bSearch
                 */
                djiExperiments.runMinArray(numVertices, adjMap, id1, id2, times);
            }
        };

        inAlgoTestBtn.getActionMap().put("Enter", inAlgoTest);
        inAlgoTestBtn.addActionListener(inAlgoTest);

        formPanel.setLayout(new GridLayout(6, 4, 10, 10));
        formPanel.add(label1);
        formPanel.add(fromIdInput);

        formPanel.add(label2);
        formPanel.add(toIdInput);

        formPanel.add(dsLabel);
        formPanel.add(dsBox);

        formPanel.add(algoLabel);
        formPanel.add(algoBox);

        formPanel.add(sendBtn);
        formPanel.add(inputTestBtn);
        formPanel.add(inAlgoTestBtn);


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
        String[] userColumnNames = {"#", "User ID"};
        Object[][] userData = new Object[labels.size()][2];
        for (String label : labels) {
            userData[n][0] = n;
            userData[n][1] = label;
            mapList.put(n, label);
            verts.put(label, g.insertVertex(n++));
            
        }

        JPanel userPanel = new JPanel();
        JTable userTable = new JTable(userData, userColumnNames);
        userPanel.setLayout(new GridLayout(0, 1));
        userPanel.add(userTable.getTableHeader());
        userPanel.add(userTable);
        userPanel.add(new JScrollPane (
            userTable, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);



        JPanel resultPanel = new JPanel();
        // String[] columnsNames = { "To", "Form", "Time Taken", "Space Used" };
        // Object[][] data = { { "Katty", "Smith", "SnowBoard", 5 }, { "Jhon", "Doe", "Rowing", 3 },
        //         { "Sue", "Black", "Knitting", 2 }, { "Jane", "White", "Speed ride", 20 } };

        // JTable table = new JTable(data, columnsNames);
        resultPanel.setLayout(new GridLayout(0, 1));
        // resultPanel.add(table.getTableHeader());
        // resultPanel.add(table);
        resultPanel.add(new JScrollPane (
            resultArea, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);

        frame.add(formPanel);
        frame.add(resultPanel);
        frame.add(userPanel);
        // frame.add(graph);

        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, formPanel);
        frame.getContentPane().add(BorderLayout.WEST, userPanel);
        frame.getContentPane().add(BorderLayout.CENTER, resultPanel);
        frame.setVisible(true);
    }

    public TreeSet<String> getLabels(JSONArray users) {
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

}