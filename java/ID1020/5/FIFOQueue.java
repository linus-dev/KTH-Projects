/*
 * This implements a FIFO Queue in a class using generics.
*/
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
  private int total_nodes;

  public FIFOQueue() {
    first = null;
    last  = null;
    total_nodes = 0;
  }
  /*
   *  Add to Queue.
   */
  public void Add(T item) {
    Node<T> old = this.last;
    this.last = new Node<T>();
    this.last.item = item;
    /* If first is null, then this is the first item ever added. */ 
    if (first == null) {
      first = this.last;
    }
    if (old != null) {
      old.next = this.last;
      first.previous = null;
    }
    this.last.next = null;
    this.last.previous = old;
    total_nodes++;
  }
  
  /*
   *  Pop element from the queue (FIFO).
   */
  public T Pop() {
    Node<T> old_first = this.first;
    this.first = old_first.next;
    total_nodes--;
    return old_first.item;
  }
  
  public void Remove(int index) {
    int current_index = total_nodes; 
    Iterator<T> i = this.iterator();
    while (i.hasNext()) {
      if (index == current_index) {
        i.remove();
        break;
      }
      current_index--;
      i.next();
    }
  }

  public String toString() {
    StringBuilder data = new StringBuilder();
    int i = 0;
    for(T item : this) {
      data.append("[");
      data.append(item);
      data.append("]");
      data.append(" ");
    }
    return data.toString();
  }

  /* I can not be fucking bothered... */
  private void SetFirstNext() {
    this.first = first.next;
  }
  private void SetLastPrevious() {
    this.last = last.previous;
  }

  public Iterator<T> iterator()  {
        return new ListIterator<T>(first);  
    }

  private class ListIterator<E> implements Iterator<E> {
    private Node<E> current;

    public ListIterator(Node<E> first) {
      current = first;
    }

    public boolean hasNext()  {
      return current != null;
    }
    
    public void remove() {
      Node<E> deleted = current;
      if (deleted.previous != null) {
        deleted.previous.next = deleted.next;
      } else {
        SetFirstNext();
      }
      if (deleted.next != null) {
        deleted.next.previous = deleted.previous;
      } else {
        SetLastPrevious();
      }
      total_nodes--;
    }

    public E next() {
      if (!hasNext()) throw new NoSuchElementException();
      E item = current.item;
      current = current.next; 
      return item;
    }
  }
}
