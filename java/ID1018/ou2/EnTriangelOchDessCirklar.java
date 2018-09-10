class EnTriangelOchDessCirklar {
  public static void main(String[] args) {
    java.util.Scanner in = new java.util.Scanner(System.in);
    double triangle_sides[] = new double[3];

    System.out.println("Input triangle sides:");
    System.out.print("A: "); 
    triangle_sides[0] = in.nextDouble();
    System.out.print("B: ");
    triangle_sides[1] = in.nextDouble();
    System.out.print("C: ");
    triangle_sides[2] = in.nextDouble();
    System.out.println("Outer circle: " + 
                       Triangle.Circumcircle(triangle_sides[0],
                                             triangle_sides[1],
                                             triangle_sides[2]));
    System.out.println("Inner circle: " +
                       Triangle.Incircle(triangle_sides[0], triangle_sides[1],
                                         triangle_sides[2]));
  }
}
