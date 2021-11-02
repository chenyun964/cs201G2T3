package sg.edu.smu.app;

public class GraphAjdacencyMatrix {
    public int vertex;
    public boolean[][] matrix;

    public GraphAjdacencyMatrix(int vertex) {
        this.vertex = vertex;
        matrix = new boolean[vertex][vertex];
    }

    public void addEdge(int source, int destination) {
        // add edge
        matrix[source][destination] = true;

        // add bak edge for undirected graph
        matrix[destination][source] = true;
    }

    public void printGraph() {
        System.out.println("Graph: (Adjacency Matrix)");
        for (int i = 0; i < vertex; i++) {
            for (int j = 0; j < vertex; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < vertex; i++) {
            System.out.print("Vertex " + i + " is connected to:");
            for (int j = 0; j < vertex; j++) {
                if (matrix[i][j]) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
}
