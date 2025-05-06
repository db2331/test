#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

void printBoard(const vector<vector<int>>& board, int N) {
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            if (board[i][j] == 1)
                cout << "Q ";
            else
                cout << ". ";
        }
        cout << endl;
    }
    cout << endl;
}

// Function to check if a queen can be placed at board[row][col]
bool isSafe(const vector<vector<int>>& board, int row, int col, int N) {
    // Check the column for the same row
    for (int i = 0; i < row; i++) {
        if (board[i][col] == 1)
            return false;
    }
    // Check the upper-left diagonal
    for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
        if (board[i][j] == 1)
            return false;
    }
    // Check the upper-right diagonal
    for (int i = row, j = col; i >= 0 && j < N; i--, j++) {
        if (board[i][j] == 1)
            return false;
    }
    return true;
}

// Backtracking approach
bool solveNQueensBacktracking(vector<vector<int>>& board, int row, int N) {
    if (row == N)  // All queens are placed
        return true;
    // Try placing a queen in each column of the current row
    for (int col = 0; col < N; col++) {
        if (isSafe(board, row, col, N)) {
            board[row][col] = 1;  // Place queen
            cout << "Placing queen at (" << row << ", " << col << "):\n";
            printBoard(board, N);
            if (solveNQueensBacktracking(board, row + 1, N))  // Recur to place the rest
                return true;
            board[row][col] = 0;  // Backtrack if no solution
            cout << "Backtracking from (" << row << ", " << col << "):\n";
            printBoard(board, N);
        }
    }
    return false;  // No valid position found for a queen in this row
}

// Branch and Bound approach
bool solveNQueensBranchAndBound(vector<vector<int>>& board, int row, int N, vector<int>& colConflicts, vector<int>& diag1Conflicts, vector<int>& diag2Conflicts) {
    if (row == N)  // All queens are placed
        return true;
    // Try placing a queen in each column of the current row
    for (int col = 0; col < N; col++) {
        // Check if placing queen results in conflict with columns or diagonals
        if (colConflicts[col] == 0 && diag1Conflicts[row - col + N - 1] == 0 && diag2Conflicts[row + col] == 0) {
            board[row][col] = 1;  // Place queen
            colConflicts[col] = 1;
            diag1Conflicts[row - col + N - 1] = 1;
            diag2Conflicts[row + col] = 1;
            cout << "Placing queen at (" << row << ", " << col << "):\n";
            printBoard(board, N);
            if (solveNQueensBranchAndBound(board, row + 1, N, colConflicts, diag1Conflicts, diag2Conflicts))
                return true;
            // Backtrack
            board[row][col] = 0;
            colConflicts[col] = 0;
            diag1Conflicts[row - col + N - 1] = 0;
            diag2Conflicts[row + col] = 0;
            cout << "Backtracking from (" << row << ", " << col << "):\n";
            printBoard(board, N);
        }
    }
    return false;  // No valid position found for a queen in this row
}

int main() {
    int N, choice;
    cout << "Enter the number of queens (N): ";
    cin >> N;
    cout << "Choose the approach:\n";
    cout << "1. Backtracking\n";
    cout << "2. Branch and Bound\n";
    cout << "Enter choice (1/2): ";
    cin >> choice;

    vector<vector<int>> board(N, vector<int>(N, 0));
    
    switch (choice) {
        case 1: {
            cout << "\nBacktracking Solution:\n";
            if (solveNQueensBacktracking(board, 0, N)) {
                cout << "Solution found using Backtracking:\n";
                printBoard(board, N);
            } else {
                cout << "No solution exists for " << N << " queens using Backtracking." << endl;
            }
            break;
        }
        case 2: {
            cout << "\nBranch and Bound Solution:\n";
            vector<int> colConflicts(N, 0);  // Track column conflicts
            vector<int> diag1Conflicts(2 * N - 1, 0);  // Track upper-left to bottom-right diagonal conflicts
            vector<int> diag2Conflicts(2 * N - 1, 0);  // Track upper-right to bottom-left diagonal conflicts
            board.assign(N, vector<int>(N, 0));  // Reset board
            if (solveNQueensBranchAndBound(board, 0, N, colConflicts, diag1Conflicts, diag2Conflicts)) {
                cout << "Solution found using Branch and Bound:\n";
                printBoard(board, N);
            } else {
                cout << "No solution exists for " << N << " queens using Branch and Bound." << endl;
            }
            break;
        }
        default:
            cout << "Invalid choice!" << endl;
            break;
    }

    return 0;
}
