import java.util.*;
 
class Vertex implements Comparable<Vertex>{

  String name;            
  double distance = Double.MAX_VALUE;
  boolean visited;
          
  List<Edge> adjacentEdge = new ArrayList<>(); 
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

  Vertex source;
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
      Vertex actualVertex = vertexQueue.peek();    

      for(Edge edge: actualVertex.adjacentEdge){
        Vertex v = edge.to;

        if( !v.visited){
          if(actualVertex.distance + edge.weight < v.distance){
            v.distance = actualVertex.distance + edge.weight;
            v.predecessor =  actualVertex;     
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