import java.util.*;
import edu.princeton.cs.algs4.*;

public class two_bst {
  public static class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Returns true if this symbol table is empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) {
          return 0;
        } else {
          return x.size;
        }
    }

    /**
     * Does this symbol table contain the given key?
     */
    public boolean contains(Key key) {
        if (key == null) {
          throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null) {
          throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
          return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
          return get(x.left, key);
        } else if (cmp > 0){
          return get(x.right, key);
        } else {
          return x.val;
        }
    }
   
 
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    } 
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 
    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 
   public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
   /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     */
    public void delete(Key key) {
      if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
      root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
      if (x == null) return null;

      int cmp = key.compareTo(x.key);
      if      (cmp < 0) x.left  = delete(x.left,  key);
      else if (cmp > 0) x.right = delete(x.right, key);
      else { 
        if (x.right == null) return x.left;
        if (x.left  == null) return x.right;
        Node t = x;
        x = min(t.right);
        x.right = deleteMin(t.right);
        x.left = t.left;
      } 
      x.size = size(x.left) + size(x.right) + 1;
      return x;
    } 

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     */
    public void put(Key key, Value val) {
        if (key == null) {
          throw new IllegalArgumentException("calls put() with a null key");
        }
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
          return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
          x.left  = put(x.left,  key, val);
        } else if (cmp > 0) {
          x.right = put(x.right, key, val);
        } else {
          x.val   = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     */
    public void deleteMax() {
        if (isEmpty()) {
          throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
          return x.left;
        }
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * Returns the largest key in the symbol table.
     */
    public Key max() {
      if (isEmpty()) {
        throw new NoSuchElementException("calls max() with empty symbol table");
      }
      return max(root).key;
    } 

    private Node max(Node x) {
      if (x.right == null) {
        return x; 
      }
      else {
        return max(x.right); 
      }
    } 

    public Iterable<Key> keys() {
      if (isEmpty()) {
        return new edu.princeton.cs.algs4.Queue<Key>();
      }
      return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an Iterable.
     */
    public Iterable<Key> keys(Key lo, Key hi) {
      if (lo == null) {
        throw new IllegalArgumentException("first argument to keys() is null");
      }
      if (hi == null) {
        throw new IllegalArgumentException("second argument to keys() is null");
      }

      edu.princeton.cs.algs4.Queue<Key> queue = new edu.princeton.cs.algs4.Queue<Key>();
      keys(root, queue, lo, hi);
      return queue;
    } 

    private void keys(Node x, edu.princeton.cs.algs4.Queue<Key> queue, Key lo, Key hi) { 
      if (x == null) {
        return; 
      }
      int cmplo = lo.compareTo(x.key); 
      int cmphi = hi.compareTo(x.key); 
      if (cmplo < 0) {
        keys(x.left, queue, lo, hi); 
      }
      if (cmplo <= 0 && cmphi >= 0) {
        queue.enqueue(x.key); 
      }
      if (cmphi > 0) {
        keys(x.right, queue, lo, hi); 
      }
    } 

    /**
     * Returns the number of keys in the symbol table in the given range.
     */
    public int size(Key lo, Key hi) {
      if (lo == null) {
        throw new IllegalArgumentException("first argument to size() is null");
      }
      if (hi == null) {
        throw new IllegalArgumentException("second argument to size() is null");
      }

      if (lo.compareTo(hi) > 0) {
        return 0;
      }
      if (contains(hi)) {
        return rank(hi) - rank(lo) + 1;
      } else {
        return rank(hi) - rank(lo);
      }
    }
  }

  public static void FrequencyCounter(int min_length) {
    int distinct = 0, words = 0;
    int minlen = min_length;
    BST<String, Integer> st = new BST<String, Integer>();

    // compute frequency counts
    while (!StdIn.isEmpty()) {
      String key = StdIn.readString();
      if (key.length() < minlen) continue;
      words++;
      if (st.contains(key)) {
        st.put(key, st.get(key) + 1);
      }
      else {
        st.put(key, 1);
        distinct++;
      }
    }

    // find a key with the highest frequency count
    String max = "";
    st.put(max, 0);
    for (String word : st.keys()) {
      if (st.get(word) > st.get(max))
          max = word;
    }

    StdOut.println(max + " " + st.get(max));
    StdOut.println("distinct = " + distinct);
    StdOut.println("words    = " + words);
  } 

  public static void main(String[] args) {
    FrequencyCounter(1);
  }
}
