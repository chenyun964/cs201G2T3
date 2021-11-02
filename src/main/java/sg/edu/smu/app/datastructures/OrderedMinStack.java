package sg.edu.smu.app.datastructures;

/**
 * This file contains the Stack data structure used in Dijkstra + Stack implementation
 */

import java.util.Stack;

public class OrderedMinStack {

  private static Stack<CustomNode> leftStack = new Stack<CustomNode>();
  private static Stack<CustomNode> rightStack = new Stack<CustomNode>();
  private static Stack<CustomNode> currentStack = leftStack;
  private static Stack<CustomNode> altStack = rightStack;

  public OrderedMinStack() {
  }

  public static void main(String[] args) {
  }

  public void set(CustomNode node, Stack<CustomNode> stack, CustomNode target) {
    node.prevMin = target; // set as ownself
    stack.add(node);
  }

  public boolean checkAlt(CustomNode current) {
    if (!altStack.isEmpty() && altStack.peek().prevMin.cost < current.cost)
      return true;
    return false;
  }

  // insert, check prevMin against altStack
  public void insert(CustomNode node) {
    if (currentIsEmpty()) { // worst 5
      if (!checkAlt(node)) {
        set(node, currentStack, node);
        return;
      } else
        set(node, currentStack, altStack.peek().prevMin);
      return;
    }

    if (currentStack.peek().prevMin.cost >= node.cost) { // if prev = null or cost more
      if (!checkAlt(node)) { // worst 6
        set(node, currentStack, node);
        return;
      } else
        set(node, currentStack, altStack.peek().prevMin);
      return;
    }
    
    transfer(node); // worst 4
  }

  public void updatePrevMin(CustomNode current) { // update current prevMin to alt's
    if (!altStack.isEmpty() && altStack.peek().prevMin.cost < current.prevMin.cost) {
      current.prevMin = altStack.peek().prevMin;
    }
  }

  public void transfer(CustomNode current) {
    if (altIsEmpty())
      set(current, altStack, current); // transfer larger values away
    else {
      if (altStack.peek().prevMin.cost >= current.cost)
        set(current, altStack, current);
      else
        set(current, altStack, altStack.peek().prevMin);
    }
  }

  public CustomNode removeMin() { // potentially n
    CustomNode current = currentStack.pop();
    updatePrevMin(current);

    if (currentIsEmpty())
      swap();

    while (!currentIsEmpty() && current.prevMin.cost != current.cost) { // loop until found min
      transfer(current);
      current = currentStack.pop();
    }
    updatePrevMin(current);

    if (current.prevMin.cost == current.cost && !currentIsEmpty()) {
      while (currentStack.peek().prevMin.cost > current.cost + 1) { // prevMin larger then current
        CustomNode transfer = currentStack.pop();
        transfer(transfer);
        if (currentIsEmpty())
          break;
      }
    }

    if (currentIsEmpty())
      swap();
    return current.prevMin;
  }

  public void swap() {
    if (currentStack.equals(leftStack)) {
      currentStack = rightStack;
      altStack = leftStack;
      return;
    }
    currentStack = leftStack;
    altStack = rightStack;
  }

  public boolean currentIsEmpty() {
    return currentStack.isEmpty();
  }

  public boolean altIsEmpty() {
    return altStack.isEmpty();
  }
}