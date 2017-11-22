public class Polyline implements Pline {
  private Point[] vertices;
  private String colour = "black";
  private int width = 1;
  public Polyline() {
    this.vertices = new Point[0];
  }
  /*
    .

    @Parameters
      Point vertex   Vertex to add.
    @return
      void
  */
  public Polyline(Point[] vertices) {
    this.vertices = new Point[vertices.length];
    for (int i = 0; i < vertices.length; i++) {
      this.vertices[i] = new Point(vertices[i]);
    }
  }
  /*
    Return a string of the polyline.

    @Parameters
      No parameters.
    @return
      String Returns polyline in a string. Format: (ID X Y)(ID2 X2 Y2)... 
  */
  public String ToString() {
    String result = "";
    for (int i = 0; i < this.vertices.length; i++) {
      result = result + "(" + this.vertices[i].GetId() + " " + this.vertices[i].GetX() +
               " " + this.vertices[i].GetY() + ")";
    }
    return result;
  }
  /*
    Get vertices of polyline.

    @Parameters
      No parameters.
    @return
      Point[] Array of vertices of the polyline.
  */
  public Point[] GetVertices() {
    return this.vertices;
  }
  /*
    Get colour of polyline.

    @Parameters
      No parameters.
    @return
      String Colour of polyline. 
  */
  public String GetColour() {
    return this.colour;
  }
  /*
    Get width of polyline.

    @Parameters
      No parameters.
    @return
      int Width of polyline. 
  */
  public int GetWidth() {
    return this.width;
  }
  /*
    Set colour of polyline.

    @Parameters
      String colour   Colour of polyline.
    @return
      void
  */
  public void SetColour(String colour) {
    this.colour = colour;
  }
  /*
    Set width of polyline.

    @Parameters
      int width   Width of polyline.
    @return
      void
  */
  public void SetWidth(int width) {
    this.width = width;
  }
  /*
    Get length of polyline.

    @Parameters
      No parameters.
    @return
      double Lenght of polyline. 
  */
  public double GetLength() {
    double length = 0;
    for (int i = 0; i < this.vertices.length-1; i++) {
      length += this.vertices[i].Distance(this.vertices[i + 1]);
    }
    return length;
  }
  /*
    Add vertex to end of array.

    @Parameters
      Point vertex   Vertex to add.
    @return
      void
  */
  public void AddVertex(Point vertex) {
    Point[] new_vertices = new Point[this.vertices.length + 1];
    int i = 0;
    for (i = 0; i < this.vertices.length; i++) {
      new_vertices[i] = this.vertices[i];
    }
    new_vertices[i] = new Point(vertex);
    this.vertices = new_vertices;
  }
  /*
    Add vertex in front of another vertex.

    @Parameters
      Point vertex   Vertex to add.
      String vertex_name Name/ID of vertex to add infront.
    @return
      void
  */
  public void AddToFront(Point vertex, String vertex_name) {
    Point[] new_vertices = new Point[this.vertices.length + 1];
    Point new_vertex = new Point(vertex);
    int i = 0;
    int j = 0;
    for (i = 0; i < this.vertices.length; i++) {
      if (this.vertices[i].GetId() == vertex_name) { 
        new_vertices[i] = new_vertex;
        new_vertices[i + 1] = this.vertices[i];
        i++;
      } else {
        new_vertices[i] = this.vertices[j];
      }
      j++;
    }
    i = i >= new_vertices.length ? (new_vertices.length - 1) : i;
    new_vertices[i] = this.vertices[i-1];
    this.vertices = new_vertices;
  }
  /*
    Remove vertex from array.

    @Parameters
      String vertex_name   Vertex name/id to remove.
    @return
      void
  */
  public void Remove(String vertex_name) {
    int index_to_ignore = -1;
    /* TODO: EXISTS IN POLYLINE, MAKE METHOD */
    for (int i = 0; i < this.vertices.length; i++) {
      if (this.vertices[i].GetId() == vertex_name) {
        index_to_ignore = i; 
      }
    }
    if (index_to_ignore != -1) {
      int j = 0;
      Point[] new_vertices = new Point[this.vertices.length - 1];
      for (int i = 0; i < this.vertices.length; i++) {
        if (i != index_to_ignore) {
          if (i == (this.vertices.length - 1)) {
            new_vertices[i-1] = this.vertices[i];
          } else {
            new_vertices[j] = this.vertices[i];
          }
          j++;
        }
      }
      this.vertices = new_vertices;
    }
  }
  
  public class PolyIterator implements java.util.Iterator<Point> {
    private int current = -1;
    public PolyIterator() {
      if (Polyline.this.vertices.length > 0) {
        current = 0;
      }
    }
    public boolean hasNext() {
      return Polyline.this.vertices[current + 1] != null;
    }
    public Point next() throws java.util.NoSuchElementException {
      if (this.current == -1 || Polyline.this.vertices[current] == null)  {
        throw new java.util.NoSuchElementException("End of iteration");
      }
      Point vertex = Polyline.this.vertices[current];
      current++;
      return vertex;
    }
  }
  public java.util.Iterator<Point> iterator() {
    return new PolyIterator();
  }
}
