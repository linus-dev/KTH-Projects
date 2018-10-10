import edu.princeton.cs.algs4.*;

public class DepthFirstSearch {
    private SET<String> marked; 
    private int count;
    private final String s;   

    public DepthFirstSearch(Graph G, String s) {
      this.s = s;
      marked = new SET<String>();
      validateVertex(G, s);
      dfs(G, s);
    }

    private void dfs(Graph G, String v) {
      count++;
      if (!marked.contains(v)) { 
        marked.add(v);
      }
      for (String w : G.adj(v)) {
        if (!marked.contains(w)) {
          dfs(G, w);
        }
      }
    }
    

    public Iterable<Integer> pathTo(Graph G, String goal) {
      Stack<String> path = new Stack<String>();
      for (String sAdj : G.adj(s)) {
        findShortest(G, sAdj, path, goal);
      }
      return path;
    }
    
    private boolean findShortest(Graph G, String current, Stack<String> st, String goal) {
      st.push(current);
      if (current == goal) {
        return true; 
      }
      for (String adj : G.adj(current)) {
        findShortest(G, adj, st, length, goal);
      }
      return false;
    }

    public boolean marked(String v) {
        return marked.contains(v);
    }

    public int count() {
        return count;
    }

    private void validateVertex(Graph G, String v) {
        if (!G.hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
    }

    public static void main(String[] args) {
        Graph G = new Graph();
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            G.addEdge(v, w);
        }

        DepthFirstSearch search = new DepthFirstSearch(G, "AL");
        for (String v : G.vertices()) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }
        search.pathTo(G, "FL");
        StdOut.println();
        if (search.count() != G.V()) StdOut.println("NOT connected");
        else                         StdOut.println("connected");
    }
}
