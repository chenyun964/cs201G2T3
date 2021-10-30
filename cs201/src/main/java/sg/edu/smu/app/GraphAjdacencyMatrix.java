package sg.edu.smu.app;

public class GraphAjdacencyMatrix {
    int vertex;
    int[][] matrix;

    public GraphAjdacencyMatrix(int vertex) {
        this.vertex = vertex;
        matrix = new int[vertex][vertex];
    }

    public void addEdge(int source, int destination) {
        // add edge
        matrix[source][destination] = 1;

        // add bak edge for undirected graph
        matrix[destination][source] = 1;
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
                if (matrix[i][j] == 1) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
}
