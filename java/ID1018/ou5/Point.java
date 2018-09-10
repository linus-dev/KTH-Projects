class Point {
  private String id = "";
  private double x = 0;
  private double y = 0;

  /* Constructors */
  public Point(String name, double x, double y) {
    this.id = name;
    this.x = x;
    this.y = y;
  }
  public Point(Point point) {
    this.id = point.GetId();
    this.x = point.GetX();
    this.y = point.GetY();
  }

  public double Distance(Point point) {
    return Math.sqrt(Math.pow(this.x - point.GetX(), 2) +
                     Math.pow(this.y - point.GetY(), 2));
  }
  public boolean Equals(Point point) {
    return (this.x == point.GetX() && this.y == point.GetY());
  }
  public void SetId(String id) {
    this.id = id;
  }
  public String GetId() {
    return this.id;
  }
  public void SetX(double x) {
    this.x = x;
  }
  public void SetY(double y) {
    this.y = y;
  }
  public double GetX() {
    return this.x;
  } 
  public double GetY() {
    return this.y;
  }
}
