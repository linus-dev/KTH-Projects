import edu.princeton.cs.algs4.*;

public class SymbolGraph {
  private ST<String, Integer> st; // string -> index
  private String[] keys; // index  -> string
  private EdgeWeightedGraph graph; // the underlying graph
  private Graph graph_b; // the underlying graph

  public SymbolGraph(String filename, String delimiter) {
    st = new ST<String, Integer>();

    In in = new In(filename);
    while (!in.isEmpty()) {
      String[] a = in.readLine().split(delimiter);
      String v = a[0];
      String w = a[1];
      if (!st.contains(v))
        st.put(v, st.size());
      if (!st.contains(w))
        st.put(w, st.size());
    }

    keys = new String[st.size()];
    for (String name : st.keys()) {
      keys[st.get(name)] = name;
    }

    graph = new EdgeWeightedGraph(st.size());
    graph_b = new Graph(st.size());
    in = new In(filename);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(delimiter);
      int v = st.get(a[0]);
      int weight = Integer.parseInt(a[2]);
      for (int i = 1; i < a.length - 1; i++) {
        int w = st.get(a[i]);
        graph.addEdge(new Edge(v, w, weight));
        graph_b.addEdge(v, w);
      }
    }
  }

  public boolean contains(String s) {
    return st.contains(s);
  }

  public int indexOf(String s) {
    return st.get(s);
  }

  public String nameOf(int v) {
    validateVertex(v);
    return keys[v];
  }

  public EdgeWeightedGraph graph() {
    return graph;
  }

  public Graph graph_b() {
    return graph_b;
  }

  private void validateVertex(int v) {
    int V = graph.V();
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  public static void main(String[] args) {
    String filename = args[0];
    String delimiter = args[1];
    SymbolGraph sg = new SymbolGraph(filename, delimiter);
    EdgeWeightedGraph graph = sg.graph();
    DijkstraUndirectedSP dsp = new DijkstraUndirectedSP(graph, sg.indexOf("AL"));
    int total_weight = 0;
    for (Edge v : dsp.pathTo(sg.indexOf("WY"))) {
      int prev = v.either();
      int next = v.other(prev);
      StdOut.println(sg.nameOf(prev) + "-" + sg.nameOf(next));
      total_weight += v.weight();
    }
    StdOut.println("Total weight: " + total_weight);
  }
}
