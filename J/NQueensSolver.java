import java.util.Scanner;
public class NQueensSolver {
    
    static void printBoard(int[][] board, int N) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 1 ? "Q " : ". "));
            }
            System.out.println();
        }
        System.out.println();
    }

    static boolean isSafe(int[][] board, int row, int col, int N) {
        
        for (int i = 0; i < row; i++)   // Check column
            if (board[i][col] == 1)
                return false;

        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)  // Check upper-left diagonal
            if (board[i][j] == 1)
                return false;

        for (int i = row, j = col; i >= 0 && j < N; i--, j++)   // Check upper-right diagonal
            if (board[i][j] == 1)
                return false;

        return true;
    }

    static boolean solveBacktracking(int[][] board, int row, int N) {
        if (row == N)
            return true;

        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col, N)) {
                board[row][col] = 1;
                System.out.println("Placing queen at (" + row + ", " + col + "):");
                printBoard(board, N);

                if (solveBacktracking(board, row + 1, N))
                    return true;

                board[row][col] = 0;
                System.out.println("Backtracking from (" + row + ", " + col + "):");
                printBoard(board, N);
            }
        }
        return false;
    }

    static boolean solveBranchAndBound(int[][] board, int row, int N, int[] colConflicts, int[] diag1Conflicts, int[] diag2Conflicts) {
        if (row == N)
            return true;

        for (int col = 0; col < N; col++) {
            if (colConflicts[col] == 0 && diag1Conflicts[row - col + N - 1] == 0 && diag2Conflicts[row + col] == 0) {
                board[row][col] = 1;
                colConflicts[col] = 1;
                diag1Conflicts[row - col + N - 1] = 1;
                diag2Conflicts[row + col] = 1;

                System.out.println("Placing queen at (" + row + ", " + col + "):");
                printBoard(board, N);

                if (solveBranchAndBound(board, row + 1, N, colConflicts, diag1Conflicts, diag2Conflicts))
                    return true;

                board[row][col] = 0;
                colConflicts[col] = 0;
                diag1Conflicts[row - col + N - 1] = 0;
                diag2Conflicts[row + col] = 0;

                System.out.println("Backtracking from (" + row + ", " + col + "):");
                printBoard(board, N);
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens (N): ");
        int N = scanner.nextInt();

        System.out.println("Choose the approach:");
        System.out.println("1. Backtracking");
        System.out.println("2. Branch and Bound");
        System.out.print("Enter choice (1/2): ");
        int choice = scanner.nextInt();

        int[][] board = new int[N][N];

        switch (choice) {
            case 1:
                System.out.println("\nBacktracking Solution:\n");
                if (solveBacktracking(board, 0, N)) {
                    System.out.println("Solution found using Backtracking:");
                    printBoard(board, N);
                } else {
                    System.out.println("No solution exists using Backtracking.");
                }
                break;

            case 2:
                System.out.println("\nBranch and Bound Solution:\n");
                int[] colConflicts = new int[N];
                int[] diag1Conflicts = new int[2 * N - 1];
                int[] diag2Conflicts = new int[2 * N - 1];

                if (solveBranchAndBound(board, 0, N, colConflicts, diag1Conflicts, diag2Conflicts)) {
                    System.out.println("Solution found using Branch and Bound:");
                    printBoard(board, N);
                } else {
                    System.out.println("No solution exists using Branch and Bound.");
                }
                break;

            default:
                System.out.println("Invalid choice!");
        }

        scanner.close();
    }
}
