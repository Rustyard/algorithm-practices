/* *****************************************************************************
 *  Name: Board.java
 *  Date: May 26, 2021
 *  Description: A n-by-n board with sliding tiles model.
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private Stack<Board> neighbors;
    private int[][] tiles;
    private int n; // board dimension (N*N board)

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)

    public Board(int[][] tiles) {
        neighbors = new Stack<>();
        n = tiles.length; // get n
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            this.tiles[i] = tiles[i].clone(); // deep copy of 2D array!!!
        }
    }

    public static void main(String[] args) {
        int[][] tiles = {{1, 7, 3}, {4, 0, 8}, {2, 5, 6}};
        int[][] solve = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board test = new Board(tiles);
        Board test2 = new Board(tiles);
        Board solved = new Board(solve);
        StdOut.println(test.toString());
        Iterable<Board> boards = test.neighbors();
        boards.forEach(board -> StdOut.println(board.toString())); // test ok
        Board twin = test.twin();
        StdOut.println(twin.toString());
        StdOut.println(test.hamming());
        StdOut.println(test.manhattan());
        StdOut.println(test.equals(test2));
        StdOut.println(test.equals(twin));
        StdOut.println(test.isGoal());
        StdOut.println(solved.isGoal());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        setNeighbors(); // calculate all neighbors
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // first 2 tiles that is not blank
        int i1 = 0, j1 = 0, i2 = 0, j2 = 1;

        // skip blank tile
        while (tiles[i1][j1] == 0) {
            j1++;
            if (j1 == n) {
                j1 = 0;
                i1++;
            }
        }
        while (tiles[i2][j2] == 0 || tiles[i2][j2] == tiles[i1][j1]) {
            j2++;
            if (j2 == n) {
                j2 = 0;
                i2++;
            }
        }

        return new Board(exchange(i1, j1, i2, j2));
    }

    // exchange 2 elements in tiles, return a new 2D array
    private int[][] exchange(int row1, int col1, int row2, int col2) {
        int[][] tempTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            tempTiles[i] = tiles[i].clone(); // copy the whole tiles
        }
        int temp = tempTiles[row1][col1];
        tempTiles[row1][col1] = tempTiles[row2][col2];
        tempTiles[row2][col2] = temp; // exchange elements

        return tempTiles;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of space
    public int hamming() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i * n + j + 1 && tiles[i][j] != 0) {
                    result++;
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i * n + j + 1 && tiles[i][j] != 0) {
                    result += Math.abs((tiles[i][j] - 1) / n - i);
                    result += Math.abs((tiles[i][j] - 1) % n - j);
                }
            }
        }
        return result;
    }

    // does this board equal y? (first time rewriting this func)
    // change: don't use toString here but use array equals (deep equals for 2d arrays)
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() == this.getClass()) {
            return Arrays.deepEquals(this.tiles, ((Board) y).tiles);
        } else
            return false;
    }

    // string representation (their code)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private void setNeighbors() {
        int row0 = -1, col0 = -1; // the position of blank square
        // find blank
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    row0 = i;
                    col0 = j;
                    break;
                }
            }
            if (row0 != -1) break;
        }

        // adding neighboring boards
        if (col0 - 1 >= 0) {
            neighbors.push(new Board(exchange(row0, col0, row0, col0 - 1)));
        }
        if (col0 + 1 < n) {
            neighbors.push(new Board(exchange(row0, col0, row0, col0 + 1)));
        }
        if (row0 - 1 >= 0) {
            neighbors.push(new Board(exchange(row0, col0, row0 - 1, col0)));
        }
        if (row0 + 1 < n) {
            neighbors.push(new Board(exchange(row0, col0, row0 + 1, col0)));
        }
    }
}
