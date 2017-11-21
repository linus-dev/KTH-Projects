class trig_test {
  public static void main(String[] args) {
    for(int i = 0; i <= 3; i++) {
      double new_pi = Math.PI/2 + Math.PI * Math.floor(i/2);
      int sin = (int)(Math.sin(Math.PI / 2 + Math.PI * Math.floor(i/2)));
      int cos = (int)(Math.cos(Math.PI * i));
      System.out.println(sin + " " + cos + "New PI: " + new_pi);
    }
  }
}
