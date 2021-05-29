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
    private SearchNode finalNode;

    // find a solution to the initial board (using the A* algorithm and manhattan priority)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        Board twin = initial.twin(); // the twin board used for checking if initial is solvable

        SearchNode originalNode = new SearchNode(initial, 0, null);
        SearchNode twinOriginalNode = new SearchNode(twin, 0, null);
        finalNode = null;

        MinPQ<SearchNode> originalMinPQ = new MinPQ<>();
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();

        originalMinPQ.insert(originalNode);
        twinMinPQ.insert(twinOriginalNode);


        // main loop of A* algorithm
        SearchNode tempNode = originalMinPQ.delMin();
        SearchNode twinTempNode = twinMinPQ.delMin();
        while (!tempNode.currentBoard.isGoal() && !twinTempNode.currentBoard.isGoal()) {
            // effectively final variable used for lambda expression
            SearchNode finalTempNode = tempNode;
            // insert neighboring search nodes
            tempNode.currentBoard.neighbors().forEach(board -> {
                // don't insert board that is identical to the previous board
                if (finalTempNode.previousNode == null) {
                    originalMinPQ
                            .insert(new SearchNode(board, finalTempNode.numberOfMoves + 1,
                                    finalTempNode));
                } else if (!board.equals(finalTempNode.previousNode.currentBoard))
                    originalMinPQ
                            .insert(new SearchNode(board, finalTempNode.numberOfMoves + 1,
                                    finalTempNode));
            });
            SearchNode finalTwinTempNode = twinTempNode;
            twinTempNode.currentBoard.neighbors().forEach(board -> {
                if (finalTwinTempNode.previousNode == null) {
                    twinMinPQ
                            .insert(new SearchNode(board, finalTwinTempNode.numberOfMoves + 1,
                                    finalTwinTempNode));
                } else if (!board.equals(finalTwinTempNode.previousNode.currentBoard))
                    twinMinPQ
                            .insert(new SearchNode(board, finalTwinTempNode.numberOfMoves + 1,
                                    finalTwinTempNode));
            });

            tempNode = originalMinPQ.delMin();
            twinTempNode = twinMinPQ.delMin();
        }

        if (tempNode.currentBoard.isGoal()) {
            finalNode = tempNode;
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
        return finalNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (finalNode == null)
            return -1;
        return finalNode.numberOfMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (finalNode == null)
            return null;
        Stack<Board> solution = new Stack<>();
        SearchNode tempNode = finalNode;
        while (tempNode.previousNode != null) {
            solution.push(tempNode.currentBoard);
            tempNode = tempNode.previousNode;
        }
        solution.push(tempNode.currentBoard);
        return solution;
    }

    // search node class for A* algorithm
    private class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        private final int numberOfMoves;
        private final SearchNode previousNode;
        private final int manhattanPriority; // caching manhattan priority

        SearchNode(Board currentBoard, int numberOfMoves, SearchNode previousNode) {
            this.currentBoard = currentBoard;
            this.numberOfMoves = numberOfMoves;
            this.previousNode = previousNode;
            this.manhattanPriority = this.currentBoard.manhattan() + this.numberOfMoves;
        }

        // comparing with manhattan priority
        public int compareTo(SearchNode that) {
            return this.manhattanPriority - that.manhattanPriority;
        }
    }
}
