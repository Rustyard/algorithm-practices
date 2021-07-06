public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    // orient a (temporarily) right-leaning red link to lean left
    private Node rotateLeft(Node high) {
        assert isRed(high.right);
        Node x = high.right;
        high.right = x.left;
        x.left = high;
        x.color = high.color;
        high.color = RED;
        return x; // return higher node after rotation
    }

    // orient a left-leaning red link to (temporarily) lean right
    private Node rotateRight(Node high) {
        assert isRed(high.left);
        Node x = high.left;
        high.left = x.right;
        x.right = high;
        x.color = high.color;
        high.color = RED;
        return x;
    }

    // split a (temporary) 4-node by recoloring
    private void flipColors(Node middle) {
        assert !isRed(middle);
        assert isRed(middle.left);
        assert isRed(middle.right);
        middle.color = RED;
        middle.left.color = BLACK;
        middle.right.color = BLACK;
    }

    private boolean isRed(Node n) {
        if (n == null) return false;
        return n.color;
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

    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, RED);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h); // lean left
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h); // balance 4 node
        if (isRed(h.left) && isRed(h.right)) flipColors(h); // split 4 node

        return h;
    }

    private class Node {
        Key key;
        Value val;
        Node left, right;
        // int count; // number of nodes in subtree
        boolean color; // color of parent link

        public Node(Key key, Value val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }
}
