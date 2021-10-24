package sg.edu.smu.app;

import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FloydWarshall {
    static final int INF = 999999;

    public static void execute(int v, int[][] graph) {
        int[][] dist = new int[v][v];

        for (int i = 0; i < v; i++)
            dist[i] = Arrays.copyOf(graph[i], v);

        /*
         * Add all vertices one by one to the set of intermediate vertices. ---> Before
         * start of an iteration, we have shortest distances between all pairs of
         * vertices such that the shortest distances consider only the vertices in set
         * {0, 1, 2, .. k-1} as intermediate vertices. ----> After the end of an
         * iteration, vertex no. k is added to the set of intermediate vertices and the
         * set becomes {0, 1, 2, .. k}
         */
        for (int k = 0; k < v; k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < v; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = 0; j < v; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        // Print the shortest distance matrix
        printSolution(v, dist);
    }

    static void printSolution(int v, int dist[][]) {
        System.out.println("The following matrix shows the shortest " + "distances between every pair of vertices");
        for (int i = 0; i < v; ++i) {
            for (int j = 0; j < v; ++j) {
                if (dist[i][j] == INF)
                    System.out.print(" INF");
                else
                    System.out.printf("%4d", dist[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // int[][] graph = { { 0, 1, INF, 1 }, { INF, 0, 1, INF }, { INF, INF, 0, 1 }, { INF, INF, INF, 0 } };
        // FloydWarshall.execute(4, graph);

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("data.json")) {

            JSONArray users = (JSONArray) parser.parse(reader);
            for (Object u : users) {
                JSONObject user = (JSONObject) u;
                String user_id = (String) user.get("user_id");
                String friends = (String) user.get("friends");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
