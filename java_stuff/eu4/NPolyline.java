public class NPolyline implements Pline {
  private static class Node {
    public Point vertex;
    public Node next_node;
    public Node(Point vertex) {
      this.vertex = vertex;
      this.next_node = null;
    }
  }
  private Node first_node;
  private Node last_node;
  private String colour = "black";
  private int width = 1; /* Pixels */

  public NPolyline() {
    this.first_node = null;
    this.last_node = null;
  }
  public NPolyline(Point[] vertices) {
    if (vertices.length > 0) {
      Node node = new Node(new Point(vertices[0]));
      this.first_node = node;
      int pos = 1;
      while (pos < vertices.length) {
        node.next_node = new Node(new Point(vertices[pos++]));
        node = node.next_node;
      }
      this.last_node = node;
    }
  }
  public String ToString() {
    String result = "";
    if (this.first_node != null) {
      Node node = this.first_node;
      while (node.next_node != null) {
        result = result + node.vertex.GetId() + " --> " + node.next_node.vertex.GetId() + "\n";
        node = node.next_node; 
      }
      result = result + node.vertex.GetId();
    }
    
    return result;
  }
  public Point[] GetVertices() {
    return null;
  }
  public String GetColour() {
    return this.colour;
  }
  public int GetWidth() {
    return this.width;
  }
  public void SetColour(String colour) {
    this.colour = colour;
  }
  public void SetWidth(int width) {
    this.width = width;
  }
  public double GetLength() {
    return 0;
  }
  public void AddVertex(Point vertex) {
    if (this.first_node != null) {
      this.last_node.next_node = new Node(new Point(vertex));
      this.last_node = this.last_node.next_node;
    } else {
      this.first_node = new Node(new Point(vertex));
      this.last_node = this.first_node;
    }
  }
  public void AddToFront(Point vertex, String vertex_name) {
    if (this.first_node != null) {
      Node node = this.first_node;
      while (node.next_node != null) {
        if (node.next_node.vertex.GetId() == vertex_name) {
          Node temp = node.next_node; 
          node.next_node = new Node(new Point(vertex));
          node.next_node.next_node = temp;
          return;
        } else if (this.first_node.vertex.GetId() == vertex_name) {
          Node temp = this.first_node;
          this.first_node = new Node(new Point(vertex));
          first_node.next_node = temp;
        }
        node = node.next_node;
      }
    } else {
      this.first_node = new Node(new Point(vertex));
    }
  }
  public void Remove(String vertex_name) {
  }
  
  public class PolyIterator implements java.util.Iterator<Point> { 
    private Node current = null;
    private Node returned = null;
    public PolyIterator() {
      if (NPolyline.this.first_node != null) {
        current = NPolyline.this.first_node;
      }
    }
    public boolean hasNext() {
      return current.next_node != null;    
    }
    
    public Point next() throws java.util.NoSuchElementException {
      if (this.current == null) {
        throw new java.util.NoSuchElementException("No element");
      }
      returned = current;
      current = current.next_node; 
      return returned.vertex;
    }
  }
  public java.util.Iterator<Point> iterator() {
    return new PolyIterator();
  }
}
