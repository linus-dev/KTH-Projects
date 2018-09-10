import java.util.*;

class Triangle {
  public static double Area(double length, double height) {
    return (length*height)/2;
  }
  public static double Bisektris(double b, double c, double alpha) {
    double p = 2 * b * c * Math.cos(alpha / 2);
    double bis = p / (b + c);
    return bis;
  }
  public static double Circ(double a, double b, double c) {
    return (a + b + c);
  }
  public static double Median(double a, double b, double c) {
    double median = Math.sqrt((2*Math.pow(b, 2) + 2*Math.pow(c, 2) - 
                              Math.pow(a, 2))/4);
    return median;   
  }
  public static double Circumcircle(double a, double b, double c) {
    double centre = (a+b+c)/2;
    double radius = (a*b*c)/(4*Math.sqrt(centre*(centre-a)*(centre-b)*(centre-c)));
    return radius;
  }
  public static double Incircle(double a, double b, double c) {
    double centre = (a+b+c)/2;
    double radius = Math.sqrt((centre-a)*(centre-b)*(centre-c)/centre);
    return radius;
  }
}
