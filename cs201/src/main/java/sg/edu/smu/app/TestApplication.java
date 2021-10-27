package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;

import com.sun.source.tree.Tree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sg.edu.smu.app.datastructures.AdjacencyMapGraph;
import sg.edu.smu.app.datastructures.Graph;
import sg.edu.smu.app.datastructures.Vertex;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class TestApplication {
    public static void main(String[] args) {
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
        JLabel label1 = new JLabel("Enter id 1");
        JTextField tf1 = new JTextField(20); // accepts upto 20 characters
        JLabel label2 = new JLabel("Enter id 2");
        JTextField tf2 = new JTextField(20); // accepts upto 20 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");

        panel.add(label1); // Components Added using Flow Layout
        panel.add(tf1);
        panel.add(label2); // Components Added using Flow Layout
        panel.add(tf2);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();
        frame.add(panel);
        frame.add(graph);

        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.setVisible(true);

        System.out.println("Hello world");

        JSONParser parser = new JSONParser();

        Graph<String, Integer> g = new AdjacencyMapGraph<>(false);
        TreeSet<String> labels = new TreeSet<>();
        HashMap<String, Vertex<String>> verts = new HashMap<>();


        try (Reader reader = new FileReader("data.json")) {
            JSONArray users = (JSONArray) parser.parse(reader);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(g);
    }
}

class GraphDraw extends JPanel {
    int width;
    int height;

    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    public GraphDraw() { // Constructor
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        width = 30;
        height = 30;
    }

    class Node {
        int x, y;
        String name;

        public Node(String myName, int myX, int myY) {
            x = myX;
            y = myY;
            name = myName;
        }
    }

    class edge {
        int i, j;

        public edge(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    public void addNode(String name, int x, int y) {
        // add a node at pixel (x,y)
        nodes.add(new Node(name, x, y));
        this.repaint();
    }

    public void addEdge(int i, int j) {
        // add an edge between nodes i and j
        edges.add(new edge(i, j));
        this.repaint();
    }

    public void paint(Graphics g) { // draw the nodes and edges
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());

        g.setColor(Color.black);
        for (edge e : edges) {
            g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);
        }

        for (Node n : nodes) {
            int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
            g.setColor(Color.white);
            g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);

            g.drawString(n.name, n.x - f.stringWidth(n.name) / 2, n.y + f.getHeight() / 2);
        }
    }

    public static Graph<String, Integer> graphFromEdgelist(String[][] edges, boolean directed) {
        Graph<String, Integer> g = new AdjacencyMapGraph<>(directed);

        // first pass to get sorted set of vertex labels
        TreeSet<String> labels = new TreeSet<>();
        for (String[] edge : edges) {
            labels.add(edge[0]);
            labels.add(edge[1]);
        }

        // now create vertices (in alphabetical order)
        HashMap<String, Vertex<String>> verts = new HashMap<>();
        for (String label : labels)
            verts.put(label, g.insertVertex(label));

        // now add edges to the graph
        for (String[] edge : edges) {
            Integer cost = (edge.length == 2 ? 1 : Integer.parseInt(edge[2]));
            g.insertEdge(verts.get(edge[0]), verts.get(edge[1]), cost);
        }
        return g;
    }

}
