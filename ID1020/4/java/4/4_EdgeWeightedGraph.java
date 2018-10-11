import edu.princeton.cs.algs4.*;

public class EdgeWeightedGraph {
  private static final String NEWLINE = System.getProperty("line.separator");

  private final int V;
  private int E;
  private Bag<Edge>[] adj;

  public EdgeWeightedGraph(int V) {
    if (V < 0)
      throw new IllegalArgumentException("Number of vertices must be nonnegative");
    this.V = V;
    this.E = 0;
    adj = (Bag<Edge>[]) new Bag[V];
    for (int v = 0; v < V; v++) {
      adj[v] = new Bag<Edge>();
    }
    StdOut.println(V);
  }

  public EdgeWeightedGraph(int V, int E) {
    this(V);
    if (E < 0)
      throw new IllegalArgumentException("Number of edges must be nonnegative");
    for (int i = 0; i < E; i++) {
      int v = StdRandom.uniform(V);
      int w = StdRandom.uniform(V);
      double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
      Edge e = new Edge(v, w, weight);
      addEdge(e);
    }
  }

  public EdgeWeightedGraph(In in) {
    this(in.readInt());
    int E = in.readInt();
    if (E < 0)
      throw new IllegalArgumentException("Number of edges must be nonnegative");
    for (int i = 0; i < E; i++) {
      int v = in.readInt();
      int w = in.readInt();
      validateVertex(v);
      validateVertex(w);
      double weight = in.readDouble();
      Edge e = new Edge(v, w, weight);
      addEdge(e);
    }
  }

  public EdgeWeightedGraph(EdgeWeightedGraph G) {
    this(G.V());
    this.E = G.E();
    for (int v = 0; v < G.V(); v++) {
      // reverse so that adjacency list is in same order as original
      Stack<Edge> reverse = new Stack<Edge>();
      for (Edge e : G.adj[v]) {
        reverse.push(e);
      }
      for (Edge e : reverse) {
        adj[v].add(e);
      }
    }
  }

  /**
   * Returns the number of vertices in this edge-weighted graph.
   *
   * @return the number of vertices in this edge-weighted graph
   */
  public int V() {
    return V;
  }

  /**
   * Returns the number of edges in this edge-weighted graph.
   *
   * @return the number of edges in this edge-weighted graph
   */
  public int E() {
    return E;
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertex(int v) {
    if (v < 0 || v >= V)
      throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
  }

  /**
   * Adds the undirected edge {@code e} to this edge-weighted graph.
   *
   * @param  e the edge
   * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
   */
  public void addEdge(Edge e) {
    int v = e.either();
    int w = e.other(v);
    validateVertex(v);
    validateVertex(w);
    adj[v].add(e);
    adj[w].add(e);
    E++;
  }

  /**
   * Returns the edges incident on vertex {@code v}.
   *
   * @param  v the vertex
   * @return the edges incident on vertex {@code v} as an Iterable
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public Iterable<Edge> adj(int v) {
    validateVertex(v);
    return adj[v];
  }

  /**
   * Returns the degree of vertex {@code v}.
   *
   * @param  v the vertex
   * @return the degree of vertex {@code v}
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public int degree(int v) {
    validateVertex(v);
    return adj[v].size();
  }

  /**
   * Returns all edges in this edge-weighted graph.
   * To iterate over the edges in this edge-weighted graph, use foreach notation:
   * {@code for (Edge e : G.edges())}.
   *
   * @return all edges in this edge-weighted graph, as an iterable
   */
  public Iterable<Edge> edges() {
    Bag<Edge> list = new Bag<Edge>();
    for (int v = 0; v < V; v++) {
      int selfLoops = 0;
      for (Edge e : adj(v)) {
        if (e.other(v) > v) {
          list.add(e);
        }
        // add only one copy of each self loop (self loops will be consecutive)
        else if (e.other(v) == v) {
          if (selfLoops % 2 == 0)
            list.add(e);
          selfLoops++;
        }
      }
    }
    return list;
  }

  /**
   * Returns a string representation of the edge-weighted graph.
   * This method takes time proportional to <em>E</em> + <em>V</em>.
   *
   * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
   *         followed by the <em>V</em> adjacency lists of edges
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(V + " " + E + NEWLINE);
    for (int v = 0; v < V; v++) {
      s.append(v + ": ");
      for (Edge e : adj[v]) {
        s.append(e + "  ");
      }
      s.append(NEWLINE);
    }
    return s.toString();
  }

  /**
   * Unit tests the {@code EdgeWeightedGraph} data type.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    In in = new In(args[0]);
    EdgeWeightedGraph G = new EdgeWeightedGraph(in);
    StdOut.println(G);
  }
}

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
