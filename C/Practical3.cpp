#include <iostream>
#include <vector>
#include <queue>
#include <climits>
using namespace std;

typedef pair<int, int> pii; // (weight, vertex)

void primMST(vector<vector<pii>>& adj, int V) {
    vector<int> key(V, INT_MAX);
    vector<int> parent(V, -1);
    vector<bool> inMST(V, false);

    priority_queue<pii, vector<pii>, greater<pii>> pq;
    key[0] = 0;
    pq.push({0, 0});

    while (!pq.empty()) {
        int u = pq.top().second;
        pq.pop();
        inMST[u] = true;

        for (auto &edge : adj[u]) {
            int weight = edge.first;
            int v = edge.second;
            if (!inMST[v] && weight < key[v]) {
                key[v] = weight;
                parent[v] = u;
                pq.push({key[v], v});
            }
        }
    }

    cout << "Prim's MST:\nEdge \tWeight\n";
    for (int i = 1; i < V; i++) {
        cout << parent[i] << " - " << i << "\t" << key[i] << "\n";
    }
}

void dijkstra(vector<vector<pii>>& adj, int V, int src) {
    vector<int> dist(V, INT_MAX);
    dist[src] = 0;

    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, src});

    while (!pq.empty()) {
        int u = pq.top().second;
        pq.pop();

        for (auto &edge : adj[u]) {
            int weight = edge.first;
            int v = edge.second;
            if (dist[u] + weight < dist[v]) {
                dist[v] = dist[u] + weight;
                pq.push({dist[v], v});
            }
        }
    }

    cout << "Dijkstra's Shortest Path:\nVertex \t Distance from Source\n";
    for (int i = 0; i < V; i++) {
        cout << i << " \t " << dist[i] << "\n";
    }
}

int main() {
    int V, E;
    cout << "Enter number of vertices and edges: ";
    cin >> V >> E;

    vector<vector<pii>> adj(V); // adjacency list

    cout << "Enter edges in format: u v weight\n";
    for (int i = 0; i < E; i++) {
        int u, v, w;
        cin >> u >> v >> w;
        adj[u].push_back({w, v});
        adj[v].push_back({w, u}); // undirected graph
    }

    int choice;
    cout << "\nSelect the algorithm to run:\n";
    cout << "1. Prim's Minimal Spanning Tree\n";
    cout << "2. Dijkstra's Single-Source Shortest Path\n";
    cout << "Choice: ";
    cin >> choice;

    switch (choice) {
        case 1:
            primMST(adj, V);
            break;
        case 2: {
            int source;
            cout << "Enter source vertex: ";
            cin >> source;
            dijkstra(adj, V, source);
            break;
        }
        default:
            cout << "Invalid choice!\n";
    }

    return 0;
}
