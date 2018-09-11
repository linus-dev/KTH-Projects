import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class FIFOQueue<T> implements Iterable<T> {
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
    
    if (first == null) {
      first = this.last;
    }
    if (old != null) {
      old.next = this.last;
      first.previous = this.last;
    }

    this.last.next = null;
    this.last.previous = old;
  }
  
  public T Pop() {
    Node<T> old_first = this.first;
    this.first = old_first.next;
    return old_first.item;
  }
  
  public Iterator<T> iterator()  {
        return new ListIterator<T>(first);  
    }

  private class ListIterator<T> implements Iterator<T> {
    private Node<T> current;

    public ListIterator(Node<T> first) {
      current = first;
    }

    public boolean hasNext()  {
      return current != null;
    }
    
    public void remove() {
      throw new UnsupportedOperationException();
    }

    public T next() {
      if (!hasNext()) throw new NoSuchElementException();
      T item = current.item;
      current = current.next; 
      return item;
    }
  }
}
