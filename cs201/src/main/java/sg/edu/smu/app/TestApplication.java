package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONArray;

public class TestApplication {
    public static void main(String[] args) {
        // Creating the Frame
        JFrame frame = new JFrame("Chat Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

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

        // Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.setVisible(true);

        System.out.println("Hello world");
        File file = new File("data.json");

        try {
            String content = FileUtils.readFileToString(file, "utf-8");
            JSONObject tomJsonObject = new JSONObject(content);
            JSONArray users = tomJsonObject.getJSONArray("users");

            for (int i = 0; i < users.length(); i++) {
                String user = (String) users.get(i);
                System.out.println(user);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
