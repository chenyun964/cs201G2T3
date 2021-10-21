package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

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

        try (Reader reader = new FileReader("data.json")) {

            JSONArray users = (JSONArray) parser.parse(reader);
            for (Object u : users) {
                JSONObject user = (JSONObject) u;

                String name = (String) user.get("user_id");
                System.out.println(name);

                String city = (String) user.get("friends");
                System.out.println(city);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
