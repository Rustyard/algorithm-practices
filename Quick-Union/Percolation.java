public class Percolation {
    private final int bigN; //
    private boolean[] sites; // open -> true, blocked -> false
    private int openSites; // count for sites that is open
    private int[] siteParent; // tree for union-find
    private int[] siteTreeSize; // store size on root
    private final int virtualTop;
    private final int virtualBottom; // used in siteParent and siteTreesize

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        bigN = n;
        virtualTop = bigN * bigN;
        virtualBottom = bigN * bigN + 1;
        openSites = 0;
        sites = new boolean[bigN * bigN];
        siteParent = new int[bigN * bigN + 2]; // adding 2 virtual sites, one at very top, one at very bottom
        siteTreeSize = new int[bigN * bigN + 2]; // site[N] being the top one and site[N+1] being the bottom one
        for (int row = 0; row < bigN; row++) {
            for (int col = 0; col < bigN; col++) {
                sites[row * bigN + col] = false;
                siteParent[row * bigN + col] = row * bigN + col;
                siteTreeSize[row * bigN + col] = 1;
            }
        }
        siteParent[virtualBottom] = virtualBottom;
        siteParent[virtualTop] = virtualTop;
        siteTreeSize[virtualTop] = 1;
        siteTreeSize[virtualBottom] = 1;
    }

    // return the root of p
    private int root(int p) {
        int temp = p;
        while (siteParent[temp] != temp) {
            siteParent[temp] = siteParent[siteParent[temp]]; // path compression
            temp = siteParent[temp];
        }
        return temp;
    }

//    // return if p and q are connected, i.e. have the same root
//    private boolean connected(int p, int q) {
//        return root(p) == root(q);
//    }

    // connect p and q
    private void union(int p, int q) {
        int bigP = root(p);
        int bigQ = root(q);
        if (bigP == bigQ) return;
        // weighting
        if (siteTreeSize[bigP] < siteTreeSize[bigQ]) {
            siteParent[bigP] = bigQ;
            siteTreeSize[bigQ] += siteTreeSize[bigP];
        }
        else {
            siteParent[bigQ] = bigP;
            siteTreeSize[bigP] += siteTreeSize[bigQ];
        }
    }

    /*
     opens the site (row, col) if it is not open already
     check if the site need to call union
    */
    public void open(int row, int col) {
        if (isOpen(row, col)) return; // already open
        row--;
        col--; // added this for open() isOpen() isFull() because the problem says (1,1) is upper-left site... lol
        if (row < 0 || row >= bigN || col < 0 || col >= bigN) {
            throw new IllegalArgumentException();
        }
        int index = row * bigN + col;
        sites[index] = true;
        openSites++;
        /*
         case 1: the site is in the top row, should union() to virtual top site.
        */
        if (index < bigN) {
            union(index, virtualTop);
            if (bigN == 1) {
                union(index, virtualBottom); // extreme case when there is only one site, connect to both virtual sites
            }
            else {
                // left is open
                if (col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
                    union(index, row * bigN + col - 1);
                }
                // right is open
                if (col + 1 < bigN && isOpen(row + 1, col + 1 + 1)) {
                    union(index, row * bigN + col + 1);
                }
                // bottom is open
                if (isOpen(row + 1 + 1, col + 1)) {
                    union(index, (row + 1) * bigN + col);
                }
            }
        }
        /*
         case 2: ths site is not in top row or bottom row,
                 needs to check it's 4 directions to see if union is needed
        */
        else if (index < (bigN - 1) * bigN) {
            // top is open
            if (isOpen(row - 1 + 1, col + 1)) {
                union(index, (row - 1) * bigN + col);
            }
            // left is open
            if (col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
                union(index, row * bigN + col - 1);
            }
            // right is open
            if (col + 1 < bigN && isOpen(row + 1, col + 1 + 1)) {
                union(index, row * bigN + col + 1);
            }
            // bottom is open
            if (isOpen(row + 1 + 1, col + 1)) {
                union(index, (row + 1) * bigN + col);
            }
        }
        /*
         case 3: the site is at bottom, should union() to the virtual bottom
                 still checking it's other 3 directions to see if any site adjacent is open
         */
        else {
            union(index, virtualBottom);
            // top is open
            if (isOpen(row - 1 + 1, col + 1)) {
                union(index, (row - 1) * bigN + col);
            }
            // left is open
            if (col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
                union(index, row * bigN + col - 1);
            }
            // right is open
            if (col + 1 < bigN && isOpen(row + 1, col + 1 + 1)) {
                union(index, row * bigN + col + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        if (row < 0 || row >= bigN || col < 0 || col >= bigN) {
            throw new IllegalArgumentException();
        }
        return sites[row * bigN + col];
    }

    // is the site (row, col) full?
    // which mean it's filled with water...
    // TODO: fix the backwash incorrectness problem
    public boolean isFull(int row, int col) {
        row--;
        col--;
        if (row < 0 || row >= bigN || col < 0 || col >= bigN) {
            throw new IllegalArgumentException();
        }
        return root(virtualTop) == root(row * bigN + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return root(virtualTop) == root(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(1);

        System.out.println(percolation.percolates());
        percolation.open(1, 1);
        System.out.println(percolation.percolates());
    }
}
