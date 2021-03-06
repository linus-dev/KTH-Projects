/*
 * The program shows an example of a circular linked list.
 */
import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class Stack<T> implements Iterable<T> {
  private static class Node<T> {
    private T item;
    private Node<T> next;
    private Node<T> previous;
  }
  
  /* First, last, and total nodes. */
  private Node<T> first;
  private Node<T> last;
  private int total_nodes;

  public Stack() {
    first = null;
    last  = null;
    total_nodes = 0;
  }
  

  /**
    * Add item to the back of the queue.
    * @author Linus Berg 
    * @param <T> : Item to add.
    * @date 12/09/2018
    */
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
  
  /**
    * Add item to the front of the queue.
    * @author Linus Berg 
    * @param <T> : Item to add.
    * @date 12/09/2018
    */
  public void AddToFront(T item) {
    Node<T> old = this.first;
    this.first = new Node<T>();
    this.first.item = item;
   
    /* If the old is not null then we have to set previous ( circular ) */
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
  
  /**
    * Pop item from the front of the queue.
    * @author Linus Berg 
    * @return <T> : Item in front node.
    * @date 12/09/2018
    */
  public T PopFromFront() {
    if (this.first != null) {
      Node<T> old_first = this.first;
      this.first = old_first.next;
      this.first.previous = this.last;
      total_nodes--;
      return old_first.item;
    }
  }
  
  /**
    * Pop item from the back of the queue.
    * @author Linus Berg 
    * @return <T> : Item in back node.
    * @date 12/09/2018
    */
  public T PopFromBack() {
    if (this.last != null) {
      Node<T> old_last = this.last;
      this.last = old_last.previous;
      this.last.next = this.first;
      total_nodes--;
      return old_last.item;
    }
  }
  
  /**
    * Text based representation of the nodes.
    * @author Linus Berg 
    * @return String : String from stringbuilder.
    * @date 12/09/2018
    */
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
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T item = current.item;
      current = current.next; 
      return item;
    }
  }
  
  public static void main(String[] args) {
    Stack<Integer> test = new Stack<Integer>();
    test.PopFromBack();
    test.AddToBack(Integer.valueOf(100));
    test.AddToFront(Integer.valueOf(340));
    test.AddToBack(Integer.valueOf(210));
    System.out.println(test.toString());
    System.out.println("---");
  }

}
