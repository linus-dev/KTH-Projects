import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class FIFOQueue<T> {
  private static class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;
  }

  private Node<T> first;
  private Node<T> last;

  public FIFOQueue() {
    first = null;
    last  = null;
  }

  public void Add(T item) {
    Node<T> old = this.last;
    this.last = new Node<T>();
    this.last.item = item;
    this.last.next = null;
    this.last.previous = old;
    if (first == null) {
      first = this.last;
    }
    if (old != null) {
      old.next = this.last;
    }
  }

  public void test() {
    Node<T> current = first;
    while(current != null) {
      System.out.print(current.item);
      current = current.next;
    }
  }

}

class algo_d {
  public static void main(String[] args) {
    FIFOQueue<Integer> test = new FIFOQueue<Integer>();
    test.Add(Integer.valueOf(100));
    test.test();
  }
}

