import java.util.*;
class Node {
    Node parent;
    int[][] mat;
    int x, y; 
    int g, h; 

    public Node(int[][] mat, int x, int y, int newX, int newY, int g, Node parent) {
        this.mat = new int[3][3];
        for (int i = 0; i < 3; i++)
            this.mat[i] = mat[i].clone();

        int temp = this.mat[x][y];
        this.mat[x][y] = this.mat[newX][newY];
        this.mat[newX][newY] = temp;

        this.x = newX;
        this.y = newY;
        this.g = g;
        this.h = Integer.MAX_VALUE;
        this.parent = parent;
    }
}
public class EightPuzzleSolver {
    static int N = 3;

    static int[] dRow = {1, 0, -1, 0};
    static int[] dCol = {0, -1, 0, 1};

/*F1*/ static int heuristic(int[][] initial, int[][] goal) {
    int count = 0;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            if (initial[i][j] != 0 && initial[i][j] != goal[i][j])
                count++;
    return count;
}

/*F2*/ static boolean isSafe(int x, int y) {
    return x >= 0 && x < N && y >= 0 && y < N;
}

/*F3*/ static boolean isSolvable(int[][] mat) {
    List<Integer> list = new ArrayList<>();
    for (int[] row : mat)
        for (int val : row)
            if (val != 0)
                list.add(val);

    int inv = 0;
    for (int i = 0; i < list.size(); i++)
        for (int j = i + 1; j < list.size(); j++)
            if (list.get(i) > list.get(j))
                inv++;

    return inv % 2 == 0;
}

/*F4*/ static void printMatrix(int[][] mat, int g, int h) {
        for (int[] row : mat) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println("g: " + g + " h: " + h + " f: " + (g + h));
    }

/*F5*/ static void printPath(Node root) {
        if (root == null) return;
        printPath(root.parent);
        printMatrix(root.mat, root.g, root.h);
        System.out.println();
    }

/*F6*/ public static void solve(int[][] start, int x, int y, int[][] goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> (a.g + a.h)));

        Node root = new Node(start, x, y, x, y, 0, null);
        root.h = heuristic(start, goal);
        pq.add(root);
        int moves = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.h == 0) {
                System.out.println("\n\nThis puzzle is solved in " + moves + " moves.\n");
                printPath(current);
                return;
            }

            moves++;

            for (int i = 0; i < 4; i++) {
                int newX = current.x + dRow[i];
                int newY = current.y + dCol[i];

                if (isSafe(newX, newY)) {
                    Node child = new Node(current.mat, current.x, current.y, newX, newY, current.g + 1, current);
                    child.h = heuristic(child.mat, goal);
                    pq.add(child);
                }
            }
        }
        System.out.println("No solution found.");
    }
/*F7*/ public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] start = new int[N][N];
        int[][] goal = new int[N][N];
        int x = -1, y = -1;

        System.out.println("Enter the start state (3x3 matrix, 0 as blank):");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                start[i][j] = sc.nextInt();
                if (start[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }

        System.out.println("Enter the goal state (3x3 matrix):");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                goal[i][j] = sc.nextInt();

        if (!isSolvable(start)) {
            System.out.println("\nThis puzzle is not solvable.");
            return;
        }

        solve(start, x, y, goal);
    }
}
