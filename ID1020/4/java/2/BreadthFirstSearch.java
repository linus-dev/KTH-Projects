import edu.princeton.cs.algs4.*;

public class BreadthFirstSearch {

    // prev[v] = previous vertex on shortest path from s to v
    // dist[v] = length of shortest path from s to v
    private ST<String, String>  prev = new ST<String, String>();
    private ST<String, Integer> dist = new ST<String, Integer>();

    // run BFS in graph G from given source vertex s
    public BreadthFirstSearch(Graph G, String s) {

        // put source on the queue
        Queue<String> queue = new Queue<String>();
        queue.enqueue(s);
        dist.put(s, 0);
        
        // repeated remove next vertex v from queue and insert
        // all its neighbors, provided they haven't yet been visited
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

    // is v reachable from the source s?
    public boolean hasPathTo(String v) {
        return dist.contains(v);
    }

    // return the length of the shortest path from v to s
    public int distanceTo(String v) {
        if (!hasPathTo(v)) return Integer.MAX_VALUE;
        return dist.get(v);
    }

    // return the shortest path from v to s as an Iterable
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
