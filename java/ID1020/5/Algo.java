/*
 * This demonstrates the FIFO queue.
*/

import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class Algo {
  public static void main(String[] args) {
    FIFOQueue<Integer> test = new FIFOQueue<Integer>();
    test.Add(Integer.valueOf(100));
    test.Add(Integer.valueOf(340));
    test.Add(Integer.valueOf(210));
    test.Add(Integer.valueOf(128));
    test.Remove(1);
    test.Add(Integer.valueOf(256));
    test.Remove(2);
    System.out.println(test.toString());
  }
}

