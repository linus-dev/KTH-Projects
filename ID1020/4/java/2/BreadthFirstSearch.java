import edu.princeton.cs.algs4.*;

public class BreadthFirstSearch {
  private ST<String, String> prev = new ST<String, String>();
  private ST<String, Integer> dist = new ST<String, Integer>();

  public BreadthFirstSearch(Graph G, String s) {
    Queue<String> queue = new Queue<String>();
    queue.enqueue(s);
    dist.put(s, 0);

    while (!queue.isEmpty()) {
      String v = queue.dequeue();
      for (String w : G.adj(v)) {
        if (!dist.contains(w)) {
          queue.enqueue(w);
          dist.put(w, 1 + dist.get(v));
          prev.put(w, v);
        }
      }
    }
  }

  public boolean hasPathTo(String v) {
    return dist.contains(v);
  }
  
  public int distanceTo(String v) {
    if (!hasPathTo(v))
      return Integer.MAX_VALUE;
    return dist.get(v);
  }

  public Iterable<String> pathTo(String v) {
    Stack<String> path = new Stack<String>();
    while (v != null && dist.contains(v)) {
      path.push(v);
      v = prev.get(v);
    }
    return path;
  }

  public static void main(String[] args) {
    // create graph
    Graph G = new Graph();
    while (!StdIn.isEmpty()) {
      String v = StdIn.readString();
      String w = StdIn.readString();
      G.addEdge(v, w);
    }

    String s = args[0];
    BreadthFirstSearch pf = new BreadthFirstSearch(G, s);
    for (String t : G.vertices()) {
      StdOut.println(t + "---");
      for (String v : pf.pathTo(t)) {
        StdOut.println("   " + v);
      }
      StdOut.println("distance " + pf.distanceTo(t));
    }
  }
}
