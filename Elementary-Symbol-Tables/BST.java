import edu.princeton.cs.algs4.Queue;

// binary search tree
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.count;
    }

    // number of keys less than the given key
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); // 1 for root
        else return size(x.left);
    }

    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    // recursive code, read carefully!
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;

        x.count = 1 + size(x.left) + size(x.right); // maintain the size
        return x;
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }
        return null;
    }


    // time: sqrt(N)
    // hibbard deletion, problem: not symmetric, takes sqrt(N) to delete
    // solution: use Red-Black BST
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        // search for the key
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left; // no right child
            if (x.left == null) return x.right; // no left child

            // replace with successor
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        // update counts
        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right); // update the new count (recursively)
        return x;
    }

    // return the largest Key <= Provided Key
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);

        if (cmp == 0) return x; // the key exists

        if (cmp < 0) return floor(x.left, key); // recursive call, search in the left subtree

        Node t = floor(x.right, key); // the key might exist in the right subtree
        if (t != null) return t; // the key exists
        else return x; // the key don't exist, return the largest one that is smaller than the key
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q); // node's left subtree recursive
        q.enqueue(x.key); // enqueue node itself
        inorder(x.right, q); // node's right subtree recursive
    }

    public Node getRoot() {
        return root;
    }

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int count; // number of nodes in subtree

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }
}
