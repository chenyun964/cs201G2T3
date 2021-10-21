package sg.edu.smu.app;

import javax.swing.*;
import java.awt.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;

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

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("data.json")) {

            JSONArray users = (JSONArray) parser.parse(reader);
            for (Object u : users)
            {
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
