import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class Algo {
  public static void main(String[] args) {
    FIFOQueue<Integer> test = new FIFOQueue<Integer>();
    test.Add(Integer.valueOf(100));
    test.Add(Integer.valueOf(340));
    test.Add(Integer.valueOf(210));
    System.out.println(test.Pop());
    System.out.println(test.Pop());
    System.out.println("---");
  }
  
  public void test() {
    System.out.println("lmao"); 
  }
}

