public class Test {
    
    public static void main(String[] args){

    //  Node A = new Node("A");
    //  Node B = new Node("B");
    //  Node C = new Node("C");
    //  Node D = new Node("D");
    //  Node E = new Node("E");
    //  Node F = new Node("F");
    //  Node G = new Node("G");
    //  Node H = new Node("H");

    //  A.addAdjacentNodes(B, 20);
    //  A.addAdjacentNodes(G, 90);
    //  A.addAdjacentNodes(D, 80);  
    //  B.addAdjacentNodes(F, 10);
    //  C.addAdjacentNodes(H, 20);
    //  C.addAdjacentNodes(F, 50);
    //  C.addAdjacentNodes(D, 10);
    //  D.addAdjacentNodes(G, 20);
    //  D.addAdjacentNodes(C, 10);
    //  E.addAdjacentNodes(B, 50);
    //  E.addAdjacentNodes(G, 30);
    //  F.addAdjacentNodes(C, 10);
    //  F.addAdjacentNodes(D, 40);
    //  Node[] list = {A, B, C, D, E, F, G, H};
    //  Dijkstra test = new Dijkstra(list, A);

    Node Alice = new Node("Alice");
    Node Bob = new Node("Bob");
    Node Tom = new Node("Tom");
    Node Jerry = new Node("Jerry");
    Node Ali = new Node("Ali");
    Node Cariene = new Node("Cariene");
    Node David = new Node("David");
    Node Tim = new Node("Tim");
    Node Don = new Node("Don");
    Node Gary = new Node("Gary");

    Alice.addAdjacentNodes(Bob, 1);
    Alice.addAdjacentNodes(Ali, 1);
    Alice.addAdjacentNodes(Cariene, 1);  
    Alice.addAdjacentNodes(David, 1);
    Tom.addAdjacentNodes(Tim, 1);
    Tom.addAdjacentNodes(Don, 1);
    Jerry.addAdjacentNodes(Gary, 1);

    Node[] list = {Alice, Bob, Cariene, David, Tom, Ali, Tim, Don, Gary, Jerry};
    Dijkstra test = new Dijkstra(list, Alice);


    // String [] users = {"Alice", "Tom", "Jerry"};
    // String [][] userFriends = { { "Ali", "Bob", "Cariene" , "David"}, { "Tim", "Bryan", "Catlin", "Don"},{"Gary", "Zen", "Cariene"} };
    
    // for (int i = 0; i < users.length; i++){
    //         for (int j = 0; j < userFriends[i].length; j++){
    //             System.out.println("user[" + i + "][" + j + "] = " + userFriends[i][j]);
    //             Node users[i] = new Node(user[i]);
    //         }
    // }

    }
   }