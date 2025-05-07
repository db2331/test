import java.util.*;

class Graph {
    int V; 
    ArrayList<ArrayList<Integer>> adj; 

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v); 
    }

    public void BFS(int s) {
        boolean[] visited = new boolean[V];