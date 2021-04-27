public class Percolation {
    private final int bigN; //
    private boolean[] sites; // open -> true, blocked -> false
    private int openSites; // count for sites that is open
    private int[] siteParent; // tree for union-find
    private int[] siteParent2; // tree for avoiding backwash, NOTE: doubling everything isn't a good idea, should use
                               // new class instead
    private int[] siteTreeSize; // store size on root
    private int[] siteTreeSize2; // store size on root
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
        siteParent2 = new int[bigN * bigN + 1]; // this one only need the top virtual site
        siteTreeSize = new int[bigN * bigN + 2]; // site[N] being the top one and site[N+1] being the bottom one
        siteTreeSize2 = new int[bigN * bigN + 1];
        for (int row = 0; row < bigN; row++) {
            for (int col = 0; col < bigN; col++) {
                sites[row * bigN + col] = false;
                siteParent[row * bigN + col] = row * bigN + col;
                siteParent2[row * bigN + col] = row * bigN + col;
                siteTreeSize[row * bigN + col] = 1;
                siteTreeSize2[row * bigN + col] = 1;
            }
        }
        siteParent[virtualBottom] = virtualBottom;
        siteParent[virtualTop] = virtualTop;
        siteParent2[virtualTop] = virtualTop;
        siteTreeSize[virtualTop] = 1;
        siteTreeSize[virtualBottom] = 1;
        siteTreeSize2[virtualTop] = 1;
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

    // root for isFilled
    private int root2(int p) {
        int temp = p;
        while (siteParent2[temp] != temp) {
            siteParent2[temp] = siteParent2[siteParent2[temp]]; // path compression
            temp = siteParent2[temp];
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

    // connect p and q, for avoiding backwash
    private void union2(int p, int q) {
        int bigP = root2(p);
        int bigQ = root2(q);
        if (bigP == bigQ) return;
        // weighting
        if (siteTreeSize2[bigP] < siteTreeSize2[bigQ]) {
            siteParent2[bigP] = bigQ;
            siteTreeSize2[bigQ] += siteTreeSize2[bigP];
        }
        else {
            siteParent2[bigQ] = bigP;
            siteTreeSize2[bigP] += siteTreeSize2[bigQ];
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
            union2(index, virtualTop);
            if (bigN == 1) {
                union(index, virtualBottom); // extreme case when there is only one site, connect to both virtual sites
            }
            else {
                // left is open
                if (col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
                    union(index, row * bigN + col - 1);
                    union2(index, row * bigN + col - 1);
                }
                // right is open
                if (col + 1 < bigN && isOpen(row + 1, col + 1 + 1)) {
                    union(index, row * bigN + col + 1);
                    union2(index, row * bigN + col + 1);
                }
                // bottom is open
                if (isOpen(row + 1 + 1, col + 1)) {
                    union(index, (row + 1) * bigN + col);
                    union2(index, (row + 1) * bigN + col);
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
                union2(index, (row - 1) * bigN + col);
            }
            // left is open
            if (col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
                union(index, row * bigN + col - 1);
                union2(index, row * bigN + col - 1);
            }
            // right is open
            if (col + 1 < bigN && isOpen(row + 1, col + 1 + 1)) {
                union(index, row * bigN + col + 1);
                union2(index, row * bigN + col + 1);
            }
            // bottom is open
            if (isOpen(row + 1 + 1, col + 1)) {
                union(index, (row + 1) * bigN + col);
                union2(index, (row + 1) * bigN + col);
            }
        }
        /*
         case 3: the site is at bottom, should union() to the virtual bottom
                 still checking it's other 3 directions to see if any site adjacent is open
         */
        else {
            union(index, virtualBottom);
            //DON'T DO union2 to virtual bottom, for the sake of avoiding backwash problem

            // top is open
            if (isOpen(row - 1 + 1, col + 1)) {
                union(index, (row - 1) * bigN + col);
                union2(index, (row - 1) * bigN + col);
            }
            // left is open
            if (col - 1 >= 0 && isOpen(row + 1, col - 1 + 1)) {
                union(index, row * bigN + col - 1);
                union2(index, row * bigN + col - 1);
            }
            // right is open
            if (col + 1 < bigN && isOpen(row + 1, col + 1 + 1)) {
                union(index, row * bigN + col + 1);
                union2(index, row * bigN + col + 1);
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
        return root2(virtualTop) == root2(row * bigN + col);
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
