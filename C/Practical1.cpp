//Implement depth first search algorithm and Breadth First Search algorithm, use an undirected graph and 
//develop a recursive algorithm for searching all the vertices of a graph or tree data structure.
#include <iostream>
#include <vector>
using namespace std;

class Graph {
    int V;                       // Number of vertices
    vector<vector<int>> adj;     // Adjacency list

public:
    Graph(int V) {
        this->V = V;
        adj = vector<vector<int>>(V);
    }

    // Add an edge (undirected)
    void addEdge(int v, int w) {
        adj[v].push_back(w);
    }

    // Recursive BFS helper
    void BFSRec(vector<int> currentLevel, vector<bool> &visited) {
        if (currentLevel.empty()) return;
        vector<int> nextLevel;
        for (int u : currentLevel) {
            cout << u << " ";
            for (int neighbor : adj[u]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    nextLevel.push_back(neighbor);
                }
            }
        }
        BFSRec(nextLevel, visited);
    }

    // BFS traversal from a starting node
    void BFS(int s) {
        vector<bool> visited(V, false);
        visited[s] = true;
        vector<int> currentLevel = {s};
        cout << "BFS: ";
        BFSRec(currentLevel, visited);
        cout << endl;
    }

    // Recursive DFS
    void DFSRec(int s, vector<bool> &visited) {
        visited[s] = true;
        cout << s << " ";
        for (int neighbor : adj[s]) {
            if (!visited[neighbor]) {
                DFSRec(neighbor, visited);
            }
        }
    }

    // DFS for all components
    void DFS() {
        vector<bool> visited(V, false);
        cout << "DFS: ";
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                DFSRec(i, visited);
            }
        }
        cout << endl;
    }
};

int main() {
    int n, e, e1, e2, c;
    cout << "Enter number of nodes: ";
    cin >> n;
    cout << "Enter number of edges: ";
    cin >> e;

    Graph g(n);
    cout << "Enter edges (0-based node numbers):\n";
    for (int i = 0; i < e; i++) {
        cin >> e1 >> e2;
        if (e1 < 0 || e1 >= n || e2 < 0 || e2 >= n) {
            cout << "Invalid edge: " << e1 << " " << e2 << endl;
            continue;
        }
        g.addEdge(e1, e2); 
        g.addEdge(e2, e1);
    }

    do {
        cout << "\nSelect operation:\n1. BFS\n2. DFS\n3. Exit\nChoice: ";
        cin >> c;
        switch (c) {
            case 1: {
                int start;
                cout << "Enter starting node for BFS (0-based): ";
                cin >> start;
                if (start < 0 || start >= n) {
                    cout << "Invalid starting node!" << endl;
                    break;
                }
                g.BFS(start);
                break;
            }
            case 2:
                g.DFS();
                break;
            case 3:
                cout << "Exiting...\n";
                break;
            default:
                cout << "Invalid choice!\n";
        }
    } while (c != 3);

    return 0;
}
