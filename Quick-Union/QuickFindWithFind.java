public class QuickFindWithFind {
    private int[] id;
    private int[] size;
    private int[] number; // use this to store number of nodes

    public QuickFindWithFind(int N) {
        id = new int[N];
        size = new int[N];
        number = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            number[i] = i;
            size[i] = 1;
        }
    }

    public int root(int p) {
        int temp = p;
        while (id[temp] != temp) {
            id[temp] = id[id[temp]];
            temp = id[temp];
        }
        return temp;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int P = root(p);
        int Q = root(q);
        if (P == Q) return;
        if (size[P] < size[Q]) {
            id[P] = Q; // tree P merged into tree Q
            size[Q] += size[P];
            if (number[P] > number[Q]) {
                int swapTemp = number[Q]; // swap their number, place larger one in the root
                number[Q] = number[P];
                number[P] = swapTemp;
            }
        }
        else {
            id[Q] = P;
            size[P] += size[Q];
            if (number[P] < number[Q]) {
                int swapTemp = number[Q]; // swap their number, place larger one in the root
                number[Q] = number[P];
                number[P] = swapTemp;
            }
        }
    }

    // return the largest number in the component which i is connected to
    public int find(int i) {
        return number[root(i)];
    }

    public static void main(String[] args) {
        QuickFindWithFind test = new QuickFindWithFind(9);
        test.union(0,1);
        System.out.println("largest number connected with 1 is " + test.find(1));
        test.union(3,4);
        System.out.println("largest number connected with 1 is " + test.find(1));
        test.union(2,1);
        System.out.println("largest number connected with 1 is " + test.find(1));
        test.union(4,5);
        System.out.println("largest number connected with 1 is " + test.find(1));
        test.union(2,3);
        System.out.println("largest number connected with 1 is " + test.find(1));
    }
}