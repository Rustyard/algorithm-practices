/* *****************************************************************************
 *  Name: Board.java
 *  Date: May 26, 2021
 *  Description: A n-by-n board with sliding tiles model.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private ArrayList<Board> neighbors;
    private int[][] tiles;
    private int n; // board dimension (N*N board)

    // create a board from an n-by-n array of tiles
    // where tiles[row][col] = tile at (row, col)

    public Board(int[][] tiles) {
        neighbors = new ArrayList<>();
        n = tiles.length; // get n
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            this.tiles[i] = tiles[i].clone(); // deep copy of 2D array!!!
        }
    }

    public static void main(String[] args) {
        int[][] tiles = {{1, 7, 3}, {4, 0, 8}, {2, 5, 6}};
        Board test = new Board(tiles);
        StdOut.println(test.toString());
        Iterable<Board> boards = test.neighbors();
        boards.forEach(board -> StdOut.println(board.toString())); // test ok
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        setNeighbors(); // calculate all neighbors
        return neighbors;
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
            neighbors.add(new Board(exchange(row0, col0, row0, col0 - 1)));
        }
        if (col0 + 1 < n) {
            neighbors.add(new Board(exchange(row0, col0, row0, col0 + 1)));
        }
        if (row0 - 1 >= 0) {
            neighbors.add(new Board(exchange(row0, col0, row0 - 1, col0)));
        }
        if (row0 + 1 < n) {
            neighbors.add(new Board(exchange(row0, col0, row0 + 1, col0)));
        }
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

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i + j + 1 && tiles[i][j] != 0) {
                    result += Math.abs((tiles[i][j] - 1) / n - i);
                    result += Math.abs((tiles[i][j] - 1) % n - j);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // number of tiles out of space
    public int hamming() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i + j + 1 && tiles[i][j] != 0) {
                    result++;
                }
            }
        }
        return result;
    }

    // does this board equal y? (first time rewriting this func)
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() == this.getClass())
            return this.toString().equals(y.toString());
        else
            return false;
    }

    // string representation
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n).append('\n'); // get board size n

        // get board content
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(' ').append(tiles[i][j]);
            }
            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
