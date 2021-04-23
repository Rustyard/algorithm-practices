public class QuickFindUF {
    private int[] id;
    private int[] size; // size of each tree

    public QuickFindUF(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1; // each tree only has a root node, initial size is 1
        }
    }

// THESE ARE OLD WAYS OF NOT USING TREE STRUCTURE.
//    public boolean connected(int n, int m) {
//        return id[n] == id[m];
//    }
//
//    public void union(int n, int m) {
//        if (!connected(n, m)) {
//            int tempN = id[n];
//            int tempM = id[m];
//            for (int i = 0; i < id.length; i++) {
//                if (id[i] == tempN) {
//                    id[i] = tempM;
//                }
//            }
//        }
//        // debug message
//        System.out.println("connected " + n + " and " + m);
//    }

    // return the root of p
    public int root(int p) {
        int temp = p;
        while (id[temp] != temp) {
            // Improvement 2: path compression, flatten trees the most!
            id[temp] = id[id[temp]]; // take the note and move it "upstairs", until it's root
            temp = id[temp];
        }
        return temp;
    }

    // return if p and q are connected, i.e. have the same root
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    // connect p and q by changing p's root's parent to q's root
    public void union(int p, int q) {
        // OLDER WAY
        // id[root(p)] = root(q); // id[root(q)] is actually identical to root(q), but the latter should be faster?
        int P = root(p);
        int Q = root(q);
        if (P == Q) return;
        // Improvement 1: weighting, make tree's depth no more than lg(N)
        if (size[P] < size[Q]) {
            id[P] = Q;
            size[Q] += size[P];
        }
        else {
            id[Q] = P;
            size[P] += size[Q];
        }
    }


    public static void main(String[] args) {
        QuickFindUF test = new QuickFindUF(9);
        System.out.println(test.connected(0,1));
        test.union(0,1);
        System.out.println(test.connected(0,1));
    }
}
