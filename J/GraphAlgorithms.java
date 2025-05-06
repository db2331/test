import java.util.*;

public class GraphAlgorithms {

    static final int MAX = 100;
    
    // Function to find the vertex with the minimum key value
    public static int minKey(int key[], boolean mstSet[], int V) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    // Function to implement Prim's algorithm for MST
    public static void primMST(int[][] graph, int V) {
        int[] parent = new int[V];
        int[] key = new int[V];
        boolean[] mstSet = new boolean[V];
        
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(mstSet, false);
        key[0] = 0;
        parent[0] = -1;
        
        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet, V);
            mstSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }
        System.out.println("Prim's MST:");
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
        }
    }

    // Function to implement Dijkstra's algorithm for shortest path
    public static void dijkstra(int[][] graph, int V, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.dist));
        pq.add(new Pair(0, src));

        while (!pq.isEmpty()) {
            int u = pq.poll().vertex;

            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    pq.add(new Pair(dist[v], v));
                }
            }
        }

        // Display the shortest paths
        System.out.println("Dijkstra's Shortest Path:");
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < V; i++) {
            System.out.println(i + " \t " + dist[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of vertices
        System.out.print("Enter the number of vertices: ");
        int V = sc.nextInt();
        int[][] graph = new int[MAX][MAX];

        // Input adjacency matrix
        System.out.println("Enter the adjacency matrix (use 0 for no edge):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = sc.nextInt();
            }
        }

        // Menu for algorithm selection
        System.out.println("\nSelect the algorithm to run:");
        System.out.println("1. Prim's Minimal Spanning Tree");
        System.out.println("2. Dijkstra's Single-Source Shortest Path");
        System.out.print("Choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                primMST(graph, V);
                break;
            case 2:
                System.out.print("Enter the source vertex: ");
                int source = sc.nextInt();
                dijkstra(graph, V, source);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }

    // Pair class to help with the priority queue in Dijkstra's algorithm
    static class Pair {
        int dist;
        int vertex;

        Pair(int dist, int vertex) {
            this.dist = dist;
            this.vertex = vertex;
        }
    }
}
