/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private int moves;
    private SearchNode goal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        goal = findGoal(initial);
        moves = goal != null ? goal.movesSoFar : -1;
    }

    private SearchNode findGoal(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        pqTwin.insert(new SearchNode(initial.twin(), null, 0));
        pq.insert(new SearchNode(initial, null, 0));

        while (!pq.min().board.isGoal() && !pqTwin.min().board.isGoal()) {
            SearchNode cur = pq.delMin();
            SearchNode curTwin = pqTwin.delMin();

            for (Board neighb : cur.board.neighbors()) {
                Board grandParent = cur.previousNode == null ? null : cur.previousNode.board;
                if (!neighb.equals(grandParent)) {
                    pq.insert(new SearchNode(neighb, cur, cur.movesSoFar + 1));
                }
            }

            for (Board neighb : curTwin.board.neighbors()) {
                Board grandParentTwin = curTwin.previousNode == null ? null :
                                        curTwin.previousNode.board;
                if (!neighb.equals(grandParentTwin)) {
                    pqTwin.insert(new SearchNode(neighb, curTwin, curTwin.movesSoFar + 1));
                }
            }
        }

        if (pq.min().board.isGoal()) return pq.min();
        else return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (goal == null) return null;
        LinkedList<Board> lst = new LinkedList<>();
        SearchNode dummy = goal;
        while (dummy != null) {
            lst.addFirst(dummy.board);
            dummy = dummy.previousNode;
        }
        return lst;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode previousNode;
        private int movesSoFar;
        private final int estMovesLeft;

        SearchNode(Board board, SearchNode previousNode, int movesSoFar) {
            this.board = board;
            this.previousNode = previousNode;
            this.movesSoFar = movesSoFar;
            estMovesLeft = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode o) {
            int thisPriority = movesSoFar + estMovesLeft;
            int oPriority = o.movesSoFar + o.estMovesLeft;
            return thisPriority - oPriority;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        for (Board b : solver.solution()) {
            StdOut.println(b.toString());
        }
    }

}
