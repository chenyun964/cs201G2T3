// import java.util.*;

// // Data structure to store graph edges
// class Edge
// {
// 	int source, dest, weight;

// 	public Edge(int source, int dest, int weight) {
// 		this.source = source;
// 		this.dest = dest;
// 		this.weight = weight;
// 	}
// };

// // Data structure to store heap nodes
// class Node
// {
// 	int vertex, weight;

// 	public Node(int vertex, int weight) {
// 		this.vertex = vertex;
// 		this.weight = weight;
// 	}
// };

// // class to represent a graph object
// class Graph
// {
// 	// An array of Lists to represent adjacency list
// 	List<List<Edge>> adjList = null;

// 	// Constructor
// 	Graph(List<Edge> edges, int N)
// 	{
// 		adjList = new ArrayList<>(N);

// 		for (int i = 0; i < N; i++) {
// 			adjList.add(i, new ArrayList<>());
// 		}

// 		// add edges to the undirected graph
// 		for (Edge edge: edges) {
// 			adjList.get(edge.source).add(edge);
// 		}
// 	}
// }

// class Dijkstra
// {
// 	private static void printRoute(int prev[], int i)
// 	{
// 		if (i < 0)
// 			return;

// 		printRoute(prev, prev[i]);
// 		System.out.print(i + " ");
// 	}

// 	// Run Dijkstra's algorithm on given graph
// 	public static void shortestPath(Graph graph, int source, int N)
// 	{
// 		// create min heap and push source node having distance 0
// 		PriorityQueue<Node> minHeap = new PriorityQueue<>(
// 								(lhs, rhs) -> lhs.weight - rhs.weight);

// 		minHeap.add(new Node(source, 0));

// 		// set infinite distance from source to v initially
// 		List<Integer> dist = new ArrayList<>(
// 					Collections.nCopies(N, Integer.MAX_VALUE));

// 		// distance from source to itself is zero
// 		dist.set(source, 0);

// 		// boolean array to track vertices for which minimum
// 		// cost is already found
// 		boolean[] done = new boolean[N];
// 		done[0] = true;

// 		// stores predecessor of a vertex (to print path)
// 		int prev[] = new int[N];
// 		prev[0] = -1;

// 		// run till minHeap is not empty
// 		while (!minHeap.isEmpty())
// 		{
// 			// Remove and return best vertex
// 			Node node = minHeap.poll();

// 			// get vertex number
// 			int u = node.vertex;

// 			// do for each neighbor v of u
// 			for (Edge edge: graph.adjList.get(u))
// 			{
// 				int v = edge.dest;
// 				int weight = edge.weight;

// 				// Relaxation step
// 				if (!done[v] && (dist.get(u) + weight) < dist.get(v))
// 				{
// 					dist.set(v, dist.get(u) + weight);
// 					prev[v] = u;
// 					minHeap.add(new Node(v, dist.get(v)));
// 				}
// 			}

// 			// marked vertex u as done so it will not get picked up again
// 			done[u] = true;
// 		}

// 		for (int i = 1; i < N; ++i)
// 		{
// 			System.out.print("Path from vertex 0 to vertex " + i +
// 							" has minimum cost of " + dist.get(i) +
// 							" and the route is [ ");
// 			printRoute(prev, i);
// 			System.out.println("]");
// 		}
// 	}

// 	public static void main(String[] args)
// 	{
// 		// initialize edges as per above diagram
// 		// (u, v, w) triplet represent undirected edge from
// 		// vertex u to vertex v having weight w
// 		List<Edge> edges = Arrays.asList(
// 				new Edge(0, 1, 10), new Edge(0, 4, 3),
// 				new Edge(1, 2, 2), new Edge(1, 4, 4),
// 				new Edge(2, 3, 9), new Edge(3, 2, 7),
// 				new Edge(4, 1, 1), new Edge(4, 2, 8),
// 				new Edge(4, 3, 2)
// 		);

// 		// Set number of vertices in the graph
// 		final int N = 5;

// 		// construct graph
// 		Graph graph = new Graph(edges, N);

// 		shortestPath(graph, 0, N);
// 	}
// }


import java.util.*;
 
class Vertex implements Comparable<Vertex>{
  //vertex label
  String name;
  //Distance value               
  double distance = Double.MAX_VALUE;
  //to mark vertex as visited           
  boolean visited;
  //Edges connected to the vertex, will lead to next vertex             
  List<Edge> adjacentEdge = new ArrayList<>(); 
  //distance value is updated by which vertex 
  Vertex predecessor = null;       

  public Vertex(String n){
    this.name = n;
  }

  public void addadjacentEdge(Edge e){
    adjacentEdge.add(e);
  }

  //Function to compare two vertices on basis of diatance
  //used by the priority queue
  @Override
  public int compareTo(Vertex b) {
    return (int) (this.distance - b.distance);
  }

  @Override
  public String toString(){
    return this.name;
  }
}

class Edge {
  Vertex from;
  Vertex to;
  int weight;

  public Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }
}

class Dijkstra {
  //source vertex
  Vertex source;
  //Priority queue to queue vertices w.r.t least distance
  PriorityQueue<Vertex> vertexQueue = new PriorityQueue<>();

  public Dijkstra(Vertex source) {
    this.source = source;
  }

  //Function implementing Dijkstra's Algorithm
  void computePath(){
    //Starting vertex distance should be 0
    source.distance = 0;         

    //add source vertex to the queue
    vertexQueue.add(source);

    //run until queue is not empty
    while(!vertexQueue.isEmpty()){  
      //Get vertex from the top of queue 
      //i.e. vertex with least distance   
      Vertex actualVertex = vertexQueue.peek();    

      //get adjacent vertices through connected edges
      for(Edge edge: actualVertex.adjacentEdge){
        Vertex v = edge.to;

        //If not visited then check whether the distance value could be lower
        if( !v.visited){
          if(actualVertex.distance + edge.weight < v.distance){
            v.distance = actualVertex.distance + edge.weight;
            v.predecessor =  actualVertex;     //also update its predecessor
            vertexQueue.add(v);
          }
        }
      }

      //remove vertex from queue
      vertexQueue.poll(); 
      //mark vertex as visited                  
      actualVertex.visited = true;         
    }
  }

  //Function to return the shortest path
  List<Vertex> shortestPath(Vertex v){
    //list to hold path
    List<Vertex> shortPath = new ArrayList<>();

    //Trace route from the 'v' to the source vertex
    shortPath.add(v);
    while(v.predecessor != null){
      shortPath.add(v.predecessor);
      v = v.predecessor;
    }

    //reverse the list to correct the order
    Collections.reverse(shortPath);
    
    //return list
    return shortPath;
  }

  public static void main(String args[]){

    Vertex v[] = {new Vertex("A"),new Vertex("B"),new Vertex("C")};
    Edge e[] = {new Edge(v[0],v[1],10),new Edge(v[0],v[2],30),new Edge(v[1],v[2],10)};

    v[0].addadjacentEdge(e[0]);
    v[0].addadjacentEdge(e[1]);
    v[1].addadjacentEdge(e[0]);
    v[1].addadjacentEdge(e[2]);
    v[2].addadjacentEdge(e[1]);
    v[2].addadjacentEdge(e[2]);

    Dijkstra dijkstra = new Dijkstra(v[0]);
    dijkstra.computePath();

    List<Vertex> path= dijkstra.shortestPath(v[2]);

    System.out.println("Shortest Path: ");
    System.out.println(path);
  }
}