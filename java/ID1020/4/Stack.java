import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class Stack<T> implements Iterable<T> {
  private static class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;
  }

  private Node<T> first;
  private Node<T> last;
  private int total_nodes;

  public Stack() {
    first = null;
    last  = null;
    total_nodes = 0;
  }

  public void AddToBack(T item) {
    Node<T> old = this.last;
    this.last = new Node<T>();
    this.last.item = item;
    
    if (first == null) {
      first = this.last;
    }
    if (old != null) {
      old.next = this.last;
    }

    first.previous = this.last;
   
    this.last.next = first;
    this.last.previous = old;
    total_nodes++;
  }
  
  public void AddToFront(T item) {
    Node<T> old = this.first;
    this.first = new Node<T>();
    this.first.item = item;
    
    if (old != null) {
      old.previous = this.first;
    }
    if (this.last == null) {
      this.last = this.first; 
    }
    first.next = old;
    first.previous = this.last;
    this.last.next = first;
    total_nodes++;
  }
  
  public T PopFromFront() {
    Node<T> old_first = this.first;
    this.first = old_first.next;
    this.first.previous = this.last;
    total_nodes--;
    return old_first.item;
  }
  
  public T PopFromBack() {
    Node<T> old_last = this.last;
    this.last = old_last.previous;
    this.last.next = this.first;
    total_nodes--;
    return old_last.item;
  }
  
  public String toString() {
    StringBuilder data = new StringBuilder();
    int i = 0;
    for(T item : this) {
      data.append("[");
      data.append(item);
      data.append("]");
      data.append(" ");
      if (i++ == (total_nodes - 1)) {
        break;
      }
    }
    return data.toString();
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
