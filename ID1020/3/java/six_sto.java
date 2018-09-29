import java.util.*;
import edu.princeton.cs.algs4.*;

public class six_sto {
  public static class OST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int n = 0;

    /**
     * Initializes an empty symbol table.
     */
    public OST() {
      this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     * @param capacity the maximum capacity
     */
    public OST(int capacity) { 
      keys = (Key[]) new Comparable[capacity]; 
      vals = (Value[]) new Object[capacity]; 
    }   

    // resize the underlying arrays
    private void resize(int capacity) {
      assert capacity >= n;
      Key[]   tempk = (Key[])   new Comparable[capacity];
      Value[] tempv = (Value[]) new Object[capacity];
      for (int i = 0; i < n; i++) {
        tempk[i] = keys[i];
        tempv[i] = vals[i];
      }
      vals = tempv;
      keys = tempk;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     */
    public int size() {
      return n;
    }
   
    /**
     * Returns true if this symbol table is empty.
     */
    public boolean isEmpty() {
      return size() == 0;
    }

    /**
     * Does this symbol table contain the given key?
     */
    public boolean contains(Key key) {
      if (key == null) throw new IllegalArgumentException("argument to contains() is null");
      return get(key) != null;
    }
    
    /**
     * Removes the specified key and associated value from this symbol table
     * (if the key is in the symbol table).
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null"); 
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);

        // key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < n-1; j++)  {
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }

        n--;
        keys[n] = null;  // to avoid loitering
        vals[n] = null;

        // resize if 1/4 full
        if (n > 0 && n == keys.length/4) resize(keys.length/2);
    } 

    /**
     * Returns the value associated with the given key in this symbol table.
     */
    public Value get(Key key) {
      if (key == null) throw new IllegalArgumentException("argument to get() is null"); 
      if (isEmpty()) return null;
      int i = rank(key); 
      if (i < n && keys[i].compareTo(key) == 0) return vals[i];
      return null;
    } 

    /**
     * Returns the number of keys in this symbol table strictly less than {@code key}.
     */
    public int rank(Key key) {
      if (key == null) throw new IllegalArgumentException("argument to rank() is null"); 

      int lo = 0, hi = n-1; 
      while (lo <= hi) { 
        int mid = lo + (hi - lo) / 2; 
        int cmp = key.compareTo(keys[mid]);
        if      (cmp < 0) hi = mid - 1; 
        else if (cmp > 0) lo = mid + 1; 
        else return mid; 
      } 
      return lo;
    } 



    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     */
    public void put(Key key, Value val)  {
      if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 

      if (val == null) {
        delete(key);
        return;
      }

      int i = rank(key);

      // key is already in table
      if (i < n && keys[i].compareTo(key) == 0) {
        vals[i] = val;
        return;
      }

      // insert new key-value pair
      if (n == keys.length) resize(2*keys.length);

      for (int j = n; j > i; j--)  {
        keys[j] = keys[j-1];
        vals[j] = vals[j-1];
      }
      keys[i] = key;
      vals[i] = val;
      n++;
    } 

   /**
     * Returns the smallest key in this symbol table.
     */
    public Key min() {
      if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
      return keys[0]; 
    }

    /**
     * Returns the largest key in this symbol table.
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return keys[n-1];
    }

    /**
     * Returns the number of keys in this symbol table in the specified range.
     */
    public int size(Key lo, Key hi) {
      if (lo == null) throw new IllegalArgumentException("first argument to size() is null"); 
      if (hi == null) throw new IllegalArgumentException("second argument to size() is null"); 

      if (lo.compareTo(hi) > 0) return 0;
      if (contains(hi)) return rank(hi) - rank(lo) + 1;
      else              return rank(hi) - rank(lo);
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     */
    public Iterable<Key> keys() {
      return keys(min(), max());
    }

    /**
     * Returns all keys in this symbol table in the given range,
     * as an {@code Iterable}.
     */
    public Iterable<Key> keys(Key lo, Key hi) {
      if (lo == null) throw new IllegalArgumentException("first argument to keys() is null"); 
      if (hi == null) throw new IllegalArgumentException("second argument to keys() is null"); 

      edu.princeton.cs.algs4.Queue<Key> queue = new edu.princeton.cs.algs4.Queue<Key>(); 
      if (lo.compareTo(hi) > 0) return queue;
      for (int i = rank(lo); i < rank(hi); i++) 
          queue.enqueue(keys[i]);
      if (contains(hi)) queue.enqueue(keys[rank(hi)]);
      return queue; 
    }
  }

  public static void FrequencyCounter(int min_length) {
    int distinct = 0, words = 0;
    int minlen = min_length;
    OST<String, List> st = new OST<String, List>();

    // compute frequency counts
    int index = 0;
    while (!StdIn.isEmpty()) {
      String key = StdIn.readString();
      if (key.length() < minlen) continue;
      if (st.contains(key)) {
        st.get(key).add(index);
      } else {
        st.put(key, new ArrayList());
        distinct++;
      }
    }

    // find a key with the highest frequency count
    String max = "";
    st.put(max, 0);
    for (String word : st.keys()) {
      if (st.get(word) > st.get(max)) {
        max = word;
      }
    }

    StdOut.println(max + " " + st.get(max));
    StdOut.println("distinct = " + distinct);
    StdOut.println("words    = " + words);
  } 

  public static void main(String[] args) {
    FrequencyCounter(1);
  }
}
