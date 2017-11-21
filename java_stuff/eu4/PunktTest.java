import java.io.*;
// PrintWriter
class PunktTest {
  public static void main(String[] args) {
    PrintWriter out = new PrintWriter (System.out, true);
    NPolyline poly = new NPolyline();
    Point p1 = new Point("A", 0, 2);
    Point p2 = new Point("B", 0, 4);
    Point p3 = new Point("C", 0, 6);
    Point p4 = new Point("D", 0, 8);
    poly.AddVertex(p1);
    poly.AddVertex(p2);
    poly.AddVertex(p3);
    poly.AddToFront(p4, "C");
    out.println(poly.ToString());
  }
}
