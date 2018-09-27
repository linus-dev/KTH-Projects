import java.util.*;
import edu.princeton.cs.algs4.*;

public class two_bst {
  public static class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    
    private class Node {
      private Key key;
      private Value val;
      private Node left, right;
      private int N;
      
      public Node(Key key, Value val, int N) {
        this.key = key; this.val = val; this.N = N;
      }
    }

    public int size() { 
      return size(root);
    }

    private int size(Node x) {
      if (x == null)
        return 0;
      else
        return x.N;
    }

    public boolean contains(Key key) {
        if (key == null) {
          throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    public Value get(Key key) { 
      return get(root, key);
    }

    private Value get(Node x, Key key) { 
      if (x == null)
        return null;
      int cmp = key.compareTo(x.key);
      if (cmp < 0)
        return get(x.left, key);
      else if (cmp > 0)
        return get(x.right, key);
      else
        return x.val;
    }

    public void put(Key key, Value val) {
      root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
      if (x == null)
        return new Node(key, val, 1);
      int cmp = key.compareTo(x.key);
      if (cmp < 0)
        x.left = put(x.left, key, val);
      else if (cmp > 0)
        x.right = put(x.right, key, val);
      else
        x.val = val;
      x.N = size(x.left) + size(x.right) + 1;
      return x;
    }
  }

  public static void FrequencyCounter(int min_length) {
    int minlen = min_length;
    String key = "";
    int max = 0;
    // key-length cutoff
    BST<String, Integer> st = new BST<String, Integer>();
    while (!StdIn.isEmpty()) {
      int cur = 0;
      // Build symbol table and count frequencies.
      String word = StdIn.readString();
      if (word.length() < minlen) {
        continue; // Ignore short keys.
      }
      if (!st.contains(word)) {
        cur = 1;
        st.put(word, 1);
      } else {
        cur = st.get(word) + 1; 
        st.put(word, cur);
      }
      if (cur > max) {
        key = word;
        max = cur;
      }
    }
    System.out.println("Key: " + key + " Amount: " + max);
  } 

  public static void main(String[] args) {
    FrequencyCounter(1);
  }
}
