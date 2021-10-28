package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UserInterface {
    String fromId;
    String toId;
    String ds;
    String algo;
    JTextField fromIdInput;
    JTextField toIdInput;
    JComboBox<String> dsBox;
    JComboBox<String> algoBox;

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

        JLabel dsLabel = new JLabel("Data Structure");
        String[] dsChoices = { "Ajdacency Map", "Ajdacency List", "Ajdacency Matrix"};
        dsBox = new JComboBox<String>(dsChoices);
        
        JLabel algoLabel = new JLabel("Algorithm");
        String[] algoChoices = { "BFS", "Djikstra Algo"};
        algoBox = new JComboBox<String>(algoChoices);

        JButton sendBtn = new JButton("Connect");
        Action action = new AbstractAction("1") {
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

        sendBtn.getActionMap().put("Enter", action);
        sendBtn.addActionListener(action);

        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.add(label1); // Components Added using Flow Layout
        panel.add(fromIdInput);
        panel.add(label2); // Components Added using Flow Layout
        panel.add(toIdInput);
        panel.add(dsLabel);
        panel.add(dsBox);
        panel.add(algoLabel);
        panel.add(algoBox);
        panel.add(sendBtn);

        frame.add(panel);
        frame.add(graph);

        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.setVisible(true);
    }

    
}