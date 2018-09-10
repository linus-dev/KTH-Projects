class PlineShort {
  public static int shortest(Pline[] args) {
    Pline[] polylines = args;
    double short_poly = 1000;
    for (int i = 0; i < polylines.length; i++) {
      for (Point vertex : polylines[i]) {
        if (vertex.GetColour() == "Yellow" && 
            vertex.GetLength() < short_poly) { 
          short_poly = vertex.GetLength();
        }
      }
    }
    return short_poly;
  }
}
