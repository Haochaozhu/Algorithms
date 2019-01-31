/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int[][] board;
    private int N;

    public Board(int[][] blocks) {
        int[][] temp = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i += 1) {
            for (int j = 0; j < blocks.length; j += 1) {
                temp[i][j] = blocks[i][j];
            }
        }
        board = temp;
        N = blocks.length;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int num = 1;
        int hamming = 0;
        for (int i = 0; i < board.length; i += 1) {
            for (int j = 0; j < board.length; j += 1) {
                if (i == board.length - 1 && j == board.length - 1) return hamming;
                if (board[i][j] != num) hamming += 1;
                num += 1;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    manhattan = manhattan + calculateManhattan(board[i][j], i, j);
                }
            }
        }
        return manhattan;
    }

    private int calculateManhattan(int correctNumber, int i, int j) {
        int N = board.length;
        int xPosition = correctNumber % N == 0 ? correctNumber / N - 1 : correctNumber / N;
        int yPosition = correctNumber % N == 0 ? N - 1 : correctNumber % N - 1;
        return Math.abs(xPosition - i) + Math.abs(yPosition - j);
    }


    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] temp = new int[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                temp[i][j] = board[i][j];
            }
        }
        int i = 0, j = 0;
        while (i < N) {
            if (temp[0][i] == 0) i += 1;
            else break;
        }
        while (j < N) {
            if (temp[1][j] == 0) j += 1;
            else break;
        }
        int tempNum = temp[0][i];
        temp[0][i] = temp[1][j];
        temp[1][j] = tempNum;
        return new Board(temp);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;
        Board other = (Board) y;
        return Arrays.equals(this.board, other.board);
    }

    // returns index of zero
    private int[] findZero() {
        int[] ans = new int[2];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                if (board[i][j] == 0) {
                    ans[0] = i;
                    ans[1] = j;
                    return ans;
                }
            }
        }
        return ans;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        int[] index = findZero();
        int i = index[0];
        int j = index[1];

        if (i - 1 >= 0) queue.enqueue(exch(i, j, i - 1, j));
        if (i + 1 < N) queue.enqueue(exch(i, j, i + 1, j));
        if (j - 1 >= 0) queue.enqueue(exch(i, j, i, j - 1));
        if (j + 1 < N) queue.enqueue(exch(i, j, i, j + 1));
        return queue;
    }

    // exchange the board[i][j] with b[k][l], return a board.
    private Board exch(int i, int j, int k, int m) {
        int[][] temp = new int[N][N];
        for (int a = 0; a < N; a += 1) {
            for (int b = 0; b < N; b += 1) {
                temp[a][b] = board[a][b];
            }
        }

        int tempNum = temp[i][j];
        temp[i][j] = temp[k][m];
        temp[k][m] = tempNum;
        return new Board(temp);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Board second = new Board(blocks);
        StdOut.println(initial.equals(second));
        
    }
}
