public class Percolation {
    private int N; //
    private boolean[] sites; // open -> true, blocked -> false
    private int openSites; // count for sites that is open
    private int[] siteParent; // tree for union-find
    private int[] siteTreeSize; // store size on root
    private int virtualTop, virtualBottom; // used in siteParent and siteTreesize

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        N = n;
        virtualTop = N;
        virtualBottom = N + 1;
        openSites = 0;
        sites = new boolean[N * N];
        siteParent = new int[N * N + 2]; // adding 2 virtual sites, one at very top, one at very bottom
        siteTreeSize = new int[N * N + 2]; // site[N] being the top one and site[N+1] being the bottom one
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                sites[row * N + col] = false;
                siteParent[row * N + col] = row * N + col;
                siteTreeSize[row * N + col] = 1;
            }
        }
    }

    // return the root of p
    public int root(int p) {
        int temp = p;
        while (siteParent[temp] != temp) {
            siteParent[temp] = siteParent[siteParent[temp]]; // path compression
            temp = siteParent[temp];
        }
        return temp;
    }

    // return if p and q are connected, i.e. have the same root
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // connect p and q
    public void union(int p, int q) {
        int P = root(p);
        int Q = root(q);
        if (P == Q) return;
        // weighting
        if (siteTreeSize[P] < siteTreeSize[Q]) {
            siteParent[P] = Q;
            siteTreeSize[Q] += siteTreeSize[P];
        }
        else {
            siteParent[Q] = P;
            siteTreeSize[P] += siteTreeSize[Q];
        }
    }

    /*
     opens the site (row, col) if it is not open already
     check if the site need to call union
    */
    public void open(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException();
        }
        int index = row * N + col;
        sites[index] = true;
        openSites++;
        /*
         case 1: the site is in the top row, should union() to virtual top site.
                 no need to union to its left, right or below.
        */
        if (index < N) {
            union(index, virtualTop);
        }
        /*
         case 2: ths site is not in top row or bottom row,
                 needs to check it's 4 directions to see if union is needed
        */
        else if (index < (N - 1) * N) {
            // top is open
            if (isOpen(row - 1, col)) {
                union(index, (row - 1) * N + col);
            }
            // left is open
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                union(index, row * N + col - 1);
            }
            // right is open
            if (col + 1 < N && isOpen(row, col + 1)) {
                union(index, row * N + col + 1);
            }
            // bottom is open
            if (isOpen(row + 1, col)) {
                union(index, (row + 1) * N + col);
            }
        }
        /*
         case 3: the site is at bottom, should union() to the virtual bottom
                 still checking it's other 3 directions to see if any site adjacent is open
         */
        else {
            union(index, virtualBottom);
            // top is open
            if (isOpen(row - 1, col)) {
                union(index, (row - 1) * N + col);
            }
            // left is open
            if (col - 1 >= 0 && isOpen(row, col - 1)) {
                union(index, row * N + col - 1);
            }
            // right is open
            if (col + 1 < N && isOpen(row, col + 1)) {
                union(index, row * N + col + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException();
        }
        return sites[row * N + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row >= N || col < 1 || col >= N) {
            throw new IllegalArgumentException();
        }
        return !sites[row * N + col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(virtualTop, virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);

        System.out.println(percolation.percolates());
        percolation.open(0,0);
        percolation.open(1,0);
        percolation.open(2,1);
        System.out.println(percolation.percolates());
        percolation.open(1,1);
        System.out.println(percolation.percolates());
    }
}
