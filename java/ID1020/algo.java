import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class CharNode {
  public char c;
  public CharNode prev_node;
}

class algo {
  public static void main(String[] args) {
    CharNode first = ReadC(null);
    PrintText(first);
  }

  private static void PrintText(CharNode node) {
    while (node != null) {
      System.out.print(node.c);
      node = node.prev_node;
    }
  }

  private static CharNode ReadC(CharNode prev_node) {
    char input = StdIn.readChar();
    if (input != '\n') {
      CharNode node = new CharNode();
      node.prev_node = prev_node;
      node.c = input;
      return ReadC(node);
    }
    return prev_node;
  }
}

