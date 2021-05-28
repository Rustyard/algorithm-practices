/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode tempNode;
    private SearchNode twinTempNode;
    private MinPQ<SearchNode> originalMinPQ;
    private MinPQ<SearchNode> twinMinPQ;
    private boolean isSolvable;
    private Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm and manhattan priority)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solution = new Stack<>();
        Board twin = initial.twin(); // the twin board used for checking if initial is solvable

        SearchNode originalNode = new SearchNode(initial, 0, null);
        SearchNode twinOriginalNode = new SearchNode(twin, 0, null);

        originalMinPQ = new MinPQ<>();
        twinMinPQ = new MinPQ<>();

        originalMinPQ.insert(originalNode);
        twinMinPQ.insert(twinOriginalNode);


        // main loop of A* algorithm
        tempNode = originalMinPQ.delMin();
        twinTempNode = twinMinPQ.delMin();
        while (!tempNode.currentBoard.isGoal() && !twinTempNode.currentBoard.isGoal()) {
            // insert neighboring search nodes
            tempNode.currentBoard.neighbors().forEach(board -> {
                // don't insert board that is identical to the previous board
                if (tempNode.previousNode == null) {
                    originalMinPQ
                            .insert(new SearchNode(board, tempNode.numberOfMoves + 1,
                                    tempNode));
                } else if (!board.equals(tempNode.previousNode.currentBoard))
                    originalMinPQ
                            .insert(new SearchNode(board, tempNode.numberOfMoves + 1,
                                    tempNode));
            });
            twinTempNode.currentBoard.neighbors().forEach(board -> {
                if (twinTempNode.previousNode == null) {
                    twinMinPQ
                            .insert(new SearchNode(board, twinTempNode.numberOfMoves + 1,
                                    twinTempNode));
                } else if (!board.equals(twinTempNode.previousNode.currentBoard))
                    twinMinPQ
                            .insert(new SearchNode(board, twinTempNode.numberOfMoves + 1,
                                    twinTempNode));
            });

            tempNode = originalMinPQ.delMin();
            twinTempNode = twinMinPQ.delMin();
        }

        if (tempNode.currentBoard.isGoal()) {
            isSolvable = true;
            while (tempNode.previousNode != null) {
                solution.push(tempNode.currentBoard);
                tempNode = tempNode.previousNode;
            }
            solution.push(tempNode.currentBoard);
        } else {
            isSolvable = false;
        }

    }

    // test client
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable)
            return -1;
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable)
            return null;
        return solution;
    }

    // search node class for A* algorithm
    private class SearchNode implements Comparable<SearchNode> {
        private Board currentBoard;
        private int numberOfMoves;
        private SearchNode previousNode;

        SearchNode(Board currentBoard, int numberOfMoves, SearchNode previousNode) {
            this.currentBoard = currentBoard;
            this.numberOfMoves = numberOfMoves;
            this.previousNode = previousNode;
        }

        // comparing with manhattan priority
        public int compareTo(SearchNode that) {
            return (this.currentBoard.manhattan() + this.numberOfMoves)
                    - (that.currentBoard.manhattan() + that.numberOfMoves);
        }
    }
}
