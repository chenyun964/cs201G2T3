package sg.edu.smu.app.datastructures;

/**
 * Each MinNode will store the value of its node and cost and will also point to
 * a prev minimum Node which could be itself or not
 */
public class MinNode {
  public int node, cost;
  public MinNode prevMin;

  public MinNode() {
  }
  
  public MinNode(int node, int cost) {
    this.node = node;
    this.cost = cost;
  }

  public MinNode getPrevMin() {
    return prevMin;
  }

  public void setPrevMin(MinNode node) {
    this.prevMin = node;
  }
}
