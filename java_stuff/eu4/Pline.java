public interface Pline {
  Point[] GetVertices();
 
  String GetColour();
  void SetColour(String colour);
  
  int GetWidth();
  void SetWidth(int width);

//  double Length();
  
  void AddVertex(Point vertex);
  void AddToFront(Point vertex, String vertex_name);

  void Remove(String vertex_name);
  java.util.Iterator<Point> iterator();
}
