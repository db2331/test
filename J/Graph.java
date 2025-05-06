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

    private void BFSRec(ArrayList<Integer> currentLevel, boolean[] visited) {
        if (currentLevel.isEmpty()) return;

        ArrayList<Integer> nextLevel = new ArrayList<>();
        for (int u : currentLevel) {
            System.out.print(u + " ");
            for (int neighbor : adj.get(u)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    nextLevel.add(neighbor);
                }
            }
        }
        BFSRec(nextLevel, visited);
    }

    public void BFS(int s) {
        boolean[] visited = new boolean[V];
        visited[s] = true;
        ArrayList<Integer> currentLevel = new ArrayList<>();
        currentLevel.add(s);
        System.out.print("BFS: ");
        BFSRec(currentLevel, visited);
        System.out.println();
    }

    private void DFSRec(int s, boolean[] visited) {
        visited[s] = true;
        System.out.print(s + " ");
        for (int neighbor : adj.get(s)) {
            if (!visited[neighbor]) {
                DFSRec(neighbor, visited);
            }
        }
    }

    public void DFS() {
        boolean[] visited = new boolean[V];
        System.out.print("DFS: ");
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                DFSRec(i, visited);
            }
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();
        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        Graph g = new Graph(n);

        System.out.println("Enter edges (0-based node numbers):");
        for (int i = 0; i < e; i++) {
            int e1 = sc.nextInt();
            int e2 = sc.nextInt();
            if (e1 < 0 || e1 >= n || e2 < 0 || e2 >= n) {
                System.out.println("Invalid edge: " + e1 + " " + e2);
                continue;
            }
            g.addEdge(e1, e2);
        }
        
        int c;
        do {
            System.out.println("\nSelect operation:");
            System.out.println("1. BFS");
            System.out.println("2. DFS");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            c = sc.nextInt();

            switch (c) {
                case 1: {
                    System.out.print("Enter starting node for BFS (0-based): ");
                    int start = sc.nextInt();
                    if (start < 0 || start >= n) {
                        System.out.println("Invalid starting node!");
                        break;
                    }
                    g.BFS(start);
                    break;
                }
                case 2:
                    g.DFS();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (c != 3);

        sc.close();
    }
}
