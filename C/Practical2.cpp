#include <bits/stdc++.h>
using namespace std;

#define N 3

int drow[] = {1, 0, -1, 0};
int dcol[] = {0, -1, 0, 1};

struct Node {
    Node *parent;
    int mat[N][N];
    int g, h;
    int x, y;
};

void printMatrix(int mat[N][N], int g, int h) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            cout << mat[i][j] << " ";
        }
        cout << endl;
    }
    cout << "g: " << g << " h: " << h << " f: " << g + h << endl;
}

Node* newNode(int mat[N][N], int x, int y, int nx, int ny, int g, Node *parent) {
    Node *node = new Node;
    node->parent = parent;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            node->mat[i][j] = mat[i][j];

    swap(node->mat[x][y], node->mat[nx][ny]); 
    node->x = nx;
    node->y = ny;
    node->g = g;
    node->h = INT_MAX; //will be set later
    return node;
}

int heuristic(int initial[N][N], int goal[N][N]) {
    int count = 0;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            if (initial[i][j] && initial[i][j] != goal[i][j])
                count++;
    return count;
}

bool isSolvable(int mat[N][N]) {
    vector<int> flat;
    for (int i = 0; i < N; ++i)
        for (int j = 0; j < N; ++j)
            if (mat[i][j] != 0)
                flat.push_back(mat[i][j]);

    int inv = 0;
    for (int i = 0; i < flat.size(); ++i)
        for (int j = i + 1; j < flat.size(); ++j)
            if (flat[i] > flat[j])
                inv++;

    return (inv % 2 == 0);
}

bool isSafe(int x, int y) {
    return (x >= 0 && x < N && y >= 0 && y < N);
}

void printPath(Node *root) {
    if (root == NULL) return;
    printPath(root->parent);
    printMatrix(root->mat, root->g, root->h);
    cout << endl;
}

struct comp {
    bool operator()(const Node *lhs, const Node *rhs) const {
        return (lhs->g + lhs->h) > (rhs->g + rhs->h);
    }
};

void solve(int start[N][N], int x, int y, int goal[N][N]) {
    int cnt = 0;
    priority_queue<Node *, vector<Node *>, comp> pq;

    Node *root = newNode(start, x, y, x, y, 0, NULL);
    root->h = heuristic(start, goal);
    pq.push(root);

    while (!pq.empty()) {
        Node *m = pq.top();
        pq.pop();
        if (m->h == 0) {
            cout << "\n\nThis puzzle is solved in " << cnt << " moves.\n";
            printPath(m);
            return;
        }
        cnt++;
        for (int i = 0; i < 4; i++) {
            int dx = m->x + drow[i];
            int dy = m->y + dcol[i];
            if (isSafe(dx, dy)) {
                Node *child = newNode(m->mat, m->x, m->y, dx, dy, m->g + 1, m);
                child->h = heuristic(child->mat, goal);
                pq.push(child);
            }
        }
    }
    cout << "No solution found.\n";
}

int main() {
    int start[N][N], goal[N][N];
    int x = -1, y = -1;
    cout << "Enter the start state (3x3 matrix, use 0 for blank):\n";
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++) {
            cin >> start[i][j];
            if (start[i][j] == 0) {
                x = i;
                y = j;
            }
        }
    cout << "Enter the goal state (3x3 matrix):\n";
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            cin >> goal[i][j];
    if (!isSolvable(start)) {
        cout << "\nThis puzzle is not solvable.\n";
        return 0;
    }
    solve(start, x, y, goal);
    return 0;
}
