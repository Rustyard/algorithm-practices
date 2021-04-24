public class SocialNetwork {
    private int[] member;
    private int[] size;
    private int roots; // the number of roots, if it's 1, then everyone is connected

    public SocialNetwork(int N)
    {
        member = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            member[i] = i;
            size[i] = 1;
            roots = N;
        }
    }

    public int root(int p) {
        int temp = p;
        while (member[temp] != temp) {
            member[temp] = member[member[temp]];
            temp = member[temp];
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
            member[P] = Q;
            size[Q] += size[P];
        }
        else {
            member[Q] = P;
            size[P] += size[Q];
        }
        roots--; // decrease after a successful union()
    }

    public void checkAllConnected() {
        if (roots == 1) System.out.println("Everyone is connected.");
        else System.out.println("Not yet.");
    }

    public static void main(String[] args) {
        SocialNetwork socialNetwork = new SocialNetwork(6);
        socialNetwork.union(0,1); socialNetwork.checkAllConnected();
        socialNetwork.union(3,4); socialNetwork.checkAllConnected();
        socialNetwork.union(0,1); socialNetwork.checkAllConnected();
        socialNetwork.union(2,5); socialNetwork.checkAllConnected();
        socialNetwork.union(5,1); socialNetwork.checkAllConnected();
        socialNetwork.union(2,3); socialNetwork.checkAllConnected();
    }
}
